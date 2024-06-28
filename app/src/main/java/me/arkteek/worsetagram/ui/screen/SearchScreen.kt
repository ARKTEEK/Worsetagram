package me.arkteek.worsetagram.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.ui.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
  searchViewModel: SearchViewModel = hiltViewModel(),
  navController: NavHostController,
) {
  var searchText by remember { mutableStateOf("") }
  val searchResults by searchViewModel.searchResults.collectAsState()

  Scaffold(
    topBar = {
      Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        SearchBox(
          searchText = searchText,
          onSearchTextChanged = { newText ->
            searchText = newText
            searchViewModel.searchUsers(newText)
          },
        )
      }
    },
    bottomBar = { BottomNavigationBar(navController) },
  ) { paddingValues ->
    Surface(color = Color.White) {
      Column(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
        SearchContent(searchResults, navController)
      }
    }
  }
}

@Composable
private fun SearchBox(searchText: String, onSearchTextChanged: (String) -> Unit) {
  Box(
    modifier =
      Modifier.fillMaxWidth()
        .height(36.dp)
        .background(Color.White, RoundedCornerShape(36.dp))
        .border(width = 0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(36.dp))
        .padding(horizontal = 16.dp),
    contentAlignment = Alignment.CenterStart,
  ) {
    Icon(
      Icons.Default.Search,
      contentDescription = "Search Icon",
      modifier = Modifier.padding(start = 0.dp),
    )

    BasicTextField(
      value = searchText,
      onValueChange = { onSearchTextChanged(it) },
      singleLine = true,
      modifier =
        Modifier.fillMaxWidth().padding(start = 28.dp, end = 28.dp).align(Alignment.Center),
      textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
    )

    if (searchText.isNotEmpty()) {
      IconButton(
        onClick = { onSearchTextChanged("") },
        modifier = Modifier.align(Alignment.CenterEnd),
      ) {
        Icon(Icons.Default.Clear, contentDescription = "Clear Search")
      }
    }
  }
}

@Composable
fun SearchContent(users: List<User>, navController: NavHostController) {
  LazyColumn(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
    items(users) { user -> UserRow(user, navController) }
  }
}

@Composable
fun UserRow(user: User, navController: NavHostController) {
  Row(
    modifier =
      Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable {
        navController.navigate("profile-other/${user.uid}")
      },
    verticalAlignment = Alignment.CenterVertically,
  ) {
    AsyncImage(
      model = "https://i.imgur.com/nDAA9Th.jpeg",
      contentDescription = null,
      modifier = Modifier.size(56.dp).clip(CircleShape),
      contentScale = ContentScale.Crop,
    )
    Spacer(modifier = Modifier.width(8.dp))
    Column {
      Text(text = user.nickname, color = Color.Black, fontSize = 13.sp)
      Text(
        text = "${user.firstname} ${user.lastname}",
        fontWeight = FontWeight.Normal,
        color = Color.Gray,
        fontSize = 13.sp,
      )
    }
  }
}
