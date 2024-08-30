package com.example.linksaverapp.compose.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.example.linksaverapp.R
import com.example.linksaverapp.Utils.SortRadioOptions
import com.example.linksaverapp.ui.theme.toolbarLightGreen

@Composable
fun SortScreen(selectedOption: MutableState<SortRadioOptions>, radioOptions: List<SortRadioOptions>) {
// Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(Modifier.selectableGroup()) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(id = R.string.sort_by)
        )
        radioOptions.forEach { option ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = (option == selectedOption.value),
                        onClick = { selectedOption.value = option },
                        role = Role.RadioButton
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = (option == selectedOption.value),
                    onClick = null, // null recommended for accessibility with screenreaders
                    colors = RadioButtonDefaults.colors(
                        selectedColor = toolbarLightGreen,
                    )
                )
                Text(
                    text = stringResource(id = option.text),
                )
            }
        }
    }
}