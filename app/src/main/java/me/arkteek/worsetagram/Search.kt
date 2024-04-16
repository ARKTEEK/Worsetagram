package me.arkteek.worsetagram

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.arkteek.worsetagram.ui.BottomNavigationBar

@Composable
fun Search(changePage: (String) -> Unit) {
  Scaffold(topBar = { Header() }, bottomBar = { BottomNavigationBar(changePage) }) { paddingValues
    ->
    Column(
        modifier =
            Modifier.padding(paddingValues).fillMaxSize().verticalScroll(rememberScrollState())) {}
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Header() {
  var searchText by remember { mutableStateOf("") }

  TopAppBar(
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(36.dp)
            .background(Color.White, RoundedCornerShape(36.dp))
            .border(
              width = 0.5.dp,
              color = Color.LightGray,
              shape = RoundedCornerShape(36.dp)
            )
            .padding(horizontal = 16.dp),
          contentAlignment = Alignment.CenterStart
        ) {
          Icon(
            Icons.Default.Search,
            contentDescription = "Search Icon",
            modifier = Modifier.padding(start = 0.dp)
          )

          BasicTextField(
            value = searchText,
            onValueChange = { searchText = it },
            singleLine = true,
            modifier = Modifier
              .fillMaxWidth()
              .padding(start = 28.dp, end = 28.dp)
              .align(Alignment.Center),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
          )

          if (searchText.isNotEmpty()) {
            IconButton(
              onClick = { searchText = "" },
              modifier = Modifier
                .align(Alignment.CenterEnd)
            ) {
              Icon(Icons.Default.Clear, contentDescription = "Clear Search")
            }
          }
        }
      }
    },
    actions = {
      IconButton(onClick = {}) {
        Icon(Icons.Default.MoreVert, contentDescription = null, modifier = Modifier.size(26.dp))
      }
    }
  )
}

