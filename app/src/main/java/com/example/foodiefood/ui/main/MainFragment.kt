package com.example.foodiefood.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import com.example.foodiefood.MainActivity
import com.example.foodiefood.R
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    //user specified tags for cuisine, meal type, and diet will be held in this string
    //this string will be added to the API url later
    var tagOptions: String = ""

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


        //setting the welcome message displayed to users
        welcomeTextView.text =  String(Character.toChars(0x1F959)) + String(Character.toChars(0x1F372)) +
                String(Character.toChars(0x1F371)) + String(Character.toChars(0x1F35B)) + String(Character.toChars(0x1F35C)) +
                "\n\nWelcome to Foodie Food!\n\n" + String(Character.toChars(0x1F370)) + String(Character.toChars(0x1F967)) +
                String(Character.toChars(0x1F375)) + String(Character.toChars(0x1F9C1)) + String(Character.toChars(0x1F36A))


        // setting up spinner options
        //diet options dropdown menu
        ArrayAdapter.createFromResource(activity!!.applicationContext, R.array.dietOptions, android.R.layout.simple_spinner_item).also {
            adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            dietSpinner.adapter = adapter
        }

        //cuisine options dropdown menu
        ArrayAdapter.createFromResource(activity!!.applicationContext, R.array.cuisineOptions, android.R.layout.simple_spinner_item).also{
            adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            cuisineSpinner.adapter = adapter
        }

        //meal type options dropdown menu
        ArrayAdapter.createFromResource(activity!!.applicationContext, R.array.mealTypeOptions, android.R.layout.simple_spinner_item).also{
            adapter->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
            mealTypeSpinner.adapter = adapter
        }

        // search button onClick listener to construct API url and make a request
        searchButton.setOnClickListener {
            handleGetRecipe(it)
        }

    }

    //This function creats the API Url and calls the apiCall method defined in MainActivity
    private fun handleGetRecipe(view : View){
        tagOptions = ""
        Log.i("test", "inside handleGetRecipe function in main fragment")
        Log.i("test", "tagOptions before populating: $tagOptions")
        populateTagOptions()
        var url = "https://api.spoonacular.com/recipes/random?number=1&tags=$tagOptions&apiKey="
        Log.i("test", "tagOptions after populating: $tagOptions")
        Log.i("test", "api link: $url")

        (activity as MainActivity).apiCall(url, this.id)

    }

    //check all spinners and checkboxes, update tag variable
    private fun populateTagOptions(){

        if(dietSpinner.selectedItem.toString() != "Diet Options"){
            tagOptions += dietSpinner.selectedItem.toString().toLowerCase()
        }

        if(cuisineSpinner.selectedItem.toString() != "Cuisine Options"){
            if(tagOptions == ""){
                tagOptions += cuisineSpinner.selectedItem.toString().toLowerCase()
            }
            else{
                tagOptions += "," + cuisineSpinner.selectedItem.toString().toLowerCase()
            }
        }

        if(mealTypeSpinner.selectedItem.toString() != "Meal Type Options"){
            if(tagOptions == ""){
                tagOptions += mealTypeSpinner.selectedItem.toString().toLowerCase()
            }
            else{
                tagOptions += "," + mealTypeSpinner.selectedItem.toString().toLowerCase()
            }
        }


    }

}