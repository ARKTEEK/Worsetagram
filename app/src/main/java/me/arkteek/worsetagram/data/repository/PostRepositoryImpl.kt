package me.arkteek.worsetagram.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.arkteek.worsetagram.common.constants.COLLECTION_POSTS
import me.arkteek.worsetagram.common.utilities.await
import me.arkteek.worsetagram.domain.model.Comment
import me.arkteek.worsetagram.domain.model.Post
import me.arkteek.worsetagram.domain.repository.PostRepository

class PostRepositoryImpl @Inject constructor(private val database: FirebaseFirestore) :
  PostRepository {
  override fun getPosts(): Flow<List<Post>> = flow {
    val snapshot = database.collection(COLLECTION_POSTS).get().await()
    val posts = snapshot.documents.mapNotNull { it.toObject(Post::class.java) }
    emit(posts)
  }

  override fun getPost(postId: String): Flow<Post?> = flow {
    val postDocument = database.collection(COLLECTION_POSTS).document(postId).get().await()
    val post = postDocument.toObject(Post::class.java)
    emit(post)
  }

  override suspend fun createPost(post: Post) {
    database.collection(COLLECTION_POSTS).add(post).await()
  }

  override suspend fun updatePost(post: Post) {
    database.collection(COLLECTION_POSTS).document(post.uid).set(post).await()
  }

  override suspend fun deletePost(postId: String) {
    database.collection(COLLECTION_POSTS).document(postId).delete().await()
  }

  override suspend fun addLike(postId: String, userId: String) {
    database
      .collection(COLLECTION_POSTS)
      .document(postId)
      .update("likes", FieldValue.arrayUnion(userId))
      .await()
  }

  override suspend fun removeLike(postId: String, userId: String) {
    database
      .collection(COLLECTION_POSTS)
      .document(postId)
      .update("likes", FieldValue.arrayRemove(userId))
      .await()
  }

  override suspend fun addComment(postId: String, comment: Comment) {
    val postRef = database.collection(COLLECTION_POSTS).document(postId)
    database
      .runTransaction { transaction ->
        val snapshot = transaction.get(postRef)
        val post = snapshot.toObject(Post::class.java) ?: throw Exception("Post not found")
        val updatedComments = post.comments.toMutableMap()
        val commentId = database.collection("comments").document().id
        updatedComments[commentId] = comment
        transaction.update(postRef, "comments", updatedComments)
      }
      .await()
  }

  override suspend fun deleteComment(postId: String, commentId: String) {
    val postRef = database.collection(COLLECTION_POSTS).document(postId)
    database
      .runTransaction { transaction ->
        val snapshot = transaction.get(postRef)
        val post = snapshot.toObject(Post::class.java) ?: throw Exception("Post not found")
        val updatedComments = post.comments.toMutableMap()
        updatedComments.remove(commentId)
        transaction.update(postRef, "comments", updatedComments)
      }
      .await()
  }

  override suspend fun hasLikedPost(postId: String, userId: String): Boolean {
    val snapshot =
      database
        .collection(COLLECTION_POSTS)
        .document(postId)
        .collection("likes")
        .document(userId)
        .get()
        .await()
    return snapshot.exists()
  }
}
