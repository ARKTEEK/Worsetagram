package me.arkteek.worsetagram.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.common.constants.ROUTE_PROFILE
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.ui.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.component.HeaderBar
import me.arkteek.worsetagram.ui.screen.LoadingScreen
import me.arkteek.worsetagram.ui.viewmodel.ProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsScreen(
  viewModel: ProfileViewModel = hiltViewModel(),
  navController: NavHostController,
) {
  val firebaseUser by viewModel.firebaseUser.collectAsState()
  val user by viewModel.user.collectAsState()

  var isLoading by remember { mutableStateOf(true) }

  LaunchedEffect(viewModel) { isLoading = true }

  if (firebaseUser != null && user != null) {

    Scaffold(
      topBar = {
        HeaderBar(
          title = "Profile Settings",
          leftActions =
            listOf {
              IconButton(onClick = { navController.navigate(ROUTE_PROFILE) }) {
                Icon(
                  painter = painterResource(R.drawable.ic_back),
                  contentDescription = "Settings",
                  modifier = Modifier.size(24.dp),
                )
              }
            },
        )
      },
      bottomBar = { BottomNavigationBar(navController) },
      content = { paddingValues ->
        Surface(color = Color.White, modifier = Modifier.padding(paddingValues).fillMaxSize()) {
          ProfileSettingsContent(user, navController, viewModel)
        }
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
private fun ProfileSettingsContent(
  userDetails: User?,
  navController: NavHostController,
  viewModel: ProfileViewModel,
) {
  var firstName by remember { mutableStateOf(userDetails?.firstname ?: "") }
  var lastName by remember { mutableStateOf(userDetails?.lastname ?: "") }
  var email by remember { mutableStateOf(userDetails?.email ?: "") }
  var oldPassword by remember { mutableStateOf("") }
  var newPassword by remember { mutableStateOf("") }

  Column(
    modifier = Modifier.fillMaxSize().padding(top = 48.dp).padding(horizontal = 32.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Spacer(modifier = Modifier.height(16.dp))

    Box(
      modifier =
        Modifier.size(100.dp).clickable {
          // Handle profile picture change click
        },
      contentAlignment = Alignment.Center,
    ) {
      AsyncImage(
        model = "https://i.imgur.com/2jzUqgr.png",
        contentDescription = "Profile Picture",
        modifier = Modifier.size(100.dp).clip(CircleShape).background(Color.Gray),
        contentScale = ContentScale.Crop,
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
      value = firstName,
      onValueChange = { firstName = it },
      label = { Text("First Name") },
      modifier = Modifier.fillMaxWidth(),
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
      value = lastName,
      onValueChange = { lastName = it },
      label = { Text("Last Name") },
      modifier = Modifier.fillMaxWidth(),
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
      value = email,
      onValueChange = { email = it },
      label = { Text("Email") },
      modifier = Modifier.fillMaxWidth(),
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
      value = "",
      onValueChange = {},
      label = { Text("Old Password") },
      visualTransformation = PasswordVisualTransformation(),
      modifier = Modifier.fillMaxWidth(),
    )

    Spacer(modifier = Modifier.height(8.dp))

    OutlinedTextField(
      value = newPassword,
      onValueChange = { newPassword = it },
      label = { Text("New Password") },
      visualTransformation = PasswordVisualTransformation(),
      modifier = Modifier.fillMaxWidth(),
    )

    Spacer(modifier = Modifier.height(16.dp))

    Button(
      onClick = {
        val updatedUser =
          userDetails?.copy(firstname = firstName, lastname = lastName, email = email)

        if (updatedUser != null) {
          viewModel.updateUserDetails(updatedUser, newEmail = email.takeIf { it.isNotEmpty() })

          navController.navigate(ROUTE_PROFILE)
        }
      }
    ) {
      Text("Save Changes")
    }
  }
}
