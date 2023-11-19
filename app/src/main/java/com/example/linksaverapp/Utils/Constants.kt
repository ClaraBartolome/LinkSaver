package com.example.linksaverapp.Utils

import androidx.compose.ui.graphics.Color
import com.example.linksaverapp.R
import com.example.linksaverapp.ui.theme.darkGreen
import com.example.linksaverapp.ui.theme.darkRed
import com.example.linksaverapp.ui.theme.green100
import com.example.linksaverapp.ui.theme.mediumGreen
import com.example.linksaverapp.ui.theme.mediumRed
import com.example.linksaverapp.ui.theme.red100

enum class LinkScreens(){
    Start,
    Add,
    Settings,
    SortingConfig,
    Edit,
    ChangeColor,
    AboutApp
}

enum class BottomBarOption(){
    AddFolder,
    AddFavorite,
    Share,
    Edit,
    Delete,
    None
}

enum class SearchWidgetState {
    OPENED,
    CLOSED
}

enum class SortRadioOptions(val text: String){
    NameAZ("Nombre (A-Z)"),
    NameZA("Nombre (Z-A)"),
    CreationDateNewFirst("Fecha de creación (Más recientes primero"),
    CreationDateOldFirst("Fecha de creación (Más antiguos primero"),
    ModDateNewFirst("Fecha de modificacion (Más recientes primero"),
    ModDateOldFirst("Fecha de modificación (Más antiguos primero"),
}

enum class ColorThemeOptions(val lightColor: Color, val containerLightColor: Color, val darkColor: Color ){
    Green(lightColor = mediumGreen, containerLightColor = green100, darkColor = darkGreen),
    Red(lightColor = mediumRed, containerLightColor = red100, darkColor = darkRed)
}

val colorOptions = listOf(
    ColorThemeOptions.Green,
    ColorThemeOptions.Red
)

val radioOptions = listOf(
    SortRadioOptions.NameAZ,
    SortRadioOptions.NameZA,
    SortRadioOptions.CreationDateNewFirst,
    SortRadioOptions.CreationDateOldFirst,
    SortRadioOptions.ModDateNewFirst,
    SortRadioOptions.ModDateOldFirst,
)

//region Text
    val shortText = "Lorem ipsum dolor sit amet"
    val longText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vitae nibh congue, tempor dolor eu, dictum ipsum. Cras auctor felis justo, dapibus lacinia ligula cursus ut. Morbi quis tincidunt nibh. Pellentesque sed est eu mauris dictum finibus quis ut leo. Sed consequat venenatis mi in viverra. Mauris sed porta arcu. Nulla ac lorem imperdiet, consequat urna et, aliquet nulla. Sed scelerisque fringilla ligula, a rhoncus justo iaculis eget. Proin varius scelerisque enim in mollis."
    //endregion

    val favoritesStringID = R.string.favorites
