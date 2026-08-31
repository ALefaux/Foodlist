package io.github.alefaux.foodlist.feature.scan.pane

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.scan_align_barcode
import foodlist.app.shared.generated.resources.scan_camera_permission_message
import foodlist.app.shared.generated.resources.scan_checking_connection
import foodlist.app.shared.generated.resources.scan_looking_up_product
import foodlist.app.shared.generated.resources.scan_no_internet_message
import foodlist.app.shared.generated.resources.scan_retry_button
import io.github.alefaux.foodlist.feature.scan.camera.BarcodeScannerView
import io.github.alefaux.foodlist.feature.scan.presentation.model.ScanUiState
import io.github.alefaux.foodlist.feature.scan.ui.ManualEntryButton
import io.github.alefaux.foodlist.feature.scan.ui.ProductNotFoundSheet
import io.github.alefaux.foodlist.feature.scan.ui.ScannedProductSheet
import io.github.alefaux.foodlist.feature.scan.ui.ScannerViewfinder
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanPane(
    state: ScanUiState,
    torchEnabled: Boolean,
    onBarcodeDetected: (String) -> Unit,
    onPermissionDenied: () -> Unit,
    onManualEntryClick: () -> Unit,
    onCancel: () -> Unit,
    onAddToPantry: () -> Unit,
    onRetryConnectivity: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shouldShowCamera = state != ScanUiState.PermissionDenied &&
        state != ScanUiState.CheckingConnectivity &&
        state != ScanUiState.NoNetwork

    Box(modifier = modifier.fillMaxSize()) {
        if (shouldShowCamera) {
            BarcodeScannerView(
                modifier = Modifier.fillMaxSize(),
                torchEnabled = torchEnabled,
                onBarcodeDetected = onBarcodeDetected,
                onPermissionDenied = onPermissionDenied
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.35f))
        )

        when (state) {
            ScanUiState.CheckingConnectivity -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = Color.White)
                    Text(
                        text = stringResource(Res.string.scan_checking_connection),
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            ScanUiState.NoNetwork -> {
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.scan_no_internet_message),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Button(
                        onClick = onRetryConnectivity,
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text(stringResource(Res.string.scan_retry_button))
                    }
                }

                ManualEntryButton(
                    onClick = onManualEntryClick,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 40.dp)
                )
            }

            ScanUiState.PermissionDenied -> {
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(Res.string.scan_camera_permission_message),
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                ManualEntryButton(
                    onClick = onManualEntryClick,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 40.dp)
                )
            }

            else -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ScannerViewfinder(
                        isActive = state == ScanUiState.Scanning || state is ScanUiState.LookingUp,
                        modifier = Modifier
                            .fillMaxWidth(0.75f)
                            .aspectRatio(1.6f)
                    )

                    Text(
                        text = if (state is ScanUiState.LookingUp) {
                            stringResource(Res.string.scan_looking_up_product)
                        } else {
                            stringResource(Res.string.scan_align_barcode)
                        },
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }

                ManualEntryButton(
                    onClick = onManualEntryClick,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 40.dp)
                )
            }
        }
    }

    if (state is ScanUiState.ProductFound) {
        ModalBottomSheet(
            onDismissRequest = { if (!state.isSaving) onCancel() },
            sheetState = rememberModalBottomSheetState()
        ) {
            ScannedProductSheet(
                product = state.product,
                onCancel = onCancel,
                onAddToPantry = onAddToPantry,
                isSaving = state.isSaving,
                saveError = state.saveError
            )
        }
    }

    if (state is ScanUiState.ProductNotFound) {
        ModalBottomSheet(
            onDismissRequest = onCancel,
            sheetState = rememberModalBottomSheetState()
        ) {
            ProductNotFoundSheet(
                ean = state.ean,
                onTryAgain = onCancel,
                onManualEntryClick = onManualEntryClick
            )
        }
    }
}
