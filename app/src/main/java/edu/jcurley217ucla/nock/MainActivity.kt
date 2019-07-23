package edu.jcurley217ucla.nock

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.mongodb.stitch.android.core.Stitch
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential
import kotlinx.android.synthetic.main.activity_main.*
import org.bson.Document

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var menuItem: MenuItem

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_scoring -> {
                val intentScoring = Intent(this,ScoringActivity::class.java)
                startActivity(intentScoring)
                return@OnNavigationItemSelectedListener true
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
        setContentView(R.layout.activity_main)

        Stitch.initializeDefaultAppClient(resources.getString(R.string.app_id))

        bottomNavigationView = findViewById(R.id.navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        menuItem = bottomNavigationView.menu.getItem(0)
        menuItem.setChecked(true)
    }
}
