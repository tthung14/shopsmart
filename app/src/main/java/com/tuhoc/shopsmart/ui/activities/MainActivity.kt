package com.tuhoc.shopsmart.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tuhoc.shopsmart.R
import com.tuhoc.shopsmart.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvNav.setupWithNavController(navController)

        binding.bnvNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.favoritesFragment -> {
                    navController.navigate(R.id.favoritesFragment)
                    true
                }
                R.id.cartFragment -> {
                    navController.navigate(R.id.cartFragment)
                    true
                }
                R.id.ordersFragment -> {
                    navController.navigate(R.id.ordersFragment)
                    true
                }
                R.id.accountFragment -> {
                    navController.navigate(R.id.accountFragment)
                    true
                }
            }
            false
        }

//        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bnvNav)
//        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        NavigationUI.setupWithNavController(bottomNavigation, navController)
    }
}