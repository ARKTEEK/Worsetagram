package me.arkteek.worsetagram.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.ui.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.component.HeaderBar
import me.arkteek.worsetagram.ui.screen.LoadingScreen

@Composable
fun ProfileSelfScreen(
  viewModel: ProfileViewModel = hiltViewModel(),
  navController: NavHostController,
) {
  val firebaseUser by viewModel.firebaseUser.collectAsState()
  val user by viewModel.user.collectAsState()

  var isLoading by remember { mutableStateOf(true) }

  LaunchedEffect(key1 = viewModel) { isLoading = true }

  if (firebaseUser != null && user != null) {

    Scaffold(
      topBar = {
        HeaderBar(
          title = "Profile",
          customActions =
            listOf {
              IconButton(onClick = {}) {
                Icon(
                  painter = painterResource(R.drawable.ic_settings),
                  contentDescription = "Settings",
                  modifier = Modifier.size(24.dp),
                )
              }
            },
        )
      },
      bottomBar = { BottomNavigationBar(navController) },
      content = { paddingValues ->
        ProfileContent(user, modifier = Modifier.padding(paddingValues))
      },
    )
  } else {

    LoadingScreen()

    LaunchedEffect(user) {
      if (user != null) {
        isLoading = false
      }
    }
  }
}

@Composable
private fun ProfileContent(userDetails: User?, modifier: Modifier = Modifier) {
  Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      AsyncImage(
        model = "https://i.imgur.com/oNxrcG0.jpeg",
        contentDescription = "Profile Picture",
        modifier = Modifier.size(75.dp).clip(CircleShape),
        contentScale = ContentScale.Crop,
      )
      Spacer(modifier = Modifier.width(16.dp))
      Column {
        Text(
          text = (userDetails?.firstname + " " + userDetails?.lastname),
          fontSize = 18.sp,
          fontWeight = FontWeight.Bold,
        )
        Text(text = ("@" + userDetails?.nickname), fontSize = 14.sp, color = Color.Gray)
      }
    }

    Row(
      modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Button(
        onClick = { /* TODO: Handle follow action */ },
        colors = ButtonDefaults.buttonColors(Color(0xFF1E88E5)),
        modifier = Modifier.height(45.dp).width(220.dp),
      ) {
        Text(text = "Edit Profile", color = Color.White, fontSize = 16.sp)
      }
    }

    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      ProfileCount(text = "Followers", count = "100K")
      ProfileCount(text = "Following", count = "200")
      ProfileCount(text = "Posts", count = "50")
    }

    Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))
  }
}

@Composable
private fun ProfileCount(text: String, count: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
    Text(text = count, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    Text(text = text, fontSize = 16.sp, color = Color.Gray)
  }
}
