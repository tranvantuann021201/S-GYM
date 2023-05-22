package com.example.s_gym.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.s_gym.api.Exercise
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.databinding.ItemEditBasicFitnessBinding
import com.example.s_gym.databinding.ItemNewFitnessBinding
import java.util.*

class NewFitnessAdapter(
    private var exercisesList: List<Exercises>,
    private val dragStartListener: OnStartDragListener
) :
    RecyclerView.Adapter<NewFitnessAdapter.NewFitnessViewHolder>(), ItemTouchHelperAdapter {

    init {
        notifyDataSetChanged()
    }

    inner class NewFitnessViewHolder(private val itemBinding: ItemNewFitnessBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItem(exercises: Exercises) {
            itemBinding.txtAnimationName.text = exercises.name
            itemBinding.txtAnimationAmount.text = "x ${exercises.animationMount}"
            Glide.with(itemView.context).load(exercises.urlVideoGuide)
                .into(itemBinding.imgAnimationExercise)

            itemBinding.txtIconChangeIndex.setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    dragStartListener.onStartDrag(this)
                }
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewFitnessViewHolder {
        return NewFitnessViewHolder(
            ItemNewFitnessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    override fun onBindViewHolder(holder: NewFitnessViewHolder, position: Int) {
        val exercises = exercisesList[position]
        holder.bindItem(exercises)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(exercisesList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        exercisesList.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateData(newExercisesList: List<Exercises>) {
        exercisesList = newExercisesList
    }
}