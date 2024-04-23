package me.arkteek.worsetagram.ui.view.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.domain.model.Post
import me.arkteek.worsetagram.ui.view.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.view.component.HeaderBar

@Composable
fun HomeScreen(changePage: (String) -> Unit) {
  Scaffold(
      topBar = {
        HeaderBar(
            title = "Worsetagram",
            customActions =
                listOf {
                  IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(R.drawable.ic_heart),
                        contentDescription = "Heart Icon",
                        modifier = Modifier.size(24.dp))
                  }
                },
        )
      },
      bottomBar = { BottomNavigationBar(changePage) }) { paddingValues ->
        Column(
            modifier =
                Modifier.padding(paddingValues)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())) {
              Column {
                PostSection(
                    Post(
                        description = "This is a dummy post",
                        author = "Petras Jonutis",
                        likes = 3700,
                        comments = 39,
                        images =
                            listOf(
                                "https://i.imgur.com/PEpAtBe.jpeg",
                                "https://i.imgur.com/84p7L9H.jpeg")))
                PostSection(
                    Post(
                        description = "This is a dummy post",
                        author = "Petras Jonutis",
                        likes = 3700,
                        comments = 39,
                        images =
                            listOf(
                                "https://i.imgur.com/PEpAtBe.jpeg",
                                "https://i.imgur.com/84p7L9H.jpeg")))
                PostSection(
                    Post(
                        description = "This is a dummy post",
                        author = "Petras Jonutis",
                        likes = 3700,
                        comments = 39,
                        images =
                            listOf(
                                "https://i.imgur.com/PEpAtBe.jpeg",
                                "https://i.imgur.com/84p7L9H.jpeg")))
              }
            }
      }
}

@Composable
fun PostSection(postModel: Post) {
  Surface(modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)) {
    Column {
      Row(
          Modifier.fillMaxWidth().padding(start = 6.dp, end = 6.dp, bottom = 8.dp),
          verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f).padding(end = 16.dp)) {
              AsyncImage(
                  model = "https://i.imgur.com/oNxrcG0.jpeg",
                  contentDescription = null,
                  modifier = Modifier.size(35.dp).clip(CircleShape).clickable(onClick = {}),
                  contentScale = ContentScale.Crop)
            }

            Column(Modifier.weight(6f).padding(end = 16.dp)) {
              Text(text = postModel.author, fontWeight = FontWeight.Normal, fontSize = 12.sp)
            }
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
              Icon(
                  Icons.Default.MoreVert,
                  contentDescription = null,
                  modifier = Modifier.size(26.dp))
            }
          }

      Row(
          modifier = Modifier.horizontalScroll(rememberScrollState()).fillMaxWidth(),
      ) {
        ImageScrollWithTextOverlay(postModel.images)
      }

      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
          IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_heart_thin),
                contentDescription = "Like Icon",
                modifier = Modifier.size(20.dp))
          }

          IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_comment),
                contentDescription = "Comment Icon",
                modifier = Modifier.size(20.dp))
          }

          IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.ic_share),
                contentDescription = "Share Icon",
                modifier = Modifier.size(20.dp))
          }
        }

        IconButton(onClick = {}) {
          Icon(
              painter = painterResource(R.drawable.ic_bookmark),
              contentDescription = "Save Icon",
              modifier = Modifier.size(20.dp))
        }
      }

      Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 1.dp, bottom = 4.dp)) {
        Row {
          Text(
              text = postModel.likes.toString() + " likes",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp)
        }

        Row {
          Text(text = postModel.author, fontWeight = FontWeight.Bold, fontSize = 15.sp)
          Text(text = " " + postModel.description, fontWeight = FontWeight.Normal, fontSize = 13.sp)
        }

        Row {
          Text(
              text = "view all " + postModel.comments + " comments",
              fontWeight = FontWeight.Thin,
              fontSize = 13.sp)
        }

        Row { Text(text = "10 months ago", fontWeight = FontWeight.Thin, fontSize = 13.sp) }
      }
    }
  }
}

@Composable
private fun ImageScrollWithTextOverlay(images: List<String>) {
  val screenWidth = LocalConfiguration.current.screenWidthDp

  images.forEachIndexed { index, imageUrl ->
    Box(modifier = Modifier.width(screenWidth.dp).aspectRatio(1f)) {
      AsyncImage(
          model = imageUrl,
          contentDescription = null,
          modifier = Modifier.fillMaxWidth(),
          contentScale = ContentScale.FillWidth)

      Text(
          text = "${index + 1}/${images.size}",
          color = Color.White,
          modifier = Modifier.align(Alignment.TopEnd).padding(4.dp))
    }
  }
}
