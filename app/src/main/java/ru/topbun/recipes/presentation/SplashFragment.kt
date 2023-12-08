package ru.topbun.recipes.presentation

import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.FragmentSplashBinding
import ru.topbun.recipes.presentation.base.BaseFragment

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>(FragmentSplashBinding::inflate) {


    override fun setViews() {
        super.setViews()
        startLoader()
    }

    private fun startLoader(){
        lifecycleScope.launch{
            var progress = 0
            while (progress < 100){
                binding.progressBar.progress = progress++
                delay(5)
                // чтобы увидеть сообщение, что рецепты взяты из других источников
            }
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment())
        }
    }

}