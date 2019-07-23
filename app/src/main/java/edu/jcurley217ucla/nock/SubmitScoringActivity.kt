package edu.jcurley217ucla.nock

import android.app.Activity
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_scoring.*
import java.time.LocalDate
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.mongodb.stitch.android.core.Stitch
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential
import kotlinx.android.synthetic.main.activity_previous_scoring.*
import org.bson.Document
import java.io.File.separator
import java.util.*
import kotlin.math.min


class SubmitScoringActivity: AppCompatActivity(), PreviousScoringRecyclerAdapter.ViewHolder.OnEndListener {

    lateinit var dbHelper: DatabaseHelper
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var menuItem: MenuItem
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
//        val scoringIntent = Intent(this, PreviousScoringOverviewActivity::class.java)
//        scoringIntent.putExtra("scoringRound", scoringRoundList[position])
//        startActivity(scoringIntent)
        //Stitch.initializeDefaultAppClient(resources.getString(R.string.app_id))
        val stitchAppClient = Stitch.getDefaultAppClient()
        stitchAppClient.auth.loginWithCredential(AnonymousCredential()).addOnSuccessListener {
            val mongoClient = stitchAppClient.getServiceClient(RemoteMongoClient.factory,"mongodb-atlas")
            val scoringRoundCollection = mongoClient.getDatabase("nock" ).getCollection("scoringRounds")
            val teamMembershipCollection = mongoClient.getDatabase("nock").getCollection("teamMembership")
            val scoringRound = Document()
            val currentScoringRound = scoringRoundList[position]
            val userID = it.id
            val memberNameQuery = teamMembershipCollection.find(Document().append("teamName", intent.getStringExtra("teamName")).append("user_id", it.id)).limit(1)
            val result = mutableListOf<Document>()
            memberNameQuery.into(result).addOnSuccessListener {
                if(result.size==0)
                {
                    Toast.makeText(applicationContext, "You are not a member of this team, cannot upload scoring round!", Toast.LENGTH_SHORT).show()
                }
                else {
                    scoringRound["teamName"] = intent.getStringExtra("teamName")
                    scoringRound["personName"] = result[0]["memberName"]
                    scoringRound["division"] = currentScoringRound.division
                    scoringRound["distance"] = currentScoringRound.distance
                    scoringRound["targetSize"] = currentScoringRound.targetSize
                    scoringRound["totalScore"] = currentScoringRound.computeRunningTotal(currentScoringRound.ends - 1).toString()
                    scoringRound["ends"] = currentScoringRound.ends
                    scoringRound["arrowsPerEnd"] = currentScoringRound.arrowsPerEnd
                    scoringRound["datePerformed"] = currentScoringRound.date
                    //TODO: Get Correct Date
                    scoringRound["dateSubmitted"] = "Everyday"
                    val arrowValues = Document()
                    for (i in 0..currentScoringRound.ends - 1) {
                        arrowValues[i.toString()] = currentScoringRound.scores[i].joinToString(separator = "")
                    }
                    scoringRound["arrowValues"] = Arrays.asList(arrowValues)[0]
                    scoringRound["user_id"] = userID


                    scoringRoundCollection.insertOne(scoringRound).addOnSuccessListener {
                        Log.i("Stitch", "Successfully Added to Database")
                        super.onBackPressed()
                    }
                }
            }
        }
    }

    var scoringRoundList = arrayListOf<ScoringRound>()//= edu.jcurley217ucla.nock.ScoringRound("Today", "Barebow", "18m", "40cm", 20, 3)
    lateinit var previousScoringRRR : PreviousScoringRecyclerAdapter

    lateinit var recyclerView: RecyclerView

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
        setContentView(R.layout.activity_previous_scoring)

        //        Bottom Navigation Bar
        bottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        menuItem = bottomNavigationView.menu.getItem(0)
        menuItem.setChecked(true)

        dbHelper = DatabaseHelper(this)

        scoringRoundList = dbHelper.readScoringRoundList()

        recyclerView=findViewById(R.id.recyclerView)
        initRecyclerView()

        val itemTouchHelperCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
                var background = ColorDrawable(Color.GRAY)
                if(dX < -(viewHolder.itemView.right/2))
                {
                    background = ColorDrawable(Color.RED)
                }
                background.setBounds((viewHolder.itemView.right + dX).toInt(),viewHolder.itemView.top, viewHolder.itemView.right, viewHolder.itemView.bottom)
                background.draw(c)
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun onMove(p0: RecyclerView, p1: RecyclerView.ViewHolder, p2: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
                previousScoringRRR.removeItem(p0)
            }

        }
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallBack)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    fun initRecyclerView() {
        var linearLayoutManager: LinearLayoutManager = LinearLayoutManager(this)
        recyclerView.setLayoutManager(linearLayoutManager)
        previousScoringRRR = PreviousScoringRecyclerAdapter(scoringRoundList, this)
        recyclerView.adapter = previousScoringRRR
    }

}