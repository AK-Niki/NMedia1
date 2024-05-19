package ru.netology.nmedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class PostViewModel : ViewModel() {
    val post = MutableLiveData(
        Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 99999,
            shares = 0,
            views = 500
        )
    )

    fun like() {
        val currentPost = post.value ?: return
        val liked = !currentPost.likedByMe
        val likes = if (liked) currentPost.likes + 1 else currentPost.likes - 1
        post.value = currentPost.copy(likedByMe = liked, likes = likes)
    }

    fun share() {
        val currentPost = post.value ?: return
        post.value = currentPost.copy(shares = currentPost.shares + 1)
    }
}




