package edu.jcurley217ucla.nock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.*
import edu.jcurley217ucla.nock.R.id.divisionSpinner
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
    lateinit var loadFromPresetButton: Button
    lateinit var divisionSpinner: Spinner
    lateinit var distanceSpinner: Spinner
    lateinit var targetSpinner: Spinner
    lateinit var arrowSpinner: Spinner

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode)
        {
            1 -> if(resultCode == Activity.RESULT_OK)
            {
                division = data!!.getStringExtra("division")
                distance = data.getStringExtra("distance")
                target = data.getStringExtra("targetSize")
                numberOfEnds = data.getIntExtra("numEnds", 1)
                arrowsPerEnd = data.getIntExtra("arrowsPerEnd", 3)

                for (i in 0 until divisionSpinner.getCount()) {
                    if (divisionSpinner.getItemAtPosition(i).toString()==division) {
                        divisionSpinner.setSelection(i)
                    }
                }
                for (i in 0 until distanceSpinner.getCount()) {
                    if (distanceSpinner.getItemAtPosition(i).toString()==distance) {
                        distanceSpinner.setSelection(i)
                    }
                }
                for (i in 0 until targetSpinner.getCount()) {
                    if (targetSpinner.getItemAtPosition(i).toString()==target) {
                        targetSpinner.setSelection(i)
                    }
                }
                for (i in 0 until arrowSpinner.getCount()) {
                    if (arrowSpinner.getItemAtPosition(i).toString()== arrowsPerEnd.toString()) {
                        arrowSpinner.setSelection(i)
                    }
                }

                numEnds.text= numberOfEnds.toString()


            }
        }
    }


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
        divisionSpinner = findViewById(R.id.divisionSpinner)
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
        distanceSpinner = findViewById(R.id.distanceSpinner)
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
        targetSpinner = findViewById(R.id.targetSpinner)
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
       arrowSpinner = findViewById(R.id.arrowSpinner)
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

        val startScoring: FloatingActionButton = findViewById(R.id.startScoring)
        startScoring.setOnClickListener {
            val startScoringIntent = Intent(this, ScoringOverviewActivity::class.java)
            startScoringIntent.putExtra("division", division)
            startScoringIntent.putExtra("distance", distance)
            startScoringIntent.putExtra("targetSize", target)
            startScoringIntent.putExtra("numEnds", numberOfEnds)
            startScoringIntent.putExtra("arrowsPerEnd", arrowsPerEnd)
            startActivity(startScoringIntent)
            //Toast.makeText(applicationContext, "Start scoring was pushed", Toast.LENGTH_SHORT).show()
        }
//        Preset Test Button
        loadFromPresetButton = findViewById(R.id.loadPresetButton)
        loadFromPresetButton.setOnClickListener{
            val presetIntent = Intent(this, LoadFromPresetActivity::class.java)
            startActivityForResult(presetIntent, 1)
        }
    }
}

class DivisionSpinnerClass : AdapterView.OnItemSelectedListener
{
    override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
        division=parent.getItemAtPosition(pos).toString()
        //Toast.makeText(parent.context, division + " was selected in Division", Toast.LENGTH_SHORT).show()
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
        //Toast.makeText(parent.context, distance + " was selected in Distance", Toast.LENGTH_SHORT).show()
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
        //Toast.makeText(parent.context, target + " was selected in Target", Toast.LENGTH_SHORT).show()
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
        //Toast.makeText(parent.context, arrowsPerEnd.toString() + " was selected in Arrows Per End", Toast.LENGTH_SHORT).show()
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        // Another interface callback
    }
}

