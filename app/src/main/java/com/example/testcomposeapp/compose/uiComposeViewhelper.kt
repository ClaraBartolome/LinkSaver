package com.example.testcomposeapp.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.testcomposeapp.Utils.LinkScreens
import java.net.URL

object UiComposeViewhelper {

    //PODRIA HACER UNA CONFIGURACION PARA MOSTRAR POR DEFECTO EL HOST O EL PATH
    public fun testURL(): String{
        val url = URL("https://developer.android.com/jetpack/compose/tutorial?hl=es-419")
        return url.path
    }

    public fun sortLinks(context: Context){
        Toast.makeText(context,"Próximamente", Toast.LENGTH_SHORT).show()
    }

    public fun goToSettings(context: Context){
        Toast.makeText(context,"Próximamente", Toast.LENGTH_SHORT).show()
    }

    public fun searchLink(context: Context){
        Toast.makeText(context,"Próximamente", Toast.LENGTH_SHORT).show()
    }

    @Composable
    public fun addLink(context: Context){
        val navController = rememberNavController()
        navController.navigate(LinkScreens.Add.name)
    }

}