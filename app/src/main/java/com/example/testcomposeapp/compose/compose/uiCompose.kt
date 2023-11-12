package com.example.testcomposeapp.compose.compose

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcomposeapp.LinkSaverViewModel
import com.example.testcomposeapp.R
import com.example.testcomposeapp.Utils.LinkScreens
import com.example.testcomposeapp.compose.UiComposeViewhelper.searchLink
import com.example.testcomposeapp.compose.UiComposeViewhelper.sortLinks
import com.example.testcomposeapp.db.Model.LinkModel
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

val TAG = "START_SCREEN"

@Composable
fun CreateUI(linkSaverViewModel: LinkSaverViewModel) {
    val navController = rememberNavController()
    val links = linkSaverViewModel.allLinks.observeAsState()
    var screen by remember { mutableStateOf(LinkScreens.Start) }

    val linkText = remember { mutableStateOf("") }
    val nameText = remember { mutableStateOf("") }
    val isProtected = remember { mutableStateOf(false) }
    val linkModelIsValid = remember { mutableStateOf(true) }

    //Bottomsheet
    val isSheetOpen = remember { mutableStateOf(false) }
    val haptics = LocalHapticFeedback.current

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color =  MaterialTheme.colors.primary
    )

    Scaffold(
        topBar = {
            TopAppBarConfig(navController = navController, screen, linkModelIsValid.value) {
                linkModelIsValid.value = validateLinkModel(nameText.value, linkText.value)
                if (linkModelIsValid.value) {
                    linkSaverViewModel.insert(
                        LinkModel(
                            name = nameText.value,
                            link = linkText.value,
                            isProtected = if (isProtected.value) 1 else 0,
                            folder = ""
                        )
                    )
                }
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
                LaunchedEffect(Unit) {
                    linkSaverViewModel.getAllLinksByName()
                    Log.i(TAG, "DB created size: ${linkSaverViewModel.allLinks.value?.size}")
                }
                ScrollContent(allLinks = links, isSheetOpen) {
                    haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }
            composable(route = LinkScreens.Add.name) {
                screen = LinkScreens.Add
                AddLinkScreen(nameText, linkText, isProtected, linkModelIsValid)
            }
            composable(route = LinkScreens.Settings.name) {
                screen = LinkScreens.Settings
                Settings()
            }
        }
    }
}

private fun validateLinkModel(name: String, link: String): Boolean {
    return name.isNotBlank() && name.isNotEmpty() && link.isNotBlank() && link.isNotEmpty()
}

@Composable
private fun TitleText(screen: LinkScreens) {
    when (screen) {
        LinkScreens.Start -> Text("GuardaLinks")
        LinkScreens.Add -> Text("Añade un Link")
        LinkScreens.Settings -> Text("Configuración")
        else -> Text("GuardaLinks")
    }

}

@Composable
private fun TopAppBarConfig(
    navController: NavHostController,
    screen: LinkScreens,
    isLinkModelValid: Boolean,
    addLinkAction: () -> Unit
) {
    val context = LocalContext.current
    TopAppBar(
        title = { TitleText(screen) },
        navigationIcon = {
            when (screen) {
                LinkScreens.Start -> {
                    IconButtonApp(
                        iconId = R.drawable.ic_settings,
                        action = { navController.navigate(LinkScreens.Settings.name) })
                }

                else -> {
                    IconButtonApp(iconId = R.drawable.ic_arrow_back, action = {
                        if (screen == LinkScreens.Add) {
                            addLinkAction.invoke()
                            if (isLinkModelValid) navController.popBackStack()
                        }
                    })
                }
            }
        },
        actions = {
            when (screen) {
                LinkScreens.Start -> {
                    IconButtonApp(iconId = R.drawable.ic_search, action = { searchLink(context) })
                    IconButtonApp(iconId = R.drawable.ic_order, action = { sortLinks(context) })
                    IconButtonApp(
                        iconId = R.drawable.ic_add,
                        action = { navController.navigate(LinkScreens.Add.name) })
                }

                LinkScreens.Add -> {
                    IconButtonApp(iconId = R.drawable.ic_check, action = {
                        addLinkAction.invoke()
                        if (isLinkModelValid) navController.popBackStack()})
                }

                LinkScreens.Settings -> {
                    Image(
                        painter = painterResource(id = R.drawable.ic_settings_outlined),
                        contentDescription = ""
                    )
                }

                else -> {}
            }
        }
    )
}

@Composable
private fun IconButtonApp(iconId: Int, action: () -> (Unit), contentDescription: String = "") {
    IconButton(onClick = action) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = contentDescription
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddLinkScreenPreview() {
    TestComposeAppTheme {

    }
}


