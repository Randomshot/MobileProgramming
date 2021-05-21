package com.example.teamunderdog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.teamunderdog.databinding.FragmentExerciseListBinding


class ExerciseListFragment : Fragment() {

    var binding: FragmentExerciseListBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExerciseListBinding.inflate(layoutInflater)
        return binding!!.root
    }

}