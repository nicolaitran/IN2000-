package com.example.nntran_oblig2.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import com.example.nntran_oblig2.R
import com.example.nntran_oblig2.viewModel.AlpacaViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenu(alpacaViewModel : AlpacaViewModel) {

    val options = stringArrayResource(R.array.ValgListe)
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text("Alpaca Distrikt") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        alpacaViewModel.updateDistrict(selectionOption)
                    }
                ) {
                    Text(text = selectionOption)
                }
            }
        }
    }

}