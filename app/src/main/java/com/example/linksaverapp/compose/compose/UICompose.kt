package com.example.linksaverapp.compose.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.linksaverapp.LinkSaverViewModel
import com.example.linksaverapp.Utils.ColorThemeOptions
import com.example.linksaverapp.Utils.LinkScreens
import com.example.linksaverapp.Utils.colorOptions
import com.example.linksaverapp.Utils.favoritesStringID
import com.example.linksaverapp.Utils.radioOptions
import com.example.linksaverapp.compose.DeleteLink
import com.example.linksaverapp.compose.SortTree
import com.example.linksaverapp.compose.copyToClipboard
import com.example.linksaverapp.compose.getDate
import com.example.linksaverapp.compose.insertLink
import com.example.linksaverapp.compose.openLink
import com.example.linksaverapp.compose.saveConfig
import com.example.linksaverapp.compose.shareLink
import com.example.linksaverapp.compose.sortFolderList
import com.example.linksaverapp.compose.sortedLinkList
import com.example.linksaverapp.compose.updateLink
import com.example.linksaverapp.compose.validatePassword
import com.example.linksaverapp.compose.viewHelperVar
import com.example.linksaverapp.db.Model.LinkModel
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.linksaverapp.R

val TAG = "START_SCREEN"

@Composable
fun CreateUI(linkSaverViewModel: LinkSaverViewModel, isDarkTheme: MutableState<Boolean>, colorChosen: MutableState<ColorThemeOptions>) {
    val navController = rememberNavController()
    val ctx = LocalContext.current
    val activity = ctx as FragmentActivity
    val executor = ContextCompat.getMainExecutor(activity)
    val lifecycleOwner = LocalLifecycleOwner.current
    val links = linkSaverViewModel.allLinks.observeAsState()
    val screen = remember { mutableStateOf(LinkScreens.Start) }

    val linkList = remember { mutableStateMapOf<String, MutableList<LinkModel>>() }

    val linksObserver = Observer<List<LinkModel>> {
        sortFolderList(it, linkList)
    }
    linkSaverViewModel.allLinks.observe(lifecycleOwner, linksObserver)

    val linkText = remember { mutableStateOf("") }
    val nameText = remember { mutableStateOf("") }
    val folderText = remember { mutableStateOf("") }
    val isProtected = remember { mutableStateOf(false) }
    val linkModel = remember { mutableStateOf(LinkModel()) }
    val isLinkModelValid = remember { mutableStateOf(true) }
    val isFolderNameValid = remember { mutableStateOf(true) }
    val isDeviceUnlocked = remember { mutableStateOf(false) }

    //Bottomsheet
    val isSheetOpen = remember { mutableStateOf(false) }
    val isAlertOpen = remember { mutableStateOf(false) }

    //Alert
    val isAlertAddFolderOpen = remember { mutableStateOf(false) }

    //Order
    val selectedOption = remember { mutableStateOf(radioOptions[0]) }

    //Search
    val searchText = remember { mutableStateOf("") }
    val isSearchOpen = remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colorScheme.primary
    )
    
    viewHelperVar.favoritesString = stringResource(id = favoritesStringID)

    Scaffold(
        topBar = {
            TopAppBar(
                isSearchOpen = isSearchOpen,
                navController = navController,
                screen = screen.value,
                isLinkModelValid = isLinkModelValid,
                isFolderNameValid = isFolderNameValid,
                isAlertOpen = isAlertOpen,
                insertLinkAction = {
                    if (folderText.value.isBlank()) {
                        folderText.value = ""
                    }
                    insertLink(
                        linkSaverViewModel = linkSaverViewModel,
                        name = nameText,
                        link = linkText,
                        folder = folderText,
                        isProtected = isProtected,
                        linkModelIsValid = isLinkModelValid,
                        folderNameIsValid = isFolderNameValid,
                        folderMap = linkList
                    )
                },
                editLinkAction = {
                    with(linkModel.value) {
                        this.name = nameText.value
                        link = linkText.value
                        folder = folderText.value
                        this.isProtected = if (isProtected.value) 1 else 0
                        dateOfModified = getDate()
                    }
                    updateLink(
                        linkSaverViewModel = linkSaverViewModel,
                        link = linkModel.value,
                        folderNameIsValid = isFolderNameValid,
                        folderMap = linkList
                    )
                },
                exitSettingsAction = { saveConfig(activity, isDarkTheme, colorChosen, navController) },
                searchText = searchText,
                onTextChange = { text -> searchText.value = text },
                onClickOnSearched = { openLink(ctx, it) },
                onSearchInit = { sortedLinkList(links.value, searchText.value) },
                onCloseClicked = { isSearchOpen.value = false }
            )
        },
        backgroundColor = MaterialTheme.colorScheme.background,
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LinkScreens.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LinkScreens.Start.name) {
                SortTree(option = selectedOption.value, linkSaverViewModel = linkSaverViewModel)
                screen.value = LinkScreens.Start
                StartScreen(
                    allLinks = links,
                    folderMap = linkList,
                    isBottomSheetOpen = isSheetOpen,
                    isAlertAddFolderOpen = isAlertAddFolderOpen,
                    isDeviceUnlocked = isDeviceUnlocked,
                    onDeleteLink = { link ->
                        DeleteLink(linkSaverViewModel = linkSaverViewModel, link = link)
                        SortTree(
                            option = selectedOption.value,
                            linkSaverViewModel = linkSaverViewModel
                        )
                    },
                    onShareLink = { name, link -> shareLink(ctx, name, link) },
                    onEditLink = { link ->
                        linkModel.value = link
                        nameText.value = link.name
                        linkText.value = link.link
                        folderText.value = link.folder ?: ""
                        isProtected.value = link.isProtected == 1
                        isLinkModelValid.value = true
                        navController.navigate(LinkScreens.Edit.name)
                    },
                    onClickAction = { url -> openLink(ctx, url) },
                    onAddFavLink = { linkModel ->
                        updateLink(
                            linkSaverViewModel,
                            linkModel,
                            isFolderNameValid,
                            folderMap = linkList
                        )
                        SortTree(option = selectedOption.value, linkSaverViewModel = linkSaverViewModel)
                    },
                    folderNameValid = isFolderNameValid,
                    onCopyLink = {link -> copyToClipboard(link, activity) }
                )
            }
            composable(route = LinkScreens.Add.name) {
                screen.value = LinkScreens.Add
                linkText.value = ""
                nameText.value = ""
                folderText.value = ""
                isProtected.value = false
                isLinkModelValid.value = true
                AddLinkScreen(
                    nameText,
                    linkText,
                    folderText,
                    isProtected,
                    isLinkModelValid,
                    isFolderNameValid,
                    linkList.keys
                )
            }
            composable(route = LinkScreens.Edit.name) {
                screen.value = LinkScreens.Edit
                AddLinkScreen(
                    nameText,
                    linkText,
                    folderText,
                    isProtected,
                    isLinkModelValid,
                    isFolderNameValid,
                    linkList.keys
                )
            }
            composable(route = LinkScreens.SortingConfig.name) {
                screen.value = LinkScreens.SortingConfig
                SortScreen(selectedOption, radioOptions)
            }
            composable(route = LinkScreens.Settings.name) {
                screen.value = LinkScreens.Settings
                Settings(
                    isDarkTheme = isDarkTheme,
                    onWatchProtectedLinks = { validatePassword(context = ctx, activity = activity, executor = executor, isDeviceUnlocked) },
                    onSelectAppColor = {navController.navigate(LinkScreens.ChangeColor.name)},
                    onClickAboutApp = {navController.navigate(LinkScreens.AboutApp.name)},
                    onResetConfig = {
                        isDarkTheme.value = false
                        colorChosen.value = ColorThemeOptions.Gray
                        saveConfig(activity, isDarkTheme, colorChosen, navController)
                    }
                    )
            }
            composable(route = LinkScreens.ChangeColor.name) {
                screen.value = LinkScreens.ChangeColor
                SelectAppColorScreen(colorChosen = colorChosen, colorOptions = colorOptions)
            }
            composable(route = LinkScreens.AboutApp.name) {
                screen.value = LinkScreens.AboutApp
                AboutAppScreen()
            }
        }
        if (isAlertOpen.value) {
            com.example.linksaverapp.compose.compose.components.AlertDialogCustom(
                onDismissRequest = {
                    isAlertOpen.value = false
                },
                onConfirmation = {
                    isAlertOpen.value = false
                    navController.popBackStack()
                },
                dialogTitle = stringResource(id = R.string.changes_not_saved_label),
                dialogText = stringResource(id = R.string.changes_not_saved_question)
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


