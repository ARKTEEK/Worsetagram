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
    emit(userDocument.toObject(User::class.java))
  }

  override suspend fun search(query: String): List<User> {
    val capitalizedQuery = query.capitalize()
    val nicknameResults = searchByField("nickname", query)
    if (nicknameResults.isNotEmpty()) return nicknameResults

    val firstnameResults = searchByField("firstname", capitalizedQuery)
    if (firstnameResults.isNotEmpty()) return firstnameResults

    return searchByField("lastname", capitalizedQuery)
  }

  private suspend fun searchByField(field: String, query: String): List<User> {
    val result =
      database
        .collection(COLLECTION_USERS)
        .orderBy(field)
        .startAt(query)
        .endAt("$query\uf8ff")
        .get()
        .await()
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
    updateUserFollowStatus(currentUserId, targetUserId, FieldValue.arrayUnion(targetUserId))
  }

  override suspend fun unfollow(currentUserId: String, targetUserId: String) {
    updateUserFollowStatus(currentUserId, targetUserId, FieldValue.arrayRemove(targetUserId))
  }

  private suspend fun updateUserFollowStatus(
    userId: String,
    targetId: String,
    updateAction: FieldValue,
  ) {
    database.collection(COLLECTION_USERS).document(userId).update("following", updateAction).await()
    database
      .collection(COLLECTION_USERS)
      .document(targetId)
      .update("followers", updateAction)
      .await()
  }
}
