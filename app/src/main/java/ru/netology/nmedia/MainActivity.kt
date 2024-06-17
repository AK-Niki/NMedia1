package ru.netology.nmedia

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import ru.netology.nmedia.databinding.ActivityMainBinding
import android.view.View


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(
            onLikeListener = { post -> viewModel.likeById(post.id) },
            onShareListener = { post -> viewModel.shareById(post.id) },
            onEditListener = { post -> viewModel.edit(post) },
            onRemoveListener = { post -> viewModel.removeById(post.id) }
        )

        binding.list.layoutManager = LinearLayoutManager(this)
        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                binding.group.visibility = View.GONE
                return@observe
            }
            with(binding.content) {
                requestFocus()
                setText(post.content)
            }
            binding.group.visibility = View.VISIBLE
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Это поле не может быть пустым",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
            }
        }

        binding.cancel.setOnClickListener {
            viewModel.cancelEdit()
            binding.content.setText("")
            binding.content.clearFocus()
        }
    }
}





