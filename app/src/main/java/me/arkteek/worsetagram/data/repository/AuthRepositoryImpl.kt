package me.arkteek.worsetagram.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import me.arkteek.worsetagram.common.utilities.await
import me.arkteek.worsetagram.data.source.remote.AuthResource
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.domain.repository.AuthRepository
import me.arkteek.worsetagram.domain.repository.UserRepository
import javax.inject.Inject

class AuthRepositoryImpl
@Inject
constructor(private val firebaseAuth: FirebaseAuth, private val userRepository: UserRepository) :
  AuthRepository {

  override val user: Flow<FirebaseUser?>
    get() = callbackFlow {
      val authListener = FirebaseAuth.AuthStateListener { trySend(it.currentUser) }
      firebaseAuth.addAuthStateListener(authListener)
      awaitClose { firebaseAuth.removeAuthStateListener(authListener) }
    }

  override suspend fun login(email: String, password: String): AuthResource<FirebaseUser> {
    return try {
      val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
      AuthResource.Success(result.user!!)
    } catch (e: Exception) {
      e.printStackTrace()
      AuthResource.Failure(e)
    }
  }

  override suspend fun signup(
    firstname: String,
    lastname: String,
    nickname: String,
    email: String,
    password: String,
  ): AuthResource<FirebaseUser> {
    return try {
      val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
      result.user
        ?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(nickname).build())
        ?.await()

      val user = User(result.user?.uid, email, firstname, lastname, nickname)
      userRepository.merge(user)

      AuthResource.Success(result.user!!)
    } catch (e: Exception) {
      e.printStackTrace()
      AuthResource.Failure(e)
    }
  }

  override fun logout() {
    firebaseAuth.signOut()
  }
}
