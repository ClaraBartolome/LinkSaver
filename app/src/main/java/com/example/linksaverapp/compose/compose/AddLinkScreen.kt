package com.example.linksaverapp.compose.compose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linksaverapp.R
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme
import com.example.linksaverapp.ui.theme.green50
import java.net.MalformedURLException
import java.net.URL


@Composable
fun AddLinkScreen(
    nameText: MutableState<String>,
    linkText: MutableState<String>,
    folderName: MutableState<String>,
    isProtected: MutableState<Boolean>,
    linkModelValid: MutableState<Boolean>,
    folderList: MutableList<String>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        val showClearNameButton = remember { mutableStateOf(false) }
        val showClearLinkButton = remember { mutableStateOf(false) }
        val showClearFolderButton = remember { mutableStateOf(false) }
        val expanded = remember { mutableStateOf(false) }

        //Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextFieldCustom(
            valueText = nameText,
            linkModelValid = linkModelValid,
            showClearButton = showClearNameButton,
            labelText = "Nombre",
            supportingText = "Tienes que introducir un nombre"
        ){name ->
            nameText.value = name
        }

        OutlinedTextFieldCustom(
            valueText = linkText,
            linkModelValid = linkModelValid,
            showClearButton = showClearLinkButton,
            labelText = "Enlace",
            supportingText = "Tienes que introducir un enlace"
        ){link->
            linkText.value = link
            nameText.value = getName(link)
        }

        //Text("Si dejas el siguiente campo en blanco no se asignará a ninguna carpeta", fontSize = 12.sp, color = getColor())

        LazyColumn(){
            item {
                OutlinedTextFieldFolderCustom(
                    valueText = folderName,
                    showClearButton = showClearFolderButton,
                    expandable = expanded,
                    onValueChange = {folder ->
                        folderName.value = folder
                        expanded.value = true
                    },
                    onClickIcon = { showClearButton ->
                        if(showClearButton){
                            folderName.value = ""
                        }else{
                            expanded.value = !expanded.value
                        }
                    },
                    onFocusChange = { focusState ->
                        showClearFolderButton.value = (focusState.isFocused)
                        expanded.value = (focusState.isFocused)
                    },
                    onGloballyPositioned = {}
                )
            }
            item {
                AnimatedVisibility(visible = expanded.value) {
                    Card(modifier = Modifier
                        .fillMaxWidth(),
                        shape = RectangleShape,
                        colors = CardDefaults.cardColors(
                            containerColor = getBGColor(),
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                    ){
                        LazyColumn(
                            modifier = Modifier.heightIn(max = 150.dp),
                        ) {

                            if (folderName.value.isNotEmpty()) {
                                items(
                                    folderList.filter { folder ->
                                        folder.lowercase()
                                            .contains(folderName.value.lowercase()) || folder.lowercase().contains("Favoritos")
                                    }
                                        .sorted()
                                ) {
                                    ItemFolderSuggestion(folderName = it){ name->
                                        folderName.value = name
                                        expanded.value = false
                                    }
                                }
                            } else {
                                items(
                                    folderList.sorted()
                                ) {
                                    ItemFolderSuggestion(folderName = it){ name->
                                        folderName.value = name
                                        expanded.value = false
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }



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

@Composable
private fun OutlinedTextFieldCustom(
    valueText: MutableState<String>,
    linkModelValid: MutableState<Boolean>,
    showClearButton: MutableState<Boolean>,
    labelText: String,
    supportingText: String,
    onValueChange: (String) -> Unit
    ){
    val focusManager = LocalFocusManager.current
    OutlinedTextField(value = valueText.value,
        onValueChange = {onValueChange.invoke(it)},
        label = { Text(labelText, color = if(!linkModelValid.value && valueText.value.isBlank()) Color.Red else getColor()) },
        singleLine = true,
        supportingText = { if(!linkModelValid.value && valueText.value.isBlank()) {Text(supportingText, fontSize = 12.sp, color = Color.Red)} },
        isError = !linkModelValid.value && valueText.value.isBlank(),
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                showClearButton.value = (focusState.isFocused)
            },
        keyboardActions = KeyboardActions(onDone = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        trailingIcon = {
            if (showClearButton.value) {
                IconButton(onClick = { valueText.value = ""}) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = "Clear", tint = getColor())
                }
            }

        },
        textStyle = TextStyle(fontSize = 16.sp, color = getColor())
    )
}


@Composable
private fun OutlinedTextFieldFolderCustom(
    valueText: MutableState<String>,
    showClearButton: MutableState<Boolean>,
    expandable: MutableState<Boolean>,
    onValueChange: (String) -> Unit,
    onClickIcon: (Boolean) -> Unit,
    onFocusChange: (FocusState) -> Unit,
    onGloballyPositioned: (Int)-> Unit
){
    val focusManager = LocalFocusManager.current
    OutlinedTextField(value = valueText.value,
        onValueChange = { onValueChange.invoke(it) },
        label = { Text("Carpeta", color = getColor())},
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .onGloballyPositioned { onGloballyPositioned.invoke(it.size.width) }
            .onFocusChanged { onFocusChange.invoke(it) },
        keyboardActions = KeyboardActions(onDone = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        placeholder = { Text("None", color = getPlaceholderColor())},
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text
        ),
        trailingIcon = {
            IconButton(onClick = { onClickIcon.invoke(showClearButton.value)}) {
                Icon(imageVector = if(showClearButton.value)Icons.Filled.Clear else Icons.Filled.ArrowDropDown, contentDescription = "Clear", tint = getColor())
            }

        },
        textStyle = TextStyle(fontSize = 16.sp, color = getColor())
    )
}

@Composable
fun ItemFolderSuggestion(folderName:String, onClick: (String) -> Unit){
    Row(modifier = Modifier
        .clickable { onClick(folderName) }){
        Text(folderName, color = getColor(), modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp))
    }
}

private fun getName(url: String): String{
    return if(isValidUrl(url)) if(URL(url).path != "" && URL(url).path.length > 1) URL(url).path else URL(url).host else ""
}

@Composable
private fun getColor(): Color{
    return if(isSystemInDarkTheme()) Color.White else Color.DarkGray
}

@Composable
private fun getBGColor(): Color{
    return if(!isSystemInDarkTheme()) green50 else Color.DarkGray
}

@Composable
private fun getPlaceholderColor(): Color{
    return if(isSystemInDarkTheme()) Color.White else Color.LightGray
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

@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun AddLinkScreenPreview() {
    LinkSaverAppTheme {
        AddLinkScreen(
            remember{mutableStateOf("")},
            remember{mutableStateOf("")},
            remember{mutableStateOf("")},
            remember{mutableStateOf(false)},
            remember{mutableStateOf(true)},
            remember{mutableListOf()})
    }
}