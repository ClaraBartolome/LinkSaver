package com.example.linksaverapp.compose

import android.content.Context
import android.widget.Toast
import com.example.linksaverapp.db.Model.LinkModel
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

    public fun createLinkModel(id: Int, name: String, link: String, dateOfCreation: String, dateOfModified: String, folder: String, isProtected: Int): LinkModel{
        return LinkModel(id, name, link, dateOfCreation, dateOfModified, folder, isProtected)
    }

}