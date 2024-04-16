package me.arkteek.worsetagram.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import me.arkteek.worsetagram.R

@Composable
fun BottomNavigationBar(changePage: (String) -> Unit) {
  BottomAppBar(containerColor = Color(0xFFFAFAFA)) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
      // Create an icon button for home
      IconButton(onClick = { changePage("Home") }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
              painter = painterResource(R.drawable.ic_home),
              contentDescription = "Home Icon",
              modifier = Modifier.size(25.dp))
        }
      }
      // Create an icon button for search
      IconButton(onClick = { changePage("Search") }) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
              painter = painterResource(R.drawable.search_line_icon),
              contentDescription = "Search Icon",
              modifier = Modifier.size(25.dp))
        }
      }
      // Create an icon button for add
      IconButton(onClick = { /* do something */}) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
              painter = painterResource(R.drawable.plus_square_line_icon),
              contentDescription = "Add Icon",
              modifier = Modifier.size(25.dp))
        }
      }
      // Create an icon button for media
      IconButton(onClick = { /* do something */}) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()) {
              Icon(
                  painter = painterResource(R.drawable.ic_reels),
                  contentDescription = "Media Icon",
                  modifier = Modifier.size(28.dp))
            }
      }
      // Create an icon button for profile picture
      IconButton(onClick = { /* do something */}) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()) {
              AsyncImage(
                  model = "https://i.imgur.com/qXqDyoe.png",
                  contentDescription = null,
                  modifier = Modifier.size(27.dp).clip(CircleShape).clickable(onClick = {}),
                  contentScale = ContentScale.Crop)
            }
      }
    }
  }
}
