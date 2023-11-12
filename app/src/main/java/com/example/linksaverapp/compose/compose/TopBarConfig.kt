package com.example.linksaverapp.compose.compose

import androidx.compose.foundation.Image
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.linksaverapp.R
import com.example.linksaverapp.Utils.LinkScreens
import com.example.linksaverapp.compose.searchLink
import com.example.linksaverapp.compose.sortLinks

@Composable
fun TopAppBarConfig(
    navController: NavHostController,
    screen: LinkScreens,
    isLinkModelValid: MutableState<Boolean>,
    isAlertOpen: MutableState<Boolean>,
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
                            isAlertOpen.value = true
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
                        if (isLinkModelValid.value) {navController.popBackStack()}})
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

@Composable
private fun TitleText(screen: LinkScreens) {
    when (screen) {
        LinkScreens.Start -> Text("GuardaLinks")
        LinkScreens.Add -> Text("Añade un Link")
        LinkScreens.Settings -> Text("Configuración")
        else -> Text("GuardaLinks")
    }

}