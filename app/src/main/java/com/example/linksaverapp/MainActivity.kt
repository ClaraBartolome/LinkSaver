package com.example.linksaverapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.linksaverapp.compose.compose.CreateUI
import com.example.linksaverapp.db.LinksSaverApplication
import com.example.linksaverapp.ui.theme.LinkSaverAppTheme

class MainActivity : ComponentActivity() {

    private val linkSaverViewModel: LinkSaverViewModel by viewModels {
        LinkSaverViewModelFactory((application as LinksSaverApplication).repository)
    }

    val TAG = "MAIN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinkSaverAppTheme (darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    CreateUI(linkSaverViewModel)
                }
            }
        }
    }

}
