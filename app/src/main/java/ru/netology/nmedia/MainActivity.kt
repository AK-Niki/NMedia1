package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.netology_main)
        println(resources.displayMetrics.heightPixels) // 1794
        println(resources.displayMetrics.widthPixels) // 1080
        println(resources.displayMetrics.densityDpi) // 420
        println(resources.displayMetrics.density) // 2.625
    }
}