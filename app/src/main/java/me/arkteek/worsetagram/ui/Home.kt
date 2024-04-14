package me.arkteek.worsetagram.ui

import androidx.compose.foundation.border
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
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.arkteek.worsetagram.Post
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.ui.theme.InstagramCloneTheme

@Composable
fun Home() {
  Scaffold(topBar = { Header() }, bottomBar = { BottomNavigationBar() }) { paddingValues ->
    Column(
        modifier =
            Modifier.padding(paddingValues).fillMaxSize().verticalScroll(rememberScrollState())) {
          Column {
            // Story
            Row(
                modifier =
                    Modifier.padding(top = 16.dp, bottom = 16.dp, start = 0.dp, end = 0.dp)
                        .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                  YourStory(imageUrl = "https://i.imgur.com/qXqDyoe.png", name = "Your Story")
                  Story(imageUrl = "https://i.imgur.com/oNxrcG0.jpeg", name = "petras")
                  Story(imageUrl = "https://i.imgur.com/npLMqhC.jpeg", name = "antanas")
                }

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

      // Image / Video
      Row(
          modifier = Modifier.horizontalScroll(rememberScrollState()).fillMaxWidth(),
      ) {
        ImageScrollWithTextOverlay(post.images)
      }

      // Content (Reactions)
      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        // Create a row to hold the reaction icons
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

      // Create a column to hold the likes, comments, and timestamp
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
fun ImageScrollWithTextOverlay(images: List<String>) {
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
fun Header() {
  TopAppBar(
      title = {
        Text(
            "For you",
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
        IconButton(onClick = {}) {
          Icon(
              painter = painterResource(R.drawable.ic_chat),
              contentDescription = "Messenges Icon",
              modifier = Modifier.size(24.dp))
        }
      })
}

@Composable
fun YourStory(imageUrl: String, name: String) {
  Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Box(
            modifier =
                Modifier.size(70.dp)
                    .clickable(onClick = {})
                    .border(
                        width = 2.dp,
                        brush =
                            Brush.linearGradient(
                                colors = listOf(Color.Yellow, Color.Red),
                                start = Offset(0f, 0f),
                                end = Offset(70f, 70f)),
                        shape = CircleShape)) {
              AsyncImage(
                  model = imageUrl,
                  contentDescription = null,
                  modifier = Modifier.clip(CircleShape),
                  contentScale = ContentScale.Crop)
            }
        Text(text = name, fontWeight = FontWeight.Normal, fontSize = 13.sp)
      }
}

@Composable
fun Story(imageUrl: String, name: String) {
  Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.spacedBy(8.dp)) {
        // Load the story image asynchronously
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            modifier =
                Modifier.size(70.dp)
                    .clip(CircleShape)
                    .border(
                        width = 2.dp,
                        brush =
                            Brush.linearGradient(
                                colors = listOf(Color.Yellow, Color.Red),
                                start = Offset(0f, 0f),
                                end = Offset(70f, 70f)),
                        shape = CircleShape)
                    .clickable(onClick = {}),
            contentScale = ContentScale.Crop)
        Text(text = name, fontWeight = FontWeight.Normal, fontSize = 13.sp)
      }
}

@Preview(showBackground = true)
@Composable
fun HomeAppBarPreview() {
  InstagramCloneTheme { Header() }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
  InstagramCloneTheme { Home() }
}

@Preview
@Composable
fun HomeDarkPreview() {
  InstagramCloneTheme(darkTheme = true) { Home() }
}

@Composable
fun BottomNavigationBar() {
  BottomAppBar(containerColor = Color(0xFFFAFAFA)) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
      // Create an icon button for home
      IconButton(onClick = { /* do something */}) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Icon(
              painter = painterResource(R.drawable.ic_home),
              contentDescription = "Home Icon",
              modifier = Modifier.size(25.dp))
        }
      }
      // Create an icon button for search
      IconButton(onClick = { /* do something */}) {
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
