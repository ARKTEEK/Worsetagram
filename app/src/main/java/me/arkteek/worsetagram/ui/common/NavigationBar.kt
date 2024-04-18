package me.arkteek.worsetagram.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import me.arkteek.worsetagram.R

@Composable
fun BottomNavigationBar(changePage: (String) -> Unit) {
  BottomAppBar(containerColor = Color(0xFFFAFAFA)) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
      IconButton(onClick = { changePage("Home") }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
              painter = painterResource(R.drawable.ic_home),
              contentDescription = "Home Icon",
              modifier = Modifier.size(25.dp))
        }
      }
      IconButton(onClick = { changePage("Search") }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
              painter = painterResource(R.drawable.search_line_icon),
              contentDescription = "Search Icon",
              modifier = Modifier.size(25.dp))
        }
      }
      IconButton(onClick = { /* do something */}) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
              painter = painterResource(R.drawable.plus_square_line_icon),
              contentDescription = "Add Icon",
              modifier = Modifier.size(25.dp))
        }
      }
      IconButton(onClick = { changePage("Messages") }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()) {
              Icon(
                  painter = painterResource(R.drawable.ic_messages),
                  contentDescription = "Messages Icon",
                  modifier = Modifier.size(28.dp))
            }
      }
      IconButton(onClick = { changePage("Profile") }) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()) {
              Icon(
                  painter = painterResource(R.drawable.ic_profile),
                  contentDescription = "Profile Icon",
                  modifier = Modifier.size(28.dp))
            }
      }
    }
  }
}
