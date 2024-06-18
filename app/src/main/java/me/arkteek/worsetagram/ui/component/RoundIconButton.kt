package me.arkteek.worsetagram.ui.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun RoundIconButton(icon: ImageVector, onClick: () -> Unit) {
  IconButton(
    onClick = onClick,
    modifier = Modifier.size(30.dp).border(1.dp, Color.Black, CircleShape),
  ) {
    Icon(imageVector = icon, contentDescription = null, tint = Color.Black)
  }
}
