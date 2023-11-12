package com.example.linksaverapp.compose.compose

import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.linksaverapp.LinkSaverViewModel
import com.example.linksaverapp.Utils.LinkScreens
import com.example.linksaverapp.compose.UiComposeViewhelper.searchLink
import com.example.linksaverapp.compose.UiComposeViewhelper.sortLinks
import com.example.linksaverapp.db.Model.LinkModel
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.example.linksaverapp.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale

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

    //Bottomsheet
    val isSheetOpen = remember { mutableStateOf(false) }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color =  MaterialTheme.colors.primary
    )

    Scaffold(
        topBar = {
            TopAppBarConfig(navController = navController, screen, linkModelIsValid.value) {
                insertLink(
                    linkSaverViewModel = linkSaverViewModel,
                    name = nameText,
                    link = linkText,
                    folder = folderText,
                    isProtected = isProtected,
                    linkModelIsValid = linkModelIsValid
                )
                linkText.value = ""
                nameText.value = ""
                folderText.value = ""
                isProtected.value = false
                linkModelIsValid.value = true
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
                ScrollContent(allLinks = links, isSheetOpen) { link ->
                    DeleteLink(linkSaverViewModel = linkSaverViewModel, link = link)
                    GetAllLinksByName(linkSaverViewModel = linkSaverViewModel)
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

private fun insertLink(linkSaverViewModel: LinkSaverViewModel, name: MutableState<String>, link: MutableState<String>, folder: MutableState<String>, isProtected: MutableState<Boolean>, linkModelIsValid: MutableState<Boolean>){
    linkModelIsValid.value = validateLinkModel(name.value, link.value)
    if (linkModelIsValid.value) {
        linkSaverViewModel.insert(
            LinkModel(
                name = name.value,
                link = link.value,
                dateOfCreation = getDate(),
                dateOfModified = getDate(),
                folder = "",
                isProtected = if (isProtected.value) 1 else 0,
            )
        )
    }
}

private fun getDate(): String{
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(time)
}

@Composable
private fun GetAllLinksByName(linkSaverViewModel: LinkSaverViewModel){
    LaunchedEffect(Unit) {
        linkSaverViewModel.getAllLinksByName()
        Log.i(TAG, "DB created size: ${linkSaverViewModel.allLinks.value?.size}")
    }
}

@Composable
private fun DeleteLink(linkSaverViewModel: LinkSaverViewModel, link: LinkModel){
    LaunchedEffect(Unit) {
        linkSaverViewModel.deleteLink(link)
        Log.i(TAG, "DB deleted link: $link")
    }
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
    LinkSaverAppTheme {

    }
}


