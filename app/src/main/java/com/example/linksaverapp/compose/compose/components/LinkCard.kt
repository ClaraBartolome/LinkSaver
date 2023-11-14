package com.example.linksaverapp.compose.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme
import com.example.linksaverapp.ui.theme.lightGreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LinkCard(title: String = "", onLinkLongPressed: ()-> Unit, onClickLink: ()-> Unit){
    Box(modifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .fillMaxSize()
        .background(lightGreen, RoundedCornerShape(10.dp))
        .combinedClickable(
            onClick = onClickLink,
            onLongClick = onLinkLongPressed
        )
        ) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(16.dp)
            )
        }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LinkSaverAppTheme {
        LazyColumn() {
            items(5){
                LinkCard("", {}, {})
            }
        }
    }
}