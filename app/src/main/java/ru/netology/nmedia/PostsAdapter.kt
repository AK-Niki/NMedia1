package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.CardPostBinding

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit

class PostsAdapter(
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
) : ListAdapter<Post, PostsAdapter.PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }

    class PostViewHolder(
        private val binding: CardPostBinding,
        private val onLikeListener: OnLikeListener,
        private val onShareListener: OnShareListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            binding.apply {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                updateLikeIcon(post.likedByMe)
                updateLikesCount(post.likes)
                updateSharesCount(post.shares)
                updateViewsCount(post.views)

                like.setOnClickListener { onLikeListener(post) }
                repost.setOnClickListener { onShareListener(post) }
            }
        }

        private fun updateLikeIcon(liked: Boolean) {
            binding.like.setImageResource(
                if (liked) R.drawable.heart_plus_24dp_fill0_wght400_grad0_opsz24 else R.drawable.favorite_24dp_fill0_wght400_grad0_opsz24
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
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}





