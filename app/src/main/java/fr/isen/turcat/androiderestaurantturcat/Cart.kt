package fr.isen.turcat.androiderestaurantturcat

import fr.isen.turcat.androiderestaurantturcat.Dish
import java.io.File
import java.io.Serializable
import android.content.Context
import com.google.gson.GsonBuilder

class Basket(val items: MutableList<BasketItem>): Serializable {
    var itemCount : Int=0
        get() {

            return items.map {
                it.quantity
            }.reduceOrNull() { acc, i -> acc+i }?:0

        }

    var totalPrice: Float = 0f
        get(){
            return items.map {
                it.quantity*it.dish.prices.first().price.toFloat()
            }.reduceOrNull { acc, fl -> acc+fl }?: 0f
        }

    fun addItem(item: Dish, quantity: Int) {
        val existingItem = items.firstOrNull { it.dish.name==item.name }
        existingItem?.let {
            existingItem.quantity+=quantity
        }?: kotlin.run { items.add(BasketItem(item,quantity)) }
    }

    fun save(context: Context) {
        val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
        jsonFile.writeText(GsonBuilder().create().toJson(this))
        updatecount(context)
    }

    private fun updatecount(context: Context){
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, itemCount)
        editor.apply()
    }

    fun removeItem(basketItem: BasketItem)
    {
        items.remove(basketItem)
    }

    companion object {
        fun getBasket(context: Context): Basket {
            val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
            return if(jsonFile.exists()) {
                val json = jsonFile.readText()
                GsonBuilder().create().fromJson(json, Basket::class.java)
            } else {
                Basket(mutableListOf())
            }
        }
        const val BASKET_FILE="basket.json"
        const val ITEMS_COUNT="ITEMS_COUNT"
        const val USER_PREFERENCES_NAME="USER_PREFERENCES_NAME"
    }
}

class BasketItem(val dish: Dish, var quantity: Int): Serializable {}