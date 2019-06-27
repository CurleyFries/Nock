package edu.jcurley217ucla.nock

import java.io.Serializable

class Preset(): Serializable {

     var id: Int = -1
     var division : String = ""
     var distance : String = ""
     var targetSize : String = ""
     var ends : Int = 1
     var arrowsPerEnd : Int = 1

    constructor(division: String, distance: String, targetSize: String, ends: Int, arrowsPerEnd: Int): this()
    {
        this.division = division
        this.distance = distance
        this.targetSize = targetSize
        this.ends = ends
        this.arrowsPerEnd = arrowsPerEnd
    }
    constructor(id : Int, division: String, distance: String, targetSize: String, ends: Int, arrowsPerEnd: Int): this()
    {
        this.id=id
        this.division = division
        this.distance = distance
        this.targetSize = targetSize
        this.ends = ends
        this.arrowsPerEnd = arrowsPerEnd
    }
}