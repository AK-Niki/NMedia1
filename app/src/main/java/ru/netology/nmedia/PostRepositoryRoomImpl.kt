package ru.netology.nmedia

import androidx.lifecycle.LiveData

class PostRepositoryRoomImpl(
    private val dao: PostDao
) : PostRepository {

    override val data: LiveData<List<Post>> = dao.getAll()

    override fun getAll(): LiveData<List<Post>> = dao.getAll()

    override fun likeById(id: Long) {
        val post = dao.getById(id)
        if (post != null) {
            if (post.likedByMe) {
                dao.unlikeById(id)
            } else {
                dao.likeById(id)
            }
        }
    }

    override fun shareById(id: Long) {
        // Implement share logic if required
    }

    override fun save(post: Post) {
        dao.save(post)
    }

    override fun removeById(id: Long) {
        dao.removeById(id)
    }
}





