package com.example.foodiefood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.foodiefood.ui.main.MainFragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var requestQueue : RequestQueue
    var resp: JSONObject? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) { // Moved fragment replacement to occur after API call
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance(), "mainFragment")
                .commitNow()
            Log.i("test", "Replace with initial fragment in MainActivity")
        }

    }

    fun apiCall(urlString : String, fragmentId: Int){
        //instantiate the request queue
        requestQueue = Volley.newRequestQueue(this)

        //api url
        var url = urlString


        //create object request
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener<JSONObject> {
                //JSON object that we get back from the API call
                    response ->

                try {
                    Log.i("test", "response in MainActivity: $response")
                    resp = response // Make public copy of JSON object response
                }
                catch(ex : JSONException) {
                    Log.e("JSON Error", ex.toString())
                }

                if(resp?.getJSONArray("recipes")?.length() == 0){
                    //error message
                    Log.i("test", "response returned empty array")
                    val toast = Toast.makeText(this, "No recipes found. Please try again.", Toast.LENGTH_LONG)
                    toast.show()
                }
                else{
                    changeFragment(fragmentId, "mainFragment")
                }

            },
            Response.ErrorListener {
                    error ->
                Log.e("JSON Error", error.toString())
            }) // end of JSON object request

        requestQueue.add(jsonObjectRequest)
    }

    // Changes previous fragment with destination fragment
    fun changeFragment(prevFragmentId: Int, prevFragmentName: String) {
        when (prevFragmentName) {
            "mainFragment" -> { // Change from MainFragment to SecondFragment
                supportFragmentManager.beginTransaction()
                    .replace(prevFragmentId, SecondFragment.newInstance("p1", "p2"), "secondFragment")
                    .addToBackStack("null")
                    .commit()
            }
            "secondFragment" -> { // Change from SecondFragment to MainFragment
                supportFragmentManager.beginTransaction()
                    .replace(prevFragmentId, MainFragment.newInstance(), "mainFragment")
                    .addToBackStack("null")
                    .commit()
            }
        }
    }
}