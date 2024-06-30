package me.arkteek.worsetagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dagger.hilt.android.AndroidEntryPoint
import me.arkteek.worsetagram.ui.navigation.AppNavHost
import me.arkteek.worsetagram.ui.theme.WorsetagramTheme
import me.arkteek.worsetagram.ui.screen.authentication.AuthViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val authViewModel by viewModels<AuthViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      WorsetagramTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
          AppNavHost(authViewModel)
        }
      }
    }
  }
}
