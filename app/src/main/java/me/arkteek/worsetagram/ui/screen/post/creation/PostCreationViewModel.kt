package me.arkteek.worsetagram.ui.screen.post.creation

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import me.arkteek.worsetagram.domain.model.Post
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.domain.repository.PostRepository
import me.arkteek.worsetagram.domain.repository.StorageRepository
import me.arkteek.worsetagram.domain.repository.UserRepository
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class PostCreationViewModel
@Inject
constructor(
  private val userRepository: UserRepository,
  private val postRepository: PostRepository,
  private val storageRepository: StorageRepository,
) : ViewModel() {
  private val _authenticatedUser = MutableLiveData<User>()
  val authenticatedUser: LiveData<User> = _authenticatedUser

  private val _createPostState = MutableStateFlow<CreatePostState>(CreatePostState.Initial)
  val createPostState: StateFlow<CreatePostState> = _createPostState
  init {
    loadAuthenticatedUser()
  }

  private fun loadAuthenticatedUser() {
    viewModelScope.launch {
      try {
        userRepository.user.collect { firebaseUser ->
          firebaseUser?.let {
            val user = userRepository.get(it.uid).first()
            _authenticatedUser.value = user!!
          }
        }
      } catch (e: Exception) {
        e.printStackTrace()
      } finally {
      }
    }
  }

  fun createPost(description: String, imageUri: Uri) {
    viewModelScope.launch {
      _createPostState.value = CreatePostState.Loading
      try {
        val currentUser = userRepository.user.firstOrNull()
        if (currentUser != null) {
          val imageUrl = storageRepository.uploadFile(imageUri, "posts/${UUID.randomUUID()}.jpg")
          val post =
            Post(
              uid = UUID.randomUUID().toString(),
              authorUID = currentUser.uid,
              description = description,
              images = listOf(imageUrl),
              timestamp = System.currentTimeMillis(),
            )
          postRepository.createPost(post)
          _createPostState.value = CreatePostState.Success
        } else {
          _createPostState.value = CreatePostState.Error("User not authenticated")
        }
      } catch (e: Exception) {
        _createPostState.value = CreatePostState.Error(e.message ?: "Unknown error")
      }
    }
  }
}

sealed class CreatePostState {
  object Initial : CreatePostState()

  object Loading : CreatePostState()

  object Success : CreatePostState()

  data class Error(val message: String) : CreatePostState()
}
