package edu.jcurley217ucla.nock

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate

class ScoringOverviewActivity: AppCompatActivity(), ScoringRoundRecyclerAdapter.ViewHolder.OnEndListener {

    lateinit var dbHelper: DatabaseHelper
    var notesIncluded = false
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode)
        {
            1 -> if(resultCode == Activity.RESULT_OK)
                {
                    var endToChange = data!!.getIntExtra("end", 1)
                    var b : Bundle = data.extras
                    var newScores = b.getStringArray("scores")
                    ScoringRound.changeEnd(endToChange-1, newScores)
                    recyclerView.adapter!!.notifyDataSetChanged()
                }
        }
    }
    override fun onEndClick(position: Int) {
        val endIntent = Intent(this, InputScoresActivity::class.java)
        endIntent.putExtra("end", position+1)
        var b = Bundle()
        b.putStringArray("scores", ScoringRound.scores[position])
        endIntent.putExtras(b)
        endIntent.putExtra("arrowsPerEnd", intent.getIntExtra("arrowsPerEnd", 1))
        startActivityForResult(endIntent, 1)
    }

    lateinit var ScoringRound : ScoringRound//= edu.jcurley217ucla.nock.ScoringRound("Today", "Barebow", "18m", "40cm", 20, 3)
    lateinit var scoringRRA : ScoringRoundRecyclerAdapter

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scores)

        val notesEditText : EditText = findViewById(R.id.notes)

        val builder = AlertDialog.Builder(this@ScoringOverviewActivity)
        builder.setTitle("Confirming Scoring Round Completion")
        builder.setMessage("Are you sure you want to complete your scoring round? Scores cannot be changed after completion")
        builder.setPositiveButton("YES"){dialog, which ->
            if(notesIncluded) {
                ScoringRound.notes = notesEditText.text.toString()
            }
            dbHelper.insertScoringRound(ScoringRound)
            var returnHomeIntent = Intent(this, MainActivity::class.java)
            returnHomeIntent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
            //returnHomeIntent.flags=Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(returnHomeIntent)
            finish()
        }

        builder.setNegativeButton("NO") {dialog, which ->  }

        val dialog: AlertDialog  = builder.create()

        dbHelper = DatabaseHelper(this)

//        Log.i("Intent Receiving", "division was " + intent.getStringExtra("division"))
//        Log.i("Intent Receiving", "distance was " + intent.getStringExtra("distance"))
//        Log.i("Intent Receiving", "target size was " + intent.getStringExtra("targetSize"))

        //TODO: Get Correct Date
        ScoringRound = edu.jcurley217ucla.nock.ScoringRound("Today", intent.getStringExtra("division"),
                intent.getStringExtra("distance"), intent.getStringExtra("targetSize"),intent.getIntExtra("numEnds", 1), intent.getIntExtra("arrowsPerEnd", 1))

        notesIncluded = intent.getBooleanExtra("notes", false)
        if(!notesIncluded)
        {
            notesEditText.visibility = View.GONE
        }

        recyclerView=findViewById(R.id.recyclerView)
        initRecyclerView()

        val completeScoringButton: Button = findViewById(R.id.completeScoring)
        completeScoringButton.setOnClickListener{
            dialog.show()
        }

    }

    fun initRecyclerView() {
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager)
        scoringRRA = ScoringRoundRecyclerAdapter(ScoringRound, this)
        recyclerView.adapter = scoringRRA
    }

}