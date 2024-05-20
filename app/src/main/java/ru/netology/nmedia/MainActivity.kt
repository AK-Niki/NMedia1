package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import ru.netology.nmedia.databinding.NetologyMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: NetologyMainBinding
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = NetologyMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                updateLikeIcon(post.likedByMe)
                updateLikesCount(post.likes)
                updateSharesCount(post.shares)
                updateViewsCount(post.views)

                likes.setOnClickListener {
                    viewModel.like()
                }

                repost.setOnClickListener {
                    viewModel.share()
                }
            }
        }
    }

    private fun updateLikeIcon(liked: Boolean) {
        binding.likes.setImageResource(
            if (liked) R.drawable.heart_plus_24dp_fill0_wght400_grad0_opsz24 else R.drawable.favorite_24dp_fill0_wght400_grad0_opsz24
        )
    }

    private fun updateShareIcon(shares: Int) {
        binding.repost.setImageResource(
            if (shares % 2 == 0) android.R.drawable.ic_menu_share else R.drawable.share_24dp_fill0_wght400_grad0_opsz24
        )
    }

    private fun updateLikesCount(likes: Int) {
        binding.likesN.text = formatCount(likes)
    }

    private fun updateSharesCount(shares: Int) {
        binding.repostN.text = formatCount(shares)
    }

    private fun updateViewsCount(views: Int) {
        binding.viewsN.text = formatCount(views)
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

