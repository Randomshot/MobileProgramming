package com.example.teamunderdog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.teamunderdog.databinding.SportsRowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MySportsAdapter(options: FirebaseRecyclerOptions<Sports>)
    : FirebaseRecyclerAdapter<Sports, MySportsAdapter.ViewHolder>(options) {
    interface OnItemClickListener{
        fun OnItemClick(view: View, position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder (val binding: SportsRowBinding): RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                itemClickListener!!.OnItemClick(it, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = SportsRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Sports) {
        holder.binding.apply {
            sportsid.text = model.sId.toString()
            sportsname.text = model.sName.toString()
            var tempstr = ""
            if (model.k){// true 개수 false 시간
                tempstr = "초"
            }else{
                tempstr = "개"
            }
            sportscount.text = model.sCount.toString() + tempstr
        }
    }
}