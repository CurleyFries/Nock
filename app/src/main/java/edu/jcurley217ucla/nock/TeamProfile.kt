package edu.jcurley217ucla.nock

import java.io.Serializable

class TeamProfile(): Serializable {

    var teamLogo: String = ""
    var teamName : String = ""
    var teamDesc : String = ""

    constructor(teamLogo: String, teamName: String, teamDesc: String): this()
    {
        this.teamLogo = teamLogo
        this.teamName = teamName
        this.teamDesc = teamDesc
    }
}