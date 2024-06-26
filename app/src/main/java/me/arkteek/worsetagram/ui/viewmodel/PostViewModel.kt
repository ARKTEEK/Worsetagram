package me.arkteek.worsetagram.ui.viewmodel

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
class PostViewModel
@Inject
constructor(
  private val userRepository: UserRepository,
  private val postRepository: PostRepository,
) : ViewModel() {

  private val _authenticatedUser = MutableLiveData<User>()
  private val authenticatedUser: LiveData<User> = _authenticatedUser

  private val _post = MutableLiveData<Post?>()
  val post: LiveData<Post?> = _post

  private val _user = MutableLiveData<User?>()
  val user: LiveData<User?> = _user

  private val _comments = MutableLiveData<List<Comment>?>()
  val comments: MutableLiveData<List<Comment>?> = _comments

  private val _isLiked = MutableLiveData<Boolean>()
  val isLiked: LiveData<Boolean> = _isLiked

  private val _loading = mutableStateOf(false)
  val loading: State<Boolean> = _loading

  init {
    loadAuthenticatedUser()
    _isLiked.value = false
  }

  private fun loadAuthenticatedUser() {
    viewModelScope.launch {
      try {
        _loading.value = true
        userRepository.user.collect { firebaseUser ->
          if (firebaseUser != null) {
            val user = userRepository.get(firebaseUser.uid).first()
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

        val user = userRepository.get(post?.authorUID ?: "").firstOrNull()
        _user.value = user

        val hasLiked = postRepository.hasLikedPost(postId, authenticatedUser.value?.uid ?: "")
        _isLiked.value = hasLiked

        val commentsList = post?.comments ?: emptyList()
        _comments.value = commentsList
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

        val currentComments = _comments.value ?: emptyList()
        val updatedComments = currentComments.toMutableList()
        updatedComments.add(comment)
        _comments.value = updatedComments
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}
