package me.arkteek.worsetagram.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import me.arkteek.worsetagram.ui.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.component.SearchBar
import me.arkteek.worsetagram.ui.viewModel.AuthViewModel

@Composable
fun SearchScreen(viewModel: AuthViewModel?, navController: NavHostController) {
  viewModel?.currentUser?.let {
    Scaffold(
      topBar = {
        var searchText by remember { mutableStateOf("") }

        SearchBar(
          searchText = searchText,
          onSearchTextChanged = { newText -> searchText = newText },
        )
      },
      bottomBar = { BottomNavigationBar(navController) },
    ) { paddingValues ->
      Column(
        modifier =
          Modifier.padding(paddingValues).fillMaxSize().verticalScroll(rememberScrollState())
      ) {}
    }
  }
}
