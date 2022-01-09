package com.shubhamjitiya.god

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.shubhamjitiya.god.databinding.ActivityMainBinding
import com.shubhamjitiya.god.ui.AllFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val fragmentContainer = findViewById<View>(R.id.fragmentContainer)

        bottomNav.setupWithNavController(fragmentContainer.findNavController())
        replaceFragment(AllFragment.newInstance(), true)

    }

    fun replaceFragment(fragment: Fragment, istransition: Boolean) {
        val fragmentTransition = supportFragmentManager.beginTransaction()

        if (istransition) {
            fragmentTransition.setCustomAnimations(
                android.R.anim.slide_out_right,
                android.R.anim.slide_in_left
            )
        }
        fragmentTransition.replace(R.id.fragmentContainer, fragment)
            .addToBackStack(fragment.javaClass.simpleName).commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val fragments = supportFragmentManager.fragments
        if (fragments.size == 0) {
            finish()
        }
    }
}