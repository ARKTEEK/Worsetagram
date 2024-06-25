package me.arkteek.worsetagram.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.common.utilities.getTimeDifference
import me.arkteek.worsetagram.domain.model.Post
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.ui.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.component.HeaderBar
import me.arkteek.worsetagram.ui.component.ImageScrollWithTextOverlay
import me.arkteek.worsetagram.ui.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavHostController) {
  val authenticatedUser by viewModel.authenticatedUser.observeAsState()
  val postsWithAuthors by viewModel.postsWithAuthors.observeAsState()
  val loading = viewModel.loading.value

  if (authenticatedUser != null) {
    if (loading) {
      LoadingScreen()
    } else {
      Scaffold(
        topBar = {
          HeaderBar(
            title = "Following",
            leftActions =
              listOf {
                IconButton(onClick = {}) {
                  Icon(
                    painter = painterResource(R.drawable.ic_arrow_down),
                    contentDescription = "Heart Icon",
                    modifier = Modifier.size(24.dp),
                  )
                }
              },
            rightActions =
              listOf {
                IconButton(onClick = {}) {
                  Icon(
                    painter = painterResource(R.drawable.ic_heart),
                    contentDescription = "Heart Icon",
                    modifier = Modifier.size(24.dp),
                  )
                }
              },
          )
        },
        bottomBar = { BottomNavigationBar(navController) },
        content = { paddingValues ->
          Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
            Column(
              modifier =
                Modifier.padding(paddingValues).verticalScroll(rememberScrollState()).fillMaxSize()
            ) {
              Column {
                postsWithAuthors?.forEach { postWithAuthor ->
                  PostSection(
                    post = postWithAuthor.post,
                    user = postWithAuthor.author,
                    viewer = authenticatedUser,
                    viewModel,
                    navController,
                  )
                }
              }
            }
          }
        },
      )
    }
  }
}

@Composable
fun PostSection(
  post: Post,
  user: User?,
  viewer: User?,
  viewModel: HomeViewModel,
  navController: NavHostController,
) {
  val isLiked by viewModel.getLikedStatus(post.uid).observeAsState(false)
  var likesCount by remember { mutableStateOf(post.likes.size) }

  LaunchedEffect(isLiked) { likesCount = if (isLiked) post.likes.size + 1 else post.likes.size }

  Surface(modifier = Modifier.padding(top = 6.dp, bottom = 6.dp), color = Color.White) {
    Column {
      Row(
        Modifier.fillMaxWidth().padding(start = 6.dp, end = 6.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        AsyncImage(
          model = "https://i.imgur.com/tkomqK8.png",
          contentDescription = null,
          modifier =
            Modifier.size(35.dp).clip(CircleShape).clickable {
              navController.navigate("profile-other/${post.authorUID}")
            },
          contentScale = ContentScale.Crop,
        )

        Text(
          text = "${user?.nickname}",
          fontWeight = FontWeight.Normal,
          fontSize = 14.sp,
          color = Color.Black,
          modifier =
            Modifier.padding(start = 8.dp).clickable {
              navController.navigate("profile-other/${post.authorUID}")
            },
        )

        if (post.authorUID == viewer?.uid) {
          Box(
            modifier = Modifier.weight(1f).fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd,
          ) {
            Icon(Icons.Default.Clear, contentDescription = null, modifier = Modifier.size(26.dp))
          }
        }
      }

      Row(modifier = Modifier.horizontalScroll(rememberScrollState()).fillMaxWidth()) {
        ImageScrollWithTextOverlay(post.images)
      }

      Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Row {
          IconButton(
            onClick = {
              if (isLiked) {
                viewModel.unlikePost(post.uid)
              } else {
                viewModel.likePost(post.uid)
              }
            }
          ) {
            Icon(
              painter = painterResource(R.drawable.ic_heart_thin),
              contentDescription = "Like Icon",
              modifier = Modifier.size(20.dp),
              tint = if (isLiked) Color.Red else Color.Black,
            )
          }

          IconButton(onClick = { navController.navigate("post/${post.uid}") }) {
            Icon(
              painter = painterResource(R.drawable.ic_comment),
              contentDescription = "Comment Icon",
              modifier = Modifier.size(20.dp),
            )
          }

          IconButton(onClick = { /* TODO: Implement Share function */ }) {
            Icon(
              painter = painterResource(R.drawable.ic_share),
              contentDescription = "Share Icon",
              modifier = Modifier.size(20.dp),
            )
          }
        }
      }

      Column(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 1.dp, bottom = 4.dp)) {
        Row { Text(text = "$likesCount likes", fontWeight = FontWeight.Normal, fontSize = 13.sp) }

        Row {
          Text(text = user?.nickname ?: "", fontWeight = FontWeight.Normal, fontSize = 13.sp)
          Text(text = " ${post.description}", fontWeight = FontWeight.Normal, fontSize = 13.sp)
        }

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.clickable(onClick = { navController.navigate("post/${post.uid}") }),
        ) {
          Text(
            text = "View all ${post.comments.size} comments",
            fontWeight = FontWeight.Normal,
            color = Color.Gray,
            fontSize = 13.sp,
          )
        }

        Row {
          Text(
            text = getTimeDifference(post.timestamp),
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
          )
        }
      }
    }
  }
}
