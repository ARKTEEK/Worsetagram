package me.arkteek.worsetagram.domain.model

data class Post(
  val uid: String = "",
  val authorUID: String = "",
  val description: String = "",
  val likes: List<Any> = emptyList(),
  val images: List<String> = emptyList(),
  val comments: List<Comment> = emptyList(),
  val timestamp: Long = 0L,
)
