package com.example.linksaverapp.Utils

import com.example.linksaverapp.R

enum class LinkScreens(){
    Start,
    Add,
    Settings,
    SortingConfig,
    Edit
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
