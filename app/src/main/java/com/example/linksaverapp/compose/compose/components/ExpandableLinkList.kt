package com.example.linksaverapp.compose.compose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.linksaverapp.db.Model.FolderList
import com.example.linksaverapp.db.Model.LinkModel
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

@Composable
fun ExpandableLinkList(
    folderName: String? = null,
    folderList: MutableList<LinkModel>
) {
    val expandedState = remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
    ){
        if(!folderName.isNullOrBlank()){
            item {
                FolderHeader(title = folderName, expandedState = expandedState)
            }
        }
        if (expandedState.value) {
            item {
                LazyColumn(){
                    items(folderList){
                        LinkCard(it.name) {}
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultExpandableListPreview() {
    LinkSaverAppTheme {
        ExpandableLinkList(folderName = "Favoritos",
            mutableListOf(
                LinkModel(name= "Buzzfeed")
            )
        )
    }
}