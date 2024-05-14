package me.arkteek.worsetagram.domain.model

data class User(
  val uid: String? = "",
  val email: String = "",
  var firstname: String = "",
  var lastname: String = "",
  var nickname: String = "",
)
