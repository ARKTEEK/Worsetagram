package me.arkteek.worsetagram.ui.screen.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.common.utilities.getTimeDifference
import me.arkteek.worsetagram.ui.component.HeaderBar
import me.arkteek.worsetagram.ui.viewmodel.PostViewModel

@Composable
fun CommentScreen(
  postId: String,
  navController: NavHostController,
  viewModel: PostViewModel = hiltViewModel(),
) {
  val user by viewModel.user.observeAsState()
  val comments by viewModel.comments.observeAsState(emptyList())

  LaunchedEffect(postId) { viewModel.loadPost(postId) }

  Scaffold(
    topBar = {
      HeaderBar(
        title = "Post",
        leftActions =
          listOf {
            IconButton(onClick = { navController.popBackStack() }) {
              Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back Icon",
                modifier = Modifier.size(24.dp),
              )
            }
          },
      )
    },
    content = { paddingValues ->
      Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
          LazyColumn(modifier = Modifier.weight(1f)) {
            items(comments!!.size) { index ->
              val comment = comments!![index]
              CommentItem(
                username = user?.nickname!!,
                comment = comment.text,
                imageUrl = "https://i.imgur.com/2jzUqgr.png",
                timeAgo = getTimeDifference(comment.timestamp),
              )
              HorizontalDivider()
            }
          }
          HorizontalDivider()
          Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TextField(
              value = "",
              onValueChange = {},
              placeholder = { Text("What's happening?", color = Color.Gray) },
              modifier = Modifier.fillMaxWidth().background(Color.Transparent),
              colors =
                TextFieldDefaults.colors(
                  focusedContainerColor = Color.Transparent,
                  unfocusedContainerColor = Color.Transparent,
                  focusedIndicatorColor = Color.Transparent,
                  unfocusedIndicatorColor = Color.Transparent,
                ),
            )
            Text(
              text = "Post",
              color = Color.Blue,
              modifier = Modifier.padding(8.dp).clickable { /* Handle post action */ },
            )
          }
        }
      }
    },
  )
}

@Composable
fun AvatarImage(imageUrl: String, modifier: Modifier = Modifier) {
  Image(
    painter = rememberAsyncImagePainter(model = imageUrl),
    contentDescription = null,
    modifier = modifier.size(40.dp).clip(CircleShape).background(Color.White),
    contentScale = ContentScale.Crop,
  )
}

@Composable
fun CommentItem(username: String, comment: String, imageUrl: String, timeAgo: String) {

  Column(modifier = Modifier.padding(8.dp)) {
    Row {
      AvatarImage(imageUrl = imageUrl, modifier = Modifier.padding(end = 8.dp))
      Column {
        Text(text = username, fontWeight = FontWeight.Bold)
        Text(text = comment)
        Text(text = timeAgo)
      }
    }
  }
}
