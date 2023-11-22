package com.example.linksaverapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import com.example.linksaverapp.Utils.ColorThemeOptions
import com.example.linksaverapp.compose.compose.CreateUI
import com.example.linksaverapp.compose.getColorTheme
import com.example.linksaverapp.compose.getDarkMode
import com.example.linksaverapp.db.LinksSaverApplication
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

class MainActivity : FragmentActivity() {

    private val linkSaverViewModel: LinkSaverViewModel by viewModels {
        LinkSaverViewModelFactory((application as LinksSaverApplication).repository)
    }

    val TAG = "MAIN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme = remember { mutableStateOf(getDarkMode(this)) }
            val colorChosen = remember { mutableStateOf(getColorTheme(this)) }
            LinkSaverAppTheme (darkTheme = isDarkTheme.value, color = colorChosen.value) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CreateUI(linkSaverViewModel, isDarkTheme = isDarkTheme, colorChosen = colorChosen)
                }
            }
        }
    }

}
