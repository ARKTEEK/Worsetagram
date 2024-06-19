package me.arkteek.worsetagram.domain.model

data class Post(
  val uid: String = "",
  val authorUID: String = "",
  val description: String = "",
  val likes: List<String> = emptyList(),
  val images: List<String> = emptyList(),
  val comments: Map<String, Comment> = emptyMap(),
  val timestamp: Long = 0L,
)
