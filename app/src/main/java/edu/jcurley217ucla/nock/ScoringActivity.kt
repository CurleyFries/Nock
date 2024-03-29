package edu.jcurley217ucla.nock

import android.content.Intent
import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created on 6/24
 */

class ScoringActivity : AppCompatActivity() {

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


        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        var newScoringButton: Button = findViewById(R.id.newScoringButton)
        newScoringButton.setOnClickListener{
            val newScoringIntent = Intent(this, NewScoringActivity::class.java)
            startActivityForResult(newScoringIntent, 1)
        }

        var previousScoringButton: Button = findViewById(R.id.previousScoringButton)
        previousScoringButton.setOnClickListener{
            val previousScoringIntent = Intent(this, PreviousScoringActivity::class.java)
            startActivityForResult(previousScoringIntent, 1)
        }

        var newPresetButton: Button = findViewById(R.id.newPresetButton)
        newPresetButton.setOnClickListener{
            val newPresetIntent = Intent(this, NewPresetActivity::class.java)
            startActivityForResult(newPresetIntent, 1)
        }

        var viewPresetButton: Button = findViewById(R.id.viewPresetButton)
        viewPresetButton.setOnClickListener{
            val viewPresetIntent = Intent(this, ViewPresetsActivity::class.java)
            startActivityForResult(viewPresetIntent, 1)
        }

        bottomNavigationView = findViewById(R.id.navigation)
        menuItem = bottomNavigationView.menu.getItem(0)
        menuItem.setChecked(true)
    }
}