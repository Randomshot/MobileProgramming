package com.example.teamunderdog.routine

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamunderdog.databinding.RoutineRowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyRoutineAdapter(options: FirebaseRecyclerOptions<Routine>)
    : FirebaseRecyclerAdapter<Routine, MyRoutineAdapter.ViewHolder>(options) {
    interface OnItemClickListener{
        fun OnItemClick(view: View, position: Int)
    }

    var itemClickListener: OnItemClickListener?=null

    inner class ViewHolder (val binding: RoutineRowBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                itemClickListener!!.OnItemClick(it, adapterPosition)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        //return super.getItemViewType(position)
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RoutineRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Routine) {
        holder.binding.apply {
            routineid.text = model.rId.toString()
            routinename.text = model.rName.toString()
        }
    }
}