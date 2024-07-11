package ru.netology.nmedia

import androidx.lifecycle.LiveData

interface PostRepository {

    val data: LiveData<List<Post>>
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun save(post: Post)
    fun removeById(id: Long)
}


