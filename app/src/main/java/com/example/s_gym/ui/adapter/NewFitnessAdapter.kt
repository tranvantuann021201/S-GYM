package com.example.s_gym.ui.adapter

import android.app.Application
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.s_gym.database.entity.Exercises
import com.example.s_gym.database.repository.FitnessAdvanceRepository
import com.example.s_gym.databinding.ItemNewFitnessBinding
import com.example.s_gym.ui.touch.ItemTouchHelperCallback
import com.example.s_gym.ui.viewmodel.NewFitnessViewModel
import java.util.*

class NewFitnessAdapter(
    private var exercisesList: MutableList<Exercises>,
    private val dragStartListener: OnStartDragListener,
    private val viewModel: NewFitnessViewModel,
    private val fitnessAdvanceId: Int
) :
    RecyclerView.Adapter<NewFitnessAdapter.NewFitnessViewHolder>(), ItemTouchHelperAdapter {

    init {
        notifyDataSetChanged()
    }

    private var onRemoveClick: ((Int) -> Unit)? = null

    fun setOnRemoveClickListener(listener: (Int) -> Unit) {
        onRemoveClick = listener
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

            itemBinding.btnRemoveAnimation.setOnClickListener {
                onRemoveClick?.invoke(adapterPosition)
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
        viewModel.reorderExercisesList(exercisesList, fitnessAdvanceId)
    }

    override fun onItemDismiss(position: Int) {
        val exercise = exercisesList[position]
        exercisesList.removeAt(position)
        notifyItemRemoved(position)
        viewModel.removeExerciseFromList(exercise, fitnessAdvanceId)
    }

    fun updateData(newExercisesList: List<Exercises>) {
        exercisesList.clear()
        exercisesList.addAll(newExercisesList)
    }

    fun getExercisesList(): List<Exercises> {
        return exercisesList
    }
}
