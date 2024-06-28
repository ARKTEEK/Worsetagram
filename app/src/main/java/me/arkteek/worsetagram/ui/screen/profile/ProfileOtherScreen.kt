package me.arkteek.worsetagram.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.common.constants.ROUTE_CHAT
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.ui.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.component.FollowButton
import me.arkteek.worsetagram.ui.component.HeaderBar
import me.arkteek.worsetagram.ui.component.ProfileCount
import me.arkteek.worsetagram.ui.component.RoundIconButton
import me.arkteek.worsetagram.ui.screen.LoadingScreen
import me.arkteek.worsetagram.ui.viewmodel.ProfileOtherViewModel

@Composable
fun ProfileOtherScreen(
  viewModel: ProfileOtherViewModel = hiltViewModel(),
  navController: NavHostController,
  ownerUID: String,
) {
  val firebaseViewerUser by viewModel.firebaseViewerUser.collectAsState()
  val userViewer by viewModel.viewerUser.collectAsState()
  val userOwner by viewModel.userOwner.collectAsState()

  var isLoading by remember { mutableStateOf(true) }

  LaunchedEffect(viewModel) { isLoading = true }

  if (firebaseViewerUser != null && userViewer != null) {

    viewModel.loadUserOwnerProfile(ownerUID)
    LaunchedEffect(userOwner) {
      if (userOwner != null) {
        viewModel.checkFollowingStatus(userOwner!!.uid!!)
      }
    }

    Scaffold(
      topBar = {
        HeaderBar(
          title = "Profile",
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
      bottomBar = { BottomNavigationBar(navController) },
      content = { paddingValues ->
        Surface(color = Color.White) {
          ProfileContent(
            userOwner,
            userViewer,
            viewModel,
            modifier = Modifier.padding(paddingValues),
            navController,
          )
        }
      },
    )
  } else {

    LoadingScreen()

    LaunchedEffect(userViewer) {
      if (userViewer != null) {
        isLoading = false
      }
    }
  }
}

@Composable
private fun ProfileContent(
  userOwner: User?,
  userViewer: User?,
  viewModel: ProfileOtherViewModel,
  modifier: Modifier = Modifier,
  navController: NavHostController,
) {
  val isViewerFollowing by viewModel.isFollowing.collectAsState()

  Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
    ProfileContentHeader(ownerUser = userOwner)

    Row(
      modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      RoundIconButton(icon = Icons.Default.Email, onClick = { navController.navigate(ROUTE_CHAT) })

      Spacer(modifier = Modifier.width(16.dp))

      if (userOwner != null && userViewer?.uid != userOwner.uid) {
        FollowButton(
          isFollowing = isViewerFollowing ?: false,
          onToggleFollow = {
            viewModel.run {
              if (it) {
                followUser(userOwner.uid!!)
              } else {
                unfollowUser(userOwner.uid!!)
              }
            }
          },
        )
      }

      Spacer(modifier = Modifier.width(16.dp))

      RoundIconButton(
        icon = Icons.Default.Share,
        onClick = { /* TODO: Handle share icon button click */ },
      )
    }

    HorizontalDivider(
      modifier = Modifier.padding(vertical = 16.dp),
      thickness = 1.dp,
      color = Color.Gray,
    )

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      ProfileCount(text = "Followers", count = userOwner?.followers?.count()?.toString() ?: "0")
      ProfileCount(text = "Following", count = userOwner?.following?.count()?.toString() ?: "0")
      ProfileCount(text = "Posts", count = "0")
    }

    HorizontalDivider(
      modifier = Modifier.padding(vertical = 16.dp),
      thickness = 1.dp,
      color = Color.Gray,
    )
  }
}

@Composable
private fun ProfileContentHeader(ownerUser: User?) {
  ownerUser?.let {
    Row(
      modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
    ) {
      AsyncImage(
        model = "https://i.imgur.com/nDAA9Th.jpeg",
        contentDescription = "Profile Picture",
        modifier = Modifier.size(100.dp).clip(CircleShape),
        contentScale = ContentScale.Crop
      )
    }

    Row(
      modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center,
    ) {
      Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(
          text = (it.firstname + " " + it.lastname),
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center,
        )
        Text(
          text = ("@" + it.nickname),
          fontSize = 14.sp,
          color = Color.Gray,
          textAlign = TextAlign.Center,
        )
      }
    }
  }
}
