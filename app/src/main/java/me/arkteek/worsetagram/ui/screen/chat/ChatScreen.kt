package me.arkteek.worsetagram.ui.screen.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import me.arkteek.worsetagram.R
import me.arkteek.worsetagram.domain.model.Message
import me.arkteek.worsetagram.ui.component.HeaderBar
import me.arkteek.worsetagram.ui.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
  chatId: String,
  otherUserId: String,
  viewModel: ChatViewModel,
  navController: NavHostController,
) {
  val messages by viewModel.messages.collectAsState()
  val currentUser = FirebaseAuth.getInstance().currentUser

  var messageText by remember { mutableStateOf("") }
  val scrollState = rememberLazyListState()

  var conversationName by remember(otherUserId) { mutableStateOf("") }

  LaunchedEffect(chatId) {
    val users = listOf(currentUser?.uid ?: "", otherUserId)
    conversationName = viewModel.getUserNickname(otherUserId)
    viewModel.createConversationIfNotExists(chatId, users)
    viewModel.findDocument(chatId, users)
    viewModel.loadMessages(chatId, users)
  }

  Scaffold(
    topBar = {
      HeaderBar(
        title = "Chat with $conversationName",
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
    bottomBar = {
      MessageInput(
        onSendMessage = { content -> viewModel.sendMessage(chatId, content) },
        messageText = messageText,
        onMessageTextChanged = { messageText = it },
      )
    },
    content = { paddingValues ->
      Surface(color = Color.White, modifier = Modifier.padding(paddingValues).fillMaxSize()) {
        LazyColumn(contentPadding = paddingValues, state = scrollState) {
          items(messages) { message -> MessageItem(message = message, currentUser) }
        }
      }
    },
  )

  LaunchedEffect(messages.size) {
    if (messages.isNotEmpty()) {
      scrollState.scrollToItem(messages.size - 1)
    }
  }
}

@Composable
fun MessageInput(
  onSendMessage: (String) -> Unit,
  messageText: String,
  onMessageTextChanged: (String) -> Unit,
) {
  val keyboardController = LocalSoftwareKeyboardController.current

  Surface(color = Color.White) {
    Row(
      modifier = Modifier.fillMaxWidth().padding(16.dp),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      BasicTextField(
        value = messageText,
        onValueChange = onMessageTextChanged,
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
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
      )
      Spacer(modifier = Modifier.width(8.dp))
      SendMessageButton(
        onSendMessage = {
          onSendMessage(messageText)
          onMessageTextChanged("")
          keyboardController?.hide()
        }
      )
    }
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
fun MessageItem(message: Message, currentUser: FirebaseUser?) {
  val isSentByCurrentUser = message.sender == currentUser?.uid

  Column(
    horizontalAlignment = if (isSentByCurrentUser) Alignment.End else Alignment.Start,
    modifier = Modifier.padding(0.dp).fillMaxWidth(),
  ) {
    MessageBubble(message = message, isSentByCurrentUser = isSentByCurrentUser)
  }
}

@Composable
fun MessageBubble(message: Message, isSentByCurrentUser: Boolean) {
  val backgroundColor = if (isSentByCurrentUser) Color.Blue else Color.LightGray
  val contentColor = if (isSentByCurrentUser) Color.White else Color.Black
  val alignment = if (isSentByCurrentUser) Alignment.CenterEnd else Alignment.CenterStart

  Box(
    modifier =
      Modifier.padding(6.dp)
        .background(backgroundColor, RoundedCornerShape(8.dp))
        .padding(horizontal = 12.dp, vertical = 8.dp),
    contentAlignment = alignment,
  ) {
    Text(text = message.content, color = contentColor, fontSize = 14.sp)
  }
}
