package me.arkteek.worsetagram.ui.view.screen

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
import me.arkteek.worsetagram.ui.view.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.view.component.SearchBar

@Composable
fun SearchScreen(changePage: (String) -> Unit) {
  Scaffold(
      topBar = {
        var searchText by remember { mutableStateOf("") }

        SearchBar(
            searchText = searchText, onSearchTextChanged = { newText -> searchText = newText })
      },
      bottomBar = { BottomNavigationBar(changePage) }) { paddingValues ->
        Column(
            modifier =
                Modifier.padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())) {}
      }
}
