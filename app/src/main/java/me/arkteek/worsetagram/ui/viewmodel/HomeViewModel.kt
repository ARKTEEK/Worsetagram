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
import me.arkteek.worsetagram.domain.model.PostWithAuthor
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.domain.repository.PostRepository
import me.arkteek.worsetagram.domain.repository.StorageRepository
import me.arkteek.worsetagram.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
  private val userRepository: UserRepository,
  private val postRepository: PostRepository,
  private val storageRepository: StorageRepository,
) : ViewModel() {

  private val _authenticatedUser = MutableLiveData<User>()
  val authenticatedUser: LiveData<User> = _authenticatedUser

  private val _postsWithAuthors = MutableLiveData<List<PostWithAuthor>>()
  val postsWithAuthors: LiveData<List<PostWithAuthor>> = _postsWithAuthors

  private val _loading = mutableStateOf(false)
  val loading: State<Boolean> = _loading

  private val _likedPosts = mutableMapOf<String, MutableLiveData<Boolean>>()

  init {
    loadAuthenticatedUser()
    loadPostsWithAuthors()
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

  private suspend fun getComments(postId: String): List<Comment> {
    return postRepository.getCommentsForPost(postId)
  }

  private fun loadPostsWithAuthors() {
    viewModelScope.launch {
      try {
        _loading.value = true
        val posts = postRepository.getPosts().first()

        val postsWithAuthors =
          posts
            .map { post ->
              val author = userRepository.get(post.authorUID).firstOrNull()

              val comments = getComments(post.uid)

              PostWithAuthor(post, author, comments)
            }
            .sortedByDescending { it.post.timestamp }

        _postsWithAuthors.value = postsWithAuthors

        posts.forEach { post -> _likedPosts[post.uid] = MutableLiveData(false) }

        val currentUser = authenticatedUser.value
        if (currentUser != null) {
          posts.forEach { post ->
            viewModelScope.launch {
              val hasLiked = postRepository.hasLikedPost(post.uid, currentUser.uid.toString())
              _likedPosts[post.uid]?.postValue(hasLiked)
            }
          }
        }
      } catch (e: Exception) {
        e.printStackTrace()
      } finally {
        _loading.value = false
      }
    }
  }

  fun getLikedStatus(postId: String): LiveData<Boolean> {
    return _likedPosts[postId] ?: MutableLiveData(false)
  }

  fun likePost(postId: String) {
    viewModelScope.launch {
      try {
        val currentUser = authenticatedUser.value ?: return@launch
        postRepository.addLike(postId, currentUser.uid.toString())
        _likedPosts[postId]?.postValue(true)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }

  fun unlikePost(postId: String) {
    viewModelScope.launch {
      try {
        val currentUser = authenticatedUser.value ?: return@launch
        postRepository.removeLike(postId, currentUser.uid.toString())
        _likedPosts[postId]?.postValue(false)
      } catch (e: Exception) {
        e.printStackTrace()
      }
    }
  }
}
