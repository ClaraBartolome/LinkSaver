package com.example.testcomposeapp.compose.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.testcomposeapp.R
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme

@Composable
fun BottomBarConfig() {
    Column(modifier = Modifier.padding(start = 16.dp),
        verticalArrangement = Arrangement.Top) {
        ItemBottomBar(name = "Añadir a carpeta", icon = R.drawable.ic_add){}
        Spacer(modifier = Modifier.height(8.dp))
        ItemBottomBar(name = "Añadir a favoritos", icon = R.drawable.ic_favorite_border){}
        Spacer(modifier = Modifier.height(8.dp))
        ItemBottomBar(name = "Compartir", icon = R.drawable.ic_share){}
        Spacer(modifier = Modifier.height(8.dp))
        ItemBottomBar(name = "Borrar", icon = R.drawable.ic_delete){}
    }
}

@Composable
private fun ItemBottomBar(name: String, icon: Int, contentDescription: String = "", action: ()-> Unit){
    Row(modifier = Modifier
        .fillMaxWidth()){
        Image(painter = painterResource(id = icon), contentDescription = contentDescription, colorFilter = ColorFilter.tint(color = getColor()))
        Text(text = name, modifier = Modifier.padding(start = 8.dp), color = getColor())
}
}

@Composable
private fun getColor(): Color{
    return if(isSystemInDarkTheme()) {
        Color.White} else Color.Black
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultBottombarPreview() {
    TestComposeAppTheme {
        BottomBarConfig()
    }
}