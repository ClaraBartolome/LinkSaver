package com.example.linksaverapp.Utils

import androidx.compose.ui.graphics.Color
import com.example.linksaverapp.R
import com.example.linksaverapp.ui.theme.containerLightBlue
import com.example.linksaverapp.ui.theme.containerLightGray
import com.example.linksaverapp.ui.theme.containerLightGreen
import com.example.linksaverapp.ui.theme.containerLightPurple
import com.example.linksaverapp.ui.theme.containerLightRed
import com.example.linksaverapp.ui.theme.containerLightTeal
import com.example.linksaverapp.ui.theme.containerLightViolet
import com.example.linksaverapp.ui.theme.containerLightYellow
import com.example.linksaverapp.ui.theme.toolbarDarkBlue
import com.example.linksaverapp.ui.theme.toolbarDarkGray
import com.example.linksaverapp.ui.theme.toolbarDarkGreen
import com.example.linksaverapp.ui.theme.toolbarDarkPurple
import com.example.linksaverapp.ui.theme.toolbarDarkRed
import com.example.linksaverapp.ui.theme.toolbarDarkTeal
import com.example.linksaverapp.ui.theme.toolbarDarkViolet
import com.example.linksaverapp.ui.theme.toolbarDarkYellow
import com.example.linksaverapp.ui.theme.toolbarLightBlue
import com.example.linksaverapp.ui.theme.toolbarLightGray
import com.example.linksaverapp.ui.theme.toolbarLightGreen
import com.example.linksaverapp.ui.theme.toolbarLightPurple
import com.example.linksaverapp.ui.theme.toolbarLightRed
import com.example.linksaverapp.ui.theme.toolbarLightTeal
import com.example.linksaverapp.ui.theme.toolbarLightViolet
import com.example.linksaverapp.ui.theme.toolbarLightYellow
import java.lang.Enum
import kotlin.Exception
import kotlin.String

enum class LinkScreens() {
    Start,
    Add,
    Settings,
    SortingConfig,
    Edit,
    ChangeColor,
    AboutApp
}

enum class BottomBarOption() {
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

enum class SortRadioOptions(val text: String) {
    NameAZ("Nombre (A-Z)"),
    NameZA("Nombre (Z-A)"),
    CreationDateNewFirst("Fecha de creación (Más recientes primero"),
    CreationDateOldFirst("Fecha de creación (Más antiguos primero"),
    ModDateNewFirst("Fecha de modificacion (Más recientes primero"),
    ModDateOldFirst("Fecha de modificación (Más antiguos primero"),
}

enum class ColorThemeOptions(
    val lightColor: Color,
    val containerLightColor: Color,
    val darkColor: Color
) {
    Green(
        lightColor = toolbarLightGreen,
        containerLightColor = containerLightGreen,
        darkColor = toolbarDarkGreen
    ),
    Red(
        lightColor = toolbarLightRed,
        containerLightColor = containerLightRed,
        darkColor = toolbarDarkRed
    ),
    Blue(
        lightColor = toolbarLightBlue,
        containerLightColor = containerLightBlue,
        darkColor = toolbarDarkBlue
    ),
    Yellow(
        lightColor = toolbarLightYellow,
        containerLightColor = containerLightYellow,
        darkColor = toolbarDarkYellow
    ),
    Violet(
        lightColor = toolbarLightViolet,
        containerLightColor = containerLightViolet,
        darkColor = toolbarDarkViolet
    ),
    Purple(
        lightColor = toolbarLightPurple,
        containerLightColor = containerLightPurple,
        darkColor = toolbarDarkPurple
    ),
    Gray(
        lightColor = toolbarLightGray,
        containerLightColor = containerLightGray,
        darkColor = toolbarDarkGray
    ),
    Teal(
        lightColor = toolbarLightTeal,
        containerLightColor = containerLightTeal,
        darkColor = toolbarDarkTeal
    ),
    Black(
        lightColor = Color.DarkGray,
        containerLightColor = Color.LightGray,
        darkColor = Color.DarkGray
    );

    companion object {
        fun toColorThemeOption(name: String?): ColorThemeOptions {
            try {
                name?.let{
                     return ColorThemeOptions.valueOf(name)
                }
               return Gray
            } catch (ex: Exception) {
                return Gray
            }
        }
    }
}

val colorOptions = listOf(
    ColorThemeOptions.Gray,
    ColorThemeOptions.Black,
    ColorThemeOptions.Yellow,
    ColorThemeOptions.Red,
    ColorThemeOptions.Green,
    ColorThemeOptions.Blue,
    ColorThemeOptions.Teal,
    ColorThemeOptions.Violet,
    ColorThemeOptions.Purple,
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
val longText =
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam vitae nibh congue, tempor dolor eu, dictum ipsum. Cras auctor felis justo, dapibus lacinia ligula cursus ut. Morbi quis tincidunt nibh. Pellentesque sed est eu mauris dictum finibus quis ut leo. Sed consequat venenatis mi in viverra. Mauris sed porta arcu. Nulla ac lorem imperdiet, consequat urna et, aliquet nulla. Sed scelerisque fringilla ligula, a rhoncus justo iaculis eget. Proin varius scelerisque enim in mollis."
//endregion

val favoritesStringID = R.string.favorites
val PREFERENCES_DARK_MODE = "DARK_MODE"
val PREFERENCES_COLOR_THEME = "COLOR_THEME"
val PREFERENCE_FILE = "PREFERENCE_FILE"


