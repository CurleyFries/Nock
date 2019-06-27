package edu.jcurley217ucla.nock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_scoring.*
import java.time.LocalDate

class PreviousScoringActivity: AppCompatActivity(), PreviousScoringRecyclerAdapter.ViewHolder.OnEndListener {

    lateinit var dbHelper: DatabaseHelper
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

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
//        Toast.makeText(applicationContext, "Scoring Round " + (position+1) +  " was selected", Toast.LENGTH_SHORT).show()
        val scoringIntent = Intent(this, PreviousScoringOverviewActivity::class.java)
        scoringIntent.putExtra("scoringRound", scoringRoundList[position])
        startActivity(scoringIntent)
    }

    var scoringRoundList = arrayListOf<ScoringRound>()//= edu.jcurley217ucla.nock.ScoringRound("Today", "Barebow", "18m", "40cm", 20, 3)
    lateinit var previousScoringRRR : PreviousScoringRecyclerAdapter

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_previous_scoring)

        dbHelper = DatabaseHelper(this)

        scoringRoundList = dbHelper.readScoringRoundList()

        recyclerView=findViewById(R.id.recyclerView)
        initRecyclerView()

    }

    fun initRecyclerView() {
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager)
        previousScoringRRR = PreviousScoringRecyclerAdapter(scoringRoundList, this)
        recyclerView.adapter = previousScoringRRR
    }

}