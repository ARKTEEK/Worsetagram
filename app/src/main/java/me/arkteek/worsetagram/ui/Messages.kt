package me.arkteek.worsetagram.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import me.arkteek.worsetagram.Message
import me.arkteek.worsetagram.R

@Composable
fun Messages(changePage: (String) -> Unit) {
  Scaffold(topBar = { Header() }, bottomBar = { BottomNavigationBar(changePage) }) { paddingValues
    ->
    Column(
        modifier =
            Modifier.padding(paddingValues).fillMaxSize().verticalScroll(rememberScrollState())) {
          SearchSection()
          val messages =
              listOf(
                  Message(
                      avatar = "https://i.imgur.com/oNxrcG0.jpeg",
                      name = "Petras Jonutis",
                      lastMessage = "This is a dummy message!"),
                  Message(
                      avatar = "https://i.imgur.com/oNxrcG0.jpeg",
                      name = "Jonas Petrautis",
                      lastMessage = "Dummy message..."))
          MessagesTab(messages = messages)
        }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Header() {
  TopAppBar(
      title = {
        Text(
            "Messages",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
      },
      actions = {
        IconButton(onClick = {}) {
          Icon(
              painter = painterResource(R.drawable.ic_send),
              contentDescription = "New Message",
              modifier = Modifier.size(24.dp))
        }
      })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchSection() {
  var searchText by remember { mutableStateOf("") }

  TopAppBar(
      title = {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically) {
              Box(
                  modifier =
                      Modifier.fillMaxWidth()
                          .height(36.dp)
                          .background(Color.White, RoundedCornerShape(36.dp))
                          .border(
                              width = 0.5.dp,
                              color = Color.LightGray,
                              shape = RoundedCornerShape(36.dp))
                          .padding(horizontal = 16.dp),
                  contentAlignment = Alignment.CenterStart) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "Search Icon",
                        modifier = Modifier.padding(start = 0.dp))

                    BasicTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        singleLine = true,
                        modifier =
                            Modifier.fillMaxWidth()
                                .padding(start = 28.dp, end = 28.dp)
                                .align(Alignment.Center),
                        textStyle = TextStyle(color = Color.Black, fontSize = 15.sp))

                    if (searchText.isNotEmpty()) {
                      IconButton(
                          onClick = { searchText = "" },
                          modifier = Modifier.align(Alignment.CenterEnd)) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear Search")
                          }
                    }
                  }
            }
      },
      actions = {
        IconButton(onClick = {}) {
          Icon(Icons.Default.MoreVert, contentDescription = null, modifier = Modifier.size(26.dp))
        }
      })
}

@Composable
fun MessagesTab(messages: List<Message>) {
  Column(modifier = Modifier.padding(16.dp)) {
    messages.forEach { message ->
      MessageItem(message = message)
      Spacer(modifier = Modifier.height(8.dp))
    }
  }
}

@Composable
fun MessageItem(message: Message) {
  Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {}) {
    Avatar(image = message.avatar)

    Spacer(modifier = Modifier.width(12.dp))

    Column {
      Text(
          text = message.name,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.fillMaxWidth(),
          fontWeight = FontWeight.Bold)

      Text(
          text = message.lastMessage,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
          modifier = Modifier.fillMaxWidth(),
          fontSize = 13.sp,
          color = Color.Gray)
    }
  }
}

@Composable
fun Avatar(image: String) {
  Box(
      modifier = Modifier.size(50.dp),
      contentAlignment = Alignment.Center,
  ) {
    AsyncImage(
        modifier =
            Modifier.size(50.dp)
                .clip(CircleShape)
                .clickable(onClick = {})
                .border(1.dp, Color.Gray, CircleShape),
        contentScale = ContentScale.Crop,
        model = image,
        contentDescription = null)
  }
}
