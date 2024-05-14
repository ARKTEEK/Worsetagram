package me.arkteek.worsetagram.domain.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import me.arkteek.worsetagram.domain.model.User

interface UserRepository {
  val user: Flow<FirebaseUser?>

  suspend fun get(id: String): Flow<User?>

  suspend fun merge(user: User)

  suspend fun remove(id: String)
}
