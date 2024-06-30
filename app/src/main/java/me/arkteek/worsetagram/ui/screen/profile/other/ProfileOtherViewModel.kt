package me.arkteek.worsetagram.ui.screen.profile.other

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.domain.repository.AuthRepository
import me.arkteek.worsetagram.domain.repository.PostRepository
import me.arkteek.worsetagram.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileOtherViewModel
@Inject
constructor(
  private val authRepository: AuthRepository,
  private val userRepository: UserRepository,
  private val postRepository: PostRepository,
) : ViewModel() {

  private val _firebaseViewerUser = MutableStateFlow<FirebaseUser?>(null)
  val firebaseViewerUser: StateFlow<FirebaseUser?>
    get() = _firebaseViewerUser

  private val _userViewer = MutableStateFlow<User?>(null)
  val userViewer: StateFlow<User?>
    get() = _userViewer

  private val _userOwner = MutableStateFlow<User?>(null)
  val userOwner: StateFlow<User?>
    get() = _userOwner

  private val _isFollowing = MutableStateFlow<Boolean?>(null)
  val isFollowing: StateFlow<Boolean?>
    get() = _isFollowing

  init {
    viewModelScope.launch {
      authRepository.user.collect { firebaseUser ->
        _firebaseViewerUser.value = firebaseUser
        firebaseUser?.uid?.let { viewerUserId ->
          userRepository.get(viewerUserId).collect { _userViewer.value = it }
        }
      }
    }
  }

  fun loadUserOwnerProfile(ownerUserId: String) {
    viewModelScope.launch { userRepository.get(ownerUserId).collect { _userOwner.value = it } }
  }

  fun checkFollowingStatus(ownerUserId: String) {
    val currentUserId = _firebaseViewerUser.value?.uid ?: return
    viewModelScope.launch {
      val user = userRepository.get(currentUserId).firstOrNull()
      _isFollowing.value = user?.following?.contains(ownerUserId) ?: false
    }
  }

  fun followUser(targetUserId: String) {
    val currentUserId = _firebaseViewerUser.value?.uid ?: return
    viewModelScope.launch {
      userRepository.follow(currentUserId, targetUserId)
      _isFollowing.value = true
    }
  }

  fun unfollowUser(targetUserId: String) {
    val currentUserId = _firebaseViewerUser.value?.uid ?: return
    viewModelScope.launch {
      userRepository.unfollow(currentUserId, targetUserId)
      _isFollowing.value = false
    }
  }

  suspend fun getPostAmount(userId: String): Int {
    return postRepository.getUserPosts(userId).count()
  }
}
