package edu.jcurley217ucla.nock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.TextViewCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class InputScoresActivity : AppCompatActivity() {


    lateinit var endScore: TextView
    lateinit var endNumber: TextView

    lateinit var arrowValues: Array<String?>
    var index = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_scores)

        val arrowsPerEnd: Int = intent.getIntExtra("arrowsPerEnd", 3)

        var b : Bundle = intent.extras
        arrowValues= b.getStringArray("scores")

        for(i in 0..arrowsPerEnd-1)
        {
            if(arrowValues[i]!= "")
                index++
            else
                break
        }
        index = minOf(arrowsPerEnd-1, index)
        endScore = findViewById(R.id.arrowValues)
        endScore.text=arrowValues.joinToString(separator = " ")
        TextViewCompat.setAutoSizeTextTypeWithDefaults(endScore, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM)

        endNumber = findViewById(R.id.endNumber)
        endNumber.text = "End " + intent.getIntExtra("end", 1)


//        Button Listeners
        val xButton : Button = findViewById(R.id.xButton)
        xButton.setOnClickListener{
            insertValue(xButton)
        }
        val tenButton : Button = findViewById(R.id.tenButton)
        tenButton.setOnClickListener{
            insertValue(tenButton)
        }
        val nineButton : Button = findViewById(R.id.nineButton)
        nineButton.setOnClickListener{
            insertValue(nineButton)
        }
        val eightButton : Button = findViewById(R.id.eightButton)
        eightButton.setOnClickListener{
            insertValue(eightButton)
        }
        val sevenButton : Button = findViewById(R.id.sevenButton)
        sevenButton.setOnClickListener{
            insertValue(sevenButton)
        }
        val sixButton : Button = findViewById(R.id.sixButton)
        sixButton.setOnClickListener{
            insertValue(sixButton)
        }
        val fiveButton : Button = findViewById(R.id.fiveButton)
        fiveButton.setOnClickListener{
            insertValue(fiveButton)
        }
        val fourButton : Button = findViewById(R.id.fourButton)
        fourButton.setOnClickListener{
            insertValue(fourButton)
        }
        val threeButton : Button = findViewById(R.id.threeButton)
        threeButton.setOnClickListener{
            insertValue(threeButton)
        }
        val twoButton : Button = findViewById(R.id.twoButton)
        twoButton.setOnClickListener{
            insertValue(twoButton)
        }
        val oneButton : Button = findViewById(R.id.oneButton)
        oneButton.setOnClickListener{
            insertValue(oneButton)
        }
        val missButton : Button = findViewById(R.id.missButton)
        missButton.setOnClickListener{
            insertValue(missButton)
        }

        val delButton : Button = findViewById(R.id.delButton)
        delButton.setOnClickListener{
            deleteValue()
        }

        val clrButton: Button = findViewById(R.id.clrButton)
        clrButton.setOnClickListener{
            clearValues()
        }

        val confirmButton: Button = findViewById(R.id.confirmButton)
        confirmButton.setOnClickListener{
            val returnIntent = Intent(this, ScoringOverviewActivity::class.java)
            returnIntent.putExtra("end", intent.getIntExtra("end", 1))
            var b = Bundle()
            b.putStringArray("scores", arrowValues)
            returnIntent.putExtras(b)

            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }


    }

    fun insertValue(button: Button)
    {
        arrowValues[index]=button.text.toString()
        if(index<arrowsPerEnd-1)
            index++
        endScore.text=arrowValues.joinToString(separator = " ")
    }

    fun deleteValue()
    {
        if(arrowValues[index]=="" && index!=0)
            index--
        arrowValues[index]=""
        endScore.text=arrowValues.joinToString(separator = " ")
    }

    fun clearValues()
    {
        for(i in 0..arrowsPerEnd-1)
        {
            arrowValues[i]= ""
        }
        index=0
        endScore.text=arrowValues.joinToString(separator = " ")
    }

}