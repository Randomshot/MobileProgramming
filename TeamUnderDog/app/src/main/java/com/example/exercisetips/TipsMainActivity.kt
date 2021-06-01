package com.example.exercisetips

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.teamunderdog.databinding.ActivityTipsMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class TipsMainActivity : AppCompatActivity() {

    lateinit var binding: ActivityTipsMainBinding

    // Tab에 출력되는 2가지 메뉴
    val textarr = arrayListOf<String>("벤치 프레스", "스쿼트", "뎀벨로우", "푸쉬업", "플랭크", "런지")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTipsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {

        var frag_benchpress:TipsImageFragment
        var frag_squat:TipsImageFragment
        var frag_dullbellrow:TipsImageFragment
        var frag_pushup:TipsImageFragment
        var frag_plank:TipsImageFragment
        var frag_lounge:TipsImageFragment

        val frag1 = TipsImageFragment()
        val frag2 = TipsImageFragment()
        val frag3 = TipsImageFragment()
        val frag4 = TipsImageFragment()
        val frag5 = TipsImageFragment()
        val frag6 = TipsImageFragment()

        var args = Bundle()
        args.putInt("img", 0)
        args.putString("url","https://youtu.be/0DsXTSHo3lU")
        frag1.arguments = args
        frag_benchpress = frag1

        args = Bundle()
        args.putInt("img", 1)
        args.putString("url","https://youtu.be/Fk9j6pQ6ej8")
        frag2.arguments = args
        frag_squat = frag2

        args = Bundle()
        args.putInt("img", 2)
        args.putString("url","https://youtu.be/VzpvMaGEiAA")
        frag3.arguments = args
        frag_dullbellrow = frag3

        args = Bundle()
        args.putInt("img", 3)
        args.putString("url","https://youtu.be/aoH7qNedO8k")
        frag4.arguments = args
        frag_pushup = frag4

        args = Bundle()
        args.putInt("img", 4)
        args.putString("url","https://youtu.be/Zq8nRY9P_cM")
        frag5.arguments = args
        frag_plank = frag5

        args = Bundle()
        args.putInt("img", 5)
        args.putString("url","https://youtu.be/oYiBDWhmrX8")
        frag6.arguments = args
        frag_lounge = frag6

        val arr = arrayListOf<Fragment>(frag_benchpress, frag_squat, frag_dullbellrow, frag_pushup,
                                        frag_plank, frag_lounge)

        binding.viewPager.adapter = TipsFragStateAdapter(this, arr)
        TabLayoutMediator(binding.tablayout, binding.viewPager){
                tab, position->
            tab.text = textarr[position]
        }.attach()
    }
}
