package ru.topbun.recipes.presentation.detail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailRecipePageAdapter(
    listFragment: List<Fragment>,
    fragmentManager: FragmentManager,
    lifecycle:Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragments = listFragment

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}