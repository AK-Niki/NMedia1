package ru.netology.nmedia

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PostDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_AUTHOR TEXT NOT NULL,
                $COLUMN_CONTENT TEXT NOT NULL,
                $COLUMN_PUBLISHED TEXT NOT NULL,
                $COLUMN_LIKED_BY_ME INTEGER NOT NULL,
                $COLUMN_LIKES INTEGER NOT NULL,
                $COLUMN_SHARES INTEGER NOT NULL,
                $COLUMN_VIEWS INTEGER NOT NULL
            );
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "posts.db"
        const val TABLE_NAME = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_LIKED_BY_ME = "likedByMe"
        const val COLUMN_LIKES = "likes"
        const val COLUMN_SHARES = "shares"
        const val COLUMN_VIEWS = "views"
    }
}
