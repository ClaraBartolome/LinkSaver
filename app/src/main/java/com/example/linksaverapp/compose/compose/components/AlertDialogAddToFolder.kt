package com.example.linksaverapp.compose.compose.components

import android.R
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.linksaverapp.Utils.favoritesStringID
import com.example.linksaverapp.compose.compose.ItemFolderSuggestion
import com.example.linksaverapp.compose.compose.OutlinedTextFieldFolderCustom
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

@Composable
fun AlertDialogAddToFolder(
    folderName: MutableState<String>,
    folderNameValid: MutableState<Boolean>,
    folderList: MutableSet<String>,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {

    val favorites = stringResource(id = favoritesStringID)
    val showClearButton = remember { mutableStateOf(false) }
    val expanded = remember { mutableStateOf(false) }

    Dialog(onDismissRequest = { }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White,
            modifier = Modifier.wrapContentHeight()
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Añadir a carpeta",
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Icon(
                            imageVector = Icons.Filled.Clear,
                            contentDescription = "",
                            tint = colorResource(R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable { onDismissRequest.invoke() }
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    LazyColumn() {
                        item {
                            OutlinedTextFieldFolderCustom(
                                valueText = folderName,
                                showClearButton = showClearButton,
                                folderNameValid = folderNameValid,
                                onValueChange = {
                                    folderName.value = it
                                    expanded.value = true
                                },
                                onClickIcon = { showClearButton ->
                                    if (showClearButton) {
                                        folderName.value = ""
                                    } else {
                                        expanded.value = !expanded.value
                                    }
                                },
                                onFocusChange = { focusState ->
                                    showClearButton.value = (focusState.isFocused)
                                    expanded.value = (focusState.isFocused)
                                }
                            )
                        }
                        if (!folderNameValid.value) {
                            item {
                                androidx.compose.material.Text(
                                    "Solo puedes guardar 5 enlaces en 'Favoritos'",
                                    fontSize = 12.sp,
                                    color = Color.Red,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                        item {
                            AnimatedVisibility(visible = expanded.value) {
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    shape = RectangleShape,
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White,
                                    ),
                                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
                                ) {
                                    val copy = folderList.toMutableSet()
                                    copy.remove("")
                                    LazyColumn(
                                        modifier = Modifier.heightIn(max = 150.dp),
                                    ) {
                                        if (folderName.value.isNotBlank()) {
                                            items(
                                                copy.filter { folder ->
                                                    folder.lowercase()
                                                        .contains(folderName.value.lowercase())
                                                }
                                                    .sorted()
                                            ) {
                                                ItemFolderSuggestion(folderName = it) { name ->
                                                    folderName.value = name
                                                    expanded.value = false
                                                }
                                            }
                                        } else {
                                            items(
                                                copy.sorted()
                                            ) {
                                                ItemFolderSuggestion(folderName = it) { name ->
                                                    folderName.value = name
                                                    expanded.value = false
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        item{
                            Spacer(modifier = Modifier.height(20.dp))
                            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                                Button(
                                    onClick = {
                                        onConfirmation.invoke()
                                    },
                                    shape = RoundedCornerShape(50.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                                ) {
                                    Text(text = "Confirmar", color = MaterialTheme.colorScheme.onPrimary)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultAlertPreview() {
    LinkSaverAppTheme {
        AlertDialogAddToFolder(
            remember { mutableStateOf("") },
            remember { mutableStateOf(false) },
            mutableSetOf(),
            {},
            {}
        )
    }
}