package com.david.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.david.myapplication.fragments.FavoritesFragment
import com.david.myapplication.fragments.RacesFragment
import com.david.myapplication.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnItemSelectedListener{
            selectFragment(it)
            true
        }

        navigation.selectedItemId = R.id.navigation_races
    }

    private fun selectFragment(it: MenuItem) {

        val fragmentClicked : Fragment = when (it.itemId){
            R.id.navigation_races -> RacesFragment.newInstance()
            R.id.navigation_favorites -> FavoritesFragment.newInstance()
            else -> SettingsFragment.newInstance()
        }

        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.mainContent, fragmentClicked)
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        ft.commit()
    }
}