package fr.isen.turcat.androiderestaurantturcat

import android.icu.number.NumberFormatter.with
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso
import fr.isen.turcat.androiderestaurantturcat.databinding.CellMealBinding;
import java.lang.System.load


class MealsAdapter(private val entries: List<Dish>, val itemClickListener: (Dish) -> Unit): RecyclerView.Adapter<MealsAdapter.MealsViewHolder>() {
    class MealsViewHolder(binding:CellMealBinding): RecyclerView.ViewHolder(binding.root){
        val title=binding.mealTitle
        val image=binding.mealImage
        val price= binding.mealPrice
        val layout = binding.root

        fun bind (meal:Dish){
            title.text=meal.name
            val imagepath = meal.images.first()
            if(imagepath.isNotEmpty()){
                Picasso.get().load(imagepath).resize(50,50).centerCrop().into(image)
            }
            else{
                Picasso.get().load("https://static.jow.fr/750x750/recipes/rEHe873YT9.jpg").resize(50,50).centerCrop().into(image)
            }

            price.text="${meal.prices.first().price} €"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsViewHolder {
        val binding = CellMealBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MealsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MealsViewHolder, position: Int) {
        val item = entries[position]
        //holder.title.text = item.name
        holder.layout.setOnClickListener {
            itemClickListener.invoke(item)
        }
        holder.bind(item)
    }

    override fun getItemCount(): Int = entries.count()

}
