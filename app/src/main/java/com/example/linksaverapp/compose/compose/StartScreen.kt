package com.example.linksaverapp.compose.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.linksaverapp.db.Model.LinkModel

@Composable
fun ScrollContent(
    allLinks: State<List<LinkModel>?>,
    openBottomSheet: MutableState<Boolean>,
    onDeleteLink: @Composable() (LinkModel) -> Unit
) {
    val linkId = remember { mutableStateOf(0) }
    val linkName = remember { mutableStateOf("") }
    val linkText = remember { mutableStateOf("") }
    val linkDateOg = remember { mutableStateOf("") }
    val linkDateMod = remember { mutableStateOf("") }
    val linkFolder = remember { mutableStateOf<String?>(null) }
    val linkProtected = remember { mutableStateOf(0) }
    LazyColumn {
        allLinks.value?.let {
            items(it) {
                LinkCard(it.name) {
                    openBottomSheet.value = true
                    linkId.value = it.id
                    linkName.value = it.name
                    linkText.value = it.link
                    linkDateOg.value = it.dateOfCreation
                    linkDateMod.value = it.dateOfModified
                    linkFolder.value = it.folder
                    linkProtected.value = it.isProtected
                }
            }
        }
    }

    if (openBottomSheet.value) {
        LinkActionsSheet(onDismissSheet = {
            openBottomSheet.value = false
        }, onDeleteLink = { onDeleteLink.invoke(LinkModel(linkId.value, linkName.value, linkText.value, linkDateOg.value, linkDateMod.value, linkFolder.value,linkProtected.value )) })
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun LinkActionsSheet(
    onDismissSheet: () -> Unit,
    onDeleteLink: @Composable() () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = {
            onDismissSheet.invoke()
        },
        containerColor =
        if (isSystemInDarkTheme()) {
            Color.DarkGray
        } else Color.White

    ) {
        // Sheet content
        BottomBarConfig(onDeleteLink)
        Spacer(Modifier.height(64.dp))
    }
}