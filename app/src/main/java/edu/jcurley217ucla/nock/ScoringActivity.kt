package edu.jcurley217ucla.nock

import android.content.Intent
import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created on 6/24
 */

class ScoringActivity : AppCompatActivity() {

    lateinit var title: TextView
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var menuItem: MenuItem

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_team -> {
                val intentTeam = Intent(this,TeamActivity::class.java)
                startActivity(intentTeam)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scoring)

        title = findViewById(R.id.scoringTitle)
        title.setText("SCORING ACTIVITY")

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        bottomNavigationView = findViewById(R.id.navigation)
        menuItem = bottomNavigationView.menu.getItem(0)
        menuItem.setChecked(true)
    }
}