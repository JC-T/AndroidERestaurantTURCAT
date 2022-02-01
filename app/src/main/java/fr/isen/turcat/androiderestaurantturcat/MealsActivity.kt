package fr.isen.turcat.androiderestaurantturcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.turcat.androiderestaurantturcat.databinding.ActivityMealsBinding
import org.json.JSONObject

enum class MealType{
    APPETIZERS, MAIN_COURSES,DESSERTS;

    companion object {
        fun toString(mealChoice: MealType?): String {
            return when(mealChoice) {
                APPETIZERS -> "Entrées"
                MAIN_COURSES -> "Plats"
                DESSERTS -> "Desserts"
                else -> ""
            }
        }
    }
}

class MealsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealsBinding
    private lateinit var menuchoice: MealType

    private fun loadMeals() {
        val fakentries = listOf<Dish>()
        val adapter = MealsAdapter(fakentries) { selectedItem ->
            Log.d("Meals", "GG!")
        }
        binding.MealsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MealsRecyclerView.adapter = adapter

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        menuchoice =
            intent.getSerializableExtra(HomeActivity.MEALTYPE) as? MealType ?: MealType.APPETIZERS
        binding.title.text = MealType.toString(menuchoice)
        makeRequest()
        loadMeals()
    }

    private fun makeRequest() {
        val queue = Volley.newRequestQueue(this)
        val url = APIConstants.BASE_URL + APIConstants.MENU
        val parameters = JSONObject()
        parameters.put(APIConstants.KEY_SHOP, APIConstants.SHOP)

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            parameters,
            {
                //Log.d("volley", "${it.toString(2)}")
                parseresult(it.toString())
            },
            {
                Log.d("Volley error", "$it")
            }
        )

        queue.add(request)
    }

    private fun parseresult(response: String) {
        val result = GsonBuilder().create().fromJson(response, MenuResult::class.java)
        val items = result.data.firstOrNull {
            it.name == MealType.toString(menuchoice)
        }?.items
        items?.let {
            loadList(it)
        }
    }
    private fun setupTitle() {
        binding.title.text = MealType.toString(menuchoice)
    }

    private fun loadList(items: List<Dish>){
        binding.MealsRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MealsAdapter(items) { selectedItem ->
            showDetail(selectedItem)
        }
        binding.MealsRecyclerView.adapter = adapter
    }

    private fun showDetail(item: Dish) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(SELECTED_ITEM, item)
        startActivity(intent)
    }

    companion object{
        const val SELECTED_ITEM="SELECTED_ITEM"
    }
}