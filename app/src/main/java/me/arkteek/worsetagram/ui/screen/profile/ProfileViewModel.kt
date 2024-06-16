package me.arkteek.worsetagram.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.domain.repository.AuthRepository
import me.arkteek.worsetagram.domain.repository.UserRepository
import javax.inject.Inject

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
        firebaseUser?.uid?.let { userId ->
          userRepository.get(userId).collect { _user.value = it }
        }
      }
    }
  }
}
