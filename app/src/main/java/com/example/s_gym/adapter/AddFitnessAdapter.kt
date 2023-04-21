package com.example.s_gym.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.s_gym.R
import com.example.s_gym.api.WorkoutApiService
import com.example.s_gym.database.Exercise
import com.example.s_gym.databinding.ItemAddFitnessBinding

class AddFitnessAdapter(
    private var exerciseList: List<Exercise>
) :
    RecyclerView.Adapter<AddFitnessAdapter.ExerciseViewHolder>() {

    private lateinit var binding: ItemAddFitnessBinding
    private lateinit var context: Context
    public fun setData(context: Context, list: List<Exercise>) {

        this.exerciseList = list
        notifyDataSetChanged()
    }

    class ExerciseViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var nameTextView: TextView

        init {
            nameTextView = view.findViewById(R.id.txt_exer_name_add_fitness)
        }
//        val imageView: ImageView = view.findViewById(R.id.img_animation_add_fitness)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_add_fitness, parent, false
        )
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentItem:Exercise = exerciseList[position]

        holder.nameTextView.text = currentItem.name
        context = context
//        Glide.with(context)
//            .load("https://wger.de/media/exercise-images/${currentItem.id}/thumbnail.png")
//            .into(holder.imageView)
    }

    override fun getItemCount() = exerciseList.size
}