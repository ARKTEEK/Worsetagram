package me.arkteek.worsetagram.domain.model

data class User(
  val uid: String? = "",
  val email: String = "",
  val firstname: String = "",
  val lastname: String = "",
  val nickname: String = "",
  val followers: List<String> = emptyList(),
  val following: List<String> = emptyList()
)
