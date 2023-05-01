package com.example.s_gym.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.s_gym.R
import com.example.s_gym.database.FitnessDay
import com.example.s_gym.ui.fragment.BasicPlanFragment

class BasicPlanAdapter(
    private var fitnessDayList: List<FitnessDay>
) :
    RecyclerView.Adapter<BasicPlanAdapter.BasicPlanViewHolder>() {
    private lateinit var listener: BasicPlanFragment.onBasicPlanItemClickListener

    fun setItemClickListener(listeners: BasicPlanFragment.onBasicPlanItemClickListener) {
        listener = listeners
    }
    class BasicPlanViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameDayTextView: TextView = itemView.findViewById(R.id.txt_day_name)

        fun bind(fitnessDay: FitnessDay) {
            nameDayTextView.text = fitnessDay.nameDay
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasicPlanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_basic_plan, parent, false)
        return BasicPlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: BasicPlanViewHolder, position: Int) {
        holder.bind(fitnessDayList[position])
        holder.itemView.setOnClickListener {
            listener.onBasicPlanItemClick(fitnessDayList[position])
        }

//        Glide.with(context)
//            .load("https://wger.de/media/exercise-images/${currentItem.id}/thumbnail.png")
//            .into(holder.imageView)
    }

    override fun getItemCount() = fitnessDayList.size
}