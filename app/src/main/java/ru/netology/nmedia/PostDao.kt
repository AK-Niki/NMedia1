package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDao {

    @Query("SELECT * FROM Post")
    fun getAll(): LiveData<List<Post>>

    @Query("SELECT * FROM Post WHERE id = :id")
    fun getById(id: Long): Post?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(post: Post)

    @Query("UPDATE Post SET likes = likes + 1 WHERE id = :id")
    fun likeById(id: Long)

    @Query("UPDATE Post SET likes = likes - 1 WHERE id = :id")
    fun unlikeById(id: Long)

    @Query("DELETE FROM Post WHERE id = :id")
    fun removeById(id: Long)
}





