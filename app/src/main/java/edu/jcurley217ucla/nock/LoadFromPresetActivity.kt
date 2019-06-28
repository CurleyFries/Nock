package edu.jcurley217ucla.nock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class LoadFromPresetActivity: AppCompatActivity(), ViewPresetsRecyclerAdapter.ViewHolder.OnEndListener {

    lateinit var dbHelper: DatabaseHelper

    override fun onEndClick(position: Int) {
        val returnIntent = Intent(this, NewScoringActivity::class.java)
        returnIntent.putExtra("division", presetList[position].division)
        Log.i("Intent Passing", "Division is " + presetList[position].division)
        returnIntent.putExtra("distance", presetList[position].distance)
        returnIntent.putExtra("targetSize", presetList[position].targetSize)
        returnIntent.putExtra("numEnds", presetList[position].ends)
        returnIntent.putExtra("arrowsPerEnd", presetList[position].arrowsPerEnd)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    var presetList = arrayListOf<Preset>()//= edu.jcurley217ucla.nock.ScoringRound("Today", "Barebow", "18m", "40cm", 20, 3)
    lateinit var viewPresetRRA : ViewPresetsRecyclerAdapter

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presets)

        dbHelper = DatabaseHelper(this)


        presetList = dbHelper.readPresetList()

        recyclerView=findViewById(R.id.recyclerView)
        initRecyclerView()

    }

    fun initRecyclerView() {
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager)
        viewPresetRRA = ViewPresetsRecyclerAdapter(presetList, this)
        recyclerView.adapter = viewPresetRRA
    }

}