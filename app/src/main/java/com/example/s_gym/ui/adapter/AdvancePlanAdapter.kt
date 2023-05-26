package com.example.s_gym.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.s_gym.R
import com.example.s_gym.database.entity.FitnessAdvance
import com.example.s_gym.databinding.ItemAdvancedPlanBinding
import com.example.s_gym.ui.fragment.AdvancedPlanFragmentDirections
import com.example.s_gym.ui.fragment.FitnessFragment

class AdvancePlanAdapter(private var fitnessList: List<FitnessAdvance>) :
    RecyclerView.Adapter<AdvancePlanAdapter.AdvancePlanViewHolder>() {

    inner class AdvancePlanViewHolder(private val itemBinding: ItemAdvancedPlanBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(fitnessAdvance: FitnessAdvance) {
            itemBinding.txtExerName.text = fitnessAdvance.name
            itemBinding.txtExerAmount.text = "${fitnessAdvance.exercisesList.size} động tác"
            itemView.setOnClickListener {
                val position = adapterPosition // Lấy vị trí của itemView đã click
                val fitness = fitnessList[position] // Lấy đối tượng FitnessAdvance tương ứng
                val action =
                    AdvancedPlanFragmentDirections.actionAdvancedPlanFragmentToAdvancedFitnessFragment(
                        fitness
                    )
                it.findNavController().navigate(action)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvancePlanViewHolder {
        return AdvancePlanViewHolder(
            ItemAdvancedPlanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AdvancePlanViewHolder, position: Int) {
        val fitnessAdvance = fitnessList[position]
        holder.bindItem(fitnessAdvance)
    }

    override fun getItemCount() = fitnessList.size

    fun setFitnessAdvanceList(fitnessList: List<FitnessAdvance>) {
        this.fitnessList = fitnessList
        notifyDataSetChanged()
    }
}
