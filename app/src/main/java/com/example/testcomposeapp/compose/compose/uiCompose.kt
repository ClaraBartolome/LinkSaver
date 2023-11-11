package com.example.testcomposeapp.compose.compose

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcomposeapp.LinkSaverViewModel
import com.example.testcomposeapp.R
import com.example.testcomposeapp.Utils.LinkScreens
import com.example.testcomposeapp.compose.UiComposeViewhelper.searchLink
import com.example.testcomposeapp.compose.UiComposeViewhelper.sortLinks

@Composable
fun CreateUI(linkSaverViewModel: LinkSaverViewModel) {
    val navController = rememberNavController()
    val links = linkSaverViewModel.allLinks.observeAsState()
    var screen by remember { mutableStateOf(LinkScreens.Start) }
    Scaffold(
        topBar = {
            TopAppBarConfig(navController = navController, screen)
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LinkScreens.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LinkScreens.Start.name){
                screen = LinkScreens.Start
                ScrollContent(allLinks = links)
            }
            composable(route = LinkScreens.Add.name){
                screen = LinkScreens.Add
                AddLinkScreen()
            }
            composable(route = LinkScreens.Settings.name){
                screen = LinkScreens.Settings
                Settings()
            }
        }
    }
}

@Composable
private fun TitleText(screen: LinkScreens) {
    when(screen){
        LinkScreens.Start -> Text("GuardaLinks")
        LinkScreens.Add -> Text("Añade un Link")
        LinkScreens.Settings -> Text("Configuración")
        else -> Text("GuardaLinks")
    }

}

@Composable
private fun TopAppBarConfig(navController: NavHostController, screen: LinkScreens){
    val context = LocalContext.current
    TopAppBar(
        title = { TitleText(screen) },
        navigationIcon = {
            IconButtonApp(
                iconId = R.drawable.ic_settings,
                action = { navController.navigate(LinkScreens.Settings.name) })
        },
        actions = {
            if(screen == LinkScreens.Start){
                IconButtonApp(iconId = R.drawable.ic_search, action = { searchLink(context) })
                IconButtonApp(iconId = R.drawable.ic_order, action = { sortLinks(context) })
                IconButtonApp(iconId = R.drawable.ic_add, action = { navController.navigate(LinkScreens.Add.name) })
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


