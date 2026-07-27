package io.github.alefaux.foodlist.feature.dashboard.ui.menu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconButtonShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

object ActionButton {
    @Composable
    fun Primary(
        icon: ImageVector,
        title: String,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        ActionButtonImpl(
            modifier = modifier,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            icon = icon,
            title = title,
            onClick = onClick
        )
    }

    @Composable
    fun Secondary(
        icon: ImageVector,
        title: String,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        ActionButtonImpl(
            modifier = modifier,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ),
            icon = icon,
            title = title,
            onClick = onClick
        )
    }

    @Composable
    fun Tertiary(
        icon: ImageVector,
        title: String,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        ActionButtonImpl(
            modifier = modifier,
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            icon = icon,
            title = title,
            onClick = onClick
        )
    }

    @Composable
    private fun ActionButtonImpl(
        icon: ImageVector,
        title: String,
        modifier: Modifier = Modifier,
        colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors(),
        onClick: () -> Unit
    ) {
        Column(
            modifier = modifier
                .clickable(
                    onClick = onClick
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            FilledIconButton(
                colors = colors,
                content = {
                    Icon(
                        contentDescription = null,
                        imageVector = icon
                    )
                },
                shapes = IconButtonShapes(
                    shape = RoundedCornerShape(8.dp)
                ),
                onClick = {}
            )
            Text(
                style = MaterialTheme.typography.labelSmall,
                text = title
            )
        }
    }
}

@Preview
@Composable
private fun ActionButtonPreview() {
    MaterialTheme {
        ActionButton.Primary(
            icon = Icons.Default.PhotoCamera,
            title = "Scan"
        ) {}
    }
}