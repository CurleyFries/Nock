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

class ViewPresetsActivity: AppCompatActivity(), ViewPresetsRecyclerAdapter.ViewHolder.OnEndListener {
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//
//        when(requestCode)
//        {
//            1 -> if(resultCode == Activity.RESULT_OK)
//            {
//                var endToChange = data!!.getIntExtra("end", 1)
//                var b : Bundle = data.extras
//                var newScores = b.getStringArray("scores")
//                ScoringRound.changeEnd(endToChange-1, newScores)
//                recyclerView.adapter!!.notifyDataSetChanged()
//            }
//        }



    }
    override fun onEndClick(position: Int) {
//        val endIntent = Intent(this, InputScoresActivity::class.java)
//        endIntent.putExtra("end", position+1)
//        var b = Bundle()
//        b.putStringArray("scores", ScoringRound.scores[position])
//        endIntent.putExtras(b)
//        endIntent.putExtra("arrowsPerEnd", intent.getIntExtra("arrowsPerEnd", 1))
//        startActivityForResult(endIntent, 1)
    }

    var presetList = arrayListOf<Preset>()//= edu.jcurley217ucla.nock.ScoringRound("Today", "Barebow", "18m", "40cm", 20, 3)
    lateinit var viewPresetRRA : ViewPresetsRecyclerAdapter

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_presets)


        val preset1 = Preset("Barebow", "18m", "40cm", 10, 3)
        presetList.add(preset1)
        val preset2 = Preset("Barebow", "50m", "122cm", 5, 6)
        presetList.add(preset2)
        val preset3 = Preset("Recurve", "18m", "40cm", 10, 3)
        presetList.add(preset3)
        val preset4 = Preset("Recurve", "70m", "122cm", 5, 6)
        presetList.add(preset4)

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