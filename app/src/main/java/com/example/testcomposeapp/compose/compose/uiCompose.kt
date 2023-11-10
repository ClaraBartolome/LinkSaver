package com.example.testcomposeapp.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.testcomposeapp.compose.UiComposeViewhelper.testURL
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme
import com.example.testcomposeapp.compose.compose.linkCard
import com.example.testcomposeapp.compose.topAppBar.topAppBar

class UiCompose {

    @Composable
    fun createUI() {
        topAppBar()
    }

    @Preview(showBackground = true, showSystemUi = true)
    @Composable
    fun DefaultPreview() {
        TestComposeAppTheme {
            createUI()
        }
    }

}