package com.example.testcomposeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.testcomposeapp.compose.compose.CreateUI
import com.example.testcomposeapp.db.LinksSaverApplication
import com.example.testcomposeapp.ui.theme.TestComposeAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val linkSaverViewModel: LinkSaverViewModel by viewModels {
        LinkSaverViewModelFactory((application as LinksSaverApplication).repository)
    }

    val TAG = "MAIN_ACTIVITY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestComposeAppTheme {
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
