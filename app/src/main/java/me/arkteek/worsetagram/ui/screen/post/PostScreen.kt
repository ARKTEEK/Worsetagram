package me.arkteek.worsetagram.ui.screen.post

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.common.utilities.getTimeDifference
import me.arkteek.worsetagram.domain.model.Comment
import me.arkteek.worsetagram.ui.component.HeaderBar
import me.arkteek.worsetagram.ui.component.ImageScrollWithTextOverlay
import me.arkteek.worsetagram.ui.viewmodel.PostViewModel

@Composable
fun PostScreen(
  postId: String,
  navController: NavHostController,
  viewModel: PostViewModel = hiltViewModel(),
) {
  val post by viewModel.post.observeAsState()
  val user by viewModel.postAuthor.observeAsState()
  val comments by viewModel.comments.observeAsState(emptyList())
  val isLiked by viewModel.isLiked.observeAsState(false)

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
                contentDescription = "Back",
                modifier = Modifier.size(24.dp),
              )
            }
          },
      )
    },
    content = { paddingValues ->
      Column(
        modifier =
          Modifier.padding(paddingValues).fillMaxSize().verticalScroll(rememberScrollState())
      ) {
        post?.let {
          Surface(color = Color.White, modifier = Modifier.padding(top = 6.dp, bottom = 6.dp)) {
            Column {
              Row(
                Modifier.fillMaxWidth().padding(6.dp),
                verticalAlignment = Alignment.CenterVertically,
              ) {
                AsyncImage(
                  model = "https://i.imgur.com/2jzUqgr.png",
                  contentDescription = null,
                  modifier =
                    Modifier.size(35.dp).clip(CircleShape).clickable {
                      navController.navigate("profile-other/${post!!.authorUID}")
                    },
                  contentScale = ContentScale.Crop,
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                  Modifier.clickable(
                    onClick = { navController.navigate("profile-other/${post!!.authorUID}") }
                  )
                ) {
                  Text(
                    text = (user?.firstname + " " + user?.lastname),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                  )
                  Text(
                    text = "@${user?.nickname}",
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Gray,
                  )
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(Icons.Default.MoreVert, contentDescription = null)
              }

              Column(modifier = Modifier.padding()) {
                Text(
                  text = post!!.description,
                  fontWeight = FontWeight.Normal,
                  fontSize = 16.sp,
                  modifier = Modifier.padding(end = 8.dp, start = 8.dp),
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (post!!.images.isNotEmpty()) {
                  ImageScrollWithTextOverlay(post!!.images)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                ) {
                  Text(
                    text = "${post!!.likes.size} likes",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                  )

                  Spacer(modifier = Modifier.width(16.dp))

                  Text(
                    text = "${post!!.comments.size} comments",
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                  )

                  Spacer(modifier = Modifier.weight(1f))

                  Text(
                    text = getTimeDifference(post!!.timestamp),
                    fontWeight = FontWeight.Thin,
                    fontSize = 12.sp,
                    color = Color.Gray,
                  )
                }

                HorizontalDivider()
                Spacer(modifier = Modifier.height(4.dp))
              }
            }
          }
        }

        var commentText by remember { mutableStateOf("") }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(start = 4.dp, end = 8.dp, bottom = 8.dp),
        ) {
          OutlinedTextField(
            value = commentText,
            onValueChange = { commentText = it },
            placeholder = { Text("Add a comment...") },
            singleLine = true,
            modifier = Modifier.weight(1f),
          )

          Spacer(modifier = Modifier.width(8.dp))

          Button(
            modifier = Modifier.height(52.dp),
            onClick = {
              viewModel.addComment(commentText)
              commentText = ""
            },
          ) {
            Text(text = "Post")
          }
        }

        comments?.forEach { comment ->
          CommentItem(comment = comment, navController = navController)
        }
      }
    },
  )
}

@Composable
private fun CommentItem(comment: Comment, navController: NavHostController) {
  Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      AsyncImage(
        model = "https://i.imgur.com/2jzUqgr.png",
        contentDescription = null,
        modifier = Modifier.size(35.dp).clip(CircleShape).clickable {},
        contentScale = ContentScale.Crop,
      )
      Spacer(modifier = Modifier.width(8.dp))
      Column {
        Text(text = "@testas1", fontWeight = FontWeight.Bold, fontSize = 14.sp)
        Text(text = comment.text, fontWeight = FontWeight.Normal, fontSize = 12.sp)
      }
    }
  }
}
