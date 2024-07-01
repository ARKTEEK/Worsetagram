package me.arkteek.worsetagram.ui.screen.post.creation

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.launch
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.common.constants.ROUTE_HOME
import me.arkteek.worsetagram.ui.screen.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostCreationScreen(
  viewModel: PostCreationViewModel = hiltViewModel(),
  navController: NavHostController,
) {
  val authenticatedUser by viewModel.authenticatedUser.observeAsState()
  val createPostState by viewModel.createPostState.collectAsState()
  val context = LocalContext.current
  val coroutineScope = rememberCoroutineScope()

  var description by remember { mutableStateOf("") }
  val selectedImages = remember { mutableStateListOf<Uri>() }

  val launcher =
    rememberLauncherForActivityResult(contract = ActivityResultContracts.GetMultipleContents()) {
      uris: List<Uri> ->
      selectedImages.clear()
      selectedImages.addAll(uris)
    }

  if (authenticatedUser != null) {
    Scaffold(
      topBar = {
        TopAppBar(
          colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White),
          title = {},
          navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
              Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back button",
                modifier = Modifier.size(24.dp),
              )
            }
          },
          actions = {
            Button(
              onClick = {
                if (selectedImages.isNotEmpty() && description.isNotEmpty()) {
                  coroutineScope.launch {
                    viewModel.createPost(description, selectedImages.first())
                  }
                } else {
                  Toast.makeText(context, "Please add an image and description", Toast.LENGTH_SHORT)
                    .show()
                }
              }
            ) {
              Text(text = "Post")
            }
          },
        )
      },
      content = { paddingValues ->
        Surface(color = Color.White) {
          Column(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
          ) {
            Text(
              text = "${selectedImages.size} photos selected",
              modifier = Modifier.padding(16.dp),
            )

            LazyRow(
              modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
              horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
              items(selectedImages) { uri ->
                Image(
                  painter = rememberAsyncImagePainter(uri),
                  contentDescription = null,
                  modifier = Modifier.size(100.dp),
                )
              }
            }

            Button(onClick = { launcher.launch("image/*") }) { Text(text = "Select Images") }

            TextField(
              value = description,
              onValueChange = { description = it },
              placeholder = { Text("What's happening?", color = Color.Gray) },
              modifier = Modifier.fillMaxWidth().padding(16.dp).background(Color.Transparent),
              colors =
                TextFieldDefaults.colors(
                  focusedContainerColor = Color.Transparent,
                  unfocusedContainerColor = Color.Transparent,
                  focusedIndicatorColor = Color.Transparent,
                  unfocusedIndicatorColor = Color.Transparent,
                ),
            )

            if (createPostState is CreatePostState.Loading) {
              CircularProgressIndicator()
            }

            when (createPostState) {
              CreatePostState.Success -> {
                navController.navigate(ROUTE_HOME)
              }

              is CreatePostState.Error -> {
                Text(text = "Error: ${(createPostState as CreatePostState.Error).message}")
              }

              CreatePostState.Loading -> {
                LoadingScreen()
              }

              CreatePostState.Initial -> {}
            }
          }
        }
      },
    )
  }
}
