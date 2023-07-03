package com.example.s_gym.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.s_gym.database.entity.Days
import com.example.s_gym.databinding.ItemHistoryBinding
import java.text.SimpleDateFormat

class HistoryAdapter(var daysList: List<Days>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    inner class HistoryViewHolder(private val itemBinding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(days: Days) {
            val dateString = days.name
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val date = dateFormat.parse(dateString)
            val newFormat = SimpleDateFormat("dd MMM")
            val newDateString = newFormat.format(date)

            itemBinding.txtExerDay.text = newDateString
            itemBinding.txtKcal.text = String.format("%.2f", days.kcalConsumed)
            itemBinding.txtCompletedExercise.text = days.completedExercise.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            ItemHistoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val days = daysList[position]
        holder.bindItem(days)
    }


    override fun getItemCount(): Int {
        return daysList.size
    }
}