package edu.jcurley217ucla.nock

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new_scoring.view.*

/**
 * Created on 6/24
 */


class NewPresetActivity : AppCompatActivity() {

    lateinit var dbHelper: DatabaseHelper

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var menuItem: MenuItem
    lateinit var numEnds: TextView
    lateinit var increaseButton: Button
    lateinit var decreaseButton: Button
    lateinit var presetTestButton: Button


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_scoring -> {
                finish()
            }
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
        setContentView(R.layout.activity_new_scoring)

        dbHelper = DatabaseHelper(this)



//        Bottom Navigation Bar
        bottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        menuItem = bottomNavigationView.menu.getItem(0)
        menuItem.setChecked(true)

//        Division Spinner
        val divisionSpinner: Spinner = findViewById(R.id.divisionSpinner)
        divisionSpinner.onItemSelectedListener = DivisionSpinnerClass()
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.divisions_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            divisionSpinner.adapter = adapter
        }
//        Distance Spinner
        val distanceSpinner: Spinner = findViewById(R.id.distanceSpinner)
        distanceSpinner.onItemSelectedListener = DistanceSpinnerClass()
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.distances_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            distanceSpinner.adapter = adapter
        }
//        Target Spinner
        val targetSpinner: Spinner = findViewById(R.id.targetSpinner)
        targetSpinner.onItemSelectedListener = TargetSpinnerClass()
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.targets_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            targetSpinner.adapter = adapter
        }
//        Arrows Per End Spinner
        val arrowSpinner: Spinner = findViewById(R.id.arrowSpinner)
        arrowSpinner.onItemSelectedListener = ArrowSpinnerClass()
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
                this,
                R.array.arrows_per_end_array,
                android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            arrowSpinner.adapter = adapter
        }

        numEnds = findViewById(R.id.numEnds)

        decreaseButton = findViewById(R.id.decreaseEnds)
        decreaseButton.setOnClickListener {
            numberOfEnds--
            if(numberOfEnds<1)
                numberOfEnds=1
            numEnds.text= numberOfEnds.toString()
        }

        increaseButton = findViewById(R.id.increaseEnds)
        increaseButton.setOnClickListener {
            numberOfEnds++
            numEnds.text= numberOfEnds.toString()
        }

        //Redundant Save as preset Button
        val saveAsPresetButton : Button = findViewById(R.id.saveAsPresetButton)
        saveAsPresetButton.visibility= View.GONE

        val savePreset: FloatingActionButton = findViewById(R.id.startScoring)
        savePreset.setOnClickListener {
            val newPreset = Preset(division,distance,target, numberOfEnds, arrowsPerEnd)

            dbHelper.insertPreset(newPreset)

            Toast.makeText(applicationContext, "Save Preset Was Pushed", Toast.LENGTH_SHORT).show()
        }
//        HIDE Loading Presets
        presetTestButton = findViewById(R.id.loadPresetButton)
        presetTestButton.visibility = View.GONE

        //Set Default Values to Spinners
        divisionSpinner.setSelection(0)
        division=divisionSpinner.getItemAtPosition(1).toString()
        distanceSpinner.setSelection(0)
        distance=distanceSpinner.getItemAtPosition(1).toString()
        targetSpinner.setSelection(0)
        target= targetSpinner.getItemAtPosition(1).toString()
        arrowSpinner.setSelection(0)
        arrowsPerEnd = arrowSpinner.getItemAtPosition(1).toString().toInt()
    }
}

