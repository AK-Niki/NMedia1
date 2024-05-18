package ru.netology.nmedia

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.NetologyMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: NetologyMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NetologyMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            likes = 99999,
            shares = 0,
            views = 500
        )

        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            updateLikeIcon(post.likedByMe)
            updateLikesCount(post.likes)
            updateSharesCount(post.shares)
            updateViewsCount(post.views)

            Likes.setOnClickListener {
                post.likedByMe = !post.likedByMe
                post.likes += if (post.likedByMe) 1 else -1
                updateLikeIcon(post.likedByMe)
                updateLikesCount(post.likes)
            }

            Repost.setOnClickListener {
                post.shares++
                updateSharesCount(post.shares)
                updateShareIcon(post.shares)
            }
        }
    }

    private fun updateLikeIcon(liked: Boolean) {
        binding.Likes.setImageResource(
            if (liked) R.drawable.heart_plus_24dp_fill0_wght400_grad0_opsz24 else R.drawable.favorite_24dp_fill0_wght400_grad0_opsz24
        )
    }

    private fun updateShareIcon(shares: Int) {
        binding.Repost.setImageResource(
            if (shares % 2 == 0) android.R.drawable.ic_menu_share else R.drawable.share_24dp_fill0_wght400_grad0_opsz24
        )
    }

    private fun updateLikesCount(likes: Int) {
        binding.LikesN.text = formatCount(likes)
    }

    private fun updateSharesCount(shares: Int) {
        binding.RepostN.text = formatCount(shares)
    }

    private fun updateViewsCount(views: Int) {
        binding.ViewsN.text = formatCount(views)
    }

    private fun formatCount(count: Int): String {
        return when {
            count >= 1_000_000 -> "${count / 1_000_000}.${(count % 1_000_000) / 100_000}M"
            count >= 10_000 -> "${count / 1_000}K"
            count >= 1_100 -> "${count / 1_000}.${(count % 1_000) / 100}K"
            count >= 1_000 -> "1K"
            else -> count.toString()
        }
    }
}

data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    var likedByMe: Boolean,
    var likes: Int = 0,
    var shares: Int = 0,
    var views: Int = 0
)


