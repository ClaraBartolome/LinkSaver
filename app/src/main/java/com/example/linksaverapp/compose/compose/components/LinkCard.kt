package com.example.linksaverapp.compose.compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme
import com.example.linksaverapp.ui.theme.lightGreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LinkCard(
    title: String = "",
    link: String = "",
    onLinkLongPressed: () -> Unit,
    onClickLink: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxSize()
            .combinedClickable(
                onClick = onClickLink,
                onLongClick = onLinkLongPressed
            ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = lightGreen,
        )
    ) {
        Column {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding( top = 8.dp, start = 16.dp, end = 16.dp, bottom = 4.dp)
            )
            Text(
                text = link,
                maxLines = 1,
                style = MaterialTheme.typography.subtitle2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    LinkSaverAppTheme {
        LazyColumn() {
            items(5) {
                LinkCard("Name", "https://stackoverflow.com/questions/68626940/customise-focussed-indicator-line-android-compose-textfield", {}, {})
            }
        }
    }
}