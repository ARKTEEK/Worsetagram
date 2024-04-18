package me.arkteek.worsetagram.ui.views.conversations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import me.arkteek.worsetagram.models.MessageModel

@Composable
fun Message(changePage: (String) -> Unit) {
  val messageModel = remember {
    listOf(
        MessageModel(1, 1, 2, "Hello!", System.currentTimeMillis()),
        MessageModel(2, 2, 1, "Hi!", System.currentTimeMillis() + 1000),
        // Add more messages here
    )
  }

  Column(modifier = Modifier.fillMaxSize()) {
    MessageList(messages = messageModel)
    MessageInput(onSendMessage = { /* Handle sending message */})
  }
}

@Composable
private fun MessageItem(message: MessageModel) {
  Row(
      modifier = Modifier.fillMaxWidth().padding(8.dp),
      horizontalArrangement =
          if (message.senderId == 1) {
            Arrangement.End
          } else {
            Arrangement.Start
          }) {
        Text(
            text = message.content,
            modifier =
                Modifier.background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                    .padding(8.dp))
      }
}

@Composable
private fun MessageList(messages: List<MessageModel>) {
  LazyColumn(modifier = Modifier.fillMaxSize()) {
    items(messages) { message -> MessageItem(message = message) }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MessageInput(onSendMessage: (String) -> Unit) {
  var message by remember { mutableStateOf("") }

  Row(
      modifier = Modifier.fillMaxWidth().padding(8.dp),
      verticalAlignment = Alignment.CenterVertically) {
        TextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier.weight(1f).padding(end = 8.dp),
            placeholder = { Text("Type a message...") },
            singleLine = true,
            colors =
                TextFieldDefaults.textFieldColors(
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent))
        Button(
            onClick = {
              onSendMessage(message)
              message = ""
            },
            modifier = Modifier.padding(start = 8.dp)) {
              Text(text = "Send")
            }
      }
}
