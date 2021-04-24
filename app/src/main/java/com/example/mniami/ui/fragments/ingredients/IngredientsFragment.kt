package com.example.mniami.ui.fragments.ingredients

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mniami.R
import com.example.mniami.adapters.IngredientsAdapter
import com.example.mniami.models.Result
import com.example.mniami.util.Constants
import com.example.mniami.util.Constants.Companion.RECIPE_RESULT_KEY
import kotlinx.android.synthetic.main.fragment_ingredients.view.*
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.android.synthetic.main.ingredients_row_layout.view.*


class IngredientsFragment : Fragment() {

    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter()}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        setupRecyclerView(view)
        myBundle?.extendedIngredients?.let { mAdapter.setData(it)}

        return view
    }

    private fun setupRecyclerView(view: View) {
        view.ingredients_recyclerview.adapter = mAdapter
        view.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }
}