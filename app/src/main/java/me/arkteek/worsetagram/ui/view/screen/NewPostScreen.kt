package me.arkteek.worsetagram.ui.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import me.arkteek.worsetagram.ui.view.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.view.component.HeaderBar

@Composable
fun NewPostScreen(changePage: (String) -> Unit) {
  Scaffold(
      topBar = { HeaderBar(title = "New Post") },
      bottomBar = { BottomNavigationBar(changePage) }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {}
      }
}
