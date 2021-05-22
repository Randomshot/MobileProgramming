package com.example.teamunderdog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teamunderdog.databinding.FragmentExerciseListBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class ExerciseListFragment : Fragment() {

    lateinit var adapter: ExerciseAdapter
    lateinit var rdb: DatabaseReference
    private var date:String?= null
    var binding: FragmentExerciseListBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        date =arguments?.getString("date")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentExerciseListBinding.inflate(layoutInflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val rdbPath = "Exercise/" + date
        rdb = FirebaseDatabase.getInstance().getReference(rdbPath)
        binding!!.exerciseListRecyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        val query = rdb.limitToFirst(100)
        val option = FirebaseRecyclerOptions.Builder<ExerciseData>()
            .setQuery(query, ExerciseData::class.java)
            .build()

        adapter = ExerciseAdapter(option)
        adapter.itemClickListener = object:ExerciseAdapter.OnItemClickListener{
            override fun OnItemClick(view: View, position: Int) {

            }
        }

        binding!!.exerciseListRecyclerView.adapter = adapter
        adapter.startListening()
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.stopListening()
    }

    override fun onDetach() {
        super.onDetach()
        adapter.stopListening()
    }

}