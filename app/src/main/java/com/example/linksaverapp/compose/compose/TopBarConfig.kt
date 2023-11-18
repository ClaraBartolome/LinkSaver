package com.example.linksaverapp.compose.compose

import android.service.autofill.FieldClassification.Match
import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.linksaverapp.R
import com.example.linksaverapp.Utils.LinkScreens
import com.example.linksaverapp.compose.compose.components.SearchBar
import com.example.linksaverapp.db.Model.LinkModel


@Composable
fun TopAppBar(
    isSearchOpen: MutableState<Boolean>,
    navController: NavHostController,
    screen: LinkScreens,
    isLinkModelValid: MutableState<Boolean>,
    isFolderNameValid: MutableState<Boolean>,
    isAlertOpen: MutableState<Boolean>,
    insertLinkAction: () -> Unit,
    editLinkAction: () -> Unit,
    searchText: MutableState<String>,
    onTextChange: (String) -> Unit,
    onSearchInit: () -> List<LinkModel>,
    onClickOnSearched: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    if (isSearchOpen.value) {
        SearchBar(
            text = searchText,
            onTextChange = onTextChange,
            onSearchInit = onSearchInit,
            onClickOnSearched = onClickOnSearched,
            onCloseClicked = onCloseClicked
        )
    } else {
        TopAppBarDefault(
            navController = navController,
            screen = screen,
            isLinkModelValid = isLinkModelValid,
            isFolderNameValid = isFolderNameValid,
            isSearchOpen = isSearchOpen,
            isAlertOpen = isAlertOpen,
            addLinkAction = insertLinkAction,
            editLinkAction = editLinkAction
        )
    }
}

@Composable
fun TopAppBarDefault(
    navController: NavHostController,
    screen: LinkScreens,
    isLinkModelValid: MutableState<Boolean>,
    isFolderNameValid: MutableState<Boolean>,
    isSearchOpen: MutableState<Boolean>,
    isAlertOpen: MutableState<Boolean>,
    addLinkAction: () -> Unit,
    editLinkAction: () -> Unit
) {
    TopAppBar(
        title = { TitleText(screen) },
        navigationIcon = {
            when (screen) {
                LinkScreens.Start -> {
                    IconButtonApp(
                        iconId = R.drawable.ic_settings,
                        action = { navController.navigate(LinkScreens.Settings.name) })
                }

                LinkScreens.Settings, LinkScreens.SortingConfig -> {
                    IconButtonApp(
                        iconId = R.drawable.ic_arrow_back,
                        action = { navController.popBackStack() })
                }
                else -> {
                    IconButtonApp(iconId = R.drawable.ic_arrow_back, action = {
                        if (screen == LinkScreens.Add || screen == LinkScreens.Edit) {
                            isAlertOpen.value = true
                        }
                    })
                }
            }
        },
        actions = {
            when (screen) {
                LinkScreens.Start -> {
                    IconButtonApp(
                        iconId = R.drawable.ic_search,
                        action = { isSearchOpen.value = true })
                    IconButtonApp(
                        iconId = R.drawable.ic_order,
                        action = { navController.navigate(LinkScreens.SortingConfig.name) })
                    IconButtonApp(
                        iconId = R.drawable.ic_add,
                        action = { navController.navigate(LinkScreens.Add.name) })
                }
                LinkScreens.Add -> {
                    IconButtonApp(iconId = R.drawable.ic_check, action = {
                        addLinkAction.invoke()
                        if (isLinkModelValid.value && isFolderNameValid.value) {
                            navController.popBackStack()
                        }
                    })
                }
                LinkScreens.Settings -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_settings_outlined),
                        contentDescription = ""
                    )
                }
                LinkScreens.Edit -> {
                    IconButtonApp(iconId = R.drawable.ic_check, action = {
                        editLinkAction.invoke()
                        if (isLinkModelValid.value && isFolderNameValid.value) {
                            navController.popBackStack()
                        }
                    })
                }
                else -> {}
            }
        },
        backgroundColor = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun IconButtonApp(iconId: Int, action: () -> (Unit), contentDescription: String = "") {
    IconButton(onClick = action) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun TitleText(screen: LinkScreens) {
    val title = when (screen) {
        LinkScreens.Start -> "GuardaLinks"
        LinkScreens.Add -> "Añade un Link"
        LinkScreens.Edit -> "Edita un Link"
        LinkScreens.Settings -> "Configuración"
        else -> "GuardaLinks"
    }
    Text("GuardaLinks", color = MaterialTheme.colorScheme.onPrimary)
}