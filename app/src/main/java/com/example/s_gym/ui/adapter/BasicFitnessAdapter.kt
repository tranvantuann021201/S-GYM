package com.example.s_gym.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.s_gym.api.Exercise
import com.example.s_gym.databinding.ItemBasicFitnessBinding

class BasicFitnessAdapter(var exercisesList: List<Exercise>) :
    RecyclerView.Adapter<BasicFitnessAdapter.BasicFitnessViewHolder>() {
    inner class BasicFitnessViewHolder(private val itemBinding: ItemBasicFitnessBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(exercises: Exercise) {
            itemBinding.txtAnimationName.text = exercises.name
            itemBinding.txtAnimationAmount.text = "x ${exercises.animationMount}"
            Glide.with(itemView.context).load(exercises.urlVideoGuide)
                .into(itemBinding.imgAnimationExercise)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicFitnessViewHolder {
        return BasicFitnessViewHolder(
            ItemBasicFitnessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BasicFitnessViewHolder, position: Int) {
        val exercises = exercisesList[position]
        holder.bindItem(exercises)
    }


    override fun getItemCount(): Int {
        return exercisesList.size
    }
}