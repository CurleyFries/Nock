package edu.jcurley217ucla.nock

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import android.util.Log
import java.text.DecimalFormat


class DatabaseHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_SCORINGROUND_ENTRIES)
        db.execSQL(SQL_CREATE_PRESET_ENTRIES)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_SCORINGROUND_ENTRIES)
        db.execSQL(SQL_DELETE_PRESET_ENTRIES)
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }
    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "NockReader.db"

        private const val SQL_CREATE_SCORINGROUND_ENTRIES =
                "CREATE TABLE ${NockContract.ScoringRoundEntry.TABLE_NAME} (" +
                        "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_DATE} TEXT," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_TOTALSCORE} INTEGER," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_AVGARROW} INTEGER," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_DIVISION} TEXT," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_DISTANCE} TEXT," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_TARGETSIZE} TEXT," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_NUMENDS} INTEGER," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_ARROWSPEREND} INTEGER," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_ARROWVALUES} TEXT," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_NOTES} TEXT)"
        private const val SQL_CREATE_PRESET_ENTRIES =
                "CREATE TABLE ${NockContract.PresetEntry.TABLE_NAME} (" +
                        "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_DIVISION} TEXT," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_DISTANCE} TEXT," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_TARGETSIZE} TEXT," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_NUMENDS} INTEGER," +
                        "${NockContract.ScoringRoundEntry.COLUMN_NAME_ARROWSPEREND} INTEGER)"


        private const val SQL_DELETE_SCORINGROUND_ENTRIES = "DROP TABLE IF EXISTS ${NockContract.ScoringRoundEntry.TABLE_NAME}"
        private const val SQL_DELETE_PRESET_ENTRIES = "DROP TABLE IF EXISTS ${NockContract.PresetEntry.TABLE_NAME}"
    }

    fun insertScoringRound(sR: ScoringRound): Boolean
    {

        val db = writableDatabase

        val values = ContentValues()
        //values.put(NockContract.ScoringRoundEntry.COLUMN_NAME_ID, 1)
        values.put(NockContract.ScoringRoundEntry.COLUMN_NAME_DATE, sR.date)
//        Log.i("Insert Scores", "scores are " + convertFromArray(sR.scores, sR.ends, sR.arrowsPerEnd))
        //TODO: Kinda messy since computation done in two places, should be cleaned up
        val totalScore = sR.computeRunningTotal(sR.ends-1)
        var computedScore = (totalScore/(sR.ends.toDouble()*sR.arrowsPerEnd))
        val df = DecimalFormat("#.00")
        val finalScore= df.format(computedScore)
        values.put(NockContract.ScoringRoundEntry.COLUMN_NAME_TOTALSCORE, sR.computeRunningTotal(sR.ends-1))
        values.put(NockContract.ScoringRoundEntry.COLUMN_NAME_AVGARROW, finalScore)
        values.put(NockContract.ScoringRoundEntry.COLUMN_NAME_DIVISION, sR.division)
        values.put(NockContract.ScoringRoundEntry.COLUMN_NAME_DISTANCE, sR.distance)
        values.put(NockContract.ScoringRoundEntry.COLUMN_NAME_TARGETSIZE, sR.targetSize)
        values.put(NockContract.ScoringRoundEntry.COLUMN_NAME_NUMENDS, sR.ends)
        values.put(NockContract.ScoringRoundEntry.COLUMN_NAME_ARROWSPEREND, sR.arrowsPerEnd)
        values.put(NockContract.ScoringRoundEntry.COLUMN_NAME_ARROWVALUES, convertFromArray(sR.scores,sR.ends, sR.arrowsPerEnd))
        values.put(NockContract.ScoringRoundEntry.COLUMN_NAME_NOTES, sR.notes)

        val newRowID = db.insert(NockContract.ScoringRoundEntry.TABLE_NAME, null, values)

        return true

    }

    fun deleteScoringRound(id: Int): Boolean
    {
        val db = writableDatabase

        val selection = NockContract.ScoringRoundEntry.COLUMN_NAME_ID + " LIKE ?"
        val selectionArgs = arrayOf(id.toString())
        db.delete(NockContract.ScoringRoundEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readScoringRoundList() : ArrayList<ScoringRound>
    {
        val scoringRounds = ArrayList<ScoringRound>()
        val db = writableDatabase
        var cursor: Cursor? = null


        try {
            cursor = db.rawQuery("select * from " + NockContract.ScoringRoundEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_SCORINGROUND_ENTRIES)
            return ArrayList()
        }

        var id: Int
        var date : String
        var division : String
        var distance : String
        var targetSize : String
        var ends : Int
        var arrowsPerEnd : Int
        var scores: Array<Array<String>>
        var notes: String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getInt(cursor.getColumnIndex(NockContract.ScoringRoundEntry.COLUMN_NAME_ID))
                date = cursor.getString(cursor.getColumnIndex(NockContract.ScoringRoundEntry.COLUMN_NAME_DATE))
                division = cursor.getString(cursor.getColumnIndex(NockContract.ScoringRoundEntry.COLUMN_NAME_DIVISION))
                distance = cursor.getString(cursor.getColumnIndex(NockContract.ScoringRoundEntry.COLUMN_NAME_DISTANCE))
                targetSize = cursor.getString(cursor.getColumnIndex(NockContract.ScoringRoundEntry.COLUMN_NAME_TARGETSIZE))
                ends = cursor.getInt(cursor.getColumnIndex(NockContract.ScoringRoundEntry.COLUMN_NAME_NUMENDS))
                arrowsPerEnd = cursor.getInt(cursor.getColumnIndex(NockContract.ScoringRoundEntry.COLUMN_NAME_ARROWSPEREND))
                scores = convertToArray(cursor.getString(cursor.getColumnIndex(NockContract.ScoringRoundEntry.COLUMN_NAME_ARROWVALUES)), ends, arrowsPerEnd)
                notes = cursor.getString(cursor.getColumnIndex(NockContract.ScoringRoundEntry.COLUMN_NAME_NOTES))

                scoringRounds.add(ScoringRound(id, date, division, distance, targetSize, ends, arrowsPerEnd, scores, notes))
                cursor.moveToNext()
            }
        }
        return scoringRounds
    }
