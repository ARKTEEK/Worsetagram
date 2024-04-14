package me.arkteek.worsetagram

data class Post(
  val author: String,
  val description: String,
  val likes: Int,
  val comments: Int,
  val images: List<String>
)
