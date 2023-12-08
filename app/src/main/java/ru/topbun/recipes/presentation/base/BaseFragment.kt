package ru.topbun.recipes.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.RuntimeException

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB: ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding ?: throw RuntimeException("ViewBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        observeViewModel()
    }

    open fun setViews(){
        setListenersInView()
        setAdapters()
        setRecyclerViews()
    }

    open fun setListenersInView(){}
    open fun setAdapters(){}
    open fun setRecyclerViews(){}

    open fun observeViewModel(){}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}