package com.example.linksaverapp.compose

import android.app.KeyguardManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.linksaverapp.LinkSaverViewModel
import com.example.linksaverapp.R
import com.example.linksaverapp.Utils.ColorThemeOptions
import com.example.linksaverapp.Utils.PREFERENCES_COLOR_THEME
import com.example.linksaverapp.Utils.PREFERENCES_DARK_MODE
import com.example.linksaverapp.Utils.PREFERENCE_FILE
import com.example.linksaverapp.Utils.SortRadioOptions
import com.example.linksaverapp.compose.compose.TAG
import com.example.linksaverapp.compose.viewHelperVar.favoritesString
import com.example.linksaverapp.db.Model.LinkModel
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.Executor


object viewHelperVar {
    var favoritesString = ""
}

fun saveConfig(activity: FragmentActivity, isDarkTheme: MutableState<Boolean>, colorTheme: MutableState<ColorThemeOptions>, navController: NavHostController){
    setColorTheme(activity, colorTheme = colorTheme)
    setDarkMode(activity, isDarkTheme = isDarkTheme)
    navController.popBackStack()
}

fun getDarkMode(activity: FragmentActivity): Boolean {
    return activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE)
        .getBoolean(PREFERENCES_DARK_MODE, false)
}

fun setDarkMode(activity: FragmentActivity, isDarkTheme: MutableState<Boolean>) {
    activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putBoolean(
        PREFERENCES_DARK_MODE, isDarkTheme.value
    ).apply()
}

fun getColorTheme(activity: FragmentActivity): ColorThemeOptions {
    return ColorThemeOptions.toColorThemeOption(
        activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).getString(
            PREFERENCES_COLOR_THEME, ColorThemeOptions.Gray.name
        )
    )
}

fun setColorTheme(activity: FragmentActivity, colorTheme: MutableState<ColorThemeOptions>) {
    activity.getSharedPreferences(PREFERENCE_FILE, Context.MODE_PRIVATE).edit().putString(
        PREFERENCES_COLOR_THEME, colorTheme.value.name
    ).apply()
}

private fun validateLinkModel(name: String, link: String): Boolean {
    return name.isNotBlank() && link.isNotBlank()
}

private fun validateFolderName(
    name: String?,
    folderMap: MutableMap<String, MutableList<LinkModel>>
): Boolean {
    name?.let { folderName ->
        if (folderName.isBlank() || folderName.trimStart().trimEnd() != favoritesString) {
            return true
        }
        folderMap[favoritesString]?.let { list ->
            return list.size < 5
        }
        return true
    }
    return true
}

fun copyToClipboard(text: String, activity: FragmentActivity) {
    val clipboard: ClipboardManager =
        activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Simple text", text)
    clipboard.setPrimaryClip(clip)
}

