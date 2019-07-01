package edu.jcurley217ucla.nock

import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime


fun convertToArray(text: String, ends: Int, arrowsPerEnd: Int) : Array<Array<String>>
{
    var finalArray = Array(ends){Array(arrowsPerEnd){""}}
    var indexRow = 0
    var indexCol = 0
    for(i in 0..text.length-1)
    {
        if(indexCol==arrowsPerEnd)
        {
            indexCol=0
            indexRow++
        }
        if(text.get(i) == 'T')
            finalArray[indexRow][indexCol]= "10"
        else
            finalArray[indexRow][indexCol]= text.get(i).toString()

        indexCol++
    }
//   for (i in 0..ends-1)
//   {
//       for(j in 0..arrowsPerEnd-1)
//       {
//           if(text.get(i*j+j) == 'T')
//               finalArray[i][j]= "10"
//           else
//               finalArray[i][j]= text.get(i*j+j).toString()
//       }
//   }
    return finalArray
}

fun convertFromArray(arrayOfScores: Array<Array<String>>, ends: Int, arrowsPerEnd: Int) : String
{
    var finalText = ""
    for (i in 0..ends-1)
    {
        for(j in 0..arrowsPerEnd-1)
        {
            val score = arrayOfScores[i][j]
            if(score=="10")
                finalText+="T"
            else if(score == "")
                finalText+="M"
            else
                finalText+=score
        }
    }
    return finalText
}

class ScoringRound(): Serializable {
    //TODO: Get current date
    var id : Int = -1
    var date : String = ""
    var division : String = ""
    var distance : String = ""
    var targetSize : String = ""
    var ends : Int = 1
    var arrowsPerEnd : Int = 1
    lateinit var scores: Array<Array<String>>
    var notes : String = ""


    constructor(date: String, division: String, distance: String, targetSize: String, ends: Int, arrowsPerEnd: Int): this()
    {
        this.date = date
        this.division = division
        this.distance = distance
        this.targetSize = targetSize
        this.ends = ends
        this.arrowsPerEnd = arrowsPerEnd
        this.scores = Array(ends){Array(arrowsPerEnd){""}}
        for(i in 0..ends-1)
        {
            scores[i] = Array(arrowsPerEnd){""}
        }
    }


    constructor(id: Int, date: String, division: String, distance: String, targetSize: String, ends: Int, arrowsPerEnd: Int, prevScores : Array<Array<String>>, notes: String) : this() {
        this.id = id
        this.date = date
        this.division = division
        this.distance = distance
        this.targetSize = targetSize
        this.ends = ends
        this.arrowsPerEnd = arrowsPerEnd
        scores = prevScores
        this.notes = notes
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