package me.arkteek.worsetagram.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.arkteek.worsetagram.data.source.remote.AuthResource
import me.arkteek.worsetagram.domain.repository.AuthRepository

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository) : ViewModel() {
  private val _loginFlow = MutableStateFlow<AuthResource<FirebaseUser>?>(null)
  val loginFlow: StateFlow<AuthResource<FirebaseUser>?> = _loginFlow

  private val _signupFlow = MutableStateFlow<AuthResource<FirebaseUser>?>(null)
  val signupFlow: StateFlow<AuthResource<FirebaseUser>?> = _signupFlow

  val currentUser: Flow<FirebaseUser?> = repository.user

  init {
    viewModelScope.launch {
      repository.user.collect { user ->
        if (user != null) {
          _loginFlow.value = AuthResource.Success(user)
        } else {
          _loginFlow.value = null
        }
      }
    }
  }

  fun loginUser(email: String, password: String) =
    viewModelScope.launch {
      _loginFlow.value = AuthResource.Loading
      val result = repository.login(email, password)
      _loginFlow.value = result
    }

  fun singupUser(
    firstname: String,
    lastname: String,
    nickname: String,
    email: String,
    password: String,
  ) =
    viewModelScope.launch {
      _signupFlow.value = AuthResource.Loading
      val result = repository.signup(firstname, lastname, nickname, email, password)
      _signupFlow.value = result
    }

  fun logout() {
    repository.logout()
    _loginFlow.value = null
    _signupFlow.value = null
  }
}
