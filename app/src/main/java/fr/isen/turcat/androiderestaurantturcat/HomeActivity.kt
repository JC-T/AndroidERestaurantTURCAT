package fr.isen.turcat.androiderestaurantturcat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import fr.isen.turcat.androiderestaurantturcat.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity() {

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

        binding.basket.setOnClickListener(){
            val count= getItemsCount()
            if (count==0) {
                Snackbar.make(binding.root, "Panier vide", Snackbar.LENGTH_LONG).show()
            }else {
                val intent = Intent(this, CartActivity::class.java)
                startActivity(intent)
            }
        }

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