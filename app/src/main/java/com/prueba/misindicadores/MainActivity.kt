package com.prueba.misindicadores

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)

        navController.let {
            val appBarConfiguration = AppBarConfiguration
                .Builder()
                .setFallbackOnNavigateUpListener {
                    onBackPressed()
                    true
                }.build()

            appBarConfiguration.topLevelDestinations.addAll(
                setOf(
                    R.id.login_fragment,
                    R.id.indicators_fragment
                )
            )
            val toolbar = findViewById<Toolbar>(R.id.topAppBar)
            setSupportActionBar(toolbar)
            toolbar.setupWithNavController(it, appBarConfiguration)
        }
    }
}