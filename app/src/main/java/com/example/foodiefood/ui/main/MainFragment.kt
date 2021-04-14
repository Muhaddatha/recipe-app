package com.example.foodiefood.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foodiefood.MainActivity
import com.example.foodiefood.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        searchButton.setOnClickListener {
            handleGetRecipe(it)
        }
    }

    private fun handleGetRecipe(view : View){
        Log.i("test", "inside handleGetRecipe function in main fragment")

        var url = "https://api.spoonacular.com/recipes/random?number=1&tags=korean&apiKey="
        (activity as MainActivity).apiCall(url, this.id)

    }

}