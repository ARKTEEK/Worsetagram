package me.arkteek.worsetagram.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import me.arkteek.worsetagram.domain.model.ChatItem
import me.arkteek.worsetagram.domain.model.Message
import me.arkteek.worsetagram.domain.repository.ChatRepository
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(private val database: FirebaseFirestore) :
  ChatRepository {

  override suspend fun createConversationIfNotExists(chatId: String, users: List<String>) {
    val existingChatQuery = database.collection("chats").whereEqualTo("users", users).get().await()

    if (!existingChatQuery.isEmpty) {
      return
    }

    val chatRef =
      if (chatId.isEmpty() || chatId == "{chatId}") {
        database.collection("chats").document()
      } else {
        database.collection("chats").document(chatId)
      }

    val chatSnapshot = chatRef.get().await()
    if (!chatSnapshot.exists()) {
      chatRef.set(hashMapOf("users" to users)).await()
    }
  }

  override suspend fun loadMessages(chatId: String, users: List<String>?): List<Message> {

    if (chatId.isEmpty() || chatId == "{chatId}") {
      requireNotNull(users) { "Users list must be provided when chatId is empty or \"{chatId}\"" }

      val querySnapshot =
        database.collection("chats").whereEqualTo("users", users).limit(1).get().await()

      if (querySnapshot.isEmpty) {
        return emptyList()
      }

      val chatIdFromDocument = querySnapshot.documents.first().id

      val messagesSnapshot =
        database
          .collection("chats")
          .document(chatIdFromDocument)
          .collection("messages")
          .orderBy("timestamp")
          .get()
          .await()

      return messagesSnapshot.documents.mapNotNull { messageDocument ->
        messageDocument.toObject(Message::class.java)
      }
    } else {
      val messagesSnapshot =
        database
          .collection("chats")
          .document(chatId)
          .collection("messages")
          .orderBy("timestamp")
          .get()
          .await()

      return messagesSnapshot.documents.mapNotNull { messageDocument ->
        messageDocument.toObject(Message::class.java)
      }
    }
  }

  override suspend fun sendMessage(document: StateFlow<DocumentSnapshot?>, message: Message) {
    document.value?.reference?.collection("messages")?.add(message)?.await()
  }

  override suspend fun loadUserConversations(userId: String): List<ChatItem> {
    val chatsSnapshot =
      database.collection("chats").whereArrayContains("users", userId).get().await()

    return chatsSnapshot.documents.map { document ->
      val users = document.get("users") as List<String>
      val otherUserId = users.find { it != userId } ?: ""

      val userSnapshot = database.collection("users").document(otherUserId).get().await()
      val nickname = userSnapshot.getString("nickname") ?: "User $otherUserId"

      val lastMessageSnapshot =
        document.reference
          .collection("messages")
          .orderBy("timestamp", Query.Direction.DESCENDING)
          .limit(1)
          .get()
          .await()
          .documents
          .firstOrNull()

      ChatItem(
        avatar = "https://i.imgur.com/nDAA9Th.jpeg",
        name = nickname,
        lastMessage = lastMessageSnapshot?.getString("content") ?: "",
        chatId = document.id,
        otherUserId = otherUserId,
      )
    }
  }

  override suspend fun getChatDocument(chatId: String, users: List<String>?): DocumentSnapshot {
    val query =
      if (chatId.isEmpty() || chatId == "{chatId}") {
        requireNotNull(users) { "Users list must be provided when chatId is empty or \"{chatId}\"" }

        database
          .collection("chats")
          .whereEqualTo("users", users.toMutableList())
          .limit(1)
          .get()
          .await()
          .documents
          .firstOrNull()
      } else {
        database.collection("chats").document(chatId).get().await()
      }

    return requireNotNull(query) { createConversationIfNotExists(chatId, users!!) }
  }
}
