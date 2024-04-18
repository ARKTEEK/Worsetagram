package me.arkteek.worsetagram.models

data class PostModel(
    val author: String,
    val description: String,
    val likes: Int,
    val comments: Int,
    val images: List<String>
)
