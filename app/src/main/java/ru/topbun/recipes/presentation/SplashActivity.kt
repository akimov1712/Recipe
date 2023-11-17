package ru.topbun.recipes.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

    private val viewModel by viewModels<HomeViewModel> ()
    private val binding by lazy{ ActivitySplashBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        viewModel.initRecipes()
        CoroutineScope(Dispatchers.Main).launch{
            while (true){
                val sharedPreferences = getSharedPreferences("endInit", MODE_PRIVATE)
                val initComplete = sharedPreferences.getBoolean("initComplete", false)

                binding.progressBar.progress += 1

                if (initComplete) {
                    binding.progressBar.progress = 100
                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    return@launch
                }
                delay(150)
            }
        }
    }
}