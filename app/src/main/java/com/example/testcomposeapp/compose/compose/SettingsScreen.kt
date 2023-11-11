package com.example.testcomposeapp.compose.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme

@Composable
fun Settings() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Settings")
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddLinkScreenPreview() {
    TestComposeAppTheme {
        Settings()
    }
}