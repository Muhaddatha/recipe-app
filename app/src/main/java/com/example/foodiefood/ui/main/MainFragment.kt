package com.example.foodiefood.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.foodiefood.MainActivity
import com.example.foodiefood.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    //lateinit var adapterArray : ArrayAdapter<String>

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

        searchButton.setOnClickListener {
            handleGetRecipe(it)
        }



        // setting up spinner options
        ArrayAdapter.createFromResource(activity!!.applicationContext, R.array.dietOptions, android.R.layout.simple_spinner_item).also {
            adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            dietSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(activity!!.applicationContext, R.array.cuisineOptions, android.R.layout.simple_spinner_item).also{
            adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            cuisineSpinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(activity!!.applicationContext, R.array.mealTypeOptions, android.R.layout.simple_spinner_item).also{
            adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            mealTypeSpinner.adapter = adapter
        }
    }

    private fun handleGetRecipe(view : View){
        Log.i("test", "inside handleGetRecipe function in main fragment")
        var url = "https://api.spoonacular.com/recipes/random?number=1&tags=korean&apiKey="
        (activity as MainActivity).apiCall(url, this.id)

    }

}