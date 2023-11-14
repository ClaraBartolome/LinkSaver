package com.example.linksaverapp.compose.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.linksaverapp.LinkSaverViewModel
import com.example.linksaverapp.Utils.LinkScreens
import com.example.linksaverapp.Utils.radioOptions
import com.example.linksaverapp.compose.DeleteLink
import com.example.linksaverapp.compose.GetAllLinksByName
import com.example.linksaverapp.compose.sortFolderList
import com.example.linksaverapp.compose.insertLink
import com.example.linksaverapp.compose.openLink
import com.example.linksaverapp.db.Model.LinkModel
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val TAG = "START_SCREEN"

@Composable
fun CreateUI(linkSaverViewModel: LinkSaverViewModel) {
    val navController = rememberNavController()
    val ctx = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val links = linkSaverViewModel.allLinks.observeAsState()
    val screen = remember { mutableStateOf(LinkScreens.Start) }

    val linkList = remember { mutableStateMapOf<String, MutableList<LinkModel>>() }

    GetAllLinksByName(linkSaverViewModel = linkSaverViewModel)
    val linksObserver = Observer<List<LinkModel>>{
        sortFolderList(it, linkList)
    }
    linkSaverViewModel.allLinks.observe(lifecycleOwner, linksObserver)

    val linkText = remember { mutableStateOf("") }
    val nameText = remember { mutableStateOf("") }
    val folderText = remember { mutableStateOf("") }
    val isProtected = remember { mutableStateOf(false) }
    val linkModelIsValid = remember { mutableStateOf(true) }

    //Bottomsheet
    val isSheetOpen = remember { mutableStateOf(false) }
    val isAlertOpen = remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.primary
    )



    Scaffold(
        topBar = {
            TopAppBarConfig(navController = navController, screen.value, linkModelIsValid, isAlertOpen) {
                //TODO capar carpeta vacia
                insertLink(
                    linkSaverViewModel = linkSaverViewModel,
                    name = nameText,
                    link = linkText,
                    folder = folderText,
                    isProtected = isProtected,
                    linkModelIsValid = linkModelIsValid
                )
                run { linkSaverViewModel.getAllLinksByName() }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LinkScreens.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LinkScreens.Start.name) {
                screen.value = LinkScreens.Start
                linkText.value = ""
                nameText.value = ""
                folderText.value = ""
                isProtected.value = false
                linkModelIsValid.value = true
                StartScreen(
                    allLinks = links,
                    folderMap = linkList,
                    openBottomSheet = isSheetOpen,
                    onDeleteLink = { link ->
                        DeleteLink(linkSaverViewModel = linkSaverViewModel, link = link)
                        GetAllLinksByName(linkSaverViewModel = linkSaverViewModel)
                    },
                    onClickAction = {url -> openLink(ctx, url) }
                )
            }
            composable(route = LinkScreens.Add.name) {
                screen.value = LinkScreens.Add
                AddLinkScreen(
                    nameText,
                    linkText,
                    folderText,
                    isProtected,
                    linkModelIsValid,
                    linkList.keys
                )
            }
            composable(route = LinkScreens.SortingConfig.name) {
                screen.value = LinkScreens.SortingConfig
                SortScreen(radioOptions){}
            }
            composable(route = LinkScreens.Settings.name) {
                screen.value = LinkScreens.Settings
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


