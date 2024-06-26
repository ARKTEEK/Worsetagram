package me.arkteek.worsetagram.domain.model

data class PostWithAuthor(val post: Post, val author: User?, val comments: List<Comment>)
