package me.arkteek.worsetagram.models

data class MessageModel(
    val id: Int,
    val senderId: Int,
    val receiverId: Int,
    val content: String,
    val timestamp: Long
)
