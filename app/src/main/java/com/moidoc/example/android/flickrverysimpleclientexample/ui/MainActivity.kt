package com.moidoc.example.android.flickrverysimpleclientexample.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.moidoc.example.android.flickrverysimpleclientexample.R
import com.moidoc.example.android.flickrverysimpleclientexample.ui.common.ToolbarResolver

/**
 * Single app activity
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        /**
         *  subscribe to nav controller changes.
         *  I have know about [NavController.OnDestinationChangedListener]
         *  but "This method is called <strong>after</strong> navigation is complete" seems as wrong
         */
        getNavFragment()?.childFragmentManager?.addOnBackStackChangedListener {
            updateToolbar()
        }

        updateToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when {
            item.itemId == android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     *  Update toolbar title, icons and etc. if "Single activity app" mean for you "Single toolbar implementation".
     */
    private fun updateToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled((getTopNavigationScreen() as? ToolbarResolver)?.hasBackKey() == true)
    }

    /**
     *  Get NavHostFragment from activity fragments. The tag "NavHostFragment" was added in the [R.layout.main_activity]
     */
    private fun getNavFragment(): NavHostFragment? {
        return supportFragmentManager.findFragmentByTag("NavHostFragment") as? NavHostFragment
    }

    /**
     * a f***g hack for getting a top fragment in the navigation component
     */
    private fun getTopNavigationScreen(): Fragment? {
        return getNavFragment()?.childFragmentManager?.fragments?.lastOrNull()
    }
}
