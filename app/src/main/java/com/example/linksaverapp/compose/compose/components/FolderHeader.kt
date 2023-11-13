package com.example.linksaverapp.compose.compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

@Composable
fun FolderHeader(title: String, expandedState: MutableState<Boolean>) {
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState.value) 180f else 0f, label = ""
    )
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier
                .weight(6f),
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        IconButton(
            modifier = Modifier
                .weight(1f)
                .rotate(rotationState),
            onClick = {
                expandedState.value = !expandedState.value
            }) {
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Drop-Down Arrow"
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultExpandableHeaderPreview() {
    LinkSaverAppTheme {
        LazyColumn(){
            item{
                FolderHeader(title = "Titulo", expandedState = remember{ mutableStateOf(false) })
            }
        }
    }
}