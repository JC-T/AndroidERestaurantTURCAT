package fr.isen.turcat.androiderestaurantturcat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.turcat.androiderestaurantturcat.databinding.ActivityBasketBinding

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadlist()
        binding.orderButton.setOnClickListener{
            val intent = Intent(this, UserActivity::class.java)
            startActivity(intent)
        }
    }


    private fun loadlist(){
        val basket = Basket.getBasket(this)
        val items=basket.items
        binding.recyclerview.layoutManager=LinearLayoutManager(this)
        binding.recyclerview.adapter=CartAdapter(items) {
            basket.removeItem(it)
            basket.save(this)
            loadlist()
        }
    }
}