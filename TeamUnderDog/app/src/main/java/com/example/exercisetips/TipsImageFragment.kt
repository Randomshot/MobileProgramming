package com.example.exercisetips

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.exercisetips.databinding.FragmentTipsImageBinding

class TipsImageFragment : Fragment() {

    interface Listener{
        fun onClick()
    }

    lateinit var listener: Listener

    var binding: FragmentTipsImageBinding?=null

    val tipsViewModel: TipsViewModel by activityViewModels()

    val tipsimglist = arrayListOf<Int>(R.drawable.benchpress, R.drawable.squat, R.drawable.dumbellrow,
                                    R.drawable.pushup, R.drawable.plank, R.drawable.lounge)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTipsImageBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.apply {
            var img_num = arguments?.getInt("img")
            var img_url = arguments?.getString("url")
            imageView.setImageResource(tipsimglist[img_num!!])
            imageView.setOnClickListener {
               val intent = Intent(activity, TipsViewPageActivity::class.java)
                intent.putExtra("url", img_url)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}