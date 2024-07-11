package ru.netology.nmedia

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM Post")
    fun getAll(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(post: Post)

    @Query("UPDATE Post SET likes = likes + 1 WHERE id = :id")
    fun likeById(id: Long)

    @Query("UPDATE Post SET shares = shares + 1 WHERE id = :id")
    fun shareById(id: Long)

    @Query("DELETE FROM Post WHERE id = :id")
    fun removeById(id: Long)
}




