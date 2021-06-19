package com.example.teamunderdog.exerciselist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.teamunderdog.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ExerciseAdapter(options: FirebaseRecyclerOptions<ExerciseData>):
    FirebaseRecyclerAdapter<ExerciseData, ExerciseAdapter.ViewHolder>(options) {

    interface OnItemClickListener{
        fun OnItemClick(view: View, position: Int)
    }

    var itemClickListener: OnItemClickListener?=null



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.exerciseTitleTextView)
        val setsNum: TextView = itemView.findViewById(R.id.exerciseSetsNumTextView)
        val weight: TextView = itemView.findViewById(R.id.exerciseWeightTextView)
        val count:TextView = itemView.findViewById(R.id.exerciseCountTextView)
        init{

            itemView.setOnClickListener {
                itemClickListener?.OnItemClick(it,adapterPosition)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, data: ExerciseData) {

        holder.title.text = data.eTitle
        holder.setsNum.text = data.eSetsNum.toString() + " 세트"
        holder.weight.text = data.eWeight.toString() + " kg"
        if(data.k == true){
            holder.count.text = data.eCount.toString() + " 회/1세트"
        }
        else{
            holder.count.text = data.eCount.toString() + " 초"
        }
    }
}