package io.github.alefaux.foodlist.feature.scan.camera

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import io.github.alefaux.foodlist.core.logging.AppLogging
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.AVAuthorizationStatus
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureConnection
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVCaptureDeviceInput
import platform.AVFoundation.AVCaptureMetadataOutput
import platform.AVFoundation.AVCaptureMetadataOutputObjectsDelegateProtocol
import platform.AVFoundation.AVCaptureOutput
import platform.AVFoundation.AVCaptureSession
import platform.AVFoundation.AVCaptureTorchModeOff
import platform.AVFoundation.AVCaptureTorchModeOn
import platform.AVFoundation.AVCaptureVideoPreviewLayer
import platform.AVFoundation.AVLayerVideoGravityResizeAspectFill
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.AVMetadataMachineReadableCodeObject
import platform.AVFoundation.AVMetadataObjectTypeCode128Code
import platform.AVFoundation.AVMetadataObjectTypeEAN13Code
import platform.AVFoundation.AVMetadataObjectTypeEAN8Code
import platform.AVFoundation.AVMetadataObjectTypeUPCECode
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.hasTorch
import platform.AVFoundation.requestAccessForMediaType
import platform.AVFoundation.torchMode
import platform.CoreGraphics.CGRectMake
import platform.QuartzCore.CATransaction
import platform.UIKit.UIView
import platform.darwin.NSObject
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_global_queue
import platform.darwin.dispatch_get_main_queue

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun BarcodeScannerView(
    onBarcodeDetected: (String) -> Unit,
    onPermissionDenied: () -> Unit,
    modifier: Modifier,
    torchEnabled: Boolean
) {
    var authorizationStatus by remember {
        mutableStateOf(AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo))
    }

    LaunchedEffect(Unit) {
        when (authorizationStatus) {
            AVAuthorizationStatusNotDetermined -> {
                AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
                    dispatch_async(dispatch_get_main_queue()) {
                        authorizationStatus = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)
                        if (!granted) onPermissionDenied()
                    }
                }
            }

            AVAuthorizationStatusAuthorized -> Unit
            else -> onPermissionDenied()
        }
    }

    if (authorizationStatus != AVAuthorizationStatusAuthorized) return

    val session = remember { AVCaptureSession() }
    val previewLayer = remember { AVCaptureVideoPreviewLayer(session = session) }
    val delegate = remember { BarcodeDetectionDelegate(onBarcodeDetected) }

    DisposableEffect(session, delegate) {
        configureSession(session, delegate)
        dispatch_async(dispatch_get_global_queue(0, 0uL)) {
            session.startRunning()
        }

        onDispose {
            dispatch_async(dispatch_get_global_queue(0, 0uL)) {
                session.stopRunning()
            }
        }
    }

    LaunchedEffect(torchEnabled) {
        setTorchEnabled(torchEnabled)
    }

    UIKitView(
        factory = {
            val container = UIView(frame = CGRectMake(0.0, 0.0, 0.0, 0.0))
            previewLayer.videoGravity = AVLayerVideoGravityResizeAspectFill
            container.layer.addSublayer(previewLayer)
            container
        },
        modifier = modifier,
        update = { view ->
            CATransaction.begin()
            CATransaction.setDisableActions(true)
            previewLayer.setFrame(view.bounds)
            CATransaction.commit()
        }
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun configureSession(session: AVCaptureSession, delegate: BarcodeDetectionDelegate) {
    val device = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo) ?: return

    session.beginConfiguration()

    runCatching {
        val input = AVCaptureDeviceInput.deviceInputWithDevice(device, null)
        input?.let {
            if (session.canAddInput(it)) session.addInput(it)
        }
    }.onFailure { error ->
        AppLogging.e(Exception(error), "Couldn't create camera input")
    }

    val metadataOutput = AVCaptureMetadataOutput()
    if (session.canAddOutput(metadataOutput)) {
        session.addOutput(metadataOutput)
        metadataOutput.setMetadataObjectsDelegate(
            delegate,
            queue = dispatch_get_main_queue()
        )
        metadataOutput.metadataObjectTypes = listOf(
            AVMetadataObjectTypeEAN13Code,
            AVMetadataObjectTypeEAN8Code,
            AVMetadataObjectTypeUPCECode,
            AVMetadataObjectTypeCode128Code
        )
    }

    session.commitConfiguration()
}

@OptIn(ExperimentalForeignApi::class)
private fun setTorchEnabled(enabled: Boolean) {
    val device = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo) ?: return
    if (!device.hasTorch) return

    runCatching {
        device.lockForConfiguration(null)
        device.torchMode = if (enabled) AVCaptureTorchModeOn else AVCaptureTorchModeOff
        device.unlockForConfiguration()
    }.onFailure { error ->
        AppLogging.e(Exception(error), "Couldn't toggle torch")
    }
}

@OptIn(ExperimentalForeignApi::class)
private class BarcodeDetectionDelegate(
    private val onBarcodeDetected: (String) -> Unit
) : NSObject(), AVCaptureMetadataOutputObjectsDelegateProtocol {

    override fun captureOutput(
        output: AVCaptureOutput,
        didOutputMetadataObjects: List<*>,
        fromConnection: AVCaptureConnection
    ) {
        didOutputMetadataObjects
            .filterIsInstance<AVMetadataMachineReadableCodeObject>()
            .firstNotNullOfOrNull { it.stringValue }
            ?.let(onBarcodeDetected)
    }
}
