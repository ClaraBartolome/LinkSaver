package com.example.linksaverapp.compose.compose

import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.linksaverapp.Utils.favoritesStringID
import com.example.linksaverapp.compose.compose.components.AlertDialogAddToFolder
import com.example.linksaverapp.compose.compose.components.ExpandableLinkList
import com.example.linksaverapp.compose.compose.components.FolderHeaderType
import com.example.linksaverapp.compose.compose.components.LinkCard
import com.example.linksaverapp.db.Model.LinkModel

@Composable
fun StartScreen(
    allLinks: State<List<LinkModel>?>,
    folderMap: MutableMap<String, MutableList<LinkModel>>,
    isBottomSheetOpen: MutableState<Boolean>,
    isAlertAddFolderOpen: MutableState<Boolean>,
    onDeleteLink: @Composable() (LinkModel) -> Unit,
    onEditLink: @Composable() (LinkModel) -> Unit,
    onAddFavLink: @Composable() (LinkModel) -> Unit,
    folderNameValid: MutableState<Boolean>,
    onShareLink: (String, String) -> Unit,
    onClickAction: (String) -> Unit
) {
    val linkId = remember { mutableStateOf(0) }
    val linkName = remember { mutableStateOf("") }
    val linkText = remember { mutableStateOf("") }
    val linkDateOg = remember { mutableStateOf("") }
    val linkDateMod = remember { mutableStateOf("") }
    val linkFolder = remember { mutableStateOf("") }
    val linkProtected = remember { mutableStateOf(0) }

    val addLinktoFolder = remember { mutableStateOf(false) }
    allLinks.value?.let {
        Column {
            ExpandableLinkList(folderName = stringResource(id = favoritesStringID),
                folderList = folderMap[stringResource(id = favoritesStringID)]?: mutableListOf(),
                FolderHeaderType.Favorite,
                number = folderMap[stringResource(id = favoritesStringID)]?.size ?: 0,
                onLinklongPressed = {
                isBottomSheetOpen.value = true
                it?.let {
                    linkId.value = it.id
                    linkName.value = it.name
                    linkText.value = it.link
                    linkDateOg.value = it.dateOfCreation
                    linkDateMod.value = it.dateOfModified
                    linkFolder.value = it.folder.toString()
                    linkProtected.value = it.isProtected
                }
            }, onLinkClick = { url -> onClickAction.invoke(url) })

            for ((key, value) in folderMap.minus(stringResource(id = favoritesStringID))) {
                ExpandableLinkList(folderName = key, folderList = value, FolderHeaderType.Normal, onLinklongPressed = {
                    isBottomSheetOpen.value = true
                    it?.let {
                        linkId.value = it.id
                        linkName.value = it.name
                        linkText.value = it.link
                        linkDateOg.value = it.dateOfCreation
                        linkDateMod.value = it.dateOfModified
                        linkFolder.value = it.folder.toString()
                        linkProtected.value = it.isProtected
                    }
                }, onLinkClick = { url -> onClickAction.invoke(url) })
            }
            if (folderMap.containsKey("") && folderMap[""] != null) {
                folderMap[""]?.let { list ->
                    LazyColumn() {
                        items(list) {
                            LinkCard(it.name, it.link, onLinkLongPressed = {
                                isBottomSheetOpen.value = true
                                linkId.value = it.id
                                linkName.value = it.name
                                linkText.value = it.link
                                linkDateOg.value = it.dateOfCreation
                                linkDateMod.value = it.dateOfModified
                                linkFolder.value = it.folder.toString()
                                linkProtected.value = it.isProtected
                            }, onClickLink = { onClickAction.invoke(it.link) })
                        }
                    }
                }
            }
        }
    }

    if (isBottomSheetOpen.value) {
        LinkActionsSheet(
            linkFolder.value.equals(stringResource(id = favoritesStringID)),
            onDismissSheet = {
                isBottomSheetOpen.value = false
            },
            onDeleteLink = {
                onDeleteLink.invoke(
                    LinkModel(
                        linkId.value,
                        linkName.value,
                        linkText.value,
                        linkDateOg.value,
                        linkDateMod.value,
                        linkFolder.value,
                        linkProtected.value
                    )
                )
            },
            onEditLink = { onEditLink.invoke(
                LinkModel(
                    linkId.value,
                    linkName.value,
                    linkText.value,
                    linkDateOg.value,
                    linkDateMod.value,
                    linkFolder.value,
                    linkProtected.value
                )
            ) },
            onShareLink = {
                onShareLink.invoke(linkName.value, linkText.value)
            },
            onAddFavLink = {
                onAddFavLink.invoke(
                    LinkModel(
                        linkId.value,
                        linkName.value,
                        linkText.value,
                        linkDateOg.value,
                        linkDateMod.value,
                        if(linkFolder.value.equals(stringResource(id = favoritesStringID))) "" else stringResource(id = favoritesStringID),
                        linkProtected.value
                    )
                )
                isBottomSheetOpen.value = false
                folderToast(isFavorite = linkFolder.value.equals(stringResource(id = favoritesStringID)), isFolderNameValid = folderNameValid.value)
            },
            onAddtoFolder = {
                isAlertAddFolderOpen.value = true
                isBottomSheetOpen.value = false
            })
    }
    
    if(isAlertAddFolderOpen.value){
        AlertDialogAddToFolder(
            folderName = linkFolder,
            folderNameValid = folderNameValid,
            folderList = folderMap.keys,
            onDismissRequest = { isAlertAddFolderOpen.value = false},
            onConfirmation =  {
                addLinktoFolder.value = true
            })
    }

    if(addLinktoFolder.value){
        onAddFavLink.invoke(
            LinkModel(
                linkId.value,
                linkName.value,
                linkText.value,
                linkDateOg.value,
                linkDateMod.value,
                linkFolder.value,
                linkProtected.value
            )
        )
        if(folderNameValid.value){
            addLinktoFolder.value = false
            isAlertAddFolderOpen.value = false
        }
    }
}

@Composable
private fun folderToast(isFavorite: Boolean, isFolderNameValid: Boolean): Boolean{
    val toastText= if(isFavorite) "Enlace retirado de Favoritos" else if(isFolderNameValid) "Enlace añadido a Favoritos"  else "No puedes añadir más enlaces a Favoritos"
    Toast.makeText(LocalContext.current, toastText, Toast.LENGTH_SHORT).show()
    return false
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LinkActionsSheet(
    isFavorite: Boolean,
    onDismissSheet: () -> Unit,
    onDeleteLink: @Composable() () -> Unit,
    onShareLink: () -> Unit,
    onEditLink: @Composable() () -> Unit,
    onAddFavLink: @Composable() () -> Unit,
    onAddtoFolder: @Composable() () -> Unit,
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
        BottomBarConfig(
            isFavorite = isFavorite,
            onDeleteLink = {
            onDeleteLink.invoke()
            onDismissSheet.invoke()
        },
            onShareLink = {onShareLink.invoke()},
            onEditLink = {
                onEditLink.invoke()
                onDismissSheet.invoke()
            },
            onFavLink = {
                onAddFavLink.invoke()
            },
            onAddToFolder = {onAddtoFolder.invoke()})
        Spacer(Modifier.height(64.dp))
    }
}