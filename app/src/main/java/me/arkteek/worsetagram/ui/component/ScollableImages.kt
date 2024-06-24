package me.arkteek.worsetagram.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

@Composable
fun ImageScrollWithTextOverlay(images: List<String>) {
  val screenWidth = LocalConfiguration.current.screenWidthDp

  images.forEachIndexed { index, imageUrl ->
    Box(modifier = Modifier.width(screenWidth.dp).aspectRatio(1f)) {
      AsyncImage(
        model = imageUrl,
        contentDescription = null,
        modifier = Modifier.fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
      )

      Row(modifier = Modifier.align(Alignment.TopEnd).padding(horizontal = 4.dp, vertical = 8.dp)) {
        Box(
          modifier =
            Modifier.background(Color.Black.copy(alpha = 0.5f), CircleShape)
              .padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
          Text(
            text = "${index + 1}/${images.size}",
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp),
          )
        }
      }
    }
  }
}
