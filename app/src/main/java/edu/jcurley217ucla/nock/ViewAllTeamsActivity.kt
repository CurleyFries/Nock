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
import com.mongodb.stitch.android.core.Stitch
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential
import kotlinx.android.synthetic.main.activity_main.*
import org.bson.Document
import java.time.LocalDate

class ViewAllTeamsActivity: AppCompatActivity(), ViewTeamsRecyclerAdapter.ViewHolder.OnEndListener {

//    lateinit var dbHelper: DatabaseHelper
//    var notesIncluded = false
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode)
        {
            1 -> if(resultCode == Activity.RESULT_OK)
            {
//                var endToChange = data!!.getIntExtra("end", 1)
//                var b : Bundle = data.extras
//                var newScores = b.getStringArray("scores")
//                ScoringRound.changeEnd(endToChange-1, newScores)
//                recyclerView.adapter!!.notifyDataSetChanged()
            }
        }
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

    lateinit var teamRRA : ViewTeamsRecyclerAdapter
    var teamProfileList = arrayListOf<TeamProfile>()

    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all_teams)

//        val notesEditText : EditText = findViewById(R.id.notes)

//        val builder = AlertDialog.Builder(this@ScoringOverviewActivity)
//        builder.setTitle("Confirming Scoring Round Completion")
//        builder.setMessage("Are you sure you want to complete your scoring round? Scores cannot be changed after completion")
//        builder.setPositiveButton("YES"){dialog, which ->
//            if(notesIncluded) {
//                ScoringRound.notes = notesEditText.text.toString()
//            }
//            dbHelper.insertScoringRound(ScoringRound)
//            var returnHomeIntent = Intent(this, MainActivity::class.java)
//            returnHomeIntent.flags=Intent.FLAG_ACTIVITY_CLEAR_TOP
//            //returnHomeIntent.flags=Intent.FLAG_ACTIVITY_NO_HISTORY
//            startActivity(returnHomeIntent)
//            finish()
//        }
//
//        builder.setNegativeButton("NO") {dialog, which ->  }
//
//        val dialog: AlertDialog  = builder.create()
//
//        dbHelper = DatabaseHelper(this)

        //TODO: Retrieve list of teams from database
        Stitch.initializeDefaultAppClient(resources.getString(R.string.app_id))
        val stitchAppClient = Stitch.getDefaultAppClient()
        stitchAppClient.auth.loginWithCredential(AnonymousCredential()).addOnSuccessListener {
            Log.i("Stitch", "Logged In Anonymous User")
            val mongoClient = stitchAppClient.getServiceClient(RemoteMongoClient.factory,"mongodb-atlas")
            val teamProfileCollection = mongoClient.getDatabase("nock" ).getCollection("teamProfiles")
            val queryAllTeams = teamProfileCollection.find().sort(Document("teamName", 1))
            val result = mutableListOf<Document>()
            queryAllTeams.into(result).addOnSuccessListener {
                Log.i("Stitch", "Query Success, Size: " + result.size.toString())
                result.forEach {
                    teamProfileList.add(TeamProfile("Test", it["teamName"] as String, it["teamDesc"] as String))
                    Log.i("Stitch", it["teamName"] as String)
                }
                recyclerView=findViewById(R.id.recyclerView)
                initRecyclerView()
            }
            Log.i("Stitch", "reached after query")
        }





    }

    fun initRecyclerView() {
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager)
        teamRRA = ViewTeamsRecyclerAdapter(teamProfileList, this)
        recyclerView.adapter = teamRRA
    }

}