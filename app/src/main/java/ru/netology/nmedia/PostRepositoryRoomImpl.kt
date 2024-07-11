package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryRoomImpl(
    private val dao: PostDao
) : PostRepository {

    private val _data = MutableLiveData<List<Post>>()
    override val data: LiveData<List<Post>> = _data

    init {
        _data.value = dao.getAll()
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        dao.likeById(id)
        _data.value = dao.getAll()
    }

    override fun shareById(id: Long) {
        dao.shareById(id)
        _data.value = dao.getAll()
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
        _data.value = dao.getAll()
    }

    override fun save(post: Post) {
        dao.save(post)
        _data.value = dao.getAll()
    }
}

