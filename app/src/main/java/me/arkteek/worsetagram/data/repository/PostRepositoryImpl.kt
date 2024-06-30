package me.arkteek.worsetagram.data.repository

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import me.arkteek.worsetagram.common.constants.COLLECTION_POSTS
import me.arkteek.worsetagram.common.utilities.await
import me.arkteek.worsetagram.domain.model.Comment
import me.arkteek.worsetagram.domain.model.Post
import me.arkteek.worsetagram.domain.repository.PostRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(private val database: FirebaseFirestore) :
  PostRepository {

  override fun getPosts(): Flow<List<Post>> = flow { emit(fetchPosts()) }

  override fun getPost(postId: String): Flow<Post?> = flow { emit(fetchPost(postId)) }

  override fun getUserPosts(userId: String): Flow<List<Post>> = flow {
    emit(fetchPostsByUser(userId))
  }

  override suspend fun createPost(post: Post) {
    database.collection(COLLECTION_POSTS).add(post).await()
  }

  override suspend fun updatePost(post: Post) {
    database.collection(COLLECTION_POSTS).document(post.uid).set(post).await()
  }

  override suspend fun deletePost(postId: String) {
    val documentSnapshot =
      fetchDocumentSnapshot(postId)
        ?: throw IllegalStateException("Post not found for uid: $postId")
    documentSnapshot.reference.delete().await()
  }

  override suspend fun getCommentsForPost(postId: String): List<Comment> {
    val post = fetchPost(postId) ?: throw IllegalStateException("Post not found for uid: $postId")
    return post.comments
  }

  override suspend fun addLike(postId: String, userId: String) {
    updateLikes(postId) { likes ->
      if (!likes.contains(userId)) {
        likes.add(userId)
      }
    }
  }

  override suspend fun removeLike(postId: String, userId: String) {
    updateLikes(postId) { likes -> likes.remove(userId) }
  }

  override suspend fun hasLikedPost(postId: String, userId: String): Boolean {
    val post =
      fetchPost(postId) ?: throw IllegalStateException("Post not found for postId: $postId")
    return post.likes.contains(userId)
  }

  override suspend fun addComment(postId: String, comment: Comment) {
    val documentSnapshot =
      fetchDocumentSnapshot(postId)
        ?: throw IllegalStateException("Post not found for postId: $postId")
    database
      .runTransaction { transaction ->
        val post =
          transaction.get(documentSnapshot.reference).toObject(Post::class.java)
            ?: throw Exception("Post not found")
        val updatedComments = post.comments.toMutableList()
        updatedComments.add(comment)
        transaction.update(documentSnapshot.reference, "comments", updatedComments)
      }
      .await()
  }

  private suspend fun fetchPosts(): List<Post> {
    val snapshot = database.collection(COLLECTION_POSTS).get().await()
    return snapshot.documents.mapNotNull { it.toObject(Post::class.java) }
  }

  private suspend fun fetchPost(postId: String): Post? {
    val querySnapshot =
      database.collection(COLLECTION_POSTS).whereEqualTo("uid", postId).get().await()
    return querySnapshot.documents.firstOrNull()?.toObject(Post::class.java)
  }

  private suspend fun fetchPostsByUser(userId: String): List<Post> {
    val snapshot =
      database.collection(COLLECTION_POSTS).whereEqualTo("authorUID", userId).get().await()
    return snapshot.documents.mapNotNull { it.toObject(Post::class.java) }
  }

  private suspend fun fetchDocumentSnapshot(postId: String): DocumentSnapshot? {
    val querySnapshot =
      database.collection(COLLECTION_POSTS).whereEqualTo("uid", postId).get().await()
    return querySnapshot.documents.firstOrNull()
  }

  private suspend fun updateLikes(postId: String, updateAction: (MutableList<Any>) -> Unit) {
    val documentSnapshot =
      fetchDocumentSnapshot(postId)
        ?: throw IllegalStateException("Post not found for postId: $postId")
    database
      .runTransaction { transaction ->
        val post =
          transaction.get(documentSnapshot.reference).toObject(Post::class.java)
            ?: throw Exception("Post not found")
        val updatedLikes = post.likes.toMutableList()
        updateAction(updatedLikes)
        transaction.update(documentSnapshot.reference, "likes", updatedLikes)
      }
      .await()
  }
}
