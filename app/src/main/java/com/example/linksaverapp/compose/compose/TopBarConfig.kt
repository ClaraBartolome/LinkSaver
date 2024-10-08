package com.example.linksaverapp.compose.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
    exitSettingsAction: () -> Unit,
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
            editLinkAction = editLinkAction,
            exitSettingsScreen = exitSettingsAction
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
    editLinkAction: () -> Unit,
    exitSettingsScreen: () -> Unit
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

                LinkScreens.SortingConfig, LinkScreens.ChangeColor, LinkScreens.AboutApp -> {
                    IconButtonApp(
                        iconId = R.drawable.ic_arrow_back,
                        action = { navController.popBackStack() })
                }
            LinkScreens.Settings-> {
                IconButtonApp(
                    iconId = R.drawable.ic_arrow_back,
                    action = {
                        exitSettingsScreen.invoke()
                    })
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
                        painter = painterResource(id = R.drawable.ic_settings),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary),
                        modifier = Modifier.padding(end = 8.dp)
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
    Text(stringResource(id = R.string.app_name), color = MaterialTheme.colorScheme.onPrimary)
}