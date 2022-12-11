package com.usfaa.cvbuilder

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.usfaa.cvbuilder.databinding.ActivityMainBinding
import com.usfaa.cvbuilder.util.UserHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var userHelper: UserHelper

    private val navHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
    }
    private val navController get() = navHostFragment.navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_about, R.id.navigation_work, R.id.navigation_contact))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        userHelper.saveRegisteredUser()

        if (userHelper.isConnected()) {
            setInitialScreen(R.id.navigation_home)
        } else {
            setInitialScreen(R.id.navigation_login)
        }
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        binding.navView.visibility = visibility
    }

    fun setInitialScreen(resourceId: Int) {
        binding.navView.setupWithNavController(navController)
        val navGraph = navController.navInflater.inflate(R.navigation.mobile_navigation)
        navGraph.setStartDestination(resourceId)
        navController.graph = navGraph
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.telegram -> showUrl("https://telegram.org/")
            R.id.gmail -> {
                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.parse(
                    "mailto:${userHelper.user?.email}"))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Message Subject")
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Message Body")
                startActivity(Intent.createChooser(emailIntent, "Send Email"))
            }
            R.id.whatsap -> {
                val url = "https://api.whatsapp.com/send?phone=${userHelper.user?.phone}"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            R.id.linkedin -> showUrl("https://www.linkedin.com/")
            R.id.logout -> {
                userHelper.logUserOut()
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }
}