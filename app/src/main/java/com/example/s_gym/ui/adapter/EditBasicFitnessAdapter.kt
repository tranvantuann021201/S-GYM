package com.example.s_gym.ui.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.s_gym.api.Exercise
import com.example.s_gym.databinding.ItemEditBasicFitnessBinding
import java.util.*


interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}

interface OnStartDragListener {
    fun onStartDrag(viewHolder: RecyclerView.ViewHolder)
}

class EditBasicFitnessAdapter(
    var exercisesList: MutableList<Exercise>, private val dragStartListener: OnStartDragListener
) : RecyclerView.Adapter<EditBasicFitnessAdapter.EditBasicFitnessViewHolder>(), ItemTouchHelperAdapter {

    inner class EditBasicFitnessViewHolder(private val itemBinding: ItemEditBasicFitnessBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bindItem(exercises: Exercise) {
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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EditBasicFitnessViewHolder {
        return EditBasicFitnessViewHolder(
            ItemEditBasicFitnessBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: EditBasicFitnessViewHolder,
        position: Int
    ) {
        val exercises = exercisesList[position]
        holder.bindItem(exercises)
    }

    override fun getItemCount(): Int {
        return exercisesList.size
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        Collections.swap(exercisesList, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        exercisesList.removeAt(position)
        notifyItemRemoved(position)
    }

    // Add a method to get the sorted list of exercises
    fun getSortedExercises(): MutableList<Exercise> {
        return exercisesList
    }
}