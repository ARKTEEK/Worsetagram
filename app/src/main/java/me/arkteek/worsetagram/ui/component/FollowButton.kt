package me.arkteek.worsetagram.ui.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FollowButton(
  isFollowing: Boolean,
  onToggleFollow: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
) {
  Button(
    onClick = { onToggleFollow(!isFollowing) },
    colors = ButtonDefaults.buttonColors(if (isFollowing) Color.Gray else Color(0xFF1E88E5)),
    modifier = modifier.height(45.dp).width(160.dp),
  ) {
    Text(
      text = if (isFollowing) "Following" else "Follow",
      color = if (isFollowing) Color.White else Color.White,
      fontSize = 16.sp,
    )
  }
}
