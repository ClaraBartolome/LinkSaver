package com.example.linksaverapp.compose.compose

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linksaverapp.R
import com.example.linksaverapp.Utils.BottomBarOption
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

@Composable
fun BottomBarConfig(
    isFavorite: Boolean,
    onDeleteLink: @Composable() () -> Unit,
    onShareLink: () -> Unit,
    onEditLink: @Composable() () -> Unit,
    onFavLink: @Composable() () -> Unit,
    onAddToFolder: @Composable() () -> Unit) {
    Column(
        modifier = Modifier.padding(start = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        val optionChosen = remember {
            mutableStateOf(BottomBarOption.None)
        }
        ItemBottomBar(name = stringResource(id = R.string.add_to_folder), icon = R.drawable.ic_add) { optionChosen.value = BottomBarOption.AddFolder }
        Spacer(modifier = Modifier.height(8.dp))
        ItemBottomBar(name = if(isFavorite) stringResource(id = R.string.remove_from_favorites) else stringResource(id = R.string.add_to_favorites), icon = if(isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border) { optionChosen.value = BottomBarOption.AddFavorite }
        Spacer(modifier = Modifier.height(8.dp))
        ItemBottomBar(name = stringResource(id = R.string.share), icon = R.drawable.ic_share) {optionChosen.value = BottomBarOption.Share}
        Spacer(modifier = Modifier.height(8.dp))
        ItemBottomBar(name = stringResource(id = R.string.edit), icon = R.drawable.ic_edit) {optionChosen.value = BottomBarOption.Edit}
        Spacer(modifier = Modifier.height(8.dp))
        ItemBottomBar(name = stringResource(id = R.string.delete), icon = R.drawable.ic_delete, "") { optionChosen.value = BottomBarOption.Delete }

        when(optionChosen.value){
            BottomBarOption.Delete -> onDeleteLink()
            BottomBarOption.Share -> onShareLink()
            BottomBarOption.AddFavorite -> onFavLink()
            BottomBarOption.Edit -> onEditLink()
            BottomBarOption.AddFolder -> onAddToFolder()
            else -> {}
        }

    }
}

@Composable
private fun ShowToast() {
    Toast.makeText(
        LocalContext.current, "PROXIMAMENTE", Toast.LENGTH_SHORT
    ).show()
}

@Composable
private fun ItemBottomBar(
    name: String,
    icon: Int,
    contentDescription: String = "",
    action: () -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = { action.invoke() })) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = contentDescription,
            colorFilter = ColorFilter.tint(color = getColor())
        )
        Text(text = name, modifier = Modifier.padding(start = 8.dp), color = getColor())
    }
}

@Composable
private fun getColor(): Color {
    return if (isSystemInDarkTheme()) {
        Color.White
    } else Color.Black
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DefaultBottombarPreview() {
    LinkSaverAppTheme {
        BottomBarConfig(false, {}, {}, {}, {}, {})
    }
}