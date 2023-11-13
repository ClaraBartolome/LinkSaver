package com.example.linksaverapp.compose.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.linksaverapp.LinkSaverViewModel
import com.example.linksaverapp.Utils.LinkScreens
import com.example.linksaverapp.compose.DeleteLink
import com.example.linksaverapp.compose.GetAllLinksByName
import com.example.linksaverapp.compose.createFolderList
import com.example.linksaverapp.compose.insertLink
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val TAG = "START_SCREEN"

@Composable
fun CreateUI(linkSaverViewModel: LinkSaverViewModel) {
    val navController = rememberNavController()
    val links = linkSaverViewModel.allLinks.observeAsState()
    var screen by remember { mutableStateOf(LinkScreens.Start) }

    val linkText = remember { mutableStateOf("") }
    val nameText = remember { mutableStateOf("") }
    val folderText = remember { mutableStateOf("") }
    val isProtected = remember { mutableStateOf(false) }
    val linkModelIsValid = remember { mutableStateOf(true) }
    val foldersName = remember { mutableListOf<String>() }

    //Bottomsheet
    val isSheetOpen = remember { mutableStateOf(false) }
    val isAlertOpen = remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.primary
    )

    Scaffold(
        topBar = {
            TopAppBarConfig(navController = navController, screen, linkModelIsValid, isAlertOpen) {
                //TODO capar carpeta vacia
                insertLink(
                    linkSaverViewModel = linkSaverViewModel,
                    name = nameText,
                    link = linkText,
                    folder = folderText,
                    isProtected = isProtected,
                    linkModelIsValid = linkModelIsValid
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LinkScreens.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LinkScreens.Start.name) {
                screen = LinkScreens.Start
                GetAllLinksByName(linkSaverViewModel = linkSaverViewModel)
                linkText.value = ""
                nameText.value = ""
                folderText.value = ""
                isProtected.value = false
                linkModelIsValid.value = true
                StartScreen(
                    allLinks = links,
                    createHashMap = { createFolderList(linkSaverViewModel.allLinks.value) },
                    openBottomSheet = isSheetOpen
                )
                { link ->
                    DeleteLink(linkSaverViewModel = linkSaverViewModel, link = link)
                    GetAllLinksByName(linkSaverViewModel = linkSaverViewModel)
                }
            }
            composable(route = LinkScreens.Add.name) {
                screen = LinkScreens.Add
                AddLinkScreen(
                    nameText,
                    linkText,
                    folderText,
                    isProtected,
                    linkModelIsValid,
                    createFolderList(linkSaverViewModel.allLinks.value).keys
                )
            }
            composable(route = LinkScreens.Settings.name) {
                screen = LinkScreens.Settings
                Settings()
            }
        }
        if (isAlertOpen.value) {
            AlertDialog(
                onDismissRequest = {
                    isAlertOpen.value = false
                },
                onConfirmation = {
                    isAlertOpen.value = false
                    navController.popBackStack()
                },
                dialogTitle = "No has guardado los cambios",
                dialogText = "¿Estás seguro de que quieres salir?"
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddLinkScreenPreview() {
    LinkSaverAppTheme {

    }
}


