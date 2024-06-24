package ru.netology.nmedia

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositorySQLiteImpl(private val context: Context) : PostRepository {
    private val dbHelper = PostDatabaseHelper(context)
    private val data = MutableLiveData<List<Post>>()

    init {
        loadPosts()
    }

    private fun loadPosts() {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            PostDatabaseHelper.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            "${PostDatabaseHelper.COLUMN_ID} DESC"
        )
        data.value = cursorToList(cursor)
        cursor.close()
    }

    private fun cursorToList(cursor: Cursor): List<Post> {
        val posts = mutableListOf<Post>()
        while (cursor.moveToNext()) {
            val post = Post(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_ID)),
                author = cursor.getString(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_AUTHOR)),
                content = cursor.getString(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_CONTENT)),
                published = cursor.getString(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_PUBLISHED)),
                likedByMe = cursor.getInt(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_LIKED_BY_ME)) != 0,
                likes = cursor.getInt(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_LIKES)),
                shares = cursor.getInt(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_SHARES)),
                views = cursor.getInt(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_VIEWS))
            )
            posts.add(post)
        }
        return posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        try {
            val cursor = db.query(
                PostDatabaseHelper.TABLE_NAME,
                null,
                "${PostDatabaseHelper.COLUMN_ID} = ?",
                arrayOf(id.toString()),
                null,
                null,
                null
            )
            if (cursor.moveToFirst()) {
                val post = Post(
                    id = cursor.getLong(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_ID)),
                    author = cursor.getString(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_AUTHOR)),
                    content = cursor.getString(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_CONTENT)),
                    published = cursor.getString(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_PUBLISHED)),
                    likedByMe = cursor.getInt(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_LIKED_BY_ME)) != 0,
                    likes = cursor.getInt(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_LIKES)),
                    shares = cursor.getInt(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_SHARES)),
                    views = cursor.getInt(cursor.getColumnIndexOrThrow(PostDatabaseHelper.COLUMN_VIEWS))
                )
                val values = ContentValues().apply {
                    put(PostDatabaseHelper.COLUMN_LIKED_BY_ME, !post.likedByMe)
                    put(PostDatabaseHelper.COLUMN_LIKES, if (post.likedByMe) post.likes - 1 else post.likes + 1)
                }
                db.update(
                    PostDatabaseHelper.TABLE_NAME,
                    values,
                    "${PostDatabaseHelper.COLUMN_ID} = ?",
                    arrayOf(id.toString())
                )
            }
            cursor.close()
            db.setTransactionSuccessful()
        } finally {
            db.endTransaction()
        }
        loadPosts()
    }

    override fun shareById(id: Long) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(PostDatabaseHelper.COLUMN_SHARES, "shares + 1")
        }
        db.update(
            PostDatabaseHelper.TABLE_NAME,
            values,
            "${PostDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        loadPosts()
    }

    override fun save(post: Post) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(PostDatabaseHelper.COLUMN_AUTHOR, post.author)
            put(PostDatabaseHelper.COLUMN_CONTENT, post.content)
            put(PostDatabaseHelper.COLUMN_PUBLISHED, post.published)
            put(PostDatabaseHelper.COLUMN_LIKED_BY_ME, post.likedByMe)
            put(PostDatabaseHelper.COLUMN_LIKES, post.likes)
            put(PostDatabaseHelper.COLUMN_SHARES, post.shares)
            put(PostDatabaseHelper.COLUMN_VIEWS, post.views)
        }
        if (post.id == 0L) {
            db.insert(PostDatabaseHelper.TABLE_NAME, null, values)
        } else {
            db.update(
                PostDatabaseHelper.TABLE_NAME,
                values,
                "${PostDatabaseHelper.COLUMN_ID} = ?",
                arrayOf(post.id.toString())
            )
        }
        loadPosts()
    }

    override fun removeById(id: Long) {
        val db = dbHelper.writableDatabase
        db.delete(
            PostDatabaseHelper.TABLE_NAME,
            "${PostDatabaseHelper.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
        loadPosts()
    }
}
