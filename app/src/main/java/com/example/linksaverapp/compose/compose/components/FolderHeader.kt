package com.example.linksaverapp.compose.compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaverapp.R
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme
import com.example.linksaverapp.ui.theme.softPink

@Composable
fun FolderHeader(title: String, expandedState: MutableState<Boolean>, type: FolderHeaderType, number: Int = 0) {
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState.value) 0f else 90f, label = ""
    )
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
            .fillMaxSize()
            .clickable { expandedState.value = !expandedState.value }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start, modifier = Modifier.weight(1f)) {
            Image(
                painter = painterResource(id = getIcon(type)),
                contentDescription = "",
                modifier = Modifier.padding(start = 8.dp),
                colorFilter = ColorFilter.tint(color = if(type == FolderHeaderType.Favorite) softPink else getColor())
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                text = title,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End, modifier = Modifier.weight(1f)){
            if(type == FolderHeaderType.Favorite){
                Text(
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    text = "$number/5",
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                modifier = Modifier
                    .rotate(rotationState),
                onClick = {expandedState.value = !expandedState.value}) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Drop-Down Arrow"
                )
            }
        }
    }
}

@Composable
private fun getColor(): Color {
    return if (isSystemInDarkTheme()) {
        Color.White
    } else Color.Black
}

private fun getIcon(name: FolderHeaderType): Int{
    return if(name == FolderHeaderType.Favorite){
        R.drawable.ic_favorite
    }else{
        R.drawable.ic_folder
    }
}

enum class FolderHeaderType {
    Normal,
    Favorite
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultExpandableHeaderPreview() {
    LinkSaverAppTheme {
        LazyColumn(){
            item{
                FolderHeader(title = "Titulo", expandedState = remember{ mutableStateOf(false) }, FolderHeaderType.Favorite)
            }
        }
    }
}