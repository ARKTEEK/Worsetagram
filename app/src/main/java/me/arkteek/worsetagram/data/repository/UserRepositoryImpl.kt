package me.arkteek.worsetagram.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.arkteek.worsetagram.common.await
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.domain.repository.AuthRepository
import me.arkteek.worsetagram.domain.repository.UserRepository
import javax.inject.Provider

class UserRepositoryImpl
@Inject
constructor(private val database: FirebaseFirestore, private val authRepository: Provider<AuthRepository>) :
  UserRepository {
  override val user: Flow<FirebaseUser?>
    get() = authRepository.get().user

  override suspend fun get(id: String): Flow<User?> = flow {
    val userDocument = database.collection("users").document(id).get().await()
    val user = userDocument.toObject(User::class.java)
    emit(user)
  }

  override suspend fun merge(user: User) {
    val userId = user.uid ?: throw IllegalArgumentException("User id must not be null")
    database.collection("users").document(userId).set(user).await()
  }

  override suspend fun remove(id: String) {
    database.collection("users").document(id).delete().await()
  }
}
