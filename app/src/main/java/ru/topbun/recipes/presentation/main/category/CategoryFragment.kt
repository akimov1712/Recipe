package ru.topbun.recipes.presentation.main.category

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.topbun.recipes.App
import ru.topbun.recipes.R
import ru.topbun.recipes.databinding.FragmentCategoryBinding
import ru.topbun.recipes.presentation.base.ViewModelFactory
import ru.topbun.recipes.presentation.detail.DetailRecipeViewModel
import javax.inject.Inject


class CategoryFragment : Fragment() {
    private val component by lazy { (requireActivity().application as App).component }

    private var _binding: FragmentCategoryBinding? = null
    private val binding: FragmentCategoryBinding
        get() = _binding ?: throw RuntimeException("FragmentCategoryBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[DetailRecipeViewModel::class.java] }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        observeViewModel()
    }

    private fun setViews(){
        setListenersInView()
    }

    private fun observeViewModel(){
        with(binding){
            with(viewModel){

            }
        }
    }

    private fun setListenersInView(){}


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}