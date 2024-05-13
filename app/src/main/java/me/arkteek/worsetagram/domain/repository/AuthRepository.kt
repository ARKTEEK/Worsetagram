package me.arkteek.worsetagram.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import me.arkteek.worsetagram.domain.model.AuthResource

interface AuthRepository {
  val user: Flow<FirebaseUser?>

  suspend fun login(email: String, password: String): AuthResource<FirebaseUser>

  suspend fun signup(name: String, email: String, password: String): AuthResource<FirebaseUser>

  fun logout()
}
