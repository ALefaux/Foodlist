package io.github.alefaux.foodlist.feature.scan.camera

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun BarcodeScannerView(
    onBarcodeDetected: (String) -> Unit,
    onPermissionDenied: () -> Unit,
    modifier: Modifier = Modifier,
    torchEnabled: Boolean = false
)
