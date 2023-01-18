package com.mlk.day12testing

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBase(context : Context):
    SQLiteOpenHelper(context , DATABASE_NAME , null , DATABASE_VERSION){

    companion object{
        //DataBase Information
        const val DATABASE_NAME = "user_database"
        const val DATABASE_VERSION = 1
        //Table Information
        const val TABLE_NAME = "games_table"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_GENRE = "genre"
    }

    lateinit var db :SQLiteDatabase

    override fun onCreate(p0: SQLiteDatabase) {
        p0.execSQL("CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY, " +
                "$COLUMN_TITLE VARCHAR," +
                "$COLUMN_GENRE VARCHAR)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
//        TODO("Not yet implemented")
    }

    fun addGame(title:String , genre:String){
        db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE,title)
        values.put(COLUMN_GENRE,genre)
        db.insert(TABLE_NAME,null,values)
    }

    fun getAll(): ArrayList<GameModel> {
        db = readableDatabase
        val games = arrayListOf<GameModel>()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME",null)
        if (cursor.count == 0)
            return games
        while (cursor.moveToNext()){
            val temp = GameModel(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)))
            games.add(temp)
        }
        return games
    }
    fun getGameByTitle(title: String): GameModel {
        db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_TITLE = '$title'",null)
        if (!cursor.moveToFirst())
            return GameModel(-1,"","")
        return GameModel(
            cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
            cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
            cursor.getString(cursor.getColumnIndex(COLUMN_GENRE)))
    }

    fun deleteGameByTitle(title: String): Boolean {
        db = writableDatabase

        if (db.delete(TABLE_NAME,"$COLUMN_TITLE = '$title'",null) == 0)
            return false
        return true
    }

    fun editGameTitle(oldTitle:String , newTitle:String): Boolean {
        db =writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE,newTitle)
        if (db.update(TABLE_NAME , values,"$COLUMN_TITLE = '$oldTitle'",null) == 0)
            return false
        return true
    }
}