package com.example.linksaverapp.compose.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linksaverapp.db.Model.LinkModel
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme
import com.example.linksaverapp.ui.theme.whiteAlfaLow

@Composable
fun SearchBar(
    text: MutableState<String>,
    onSearchInit: () -> List<LinkModel>,
    onClickOnSearched: (String) -> Unit,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
        LazyColumn() {
            item {
                SearchBarConfig(
                    text = text,
                    onTextChange = {
                        onTextChange.invoke(it)
                        expanded.value = true
                                   },
                    onCloseClicked = onCloseClicked
                )
            }
            item {
                AnimatedVisibility(visible = expanded.value) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RectangleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ) {
                        LazyColumn(
                            modifier = Modifier.heightIn(max = 150.dp),
                        ) {
                            items(onSearchInit.invoke()){
                                ItemSearchSuggestion(linkName = it.name, link = it.link, onClick = onClickOnSearched)
                            }
                        }
                    }
                }
            }
        }
}

@Composable
private fun SearchBarConfig(
    text: MutableState<String>,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth(),
        tonalElevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colorScheme.primary
    ) {
        TextField(
            value = text.value,
            onValueChange = {
                onTextChange.invoke(it)
            },
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium),
                    enabled = false,
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = Color.White
                    )
                }
            },
            trailingIcon = {
                IconButton(
                    onClick = {
                        if (text.value.isNotEmpty()) {
                            onTextChange.invoke("")
                        } else {
                            onCloseClicked.invoke()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close Icon",
                        tint = Color.White
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = whiteAlfaLow,
                unfocusedContainerColor = whiteAlfaLow,
                disabledContainerColor = whiteAlfaLow,
                cursorColor = MaterialTheme.colorScheme.primary,
                disabledIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                disabledTextColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 4.dp)
                .border(1.dp, Color.White, CircleShape),
            shape = CircleShape,
            textStyle = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ItemSearchSuggestion(linkName: String, link: String, onClick: (String) -> Unit) {
    Column(modifier = Modifier
        .clickable { onClick(link) }) {
        Text(
            linkName,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp)
        )
        Text(
            text = link,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp, horizontal = 8.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun SearchAppBarPreview() {
    LinkSaverAppTheme {
        SearchBar(
            text = remember { mutableStateOf("Some random text y") },
            onSearchInit = { listOf() },
            onTextChange = {},
            onClickOnSearched = {},
            onCloseClicked = {}
        )
    }
}

@Composable
@Preview
private fun ItemSearchPreview() {
    LinkSaverAppTheme {
        ItemSearchSuggestion(
            linkName = "A name",
            link = "https://stackoverflow.com/questions/68626940/customise-focussed-indicator-line-android-compose-textfield",
            onClick = {}
        )
    }
}