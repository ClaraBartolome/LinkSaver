package com.example.linksaverapp.compose.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linksaverapp.R
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme
import java.net.MalformedURLException
import java.net.URL


@Composable
fun AddLinkScreen(nameText: MutableState<String>, linkText: MutableState<String>, isProtected: MutableState<Boolean>, linkModelValid: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        OutlinedTextField(value = nameText.value,
            onValueChange = { nameText.value = it },
            label = { Text("Nombre") },
            singleLine = true,
            placeholder = { if(!linkModelValid.value && (nameText.value.isBlank() || nameText.value.isEmpty())) Text("Tienes que introducir un nombre") },
            isError = !linkModelValid.value && (nameText.value.isBlank() || nameText.value.isEmpty()),
            modifier = Modifier.fillMaxWidth())

        OutlinedTextField(value = linkText.value,
            onValueChange = {
                linkText.value = it
                nameText.value = getName(linkText.value)
                            },
            label = { Text("Enlace") },
            singleLine = true,
            placeholder = { if(!linkModelValid.value && (linkText.value.isBlank() || linkText.value.isEmpty())) Text("Tienes que introducir un enlace") },
            isError = !linkModelValid.value && (linkText.value.isBlank() || linkText.value.isEmpty()),
            modifier = Modifier.fillMaxWidth())

        Row(modifier= Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 16.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Text("¿Es un link privado?")
            ElevatedButton(onClick = { isProtected.value = !isProtected.value }, colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colors.primary)) {
                Text(if(isProtected.value)"Invisible" else "Visible", modifier = Modifier.padding(end = 8.dp))
                Image(painter = painterResource(id = if(isProtected.value)R.drawable.ic_visibility_off else R.drawable.ic_visibility_on), contentDescription = "")
            }
        }
    }
}

private fun getName(url: String): String{
    return if(isValidUrl(url)) if(URL(url).path != "" && URL(url).path.length > 1) URL(url).path else URL(url).host else ""
}

@Throws(MalformedURLException::class)
fun isValidUrl(url: String?): Boolean {
    return try {
        // it will check only for scheme and not null input
        URL(url)
        true
    } catch (e: MalformedURLException) {
        false
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddLinkScreenPreview() {
    LinkSaverAppTheme {
        AddLinkScreen(remember{mutableStateOf("")}, remember{mutableStateOf("")}, remember{mutableStateOf(false)}, remember{mutableStateOf(false)})
    }
}