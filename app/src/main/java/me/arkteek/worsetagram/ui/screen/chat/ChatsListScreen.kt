package me.arkteek.worsetagram.ui.screen.chat

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import me.arkteek.worsetagram.domain.model.ChatItem
import me.arkteek.worsetagram.ui.component.BottomNavigationBar
import me.arkteek.worsetagram.ui.component.HeaderBar
import me.arkteek.worsetagram.ui.component.SearchBar

@Composable
fun ChatListScreen(viewModel: ChatViewModel, navController: NavHostController) {
  val currentUser = FirebaseAuth.getInstance().currentUser
  val conversations by viewModel.conversations.collectAsState()

  LaunchedEffect(currentUser?.uid) { currentUser?.uid?.let { viewModel.loadUserConversations(it) } }

  Scaffold(
    topBar = { HeaderBar(title = "Messages") },
    bottomBar = { BottomNavigationBar(navController) },
    content = { paddingValues ->
      Surface(color = Color.White, modifier = Modifier.padding(paddingValues).fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
          var searchText by remember { mutableStateOf("") }

          SearchBar(
            searchText = searchText,
            onSearchTextChanged = { newText -> searchText = newText },
          )

          if (conversations.isEmpty()) {
            Text("No conversations available", modifier = Modifier.padding(16.dp))
          } else {
            ChatsTab(chats = conversations) { chatItem ->
              navController.navigate("chat/${chatItem.chatId}/${chatItem.otherUserId}")
            }
          }
        }
      }
    },
  )
}

@Composable
private fun ChatsTab(chats: List<ChatItem>, onClick: (ChatItem) -> Unit) {
  Column(modifier = Modifier.padding(16.dp)) {
    chats.forEach { chat ->
      ChatItem(chat = chat) { onClick(chat) }
      Spacer(modifier = Modifier.height(8.dp))
    }
  }
}

@Composable
private fun ChatItem(chat: ChatItem, onClick: () -> Unit) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    modifier = Modifier.clickable(onClick = onClick),
  ) {
    Avatar(image = chat.avatar)

    Spacer(modifier = Modifier.width(12.dp))

    Column {
      Text(
        text = chat.name,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth(),
        fontWeight = FontWeight.Bold,
      )

      Text(
        text = chat.lastMessage,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 13.sp,
        color = Color.Gray,
      )
    }
  }
}

@Composable
private fun Avatar(image: String) {
  Box(modifier = Modifier.size(50.dp), contentAlignment = Alignment.Center) {
    AsyncImage(
      modifier =
        Modifier.size(50.dp)
          .clip(CircleShape)
          .clickable(onClick = {})
          .border(1.dp, Color.Gray, CircleShape),
      contentScale = ContentScale.Crop,
      model = image,
      contentDescription = null,
    )
  }
}
