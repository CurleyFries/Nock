package edu.jcurley217ucla.nock

import android.provider.BaseColumns

class NockContract {
    object ScoringRoundEntry: BaseColumns {
        const val TABLE_NAME = "ScoringRounds"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_NAME_TOTALSCORE = "totalScore"
        const val COLUMN_NAME_AVGARROW = "avgArrow"
        const val COLUMN_NAME_DIVISION = "division"
        const val COLUMN_NAME_DISTANCE = "distance"
        const val COLUMN_NAME_TARGETSIZE = "targetSize"
        const val COLUMN_NAME_NUMENDS = "numEnds"
        const val COLUMN_NAME_ARROWSPEREND = "arrowsPerEnd"
        const val COLUMN_NAME_ARROWVALUES = "arrowValues"
        const val COLUMN_NAME_NOTES = "notes"
    }

    object PresetEntry: BaseColumns {
        const val TABLE_NAME = "Presets"
        const val COLUMN_NAME_ID = BaseColumns._ID
        const val COLUMN_NAME_DIVISION = "division"
        const val COLUMN_NAME_DISTANCE = "distance"
        const val COLUMN_NAME_TARGETSIZE = "targetSize"
        const val COLUMN_NAME_NUMENDS = "numEnds"
        const val COLUMN_NAME_ARROWSPEREND = "arrowsPerEnd"
    }
}