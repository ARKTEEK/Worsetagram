package me.arkteek.worsetagram.models

data class MessageModel(
    val id: String,
    val content: String,
    val sender: String,
    val timestamp: Long
)
