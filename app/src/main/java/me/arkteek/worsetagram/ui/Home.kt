package me.arkteek.worsetagram.ui

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import me.arkteek.worsetagram.Post
import me.arkteek.worsetagram.R

@Composable
fun Home(changePage: (String) -> Unit) {
  Scaffold(topBar = { Header() }, bottomBar = { BottomNavigationBar(changePage) }) { paddingValues
    ->
    Column(
        modifier =
            Modifier.padding(paddingValues).fillMaxSize().verticalScroll(rememberScrollState())) {
          Column {
            PostSection(
                Post(
                    description = "This is a dummy post",
                    author = "petras",
                    likes = 3700,
                    comments = 39,
                    images =
                        listOf(
                            "https://i.imgur.com/PEpAtBe.jpeg",
                            "https://i.imgur.com/84p7L9H.jpeg")))
            PostSection(
                Post(
                    description = "This is a dummy post",
                    author = "petras",
                    likes = 3700,
                    comments = 39,
                    images =
                        listOf(
                            "https://i.imgur.com/PEpAtBe.jpeg",
                            "https://i.imgur.com/84p7L9H.jpeg")))
            PostSection(
                Post(
                    description = "This is a dummy post",
                    author = "petras",
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
fun PostSection(post: Post) {
  Surface(modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)) {
    Column {
      Row(
          Modifier.fillMaxWidth().padding(start = 6.dp, end = 6.dp, bottom = 8.dp),
          verticalAlignment = Alignment.CenterVertically) {
            // Create a column to hold the profile picture
            Column(Modifier.weight(1f).padding(end = 16.dp)) {
              AsyncImage(
                  model = "https://i.imgur.com/oNxrcG0.jpeg",
                  contentDescription = null,
                  modifier = Modifier.size(30.dp).clip(CircleShape).clickable(onClick = {}),
                  contentScale = ContentScale.Crop)
            }

            // Create a column to hold the username and audio source
            Column(Modifier.weight(6f).padding(end = 16.dp)) {
              // Display the username
              Text(text = post.author, fontWeight = FontWeight.Normal, fontSize = 11.sp)
              // Display the audio source
              Text(text = post.author, fontWeight = FontWeight.Normal, fontSize = 10.sp)
            }
            // Create a box to hold the more options icon
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
              // Display the more options icon
              Icon(
                  Icons.Default.MoreVert,
                  contentDescription = null,
                  modifier = Modifier.size(26.dp))
            }
          }

      Row(
          modifier = Modifier.horizontalScroll(rememberScrollState()).fillMaxWidth(),
      ) {
        ImageScrollWithTextOverlay(post.images)
      }

      // Content (Reactions)
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
          // Create an icon button for likes
          IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.heart_thin_icon),
                contentDescription = "Like Icon",
                modifier = Modifier.size(20.dp))
          }

          // Create an icon button for comments
          IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.instagram_comment_icon),
                contentDescription = "Comment Icon",
                modifier = Modifier.size(20.dp))
          }

          // Create an icon button for share
          IconButton(onClick = {}) {
            Icon(
                painter = painterResource(R.drawable.instagram_share_icon),
                contentDescription = "Share Icon",
                modifier = Modifier.size(20.dp))
          }
        }

        // Create an icon button for save
        IconButton(onClick = {}) {
          Icon(
              painter = painterResource(R.drawable.saved_icon),
              contentDescription = "Save Icon",
              modifier = Modifier.size(20.dp))
        }
      }

      Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 1.dp, bottom = 4.dp)) {
        // Display the number of likes
        Row {
          Text(
              text = post.likes.toString() + " likes",
              fontWeight = FontWeight.Bold,
              fontSize = 15.sp)
        }

        // Display the comments
        Row {
          Text(text = post.author, fontWeight = FontWeight.Bold, fontSize = 15.sp)
          Text(text = " " + post.description, fontWeight = FontWeight.Normal, fontSize = 13.sp)
        }

        // Display the total number of comments
        Row {
          Text(
              text = "view all " + post.comments + " comments",
              fontWeight = FontWeight.Thin,
              fontSize = 13.sp)
        }

        // Display the timestamp
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Header() {
  TopAppBar(
      title = {
        Text(
            "Worsetagram",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
      },
      actions = {
        IconButton(onClick = {}) {
          Icon(
              painter = painterResource(R.drawable.ic_heart),
              contentDescription = "Heart Icon",
              modifier = Modifier.size(24.dp))
        }
      })
}