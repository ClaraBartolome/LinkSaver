package com.example.testcomposeapp.compose.topAppBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme
import com.example.testcomposeapp.R
import com.example.testcomposeapp.Utils.LinkScreens
import com.example.testcomposeapp.compose.UiComposeViewhelper
import com.example.testcomposeapp.compose.UiComposeViewhelper.addLink
import com.example.testcomposeapp.compose.UiComposeViewhelper.goToSettings
import com.example.testcomposeapp.compose.UiComposeViewhelper.searchLink
import com.example.testcomposeapp.compose.UiComposeViewhelper.sortLinks
import com.example.testcomposeapp.compose.compose.AddLinkScreen
import com.example.testcomposeapp.compose.compose.linkCard

@Composable
fun topAppBar() {
    val context = LocalContext.current
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { TitleText() },
                navigationIcon = {
                    IconButtonApp(
                        iconId = R.drawable.ic_settings,
                        action = { goToSettings(context) })
                },
                actions = {
                    IconButtonApp(iconId = R.drawable.ic_search, action = { searchLink(context) })
                    IconButtonApp(iconId = R.drawable.ic_order, action = { sortLinks(context) })
                    IconButtonApp(iconId = R.drawable.ic_add, action = { navController.navigate(LinkScreens.Add.name) })
                }
            )
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = LinkScreens.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = LinkScreens.Start.name){
                ScrollContent(innerPadding = innerPadding)
            }
            composable(route = LinkScreens.Add.name){
                AddLinkScreen()
            }
        }
    }
}

@Composable
private fun TitleText() {
    Text("GuardaLinks")
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
fun ScrollContent(innerPadding: PaddingValues) {
    LazyColumn() {
        items(5) {
            linkCard(UiComposeViewhelper.testURL())
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultPreview() {
    TestComposeAppTheme {
        topAppBar()
    }
}