//
//    fun createSearchQuery() : String
//    {
//        var query = "SELECT * from " + NockContract.ScoringRoundEntry.TABLE_NAME
//        if(filter)
//        {
//
//        }
//        if(sort)
//        {
//            query+= "ORDER BY "
//            //TODO: Primary Sort Key First
//            if(dateSort)
//            {
//                query+= NockContract.ScoringRoundEntry.COLUMN_NAME_DATE + " "
//                if(dateOrder)
//                    query += "ASC"
//                else
//                    query += "DESC"
//                if(last!=DATE_SORT)
//                    query+= ","
//            }
//            if()
//            {
//                query+= NockContract.ScoringRoundEntry.COLUMN_NAME_DATE + " "
//                if(dateOrder)
//                    query += "ASC"
//                else
//                    query += "DESC"
//                if(last!=DATE_SORT)
//                    query+= ","
//            }
//            if(date)
//            {
//                query+= NockContract.ScoringRoundEntry.COLUMN_NAME_DATE + " "
//                if(dateOrder)
//                    query += "ASC"
//                else
//                    query += "DESC"
//                if(last!=DATE_SORT)
//                    query+= ","
//            }
//        }
//    }

    fun insertPreset(preset: Preset): Boolean
    {
        val db = writableDatabase


        val values = ContentValues()
        values.put(NockContract.PresetEntry.COLUMN_NAME_DIVISION, preset.division)
        values.put(NockContract.PresetEntry.COLUMN_NAME_DISTANCE, preset.distance)
        values.put(NockContract.PresetEntry.COLUMN_NAME_TARGETSIZE, preset.targetSize)
        values.put(NockContract.PresetEntry.COLUMN_NAME_NUMENDS, preset.ends)
        values.put(NockContract.PresetEntry.COLUMN_NAME_ARROWSPEREND, preset.arrowsPerEnd)

        val newRowID = db.insert(NockContract.PresetEntry.TABLE_NAME, null, values)

        return true

    }

    fun deletePreset(id: Int): Boolean
    {
        val db = writableDatabase

        val selection = NockContract.PresetEntry.COLUMN_NAME_ID + " LIKE ?"
        val selectionArgs = arrayOf(id.toString())
        db.delete(NockContract.PresetEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readPresetList() : ArrayList<Preset>
    {
        val presetList = ArrayList<Preset>()
        val db = writableDatabase
        var cursor: Cursor? = null


        try {
            cursor = db.rawQuery("select * from " + NockContract.PresetEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_PRESET_ENTRIES)
            return ArrayList()
        }

        var id : Int
        var division : String
        var distance : String
        var targetSize : String
        var ends : Int
        var arrowsPerEnd : Int
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                id = cursor.getInt(cursor.getColumnIndex(NockContract.PresetEntry.COLUMN_NAME_ID))
                division = cursor.getString(cursor.getColumnIndex(NockContract.PresetEntry.COLUMN_NAME_DIVISION))
                distance = cursor.getString(cursor.getColumnIndex(NockContract.PresetEntry.COLUMN_NAME_DISTANCE))
                targetSize = cursor.getString(cursor.getColumnIndex(NockContract.PresetEntry.COLUMN_NAME_TARGETSIZE))
                ends = cursor.getInt(cursor.getColumnIndex(NockContract.PresetEntry.COLUMN_NAME_NUMENDS))
                arrowsPerEnd = cursor.getInt(cursor.getColumnIndex(NockContract.PresetEntry.COLUMN_NAME_ARROWSPEREND))

                presetList.add(Preset(id, division, distance, targetSize, ends, arrowsPerEnd))
                cursor.moveToNext()
            }
        }
        return presetList
    }

}