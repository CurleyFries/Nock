package edu.jcurley217ucla.nock

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created on 6/24
 */

class TeamActivity : AppCompatActivity() {

    lateinit var title: TextView
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var menuItem: MenuItem

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_scoring -> {
                val intentScoring = Intent(this,ScoringActivity::class.java)
                startActivity(intentScoring)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)

        title = findViewById(R.id.teamTitle)
        title.setText("TEAM ACTIVITY")

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        bottomNavigationView = findViewById(R.id.navigation)
        menuItem = bottomNavigationView.menu.getItem(1)
        menuItem.setChecked(true)
    }
}