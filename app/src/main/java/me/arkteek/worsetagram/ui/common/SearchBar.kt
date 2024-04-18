package me.arkteek.worsetagram.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChanged: (String) -> Unit,
) {
  TopAppBar(
      title = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
              SearchBox(searchText = searchText, onSearchTextChanged = onSearchTextChanged)
            }
      },
      actions = {
        IconButton(onClick = {}) {
          Icon(Icons.Default.MoreVert, contentDescription = null, modifier = Modifier.size(26.dp))
        }
      })
}

@Composable
private fun SearchBox(searchText: String, onSearchTextChanged: (String) -> Unit) {
  Box(
      modifier =
          Modifier.fillMaxWidth()
              .height(36.dp)
              .background(Color.White, RoundedCornerShape(36.dp))
              .border(width = 0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(36.dp))
              .padding(horizontal = 16.dp),
      contentAlignment = Alignment.CenterStart) {
        Icon(
            Icons.Default.Search,
            contentDescription = "Search Icon",
            modifier = Modifier.padding(start = 0.dp))

        BasicTextField(
            value = searchText,
            onValueChange = { onSearchTextChanged(it) },
            singleLine = true,
            modifier =
                Modifier.fillMaxWidth().padding(start = 28.dp, end = 28.dp).align(Alignment.Center),
            textStyle = TextStyle(color = Color.Black, fontSize = 15.sp))

        if (searchText.isNotEmpty()) {
          IconButton(
              onClick = { onSearchTextChanged("") },
              modifier = Modifier.align(Alignment.CenterEnd)) {
                Icon(Icons.Default.Clear, contentDescription = "Clear Search")
              }
        }
      }
}
