package com.example.mniami.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mniami.R
import kotlinx.android.synthetic.main.fragment_recipes.view.*


class RecipesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_recipes, container, false)

        view.recyclerview.showShimmer()

        return view
    }

}