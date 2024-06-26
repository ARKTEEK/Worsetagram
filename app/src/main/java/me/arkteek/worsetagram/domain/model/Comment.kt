package me.arkteek.worsetagram.domain.model

data class Comment(val authorUID: String = "", val text: String = "", val timestamp: Long = 0L)
