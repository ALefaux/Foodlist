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
import io.github.alefaux.foodlist.core.model.extension.toDisplayString
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
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
            text = "Edit Product",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = editedName,
            onValueChange = { editedName = it },
            label = { Text("Name") },
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = editedQuantity,
            onValueChange = { editedQuantity = it },
            label = { Text("Quantity") },
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = editedCategory,
            onValueChange = { editedCategory = it },
            label = { Text("Category") },
            singleLine = true
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = editedExpirationDate?.toDisplayString().orEmpty(),
            onValueChange = {},
            label = { Text("Expiration date") },
            singleLine = true,
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { isDatePickerVisible = true }) {
                    Icon(imageVector = Icons.Filled.Event, contentDescription = "Pick expiration date")
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
                Text("Cancel")
            }
            Button(
                modifier = Modifier.weight(1f),
                enabled = editedName.isNotBlank(),
                onClick = {
                    onSave(editedName, editedQuantity, editedCategory, editedExpirationDate)
                }
            ) {
                Text("Save")
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
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { isDatePickerVisible = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

private fun LocalDate.atStartOfDayUtcMillis(): Long =
    this.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
