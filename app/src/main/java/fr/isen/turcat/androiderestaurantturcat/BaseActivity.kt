package fr.isen.turcat.androiderestaurantturcat

import android.view.Menu
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity: AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
}