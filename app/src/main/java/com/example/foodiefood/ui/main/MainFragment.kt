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

    var tagOptions: String = ""
    private lateinit var checkboxes: List<CheckBox>

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


        welcomeTextView.text = "" + String(Character.toChars(0x1F389));
        //initialize checkbox array
        checkboxes = listOf<CheckBox>(dairyCheckbox,eggCheckbox, glutenCheckbox, grainCheckbox, peanutCheckbox, seafoodCheckbox, sesameCheckbox, shellfishCheckbox,
        soyCheckbox, sulfiteCheckbox, treeNutCheckbox, wheatCheckbox)

        welcomeTextView.text =  String(Character.toChars(0x1F959)) + String(Character.toChars(0x1F372)) +
                String(Character.toChars(0x1F371)) + String(Character.toChars(0x1F35B)) + String(Character.toChars(0x1F35C)) +
                "\n\nWelcome to Foodie Food!\n\n" + String(Character.toChars(0x1F370)) + String(Character.toChars(0x1F967)) +
                String(Character.toChars(0x1F375)) + String(Character.toChars(0x1F9C1)) + String(Character.toChars(0x1F36A))

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

        for(i in 0..11){
            if(checkboxes[i].isChecked){
                if(tagOptions == ""){
                    tagOptions += checkboxes[i].text.toString().toLowerCase()
                }
                else{
                    tagOptions += "," + checkboxes[i].text.toString().toLowerCase()
                }
            }
        }

    }

}