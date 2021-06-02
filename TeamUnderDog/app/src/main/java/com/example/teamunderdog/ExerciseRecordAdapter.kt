package com.example.teamunderdog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamunderdog.databinding.ExerciseRecordRowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ExerciseRecordAdapter(options: FirebaseRecyclerOptions<ExerciseRecordData>)
    :FirebaseRecyclerAdapter<ExerciseRecordData,ExerciseRecordAdapter.ViewHolder>(options){
        interface OnItemClickListener{
            fun OnItemClick(view: View, position: Int)
        }
        var itemClickListener:OnItemClickListener?=null
        inner class ViewHolder(val binding: ExerciseRecordRowBinding):RecyclerView.ViewHolder(binding.root){
            init {
                binding.root.setOnClickListener{
                    itemClickListener!!.OnItemClick(it,adapterPosition)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ExerciseRecordRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: ExerciseRecordData) {
        holder.binding.apply {
            eid.text = model.eId.toString()
            ename.text = model.eTitle.toString()
            erecord.text = model.eRecord.toString()
            edate.text = model.eDate.toString()
        }
    }
}