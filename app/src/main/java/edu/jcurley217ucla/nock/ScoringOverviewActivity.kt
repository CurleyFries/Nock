package edu.jcurley217ucla.nock

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class ScoringOverviewActivity: AppCompatActivity() {

    lateinit var ScoringRound : ScoringRound//= edu.jcurley217ucla.nock.ScoringRound("Today", "Barebow", "18m", "40cm", 20, 3)
    lateinit var scoringRRA : ScoringRoundRecyclerAdapter

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        Log.i("Intent Receiving", "division was " + intent.getStringExtra("division"))
        Log.i("Intent Receiving", "distance was " + intent.getStringExtra("distance"))
        Log.i("Intent Receiving", "target size was " + intent.getStringExtra("targetSize"))

        ScoringRound = edu.jcurley217ucla.nock.ScoringRound("Today", intent.getStringExtra("division"),
                intent.getStringExtra("distance"), intent.getStringExtra("targetSize"),20, intent.getIntExtra("arrowsPerEnd", 1))

        recyclerView=findViewById(R.id.recyclerView)
        initRecyclerView()

    }

    fun initRecyclerView() {
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager)
        scoringRRA = ScoringRoundRecyclerAdapter(ScoringRound)
        recyclerView.adapter = scoringRRA
    }

}