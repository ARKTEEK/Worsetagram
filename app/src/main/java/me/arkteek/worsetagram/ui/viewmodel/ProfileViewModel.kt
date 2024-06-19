package me.arkteek.worsetagram.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.arkteek.worsetagram.common.utilities.await
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.domain.repository.AuthRepository
import me.arkteek.worsetagram.domain.repository.UserRepository

@HiltViewModel
class ProfileViewModel
@Inject
constructor(
  private val authRepository: AuthRepository,
  private val userRepository: UserRepository,
) : ViewModel() {

  private val _firebaseUser = MutableStateFlow<FirebaseUser?>(null)
  val firebaseUser: StateFlow<FirebaseUser?>
    get() = _firebaseUser

  private val _user = MutableStateFlow<User?>(null)
  val user: StateFlow<User?>
    get() = _user

  init {
    viewModelScope.launch {
      authRepository.user.collect { firebaseUser ->
        _firebaseUser.value = firebaseUser
        firebaseUser?.uid?.let { userId -> userRepository.get(userId).collect { _user.value = it } }
      }
    }
  }

  fun updateUserDetails(updatedUser: User, newEmail: String? = null) {
    viewModelScope.launch {
      try {
        userRepository.merge(updatedUser)
        _user.value = updatedUser

        firebaseUser.value?.let { firebaseUser ->
          newEmail
            ?.takeIf { it.isNotEmpty() }
            ?.let { email -> firebaseUser.updateEmail(email).await() }
          //          newPassword.takeIf { it.isNotEmpty() }?.let { password ->
          //            firebaseUser.updatePassword(password).await()
          //          }
        }
      } catch (_: Exception) {}
    }
  }
}
