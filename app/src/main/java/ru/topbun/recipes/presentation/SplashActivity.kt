package ru.topbun.recipes.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.topbun.recipes.App
import ru.topbun.recipes.databinding.ActivitySplashBinding
import ru.topbun.recipes.presentation.base.ViewModelFactory
import ru.topbun.recipes.presentation.main.home.HomeViewModel
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private val component by lazy {( application as App).component }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java] }
    private val binding by lazy{ ActivitySplashBinding.inflate(layoutInflater)}


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
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