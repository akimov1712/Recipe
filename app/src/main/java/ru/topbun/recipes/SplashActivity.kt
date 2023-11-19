package ru.topbun.recipes

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.topbun.recipes.databinding.ActivitySplashBinding
import ru.topbun.recipes.presentation.main.MainActivity
import ru.topbun.recipes.presentation.main.home.HomeViewModel

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val binding by lazy{ ActivitySplashBinding.inflate(layoutInflater)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        lifecycleScope.launch{
            var progress = 0
            while (progress < 100){
                binding.progressBar.progress = progress++
                delay(5)
                // чтобы увидеть сообщение, что рецепты взяты из других источников
            }
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}