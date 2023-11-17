package ru.topbun.recipes.presentation.main.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import ru.topbun.recipes.databinding.FragmentSearchBinding
import ru.topbun.recipes.presentation.detail.DetailRecipeActivity
import ru.topbun.recipes.presentation.main.recipeAdapter.RecipeAdapter

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchBinding == null")


    private val viewModel by viewModels<SearchViewModel>()
    private val recipeAdapter by lazy { RecipeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        observeViewModel()
        getData()
    }

    private fun getData(){
        val query = binding.editText.text.toString()
        viewModel.getRecipeQuery(query)
    }


    private fun setViews(){
        setListenersInView()
        setTextWatcherInEditText()
        setRecyclerView()
        setAdapter()
    }

    private fun setRecyclerView(){
        with(binding) {
            val layoutManager = rvRecipes.layoutManager

            binding.rvRecipes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (layoutManager is LinearLayoutManager) {
                        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()

                        if (firstVisiblePosition >= 3){
                            btnTop.visibility = View.VISIBLE
                        } else {
                            btnTop.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }

    private fun observeViewModel(){
        with(binding){
            with(viewModel){
                state.observe(viewLifecycleOwner){
                    when(it){
                        is SearchState.ErrorRecipe -> {
                            tvNotFount.visibility = View.VISIBLE
                        }
                        is SearchState.RecipeList -> {
                            tvNotFount.visibility = View.GONE
                            recipeAdapter.submitList(it.recipeList)
                        } else -> {}
                    }
                }
            }
        }
    }

        private fun setAdapter(){
        recipeAdapter.setOnRecipeClickListener = {urlFullRecipe, preview, id ->
            val intent = Intent(requireContext(), DetailRecipeActivity::class.java)
            intent.putExtra(DetailRecipeActivity.EXTRA_URL, urlFullRecipe)
            intent.putExtra(DetailRecipeActivity.EXTRA_PREVIEW, preview)
            intent.putExtra(DetailRecipeActivity.EXTRA_ID, id)
            startActivity(intent)
        }
        recipeAdapter.setOnFavoriteClickListener = {
            viewModel.updateFavoriteRecipe(it)
        }
        binding.rvRecipes.adapter = recipeAdapter
    }

    private fun setTextWatcherInEditText(){
        binding.editText.addTextChangedListener ( object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                s.toString().let { viewModel.getRecipeQuery(it) }
                if(s?.isNotBlank() == true){
                    binding.btnClear.visibility = View.VISIBLE
                } else {
                    binding.btnClear.visibility = View.GONE
                    binding.rvRecipes.scrollToPosition(0)
                }
            }
        })
    }

    private fun setListenersInView(){
        with(binding){
            btnClear.setOnClickListener {
                editText.text.clear()
            }
            btnTop.setOnClickListener {
                rvRecipes.scrollToPosition(0)
            }
        }
    }



    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}