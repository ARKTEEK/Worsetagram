package me.arkteek.worsetagram.domain.repository

import kotlinx.coroutines.flow.Flow
import me.arkteek.worsetagram.domain.model.Comment
import me.arkteek.worsetagram.domain.model.Post

interface PostRepository {
  fun getPosts(): Flow<List<Post>>

  fun getPost(postId: String): Flow<Post?>

  suspend fun createPost(post: Post)

  suspend fun updatePost(post: Post)

  suspend fun deletePost(postId: String)

  suspend fun addLike(postId: String, userId: String)

  suspend fun removeLike(postId: String, userId: String)

  suspend fun addComment(postId: String, comment: Comment)

  suspend fun deleteComment(postId: String, commentId: String)

  suspend fun hasLikedPost(postId: String, userId: String): Boolean
}
