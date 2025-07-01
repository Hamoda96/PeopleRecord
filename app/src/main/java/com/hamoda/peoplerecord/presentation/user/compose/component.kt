@file:OptIn(ExperimentalMaterial3Api::class)

package com.hamoda.peoplerecord.presentation.user.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.hamoda.peoplerecord.R

@Composable
fun UserImageHeader() {
    Image(
        painter = painterResource(id = R.drawable.outline_person),
        contentDescription = "Logo"
    )
}

@Composable
fun ValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    errorMessage: String?,
    hasSaveUser: Boolean,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = errorMessage != null && hasSaveUser,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth()
        )
        if (errorMessage != null && hasSaveUser) {
            Text(
                errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}


@Composable
fun GenderDropdown(
    gender: String,
    onGenderSelected: (String) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    options: List<String>,
    hasSaveUser: Boolean,
    errorMessage: String?
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                value = gender,
                onValueChange = {},
                readOnly = true,
                label = { Text("Gender") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                isError = errorMessage != null && hasSaveUser,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onGenderSelected(option)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
        if (errorMessage != null && hasSaveUser) {
            Text(
                errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Composable
fun SaveUserButton(
    isAdding: Boolean,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        enabled = !isAdding
    ) {
        Text(text = if (isAdding) "Saving..." else "Save")
    }
}

@Composable
fun SkipButton(
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(id = R.string.skip))
    }
}
