package me.arkteek.worsetagram.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderBar(
  title: String,
  leftActions: List<@Composable () -> Unit> = emptyList(),
  rightActions: List<@Composable () -> Unit> = emptyList(),
) {
  Surface(color = Color.White, modifier = Modifier.fillMaxWidth()) {
    Box(modifier = Modifier.fillMaxWidth()) {
      TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
        title = {
          Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color.Black)
        },
        modifier = Modifier.fillMaxWidth(),
        actions = {
          Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            rightActions.forEach { action -> action() }
          }
        },
        navigationIcon = {
          Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
          ) {
            leftActions.forEach { action -> action() }
          }
        },
      )
    }
  }
}
