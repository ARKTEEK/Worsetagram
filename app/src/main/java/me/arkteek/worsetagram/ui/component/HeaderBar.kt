package me.arkteek.worsetagram.ui.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderBar(title: String, customActions: List<@Composable () -> Unit> = emptyList()) {
  TopAppBar(
    title = { Text(title, fontWeight = FontWeight.Bold, fontSize = 20.sp) },
    actions = { customActions.forEach { action -> action() } },
  )
}
