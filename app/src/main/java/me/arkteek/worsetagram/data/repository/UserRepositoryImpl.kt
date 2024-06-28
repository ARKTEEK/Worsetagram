package me.arkteek.worsetagram.data.repository

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.arkteek.worsetagram.common.constants.COLLECTION_USERS
import me.arkteek.worsetagram.common.utilities.await
import me.arkteek.worsetagram.domain.model.User
import me.arkteek.worsetagram.domain.repository.AuthRepository
import me.arkteek.worsetagram.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Provider

class UserRepositoryImpl
@Inject
constructor(
  private val database: FirebaseFirestore,
  private val authRepository: Provider<AuthRepository>,
) : UserRepository {
  override val user: Flow<FirebaseUser?>
    get() = authRepository.get().user

  override suspend fun get(id: String): Flow<User?> = flow {
    val userDocument = database.collection(COLLECTION_USERS).document(id).get().await()
    val user = userDocument.toObject(User::class.java)
    emit(user)
  }

  override suspend fun search(query: String): List<User> {
    val capitalizedQuery = query.capitalize()
    val result =
      database
        .collection("users")
        .orderBy("nickname")
        .startAt(query)
        .endAt(query + "\uf8ff")
        .get()
        .await()

    if (result.isEmpty) {
      val nameResults =
        database
          .collection("users")
          .orderBy("firstname")
          .startAt(capitalizedQuery)
          .endAt(capitalizedQuery + "\uf8ff")
          .get()
          .await()

      if (nameResults.isEmpty) {
        return database
          .collection("users")
          .orderBy("lastname")
          .startAt(capitalizedQuery)
          .endAt(capitalizedQuery + "\uf8ff")
          .get()
          .await()
          .toObjects(User::class.java)
      }

      return nameResults.toObjects(User::class.java)
    }

    return result.toObjects(User::class.java)
  }

  override suspend fun merge(user: User) {
    val userId = user.uid ?: throw IllegalArgumentException("User id must not be null")
    database.collection(COLLECTION_USERS).document(userId).set(user).await()
  }

  override suspend fun remove(id: String) {
    database.collection(COLLECTION_USERS).document(id).delete().await()
  }

  override suspend fun follow(currentUserId: String, targetUserId: String) {
    database
      .collection(COLLECTION_USERS)
      .document(currentUserId)
      .update("following", FieldValue.arrayUnion(targetUserId))
      .await()

    database
      .collection(COLLECTION_USERS)
      .document(targetUserId)
      .update("followers", FieldValue.arrayUnion(currentUserId))
  }

  override suspend fun unfollow(currentUserId: String, targetUserId: String) {
    database
      .collection(COLLECTION_USERS)
      .document(currentUserId)
      .update("following", FieldValue.arrayRemove(targetUserId))
      .await()

    database
      .collection(COLLECTION_USERS)
      .document(targetUserId)
      .update("followers", FieldValue.arrayRemove(currentUserId))
  }
}
