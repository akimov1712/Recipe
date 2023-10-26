package ru.topbun.recipes.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import ru.topbun.recipes.App
import ru.topbun.recipes.R
import ru.topbun.recipes.data.repository.RecipeRepositoryImpl
import ru.topbun.recipes.presentation.base.ViewModelFactory
import ru.topbun.recipes.presentation.main.MainActivity
import ru.topbun.recipes.presentation.main.MainViewModel
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    private val component by lazy {( application as App).component }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java] }


    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel.initRecipes()
        val sharedPreferences = getSharedPreferences("endInit", MODE_PRIVATE)
        val initComplete = sharedPreferences.getBoolean("initComplete", false)
        if (initComplete) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}