package edu.jcurley217ucla.nock

import java.io.Serializable

class Preset(division: String, distance: String, targetSize: String, ends: Int, arrowsPerEnd: Int): Serializable {
    val division = division
    val distance = distance
    val targetSize = targetSize
    val ends = ends
    val arrowsPerEnd = arrowsPerEnd
}