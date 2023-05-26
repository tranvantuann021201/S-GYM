package com.example.s_gym.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.s_gym.api.Exercise
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.databinding.ItemAdvancedFitnessBinding
import com.example.s_gym.databinding.ItemBasicFitnessBinding

class AdvancedFitnessAdapter(private var exercisesList: List<Exercises>) : RecyclerView.Adapter<AdvancedFitnessAdapter.AdvancedFitnessViewHolder>() {

    inner class AdvancedFitnessViewHolder(private val itemBinding: ItemAdvancedFitnessBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(exercises: Exercises) {
            itemBinding.txtAnimationName.text = exercises.name
            itemBinding.txtAnimationAmount.text = "x ${exercises.animationMount}"
            Glide.with(itemView.context).load(exercises.urlVideoGuide)
                .into(itemBinding.imgAnimationExercise)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvancedFitnessViewHolder {
        return AdvancedFitnessViewHolder(
            ItemAdvancedFitnessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdvancedFitnessViewHolder, position: Int) {
        val exercises = exercisesList[position]
        holder.bindItem(exercises)
    }


    override fun getItemCount(): Int {
        return exercisesList.size
    }

    fun updateData(newExercisesList: List<Exercises>) {
        exercisesList = newExercisesList
        notifyDataSetChanged()
    }
}