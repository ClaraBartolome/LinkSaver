package com.example.linksaverapp.compose.compose

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {
    AlertDialog(
        title = {
            Text(text = dialogTitle, fontSize = 20.sp)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultAlertPreview() {
    LinkSaverAppTheme {
        AlertDialog(
            onDismissRequest = { },
            onConfirmation = {},
            dialogTitle = "No has guardado los cambios",
            dialogText = "¿Estás seguro de que quieres salir?"
        )
    }
}