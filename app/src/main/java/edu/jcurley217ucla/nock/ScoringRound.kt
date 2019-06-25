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
            scores[i] = Array(arrowsPerEnd){""}
        }
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