package io.github.alefaux.foodlist.feature.productdetail.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.common_cancel
import foodlist.app.shared.generated.resources.common_name_label
import foodlist.app.shared.generated.resources.common_ok
import foodlist.app.shared.generated.resources.common_save
import foodlist.app.shared.generated.resources.edit_product_category_label
import foodlist.app.shared.generated.resources.edit_product_expiration_date_label
import foodlist.app.shared.generated.resources.edit_product_pick_date_content_description
import foodlist.app.shared.generated.resources.edit_product_quantity_label
import foodlist.app.shared.generated.resources.edit_product_title
import io.github.alefaux.foodlist.core.model.extension.toDisplayString
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Instant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductSheet(
    name: String,
    quantity: String,
    category: String,
    expirationDate: LocalDate?,
    onDismiss: () -> Unit,
    onSave: (name: String, quantity: String, category: String, expirationDate: LocalDate?) -> Unit,
    modifier: Modifier = Modifier
) {
    var editedName by remember { mutableStateOf(name) }
    var editedQuantity by remember { mutableStateOf(quantity) }
    var editedCategory by remember { mutableStateOf(category) }
    var editedExpirationDate by remember { mutableStateOf(expirationDate) }
    var isDatePickerVisible by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = stringResource(Res.string.edit_product_title),
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = editedName,
            onValueChange = { editedName = it },
            label = { Text(stringResource(Res.string.common_name_label)) },
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = editedQuantity,
            onValueChange = { editedQuantity = it },
            label = { Text(stringResource(Res.string.edit_product_quantity_label)) },
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = editedCategory,
            onValueChange = { editedCategory = it },
            label = { Text(stringResource(Res.string.edit_product_category_label)) },
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = editedExpirationDate?.toDisplayString().orEmpty(),
            onValueChange = {},
            label = { Text(stringResource(Res.string.edit_product_expiration_date_label)) },
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { isDatePickerVisible = true }) {
                    Icon(
                        imageVector = Icons.Filled.Event,
                        contentDescription = stringResource(Res.string.edit_product_pick_date_content_description)
                    )
                }
            }
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                modifier = Modifier.weight(1f),
                onClick = onDismiss
            ) {
                Text(stringResource(Res.string.common_cancel))
            }
            Button(
                modifier = Modifier.weight(1f),
                enabled = editedName.isNotBlank(),
                onClick = {
                    onSave(editedName, editedQuantity, editedCategory, editedExpirationDate)
                }
            ) {
                Text(stringResource(Res.string.common_save))
            }
        }
    }

    if (isDatePickerVisible) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = editedExpirationDate
                ?.atStartOfDayUtcMillis()
        )

        DatePickerDialog(
            onDismissRequest = { isDatePickerVisible = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            editedExpirationDate = Instant.fromEpochMilliseconds(millis)
                                .toLocalDateTime(TimeZone.UTC)
                                .date
                        }
                        isDatePickerVisible = false
                    }
                ) {
                    Text(stringResource(Res.string.common_ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { isDatePickerVisible = false }) {
                    Text(stringResource(Res.string.common_cancel))
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

private fun LocalDate.atStartOfDayUtcMillis(): Long =
    this.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
