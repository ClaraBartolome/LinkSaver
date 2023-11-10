package com.example.testcomposeapp.compose.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcomposeapp.Utils.longText
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme
import com.example.testcomposeapp.ui.theme.lightGreen

@Composable
fun linkCard(title: String = ""){
    Box(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxSize()
        .background(lightGreen, RoundedCornerShape(10.dp))
        .padding(16.dp)
        ) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
            )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    TestComposeAppTheme {
        LazyColumn() {
            items(5){
                linkCard()
            }
        }
    }
}