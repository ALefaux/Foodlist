package io.github.alefaux.foodlist.feature.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.profile_debug_create_test_data_button
import foodlist.app.shared.generated.resources.profile_debug_delete_test_data_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun DebugActions(
    onCreateTestDataClick: () -> Unit,
    onDeleteTestDataClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        DebugActionButton(
            text = stringResource(Res.string.profile_debug_create_test_data_button),
            icon = Icons.Filled.BugReport,
            onClick = onCreateTestDataClick
        )
        DebugActionButton(
            text = stringResource(Res.string.profile_debug_delete_test_data_button),
            icon = Icons.Filled.DeleteSweep,
            onClick = onDeleteTestDataClick
        )
    }
}
