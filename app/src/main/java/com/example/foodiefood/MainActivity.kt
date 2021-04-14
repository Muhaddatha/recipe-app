package com.example.foodiefood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foodiefood.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }
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