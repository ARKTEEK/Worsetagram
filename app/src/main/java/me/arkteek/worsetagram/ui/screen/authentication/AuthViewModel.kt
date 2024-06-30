package me.arkteek.worsetagram.ui.screen.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.arkteek.worsetagram.data.source.remote.AuthResource
import me.arkteek.worsetagram.domain.repository.AuthRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
  private val _loginFlow = MutableStateFlow<AuthResource<FirebaseUser>?>(null)
  val loginFlow: StateFlow<AuthResource<FirebaseUser>?> = _loginFlow

  private val _signupFlow = MutableStateFlow<AuthResource<FirebaseUser>?>(null)
  val signupFlow: StateFlow<AuthResource<FirebaseUser>?> = _signupFlow

  init {
    observeUser()
  }

  private fun observeUser() {
    viewModelScope.launch {
      repository.user.collect { user ->
        _loginFlow.value = if (user != null) AuthResource.Success(user) else null
      }
    }
  }

  fun loginUser(email: String, password: String) =
    viewModelScope.launch {
      _loginFlow.value = AuthResource.Loading
      _loginFlow.value = repository.login(email, password)
    }

  fun signupUser(
    firstname: String,
    lastname: String,
    nickname: String,
    email: String,
    password: String,
  ) =
    viewModelScope.launch {
      _signupFlow.value = AuthResource.Loading
      _signupFlow.value = repository.signup(firstname, lastname, nickname, email, password)
    }

  fun logout() {
    repository.logout()
    _loginFlow.value = null
    _signupFlow.value = null
  }
}