@Composable
fun validatePassword(
    context: Context,
    activity: FragmentActivity,
    executor: Executor,
    isDeviceUnlocked: MutableState<Boolean>
) {
    if (isDeviceSecured(context) && !isDeviceUnlocked.value) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(stringResource(id = R.string.biometric_login_label))
            .setSubtitle(stringResource(id = R.string.biometric_login_subtitle))
            .setAllowedAuthenticators(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
            .build()

        val error = stringResource(id = R.string.error)
        val succeeded = stringResource(id = R.string.succeeded)
        val failed = stringResource(id = R.string.failed)
        val biometricPrompt = BiometricPrompt(activity, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Toast.makeText(
                        context,
                        error,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Toast.makeText(context, succeeded, Toast.LENGTH_SHORT).show()
                    isDeviceUnlocked.value = true
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Toast.makeText(
                        context, failed, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )

        biometricPrompt.authenticate(promptInfo)
    } else {
        val text =
            if (isDeviceUnlocked.value) stringResource(id = R.string.device_unlocked) else stringResource(id = R.string.set_password)
        Toast.makeText(
            context, text, Toast.LENGTH_LONG
        ).show()
    }
}

private fun isDeviceSecured(context: Context): Boolean {
    val keyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    return keyguardManager.isKeyguardSecure
}

fun getDate(): String {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return formatter.format(time)
}

fun sortFolderList(
    links: List<LinkModel>?,
    folderList: MutableMap<String, MutableList<LinkModel>>
) {
    folderList.clear()
    links?.let { list ->
        var lastName = list.first().folder ?: ""
        val mutableListAux = mutableListOf<LinkModel>()
        list.sortedBy { it.folder }.forEach { linkModel ->
            linkModel.folder?.let { folderName ->
                Log.i("DEBUG", "Entrada: " + linkModel.name)
                if (lastName != folderName) {
                    folderList[lastName] = mutableListAux.toMutableList()
                    lastName = folderName
                    mutableListAux.clear()
                    mutableListAux.add(linkModel)
                } else {
                    mutableListAux.add(linkModel)
                }
            }
        }
        folderList[lastName] = mutableListAux.toMutableList()
    }
}

@Composable
fun SortTree(option: SortRadioOptions, linkSaverViewModel: LinkSaverViewModel) {
    when (option) {
        SortRadioOptions.NameAZ -> {
            GetAllLinksByNameAsc(linkSaverViewModel = linkSaverViewModel)
        }

        SortRadioOptions.NameZA -> {
            GetAllLinksByNameDesc(linkSaverViewModel = linkSaverViewModel)
        }

        SortRadioOptions.CreationDateNewFirst -> {
            GetAllLinksByDateOfCreationAsc(linkSaverViewModel = linkSaverViewModel)
        }

        SortRadioOptions.CreationDateOldFirst -> {
            GetAllLinksByDateOfCreationDesc(linkSaverViewModel = linkSaverViewModel)
        }

        SortRadioOptions.ModDateNewFirst -> {
            GetAllLinksByDateOfModifiedAsc(linkSaverViewModel = linkSaverViewModel)
        }

        SortRadioOptions.ModDateOldFirst -> {
            GetAllLinksByDateOfModifiedDesc(linkSaverViewModel = linkSaverViewModel)
        }
    }
}


fun shareLink(context: Context, name: String, link: String) {
    val sendIntent = Intent(
        Intent.ACTION_SEND
    )
        .setType("text/plain")
    sendIntent.putExtra(Intent.EXTRA_TEXT, name + "\n\n" + link)
    context.startActivity(Intent.createChooser(sendIntent, "Share using:"))
}

fun openLink(context: Context, url: String) {
    val urlIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse(url)
    )
    context.startActivity(urlIntent)
}

fun sortedLinkList(links: List<LinkModel>?, searchText: String): List<LinkModel> {
    links?.let {
        return it.filter { linkModel ->
            linkModel.name.lowercase().contains(searchText.lowercase())
        }.sortedBy { it.name }
    } ?: run {
        return listOf<LinkModel>()
    }
}

//region ORDER
@Composable
private fun GetAllLinksByNameAsc(linkSaverViewModel: LinkSaverViewModel) {
    LaunchedEffect(Unit) {
        linkSaverViewModel.getAllLinksByNameAsc()
        Log.i(TAG, "DB created size: ${linkSaverViewModel.allLinks.value?.size}")
    }
}

@Composable
private fun GetAllLinksByNameDesc(linkSaverViewModel: LinkSaverViewModel) {
    LaunchedEffect(Unit) {
        linkSaverViewModel.getAllLinksByNameDesc()
        Log.i(TAG, "DB created size: ${linkSaverViewModel.allLinks.value?.size}")
    }
}

@Composable
private fun GetAllLinksByDateOfCreationAsc(linkSaverViewModel: LinkSaverViewModel) {
    LaunchedEffect(Unit) {
        linkSaverViewModel.getAllLinksByDateOfCreationAsc()
        Log.i(TAG, "DB created size: ${linkSaverViewModel.allLinks.value?.size}")
    }
}

@Composable
private fun GetAllLinksByDateOfCreationDesc(linkSaverViewModel: LinkSaverViewModel) {
    LaunchedEffect(Unit) {
        linkSaverViewModel.getAllLinksByDateOfCreationDesc()
        Log.i(TAG, "DB created size: ${linkSaverViewModel.allLinks.value?.size}")
    }
}

@Composable
private fun GetAllLinksByDateOfModifiedAsc(linkSaverViewModel: LinkSaverViewModel) {
    LaunchedEffect(Unit) {
        linkSaverViewModel.getAllLinksByDateOfModifiedAsc()
        Log.i(TAG, "DB created size: ${linkSaverViewModel.allLinks.value?.size}")
    }
}

@Composable
private fun GetAllLinksByDateOfModifiedDesc(linkSaverViewModel: LinkSaverViewModel) {
    LaunchedEffect(Unit) {
        linkSaverViewModel.getAllLinksByDateOfModifiedDesc()
        Log.i(TAG, "DB created size: ${linkSaverViewModel.allLinks.value?.size}")
    }
}

//endregion


fun insertLink(
    linkSaverViewModel: LinkSaverViewModel,
    name: MutableState<String>,
    link: MutableState<String>,
    folder: MutableState<String>,
    isProtected: MutableState<Boolean>,
    linkModelIsValid: MutableState<Boolean>,
    folderNameIsValid: MutableState<Boolean>,
    folderMap: MutableMap<String, MutableList<LinkModel>>
) {
    linkModelIsValid.value = validateLinkModel(name.value, link.value)
    folderNameIsValid.value = validateFolderName(folder.value, folderMap)
    if (linkModelIsValid.value && folderNameIsValid.value) {
        linkSaverViewModel.insert(
            LinkModel(
                name = name.value,
                link = link.value,
                dateOfCreation = getDate(),
                dateOfModified = getDate(),
                folder = folder.value,
                isProtected = if (isProtected.value) 1 else 0,
            )
        )
    }
}


fun updateLink(
    linkSaverViewModel: LinkSaverViewModel,
    link: LinkModel,
    folderNameIsValid: MutableState<Boolean>,
    folderMap: MutableMap<String, MutableList<LinkModel>>
) {
    folderNameIsValid.value = validateFolderName(link.folder, folderMap)
    if (folderNameIsValid.value) {
        linkSaverViewModel.updateLink(link)
        Log.i(TAG, "DB deleted link: $link")
    }
}

@Composable
fun DeleteLink(linkSaverViewModel: LinkSaverViewModel, link: LinkModel) {
    LaunchedEffect(Unit) {
        linkSaverViewModel.deleteLink(link)
        Log.i(TAG, "DB deleted link: $link")
    }
}