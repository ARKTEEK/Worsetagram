@file:OptIn(ExperimentalMaterial3Api::class)

package me.arkteek.worsetagram.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import java.util.UUID
import me.arkteek.worsetagram.domain.model.Message
import me.arkteek.worsetagram.ui.viewModel.AuthViewModel

@Composable
fun ChatScreen(viewModel: AuthViewModel?, navController: NavHostController) {
  viewModel?.currentUser?.let {
    val messages = remember {
      mutableStateListOf(
        Message("1", "Hello!", "friend", System.currentTimeMillis() - 100000),
        Message("2", "Hi!", "me", System.currentTimeMillis() - 90000),
        Message("3", "How are you?", "friend", System.currentTimeMillis() - 80000),
        Message("4", "I'm good, thanks!", "me", System.currentTimeMillis() - 70000),
        Message("5", "What about you?", "me", System.currentTimeMillis() - 60000),
        Message("6", "I'm doing great!", "friend", System.currentTimeMillis() - 50000),
        Message("7", "That's good to hear!", "me", System.currentTimeMillis() - 40000),
        Message("8", "Yeah!", "friend", System.currentTimeMillis() - 30000),
        Message("9", "See you later!", "friend", System.currentTimeMillis() - 20000),
        Message("10", "Bye!", "me", System.currentTimeMillis() - 10000),
      )
    }

    var messageText by remember { mutableStateOf("") }
    val scrollState = rememberLazyListState()

    val onSendMessage: (String) -> Unit = { message ->
      if (message.isNotBlank()) {
        val newMessage =
          Message(
            id = UUID.randomUUID().toString(),
            content = message,
            sender = "me",
            timestamp = System.currentTimeMillis(),
          )
        messages.add(newMessage)
        messageText = ""
      }
    }

    Scaffold(
      //    topBar = { TopBarApp(null, title = "Petras Jonutis") },
      bottomBar = { MessageInput(onSendMessage = onSendMessage, messageText = messageText) },
      content = { paddingValues ->
        LazyColumn(contentPadding = paddingValues, state = scrollState) {
          items(messages) { message -> MessageItem(message = message) }
        }
      },
    )

    LaunchedEffect(Unit) { scrollState.scrollToItem(messages.size - 1) }
  }

  @OptIn(ExperimentalMaterial3Api::class)
  @Composable
  fun TopBarApp(changePage: (String) -> Unit, title: String) {
    TopAppBar(
      title = { Text(text = title) },
      navigationIcon = {
        IconButton(onClick = { changePage("ChatsList") }) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = Color.Black,
          )
        }
      },
      actions = {},
    )
  }
}

@Composable
fun MessageInput(onSendMessage: (String) -> Unit, messageText: String) {
  var currentMessage by remember { mutableStateOf(messageText) }

  Row(
    modifier = Modifier.fillMaxWidth().padding(16.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    BasicTextField(
      value = currentMessage,
      onValueChange = { currentMessage = it },
      singleLine = true,
      textStyle = TextStyle.Default.copy(fontSize = 14.sp),
      modifier =
        Modifier.weight(1f)
          .height(ButtonDefaults.MinHeight)
          .background(Color.White, RoundedCornerShape(16.dp))
          .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp)),
      decorationBox = { innerTextField ->
        Box(
          contentAlignment = Alignment.CenterStart,
          modifier = Modifier.padding(horizontal = 12.dp),
        ) {
          innerTextField()
        }
      },
    )
    Spacer(modifier = Modifier.width(8.dp))
    SendMessageButton(onSendMessage = { onSendMessage(currentMessage) })
  }
}

@Composable
fun SendMessageButton(onSendMessage: () -> Unit) {
  Button(
    onClick = { onSendMessage() },
    modifier =
      Modifier.height(ButtonDefaults.MinHeight)
        .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
    shape = RoundedCornerShape(16.dp),
  ) {
    Text(text = "Send", color = Color.White)
  }
}

@Composable
fun MessageItem(message: Message) {
  val isSentByMe = message.sender == "me"

  Column(
    horizontalAlignment = if (isSentByMe) Alignment.End else Alignment.Start,
    modifier = Modifier.padding(0.dp).fillMaxWidth(),
  ) {
    MessageBubble(message = message, isSentByMe = isSentByMe)
  }
}

@Composable
fun MessageBubble(message: Message, isSentByMe: Boolean) {
  Box(
    modifier =
      Modifier.padding(6.dp)
        .background(if (isSentByMe) Color.Blue else Color.LightGray, RoundedCornerShape(8.dp))
        .padding(horizontal = 12.dp, vertical = 8.dp),
    contentAlignment = if (isSentByMe) Alignment.CenterEnd else Alignment.CenterStart,
  ) {
    Text(
      text = message.content,
      color = if (isSentByMe) Color.White else Color.Black,
      fontSize = 14.sp,
    )
  }
}
