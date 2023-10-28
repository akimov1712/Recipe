package ru.topbun.recipes.presentation.main.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import ru.topbun.recipes.App
import ru.topbun.recipes.databinding.FragmentHomeBinding
import ru.topbun.recipes.presentation.base.ViewModelFactory
import javax.inject.Inject

class HomeFragment : Fragment() {
    private val component by lazy { (requireActivity().application as App).component }

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel by lazy { ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java] }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container,false)
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