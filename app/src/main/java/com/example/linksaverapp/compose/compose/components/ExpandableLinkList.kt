package com.example.linksaverapp.compose.compose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linksaverapp.db.Model.LinkModel
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

@Composable
fun ExpandableLinkList(
    folderName: String? = null,
    folderList: MutableList<LinkModel>,
    folderHeaderType: FolderHeaderType,
    number: Int = 0,
    isDeviceUnlocked: MutableState<Boolean>,
    onLinkLongPressed: (String)-> Unit,
    onOptionsPressed: (LinkModel?)-> Unit,
    onLinkClick: (String) -> Unit
) {
    val expandedState = remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding()
    ){
        if(!folderName.isNullOrBlank()){
            item {
                FolderHeader(title = folderName, expandedState = expandedState,  folderHeaderType, number)
            }
        }
        if (expandedState.value) {
            item {
                folderList.forEach {
                    if(it.isProtected == 0 || isDeviceUnlocked.value) {
                        LinkCard(
                            it.name,
                            it.link,
                            onLinkLongPressed = { onLinkLongPressed.invoke(it.link) },
                            onClickLink = { onLinkClick(it.link) },
                            onOptionsPressed = {onOptionsPressed(it)})
                    }
                }
            }
        }
        item {
            Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultExpandableListPreview() {
    LinkSaverAppTheme {
        ExpandableLinkList(folderName = "Favoritos",
            mutableListOf(
                LinkModel(name= "Buzzfeed"),
            ), FolderHeaderType.Favorite, 0, remember { mutableStateOf(false) }, {}, {}, {}
        )
    }
}