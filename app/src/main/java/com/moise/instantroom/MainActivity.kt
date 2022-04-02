package com.moise.instantroom

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.moise.instantroom.databinding.ActivityMainBinding
import com.moise.instantroom.fragments.ComposeFragment
import com.moise.instantroom.fragments.HomeFragment
import com.moise.instantroom.fragments.ProfileFragment
import com.parse.ParseUser
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentManager: FragmentManager = supportFragmentManager
        binding.bottomNavigation.setOnItemSelectedListener {
            item ->
            var fragmentToShow: Fragment? = null
            when(item.itemId){
                R.id.action_home -> {
                    fragmentToShow = HomeFragment()
                }
                R.id.action_compose -> {
                    fragmentToShow = ComposeFragment()
                }
                R.id.action_profile -> {
                    fragmentToShow = ProfileFragment()
                }

            }
            if (fragmentToShow != null){
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragmentToShow).commit()
            }
            true
        }
        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.action_home

    }

    override fun onDestroy() {
        super.onDestroy()
        ParseUser.logOutInBackground()
    }

    companion object {
        const val TAG = "MainActivity"
    }
}