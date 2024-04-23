package me.arkteek.worsetagram.ui.view.screen

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsScreen() {
  var name by remember { mutableStateOf(TextFieldValue("John Doe")) }
  var email by remember { mutableStateOf(TextFieldValue("johndoe@example.com")) }
  var bio by remember { mutableStateOf(TextFieldValue("Hello, I'm John!")) }
}
