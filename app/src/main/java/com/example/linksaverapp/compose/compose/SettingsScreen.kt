package com.example.linksaverapp.compose.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

@Composable
fun Settings(onWatchProtectedLinks: @Composable () -> Unit) {
    val shouldShowResult = remember {
        mutableStateOf(false)
    }
    LazyColumn(){
        item {
            ItemSetting(text = "Reestablecer valores predeterminados", icon = Icons.Outlined.Refresh, {}, remember {
                mutableStateOf(false)
            })
        }
        item {
            ItemSetting(text = "Ver enlaces ocultos", icon = Icons.Outlined.Lock, { onWatchProtectedLinks.invoke()}, shouldShowResult)
        }
        item {
            ItemSettingSwitch(text = "Modo nocturno", icon = Icons.Outlined.Lock, {}, )
        }
        item {
            ItemSettingColor(text = "Cambiar esquema de color", icon = Icons.Outlined.Create, {})
        }
        item {
            ItemSetting(text = "Acerca de...", icon = Icons.Outlined.Info, {}, remember {
                mutableStateOf(false)
            })
        }
    }
}

@Composable
private fun ItemSetting(text: String, icon: ImageVector, onClick:@Composable () -> Unit, showResult: MutableState<Boolean>){
    Column (modifier = Modifier.clickable { showResult.value = true }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp)){
            Icon(imageVector =  icon, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
            Text(
                text = text,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.titleMedium)
        }
        Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
    }
    if (showResult.value) {
        onClick.invoke()
        showResult.value = false
    }
}

@Composable
private fun ItemSettingColor(text: String, icon: ImageVector, onClick: () -> Unit){
    Column (modifier = Modifier.clickable { onClick.invoke() }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)){
            Row(){
                Icon(imageVector =  icon, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.titleMedium)
            }

            Icon(imageVector =  Icons.Default.Favorite , contentDescription = "", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(end = 16.dp))
        }
        Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
    }
}

@Composable
private fun ItemSettingSwitch(text: String, icon: ImageVector, onClick: () -> Unit){
    Column (modifier = Modifier.clickable { onClick.invoke() }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)){
            Row(){
                Icon(imageVector =  icon, contentDescription = "", tint = MaterialTheme.colorScheme.primary)
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.titleMedium)
            }

            var checked by remember { mutableStateOf(true) }

            Switch(
                checked = checked,
                onCheckedChange = {
                    checked = it
                },
                modifier = Modifier.scale(0.75f)
            )
        }
        Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddLinkScreenPreview() {
    LinkSaverAppTheme {
        Settings({})
    }
}