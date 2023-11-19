package com.example.linksaverapp.compose.compose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.linksaverapp.Utils.ColorThemeOptions
import com.example.linksaverapp.Utils.colorOptions
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme
import com.example.linksaverapp.ui.theme.colorSelected

@Composable
fun SelectAppColorScreen(colorChosen: MutableState<ColorThemeOptions>, colorOptions: List<ColorThemeOptions>) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 64.dp),
        modifier = Modifier.padding(8.dp)){
        colorOptions.forEach { colorOption ->
            item{
                ItemGrid(colorOption, onClick = {
                    colorChosen.value = colorOption
                },
                    border = if(colorChosen.value != colorOption) null else BorderStroke(2.dp, colorSelected))
            }
        }
    }
}

@Composable
private fun ItemGrid(color: ColorThemeOptions, onClick: () -> Unit, border: BorderStroke?){
    OutlinedIconButton(
        enabled = true,
        shape = CircleShape,
        border = border,
        onClick = {onClick.invoke()},
        modifier = Modifier.requiredSize(64.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "",
            tint = color.lightColor,
            modifier = Modifier.size(48.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AddLinkScreenPreview() {
    LinkSaverAppTheme {
        SelectAppColorScreen(
            colorChosen = remember { mutableStateOf(ColorThemeOptions.Green) },
            colorOptions = colorOptions
        )
    }
}