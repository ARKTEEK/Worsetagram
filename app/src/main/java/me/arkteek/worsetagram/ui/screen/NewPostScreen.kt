package me.arkteek.worsetagram.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import me.arkteek.worsetagram.ui.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.component.HeaderBar
import me.arkteek.worsetagram.ui.viewModel.AuthViewModel

@Composable
fun NewPostScreen(viewModel: AuthViewModel?, navController: NavHostController) {
  viewModel?.currentUser?.let {
    Scaffold(
      topBar = { HeaderBar(title = "New Post") },
      bottomBar = { BottomNavigationBar(navController) },
    ) { paddingValues ->
      Column(modifier = Modifier.padding(paddingValues)) {}
    }
  }
}
