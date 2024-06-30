package me.arkteek.worsetagram.ui.screen.post.comments

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import me.arkteek.worsetagram.domain.model.Comment
import me.arkteek.worsetagram.domain.model.Post
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.domain.repository.PostRepository
import me.arkteek.worsetagram.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class CommentsViewModel
@Inject
constructor(
  private val userRepository: UserRepository,
  private val postRepository: PostRepository,
) : ViewModel() {
  private val _authenticatedUser = MutableLiveData<User>()
  val authenticatedUser: LiveData<User> = _authenticatedUser

  private val _post = MutableLiveData<Post?>()
  val post: LiveData<Post?> = _post

  private val _comments = MutableLiveData<List<Comment>?>()
  val comments: LiveData<List<Comment>?> = _comments

  private val _loading = mutableStateOf(false)
  val loading: State<Boolean> = _loading

  init {
    loadAuthenticatedUser()
  }

  private fun loadAuthenticatedUser() {
    viewModelScope.launch {
      try {
        _loading.value = true
        userRepository.user.collect { firebaseUser ->
          firebaseUser?.let {
            val user = userRepository.get(it.uid).first()
            _authenticatedUser.value = user!!
          }
        }
      } catch (e: Exception) {
        e.printStackTrace()
      } finally {
        _loading.value = false
      }
    }
  }

  fun loadPost(postId: String) {
    viewModelScope.launch {
      try {
        _loading.value = true
        val post = postRepository.getPost(postId).firstOrNull()
        _post.value = post
        _comments.value = post?.comments ?: emptyList()
      } catch (e: Exception) {
        e.printStackTrace()
      } finally {
        _loading.value = false
      }
    }
  }

  fun addComment(commentText: String) {
    viewModelScope.launch {
      try {
        val postId = _post.value?.uid ?: return@launch
        val comment =
          Comment(
            authorUID = authenticatedUser.value?.uid ?: "",
            text = commentText,
            timestamp = System.currentTimeMillis(),
          )
        postRepository.addComment(postId, comment)
        _comments.value = _comments.value.orEmpty() + comment
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  suspend fun getUserNickname(userId: String): String {
    return userRepository.get(userId).firstOrNull()?.nickname ?: ""
  }
}
