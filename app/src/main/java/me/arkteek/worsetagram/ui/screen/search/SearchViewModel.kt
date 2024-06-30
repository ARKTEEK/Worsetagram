package me.arkteek.worsetagram.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.domain.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val userRepository: UserRepository) :
  ViewModel() {

  private val _searchResults = MutableStateFlow<List<User>>(emptyList())
  val searchResults: StateFlow<List<User>> = _searchResults

  fun searchUsers(query: String) {
    viewModelScope.launch {
      val results = userRepository.search(query)
      _searchResults.value = results
    }
  }
}
