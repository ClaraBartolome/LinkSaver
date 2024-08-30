package com.example.linksaverapp.compose.compose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linksaverapp.R
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

@Composable
fun Settings(isDarkTheme: MutableState<Boolean>, onWatchProtectedLinks: @Composable () -> Unit, onSelectAppColor: @Composable () -> Unit, onClickAboutApp: @Composable () -> Unit, onResetConfig: () -> Unit ) {
    val applyValidatePassword = remember {
        mutableStateOf(false)
    }
    LazyColumn(){
        item {
            ItemSetting(text = stringResource(id = R.string.reset_default_values), icon = Icons.Outlined.Refresh,
                { onResetConfig.invoke() }, remember {
                mutableStateOf(false)
            })
        }
        item {
            ItemSetting(text = stringResource(id = R.string.see_hidden_links), icon = Icons.Outlined.Lock, { onWatchProtectedLinks.invoke()}, applyValidatePassword)
        }
        item {
            ItemSettingSwitch(text = stringResource(id = R.string.dark_mode), icon = Icons.Outlined.Lock, isDarkTheme)
        }
        item {
            ItemSettingColor(text = stringResource(id = R.string.change_color_scheme), icon = Icons.Outlined.Create, {onSelectAppColor.invoke()})
        }
        item {
            ItemSetting(text = stringResource(id = R.string.about_the_app), icon = Icons.Outlined.Info, {onClickAboutApp.invoke()}, remember {
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
            Icon(imageVector =  icon, contentDescription = "", tint = MaterialTheme.colorScheme.onTertiaryContainer)
            Text(
                text = text,
                modifier = Modifier.padding(start = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,)
        }
        Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
    }
    if (showResult.value) {
        onClick.invoke()
        showResult.value = false
    }
}

@Composable
private fun ItemSettingColor(text: String, icon: ImageVector, onClick: @Composable () -> Unit){
    val showResult = remember {
        mutableStateOf(false)
    }
    Column (modifier = Modifier.clickable { showResult.value = true }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)){
            Row(){
                Icon(imageVector =  icon, contentDescription = "", tint = MaterialTheme.colorScheme.onTertiaryContainer)
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,)
            }

            Icon(imageVector =  Icons.Default.Favorite , contentDescription = "", tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(end = 16.dp))
        }
        Divider(color = MaterialTheme.colorScheme.primary, thickness = 1.dp)
    }

    if (showResult.value) {
        onClick.invoke()
        showResult.value = false
    }
}

@Composable
private fun ItemSettingSwitch(text: String, icon: ImageVector,  switchChecked: MutableState<Boolean>){
    Column (modifier = Modifier.clickable {
        switchChecked.value = !switchChecked.value
    }) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp)){
            Row(){
                Icon(imageVector =  icon, contentDescription = "", tint = MaterialTheme.colorScheme.onTertiaryContainer)
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,)
            }
            Switch(
                checked = switchChecked.value,
                onCheckedChange = {
                    switchChecked.value = it
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
        Settings(remember{mutableStateOf(false)},{},{}, {}, {} )
    }
}