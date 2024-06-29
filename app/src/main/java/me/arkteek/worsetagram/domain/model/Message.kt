package me.arkteek.worsetagram.domain.model

data class Message(
  val id: String = "",
  val content: String = "",
  val sender: String = "",
  val timestamp: Long = 0,
) {
  constructor() : this("", "", "", 0)
}
