package com.example.testcomposeapp.compose.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.example.testcomposeapp.db.Model.LinkModel

@Composable
fun ScrollContent(allLinks: State<List<LinkModel>?>) {
    LazyColumn{
        allLinks.value?.let{
            items(it){
                LinkCard(it.name)
            }
        }
    }
}