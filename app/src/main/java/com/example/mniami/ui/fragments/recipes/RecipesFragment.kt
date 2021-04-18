package com.example.mniami.ui.fragments.recipes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mniami.R
import com.example.mniami.adapters.RecipesAdapter
import com.example.mniami.databinding.FragmentRecipesBinding
import com.example.mniami.util.Constants.Companion.API_KEY
import com.example.mniami.util.NetworkResult
import com.example.mniami.util.observeOnce
import com.example.mniami.viewmodels.MainViewModel
import com.example.mniami.viewmodels.RecipesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RecipesFragment : Fragment() {

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private val mAdapter by lazy { RecipesAdapter() }
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel


        setupRecyclerView()
        readDatabase()

        return binding.root
    }

    private fun setupRecyclerView() {
        binding.recyclerview.adapter = mAdapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
       lifecycleScope.launch {
           mainViewModel.readRecipe.observeOnce(viewLifecycleOwner, {database ->
               if(database.isNotEmpty()) {
                   Log.d("Recipes Fragment", "readDatabase called! ")
                   mAdapter.setData(database[0].foodRecipe)
                   hideShimmerEffect()
               } else {
                   requestApiData()
               }
           })
       }
    }

    private fun requestApiData() {
        Log.d("Recipes Fragment", "requestApiData called! ")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCache()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()

                }
            }
        })
    }

    private fun loadDataFromCache() {
       lifecycleScope.launch {
           mainViewModel.readRecipe.observe(viewLifecycleOwner, {database ->
               if(database.isNotEmpty()) {
                   mAdapter.setData(database[0].foodRecipe)
               }
           })
       }
    }


    private fun showShimmerEffect() {
        binding.recyclerview.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerview.hideShimmer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}