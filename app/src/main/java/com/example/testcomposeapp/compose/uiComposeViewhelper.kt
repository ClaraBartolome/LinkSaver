package com.example.testcomposeapp.compose

import android.content.Context
import android.widget.Toast
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

    public fun searchLink(context: Context){
        Toast.makeText(context,"Próximamente", Toast.LENGTH_SHORT).show()
    }

}