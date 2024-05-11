package me.arkteek.worsetagram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.arkteek.worsetagram.ui.AppNavHost
import me.arkteek.worsetagram.ui.theme.WorsetagramTheme
import me.arkteek.worsetagram.ui.viewModel.AuthViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val authViewModel by viewModels<AuthViewModel>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      WorsetagramTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          AppNavHost(authViewModel)
        }
      }
    }
  }
}
