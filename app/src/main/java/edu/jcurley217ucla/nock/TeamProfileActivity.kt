package edu.jcurley217ucla.nock

import android.content.Intent
import android.os.Bundle
import android.service.autofill.TextValueSanitizer
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.*
import com.mongodb.stitch.android.core.Stitch
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_join_team.view.*
import org.bson.Document
import java.util.*

/**
 * Created on 6/24
 */

class TeamProfileActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var menuItem: MenuItem

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
        setContentView(R.layout.activity_specific_team_profile)


        //navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        val teamLogo: ImageView = findViewById(R.id.teamLogo)

        val teamName: TextView = findViewById(R.id.teamName)
        teamName.text = intent.getStringExtra("teamName")

        val teamDesc: TextView = findViewById(R.id.teamDesc)
        teamDesc.text = intent.getStringExtra("teamDesc")

        var joinTeamButton: Button = findViewById(R.id.joinTeamButton)
        joinTeamButton.setOnClickListener{
            val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_join_team, null)
            val alertDialogBuilder = AlertDialog.Builder(this).setView(dialogView).setTitle("Join Team Form")
            val alertDialog = alertDialogBuilder.show()
//            Initiate Division Spinner
//            val divisionSpinner: Spinner = findViewById(R.id.divisionSpinner)
            dialogView.divisionSpinner.onItemSelectedListener = DivisionSpinnerClass()
            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter.createFromResource(
                    this,
                    R.array.divisions_array,
                    android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                dialogView.divisionSpinner.adapter = adapter
            }


            dialogView.confirmButton.setOnClickListener {
                val stitchAppClient = Stitch.getDefaultAppClient()
                stitchAppClient.auth.loginWithCredential(AnonymousCredential()).addOnSuccessListener {
                    val mongoClient = stitchAppClient.getServiceClient(RemoteMongoClient.factory,"mongodb-atlas")
                    val teamMembershipCollection = mongoClient.getDatabase("nock" ).getCollection("teamMembership")
                    val teamMembership = Document()
                    val teamName = intent.getStringExtra("teamName")
                    teamMembership["teamName"] = teamName
                    teamMembership["memberName"] = dialogView.firstName.text.toString() + " " + dialogView.lastName.text.toString()
                    teamMembership["memberDivision"] = division
                    //Check for already existing member name and combination
                    val duplicateQuery = teamMembershipCollection.find(teamMembership)
                    val result = mutableListOf<Document>()
                    val stitchIt = it
                    duplicateQuery.into(result).addOnSuccessListener {
                        if(result.size == 0) //No member already exists with given information
                        {
                            teamMembership["user_id"] = stitchIt.id
                            teamMembershipCollection.insertOne(teamMembership).addOnSuccessListener {
                                alertDialog.dismiss()
                            }
                        }
                        else
                        {
                            alertDialog.dismiss()
                            Toast.makeText(applicationContext, "A member with the given information already exists!", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }

            dialogView.cancelButton.setOnClickListener {
                alertDialog.dismiss()
            }
        }

        var submitScoringRoundButton: Button = findViewById(R.id.submitScoringRoundButton)
        submitScoringRoundButton.setOnClickListener{
            val submitScoringRoundIntent = Intent(this, SubmitScoringActivity::class.java)
            submitScoringRoundIntent.putExtra("teamName", teamName.text as String)
            startActivityForResult(submitScoringRoundIntent, 1)
        }

        val builder = android.app.AlertDialog.Builder(this@TeamProfileActivity)
        builder.setTitle("Confirm Leave Team")
        builder.setMessage("Are you sure you want to leave this team? If you have not joined this team, this will do nothing")
        builder.setPositiveButton("YES"){dialog, which ->
            val stitchAppClient = Stitch.getDefaultAppClient()
            stitchAppClient.auth.loginWithCredential(AnonymousCredential()).addOnSuccessListener {
                val mongoClient = stitchAppClient.getServiceClient(RemoteMongoClient.factory,"mongodb-atlas")
                val teamMembershipCollection = mongoClient.getDatabase("nock" ).getCollection("teamMembership")
                //TODO: This assumes you can only join the team once, may need to be changed
                var filterDocument = Document().append("user_id", it.id).append("teamName", teamName.text as String)
                teamMembershipCollection.deleteOne(filterDocument).addOnSuccessListener {
                    Log.i("Stitch", "Successfully deleted")
                }
            }
        }

        builder.setNegativeButton("NO") {dialog, which ->  }

        val dialog: android.app.AlertDialog = builder.create()

        val leaveTeamButton: Button = findViewById(R.id.leaveTeamButton)
        leaveTeamButton.setOnClickListener{
            dialog.show()
        }



//        bottomNavigationView = findViewById(R.id.navigation)
//        menuItem = bottomNavigationView.menu.getItem(1)
//        menuItem.setChecked(true)
    }
}