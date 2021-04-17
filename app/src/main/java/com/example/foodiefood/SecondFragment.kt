package com.example.foodiefood

import android.content.Intent
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_second.*
import org.json.JSONArray
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SecondFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SecondFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var resp : JSONObject? = null
    private lateinit var ingredientsList : ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("test", "inside onActivityCreated in second fragment")

        resp = (activity as MainActivity).resp
        //recipeSummaryTextView.text = resp.toString()

        //update imageview
        var url : String? = resp?.getJSONArray("recipes")?.getJSONObject(0)?.getString("image").toString()
        Log.i("test", "imageView url: $url")
        Picasso.get().load(url).into(imageView)

        backButton.setOnClickListener {
            handleGoBack(it)
        }

        recipeLinkButton.text = resp?.getJSONArray("recipes")?.getJSONObject(0)?.getString("title").toString()

        recipeLinkButton.setOnClickListener {
            var dynamicUrl = resp?.getJSONArray("recipes")?.getJSONObject(0)?.getString("sourceUrl").toString()
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(dynamicUrl)
            startActivity(i)
        }

        recipeLinkButton.paintFlags = recipeLinkButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        timeTextView.text = resp?.getJSONArray("recipes")?.getJSONObject(0)?.getString("readyInMinutes").toString() + " minutes"

        servingsTextView.text = resp?.getJSONArray("recipes")?.getJSONObject(0)?.getString("servings").toString() + " serving(s)"

        var ingredientsString: String = ""
        // get ingredients
        var extendedIngredientsJSONArray : JSONArray? = resp?.getJSONArray("recipes")?.getJSONObject(0)?.getJSONArray("extendedIngredients")
        extendedIngredientsJSONArray.let{
            for(i in 0 until extendedIngredientsJSONArray!!.length()){
                ingredientsString += extendedIngredientsJSONArray.getJSONObject(i).getString("originalString").toString() + "\n"
            }
        }



        ingredientsTextView.text = ingredientsString

        recipeSummaryTextView.text = Html.fromHtml(resp?.getJSONArray("recipes")?.getJSONObject(0)?.getString("summary").toString())


        // steps textview
        var numCurrentNum = 1
        var stepsString: String = ""
        var analyzedInstructionsArray : JSONArray? = resp?.getJSONArray("recipes")?.getJSONObject(0)?.getJSONArray("analyzedInstructions")

        analyzedInstructionsArray.let{
            for(i in 0 until analyzedInstructionsArray!!.length()){
                for(j in 0 until analyzedInstructionsArray?.getJSONObject(i).getJSONArray("steps").length()){
                    stepsString += "Step $numCurrentNum: " + analyzedInstructionsArray?.getJSONObject(i).getJSONArray("steps").getJSONObject(j).getString("step").toString() + "\n"
                    numCurrentNum++
                }

            }
        }

        stepsTextView.text = stepsString




    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SecondFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SecondFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    private fun handleGoBack(view : View){
        (activity as MainActivity).changeFragment(id, "secondFragment")
    }
}

private operator fun MovementMethod.invoke(instance: MovementMethod?) {

}
