package com.example.linksaverapp.compose.compose.components

import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

@Composable
fun AlertDialogAddToFolder(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit) {
    
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultAlertPreview() {
    LinkSaverAppTheme {
        AlertDialogAddToFolder(
            onDismissRequest = { },
            onConfirmation = {}
        )
    }
}