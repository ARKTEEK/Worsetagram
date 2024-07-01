package me.arkteek.worsetagram.ui.screen.post.comments

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.common.utilities.getTimeDifference
import me.arkteek.worsetagram.domain.model.Comment
import me.arkteek.worsetagram.ui.component.HeaderBar
import me.arkteek.worsetagram.ui.screen.LoadingScreen

@Composable
fun CommentScreen(
  postId: String,
  navController: NavHostController,
  viewModel: CommentsViewModel = hiltViewModel(),
) {
  val keyboardController = LocalSoftwareKeyboardController.current
  val comments by viewModel.comments.observeAsState(emptyList())
  var commentInputText by remember { mutableStateOf("") }
  val loading = viewModel.loading.value

  LaunchedEffect(postId) { viewModel.loadPost(postId) }

  if (loading) {
    LoadingScreen()
  } else {
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
                CommentItem(viewModel, comment)
                HorizontalDivider()
              }
            }
            HorizontalDivider()
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
            ) {
              TextField(
                value = commentInputText,
                onValueChange = { commentInputText = it },
                placeholder = { Text("What are your thoughts?", color = Color.Gray) },
                modifier =
                  Modifier.fillMaxWidth()
                    .background(Color.Transparent)
                    .weight(1f)
                    .width(IntrinsicSize.Max),
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
                modifier =
                  Modifier.padding(8.dp).clickable {
                    viewModel.addComment(commentInputText)
                    commentInputText = ""
                    keyboardController?.hide()
                  },
              )
            }
          }
        }
      },
    )
  }
}

@Composable
fun AvatarImage(imageUrl: String, modifier: Modifier = Modifier) {
  Image(
    painter = rememberAsyncImagePainter(model = imageUrl),
    contentDescription = null,
    modifier = modifier.size(35.dp).clip(CircleShape).background(Color.White),
    contentScale = ContentScale.Crop,
  )
}

@Composable
fun CommentItem(viewModel: CommentsViewModel, comment: Comment) {
  var authorNickname by remember(comment.authorUID) { mutableStateOf("") }

  LaunchedEffect(comment.authorUID) {
    authorNickname = viewModel.getUserNickname(comment.authorUID)
  }

  Column(modifier = Modifier.padding(8.dp)) {
    Row {
      AsyncImage(
        model = "https://i.imgur.com/nDAA9Th.jpeg",
        contentDescription = "Profile Picture",
        modifier = Modifier.padding(end = 8.dp).size(42.dp).clip(CircleShape),
        contentScale = ContentScale.Crop,
      )
      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(text = authorNickname, fontWeight = FontWeight.Bold, fontSize = 14.sp)
          Spacer(modifier = Modifier.width(4.dp))
          Text(text = comment.text, fontWeight = FontWeight.Normal, fontSize = 14.sp)
        }
        Text(
          text = getTimeDifference(comment.timestamp),
          fontWeight = FontWeight.Normal,
          fontSize = 12.sp,
          color = Color.Gray,
          modifier = Modifier.padding(top = 1.dp),
        )
      }
    }
  }
}
