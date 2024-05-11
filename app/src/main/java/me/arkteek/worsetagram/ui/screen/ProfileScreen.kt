package me.arkteek.worsetagram.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.ui.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.component.HeaderBar
import me.arkteek.worsetagram.ui.viewModel.AuthViewModel

@Composable
fun ProfileScreen(viewModel: AuthViewModel?, navController: NavHostController) {
  viewModel?.currentUser?.let {
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
      content = { paddingValues -> ProfileContent(modifier = Modifier.padding(paddingValues)) },
    )
  }
}

@Composable
private fun ProfileContent(modifier: Modifier = Modifier) {
  Column(modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(2.dp)) {
    Row(
      modifier = Modifier.fillMaxWidth().weight(0.15f),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      AsyncImage(
        model = "https://i.imgur.com/oNxrcG0.jpeg",
        contentDescription = "Profile Picture",
        modifier = Modifier.size(75.dp).clip(CircleShape),
        contentScale = ContentScale.Crop,
      )

      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth().padding(5.dp),
      ) {
        ProfileCount(text = "Posts", count = "500")
        ProfileCount(text = "Followers", count = "100K")
        ProfileCount(text = "Following", count = "200")
      }
    }

    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.Start,
      modifier = Modifier.padding(2.dp),
    ) {
      Text(text = "petraas03", fontSize = 16.sp, fontWeight = FontWeight.Bold)
      Text(text = "Dummy description", fontSize = 14.sp, color = Color.Gray)
    }

    Row(
      modifier = Modifier.fillMaxWidth().weight(0.8f).padding(16.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      ProfileButton()
    }
  }
}

@Composable
private fun ProfileCount(text: String, count: String) {
  Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(2.dp)) {
    Text(text = text, fontSize = 16.sp, fontWeight = FontWeight.Bold)
    Text(text = count, fontSize = 14.sp)
  }
}

@Composable
private fun ProfileButton() {
  Box(
    modifier =
      Modifier.fillMaxWidth()
        .height(52.dp)
        .padding(6.dp)
        .background(Color.White, shape = RoundedCornerShape(20.dp))
        .border(0.5.dp, Color.Gray, shape = RoundedCornerShape(20.dp))
        .clickable {},
    contentAlignment = Alignment.Center,
  ) {
    Text(
      text = "Edit Profile",
      color = Color.Black,
      fontWeight = FontWeight.Bold,
      textAlign = TextAlign.Center,
      modifier = Modifier.padding(8.dp).fillMaxSize(),
    )
  }
}
