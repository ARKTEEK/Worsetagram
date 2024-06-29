package me.arkteek.worsetagram.domain.repository

import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.flow.StateFlow
import me.arkteek.worsetagram.domain.model.ChatItem
import me.arkteek.worsetagram.domain.model.Message

interface ChatRepository {
  suspend fun createConversationIfNotExists(chatId: String, users: List<String>)

  suspend fun loadMessages(chatId: String, users: List<String>? = null): List<Message>

  suspend fun sendMessage(document: StateFlow<DocumentSnapshot?>, message: Message)

  suspend fun loadUserConversations(userId: String): List<ChatItem>

  suspend fun getChatDocument(chatId: String, users: List<String>? = null): DocumentSnapshot
}
