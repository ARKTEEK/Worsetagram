package me.arkteek.worsetagram.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import me.arkteek.worsetagram.domain.model.ChatItem
import me.arkteek.worsetagram.domain.model.Message
import me.arkteek.worsetagram.domain.repository.ChatRepository
import me.arkteek.worsetagram.domain.repository.UserRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ChatViewModel
@Inject
constructor(
  private val chatRepository: ChatRepository,
  private val userRepository: UserRepository,
) : ViewModel() {
  private val _messages = MutableStateFlow<List<Message>>(emptyList())
  val messages: StateFlow<List<Message>> = _messages

  private val _conversations = MutableStateFlow<List<ChatItem>>(emptyList())
  val conversations: StateFlow<List<ChatItem>> = _conversations

  private val _document = MutableStateFlow<DocumentSnapshot?>(null)
  private val document: StateFlow<DocumentSnapshot?> = _document

  fun createConversationIfNotExists(chatId: String, users: List<String>) {
    viewModelScope.launch { chatRepository.createConversationIfNotExists(chatId, users) }
  }

  fun loadMessages(chatId: String, users: List<String> = emptyList()) {
    viewModelScope.launch { _messages.value = chatRepository.loadMessages(chatId, users) }
  }

  fun sendMessage(chatId: String, content: String) {
    viewModelScope.launch {
      val message =
        Message(
          id = UUID.randomUUID().toString(),
          content = content,
          sender = FirebaseAuth.getInstance().currentUser?.uid ?: "",
          timestamp = System.currentTimeMillis(),
        )
      chatRepository.sendMessage(document, message)
      loadMessages(chatId)
    }
  }

  fun loadUserConversations(userId: String) {
    viewModelScope.launch { _conversations.value = chatRepository.loadUserConversations(userId) }
  }

  fun findDocument(chatId: String, users: List<String> = emptyList()) {
    viewModelScope.launch { _document.value = chatRepository.getChatDocument(chatId, users) }
  }

  suspend fun getUserNickname(userId: String): String {
    return userRepository.get(userId).firstOrNull()?.nickname ?: ""
  }
}
