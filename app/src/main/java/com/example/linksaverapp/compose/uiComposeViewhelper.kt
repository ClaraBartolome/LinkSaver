package com.example.linksaverapp.compose

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import com.example.linksaverapp.LinkSaverViewModel
import com.example.linksaverapp.Utils.SortRadioOptions
import com.example.linksaverapp.compose.compose.TAG
import com.example.linksaverapp.db.Model.LinkModel
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TreeMap


//PODRIA HACER UNA CONFIGURACION PARA MOSTRAR POR DEFECTO EL HOST O EL PATH
fun testURL(): String {
    val url = URL("https://developer.android.com/jetpack/compose/tutorial?hl=es-419")
    return url.path
}

fun sortLinks(context: Context) {
    Toast.makeText(context, "Próximamente", Toast.LENGTH_SHORT).show()
}

fun searchLink(context: Context) {
    Toast.makeText(context, "Próximamente", Toast.LENGTH_SHORT).show()
}

fun createLinkModel(
    id: Int,
    name: String,
    link: String,
    dateOfCreation: String,
    dateOfModified: String,
    folder: String,
    isProtected: Int
): LinkModel {
    return LinkModel(id, name, link, dateOfCreation, dateOfModified, folder, isProtected)
}

private fun validateLinkModel(name: String, link: String): Boolean {
    return name.isNotBlank() && link.isNotBlank()
}


fun insertLink(
    linkSaverViewModel: LinkSaverViewModel,
    name: MutableState<String>,
    link: MutableState<String>,
    folder: MutableState<String>,
    isProtected: MutableState<Boolean>,
    linkModelIsValid: MutableState<Boolean>
) {
    linkModelIsValid.value = validateLinkModel(name.value, link.value)
    if (linkModelIsValid.value) {
        linkSaverViewModel.insert(
            LinkModel(
                name = name.value,
                link = link.value,
                dateOfCreation = getDate(),
                dateOfModified = getDate(),
                folder = folder.value,
                isProtected = if (isProtected.value) 1 else 0,
            )
        )
    }
}

private fun getDate(): String {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(time)
}

fun sortFolderList(links: List<LinkModel>?, folderList: MutableMap<String, MutableList<LinkModel>>) {
    folderList.clear()
    links?.let { list ->
        var lastName = list.first().folder ?: ""
        val mutableListAux = mutableListOf<LinkModel>()
        list.sortedBy { it.folder }.forEach { linkModel ->
            linkModel.folder?.let { folderName ->
                Log.i("DEBUG", "Entrada: " + linkModel.name)
                if (lastName != folderName) {
                    folderList[lastName] = mutableListAux.toMutableList()
                    lastName = folderName
                    mutableListAux.clear()
                    mutableListAux.add(linkModel)
                } else {
                    mutableListAux.add(linkModel)
                }
            }
        }
        folderList[lastName] = mutableListAux.toMutableList()
        folderList.values.forEach { list ->
            list.sortBy { it.name }
        }
    }
}

fun sortTree(option: SortRadioOptions){
    when(option){
        SortRadioOptions.NameAZ -> {}
        else -> {}
    }
}


fun openLink(context:Context, url: String){
    val urlIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    context.startActivity(urlIntent)
}

@Composable
fun GetAllLinksByName(linkSaverViewModel: LinkSaverViewModel) {
    LaunchedEffect(Unit) {
        linkSaverViewModel.getAllLinksByName()
        Log.i(TAG, "DB created size: ${linkSaverViewModel.allLinks.value?.size}")
    }
}

@Composable
fun DeleteLink(linkSaverViewModel: LinkSaverViewModel, link: LinkModel) {
    LaunchedEffect(Unit) {
        linkSaverViewModel.deleteLink(link)
        Log.i(TAG, "DB deleted link: $link")
    }
}