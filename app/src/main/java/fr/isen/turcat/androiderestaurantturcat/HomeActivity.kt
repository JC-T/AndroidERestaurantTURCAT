package fr.isen.turcat.androiderestaurantturcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import fr.isen.turcat.androiderestaurantturcat.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appetizers.setOnClickListener {
            //Toast.makeText(this, "Vous avez sélectionné : Menu d'entrées ", Toast.LENGTH_SHORT).show()
            changeActivity(MealType.APPETIZERS)
            Log.e("Closure tag", "Home Activity Closed")
        }

        binding.mainCourses.setOnClickListener {
            //Toast.makeText(this, "Vous avez sélectionné : Menu des plats principaux", Toast.LENGTH_SHORT).show()
            changeActivity(MealType.MAIN_COURSES)
            Log.e("Closure tag", "Home Activity Closed")
        }

        binding.desserts.setOnClickListener {
            //Toast.makeText(this, "Vous avez sélectionné : Menu des desserts", Toast.LENGTH_SHORT).show()
            changeActivity(MealType.DESSERTS)
            Log.e("Closure tag", "Home Activity Closed")
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun changeActivity (category: MealType){
        val changePage = Intent(this, MealsActivity::class.java)
        changePage.putExtra(MEALTYPE, category)
        Log.i("INFO", "End of HomeActivity")
        startActivity(changePage)
    }
    companion object{
        const val MEALTYPE= "MEALTYPE"
    }
}