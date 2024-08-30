package com.example.linksaverapp.compose.compose.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.linksaverapp.R
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

@Composable
fun AlertDialogCustom(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSecondaryContainer)
        },
        text = {
            Text(text = dialogText, color = MaterialTheme.colorScheme.onSecondaryContainer)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(id = R.string.confirm), color = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(id = R.string.cancel), color = MaterialTheme.colorScheme.onSecondaryContainer)
            }
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultAlertPreview() {
    LinkSaverAppTheme {
        AlertDialogCustom(
            onDismissRequest = { },
            onConfirmation = {},
            dialogTitle = "No has guardado los cambios",
            dialogText = "¿Estás seguro de que quieres salir?"
        )
    }
}