package edu.jcurley217ucla.nock

import android.content.Intent
import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import android.support.design.widget.BottomNavigationView
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

var division: String = "Barebow"
var distance : String = "10m"
var target : String = "40cm"
var numberOfEnds : Int = 10
var arrowsPerEnd: Int = 1


class NewScoringActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var menuItem: MenuItem
    lateinit var numEnds: TextView
    lateinit var increaseButton: Button
    lateinit var decreaseButton: Button
    lateinit var presetTestButton: Button


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
        setContentView(R.layout.activity_new_scoring)

//        Bottom Navigation Bar
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        bottomNavigationView = findViewById(R.id.navigation)
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

//        Preset Test Button
        presetTestButton = findViewById(R.id.presetTestButton)
        presetTestButton.setOnClickListener{
            divisionSpinner.setSelection(1)
            division=divisionSpinner.getItemAtPosition(1).toString()
            distanceSpinner.setSelection(1)
            distance=distanceSpinner.getItemAtPosition(1).toString()
            targetSpinner.setSelection(1)
            target= targetSpinner.getItemAtPosition(1).toString()
            arrowSpinner.setSelection(1)
            arrowsPerEnd = arrowSpinner.getItemAtPosition(1).toString().toInt()
        }
    }
}

class DivisionSpinnerClass : AdapterView.OnItemSelectedListener
{
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        division=parent.getItemAtPosition(pos).toString()
        Toast.makeText(parent.context, division + " was selected in Division", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}

class DistanceSpinnerClass : AdapterView.OnItemSelectedListener
{
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        distance = parent.getItemAtPosition(pos).toString()
        Toast.makeText(parent.context, distance + " was selected in Distance", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}

class TargetSpinnerClass : AdapterView.OnItemSelectedListener
{
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        target = parent.getItemAtPosition(pos).toString()
        Toast.makeText(parent.context, target + " was selected in Target", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}

class ArrowSpinnerClass : AdapterView.OnItemSelectedListener
{
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        arrowsPerEnd = parent.getItemAtPosition(pos).toString().toInt()
        Toast.makeText(parent.context, arrowsPerEnd.toString() + " was selected in Arrows Per End", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}