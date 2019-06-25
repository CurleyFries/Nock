package edu.jcurley217ucla.nock

import java.time.LocalDate
import java.time.LocalDateTime

class ScoringRound(date: String, division: String, distance: String, targetSize: String, ends: Int, arrowsPerEnd: Int) {
    //TODO: Get current date
    val date = date
    val division = division
    val distance = distance
    val targetSize = targetSize
    val ends = ends
    val arrowsPerEnd = arrowsPerEnd
    var scores: Array<Array<String>>

    init {
        scores = Array(ends){Array(arrowsPerEnd){""}}
        for(i in 0..ends-1)
        {
            scores[i] = arrayOf("X", "X", "X")
        }
//        scores[0]= arrayOf("X", "10", "9")
//        scores[1] = arrayOf("9", "9", "8")
//        scores[2] = arrayOf("8", "8", "7")
//        scores[3]= arrayOf("X", "10", "9")
//        scores[4] = arrayOf("9", "9", "8")
//        scores[5] = arrayOf("8", "8", "7")
//        scores[6]= arrayOf("X", "10", "9")
//        scores[7] = arrayOf("9", "9", "8")
//        scores[8] = arrayOf("8", "8", "7")
//        scores[9] = arrayOf("X","X", "X")
//        scores[10]= arrayOf("X", "10", "9")
//        scores[11] = arrayOf("9", "9", "8")
//        scores[12] = arrayOf("8", "8", "7")
//        scores[13]= arrayOf("X", "10", "9")
//        scores[14] = arrayOf("9", "9", "8")
//        scores[15] = arrayOf("8", "8", "7")
//        scores[16]= arrayOf("X", "10", "9")
//        scores[17] = arrayOf("9", "9", "8")
//        scores[18] = arrayOf("8", "8", "7")
//        scores[19] = arrayOf("X","X", "X")
    }

    fun changeEnd(end: Int, endScores: Array<String>)
    {
        scores[end] = endScores
    }

    fun computeRunningTotal(upToEnd: Int): Int
    {
        var runningTotal: Int = 0
        for(i in 0..upToEnd)
        {
            for(score in scores[i])
            {
                if(score == "X")
                    runningTotal+=10
                else if(score =="" || score == "M")
                    runningTotal+=0
                else
                    runningTotal+=score.toInt()
            }
        }
        return runningTotal
    }

    fun computeEndTotal(end: Int): Int
    {
        var endTotal: Int = 0
        for(score in scores[end])
        {
            if(score == "X")
                endTotal+=10
            else if(score =="" || score == "M")
                endTotal+=0
            else
                endTotal+=score.toInt()
        }
        return endTotal
    }
}