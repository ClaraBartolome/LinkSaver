package com.example.testcomposeapp.compose.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.testcomposeapp.R
import com.example.testcomposeapp.db.Model.LinkModel

@Composable
fun ScrollContent(allLinks: State<List<LinkModel>?>, contextMenuLinkId: MutableState<Boolean>, linkCardPressed: () -> Unit) {
    LazyColumn{
        allLinks.value?.let{
            items(it){
                LinkCard(it.name){
                    contextMenuLinkId.value = true
                }
            }
        }
    }

    if(contextMenuLinkId.value){
        LinkActionsSheet() {
            contextMenuLinkId.value = false
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
private fun LinkActionsSheet(
    onDismissSheet: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = bottomSheetState,
        onDismissRequest = {
            onDismissSheet.invoke()
        },
        containerColor =
            if(isSystemInDarkTheme()) {
                Color.DarkGray} else Color.White

    ) {
        // Sheet content
        BottomBarConfig()
        Spacer(Modifier.height(64.dp))
    }
}