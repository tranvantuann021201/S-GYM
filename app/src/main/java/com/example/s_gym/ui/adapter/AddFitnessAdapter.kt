package com.example.s_gym.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.s_gym.R
import com.example.s_gym.api.Exercise
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.ui.fragment.AddFitnessFragment

class AddFitnessAdapter(
    private var exerciseList: List<Exercises>
) :
    RecyclerView.Adapter<AddFitnessAdapter.ExerciseViewHolder>() {
    private lateinit var listener: AddFitnessFragment.onItemClickListener
    private lateinit var context: Context

    fun setItemClickListener(listeners: AddFitnessFragment.onItemClickListener) {
        listener = listeners
    }
    class ExerciseViewHolder(view: View,listeners: AddFitnessFragment.onItemClickListener) : RecyclerView.ViewHolder(view) {
        var nameExercise: TextView
        var imgExercise: ImageView

        init {
            nameExercise = view.findViewById(R.id.txt_exer_name_add_fitness)
            imgExercise = view.findViewById(R.id.img_animation_my_plan)
            itemView.setOnClickListener {
                listeners.onItemClick(adapterPosition)
            }
        }
//        val imageView: ImageView = view.findViewById(R.id.img_animation_add_fitness)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(
            R.layout.item_add_fitness, parent, false
        )
        context = parent.context
        return ExerciseViewHolder(itemView,listener)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentItem: Exercises = exerciseList[position]

        holder.nameExercise.text = currentItem.name

        Glide.with(context)
            .load(currentItem.urlVideoGuide)
            .into(holder.imgExercise)
    }

    override fun getItemCount() = exerciseList.size
}
