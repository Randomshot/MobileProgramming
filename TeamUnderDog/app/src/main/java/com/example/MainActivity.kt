package com.example.teamunderdog

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.ui.AppBarConfiguration
import com.example.exercisetips.TipsMainActivity
import com.example.teamunderdog.exerciselist.ExerciseListActivity
import com.example.teamunderdog.record.ExerciseTimerActivity
import com.example.teamunderdog.record.ShowPhysicalRecordActivity
import com.example.teamunderdog.routine.RoutineActivity
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val toolbar: Toolbar = findViewById(R.id.main_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_menu_24)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val drawerLayout: DrawerLayout = findViewById(R.id.main_drawer_layout)
        val navView: NavigationView = findViewById(R.id.main_navigationView)

        navView?.setNavigationItemSelectedListener(this)

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val drawerLayout: DrawerLayout = findViewById(R.id.main_drawer_layout)
        when(item.itemId){
            android.R.id.home->{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.CategoryRecord -> {
                val intent = Intent(this, ExerciseListActivity::class.java)
                startActivity(intent)
            }
            R.id.CategoryTimer -> {
                val intent = Intent(this, ExerciseTimerActivity::class.java)
                startActivity(intent)
            }
            R.id.CategoryTips -> {
                val intent = Intent(this, TipsMainActivity::class.java)
                startActivity(intent)
            }
            R.id.CategoryRoutine -> {
                val intent = Intent(this, RoutineActivity::class.java)
                startActivity(intent)
            }
            R.id.CategoryPhysicalRecord->{
                val intent = Intent(this, ShowPhysicalRecordActivity::class.java)
                startActivity(intent)
            }
        }
        return false
    }

}


