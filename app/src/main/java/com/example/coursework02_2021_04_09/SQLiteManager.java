package com.example.coursework02_2021_04_09;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteManager extends SQLiteOpenHelper {
    //create database
    public SQLiteManager(Context context) {
        super(context, "FilmManager.db", null, 4);
    }

    //create new table
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table Films(title TEXT primary key, year TEXT, director TEXT, actors TEXT, rating TEXT, review TEXT)");
    }

    //add new favourite column to table
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i1 > i) {
            db.execSQL("ALTER TABLE Films ADD COLUMN favourite TEXT");
        }
    }

    //add data to database with register movie page
    public Boolean addDetails(String title, int year, String director, String actors, int rating, String review){
        SQLiteDatabase dataBase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put("title",title);
        contentValues.put("year",year);
        contentValues.put("director",director);
        contentValues.put("actors",actors);
        contentValues.put("rating",rating);
        contentValues.put("review",review);

        long result=dataBase.insert("Films",null  ,contentValues);

        if(result==-1){
            return false;
        } else{
            return true;
        }
    }

    //pass data to display in display page
    public Cursor displayData() {
        SQLiteDatabase dataBase = this.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("Select * from Films ", null);
        return cursor;
    }

    //add data to favourites in display page
    public Boolean addFavourites(String title){
        SQLiteDatabase dataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title",title);
        contentValues.put("favourite",true);

        long result=dataBase.update("Films",contentValues,"title=?"  ,new String[]{title});

        if(result==-1){
            return false;
        } else{
            return true;
        }
    }

    //pass data display in favourite page
    public Cursor favouriteFilms() {
        SQLiteDatabase dataBase = this.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("Select title from Films where favourite = 1", null);
        return cursor;
    }

    //update favourites with favourite page data
    public Boolean changeFavouriteFilms(String title) {
        SQLiteDatabase dataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title",title);
        contentValues.put("favourite","null");

        long result=dataBase.update("Films",contentValues,"title=?"  ,new String[]{title});

        if(result==-1){
            return false;
        } else{
            return true;
        }
    }

    //pass data to display list view in edit page
    public Cursor displayEditData() {
        SQLiteDatabase dataBase = this.getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("Select * from Films", null);
        return cursor;
    }

    //pass data to search items
    public Cursor searchData(){
        SQLiteDatabase dataBase = this.getReadableDatabase();

        Cursor cursor = null;
        if(dataBase != null){
            cursor = dataBase.rawQuery("Select * from Films", null);
        }
        return cursor;
    }

    //pass data to display in edit page
    public Cursor displayEditPageData(){
        SQLiteDatabase dataBase = this.getReadableDatabase();

        Cursor cursor = null;
        if(dataBase != null){
            cursor = dataBase.rawQuery("Select * from Films", null);
        }
        return cursor;
    }

    //updated date with user edited in edit page
    public Boolean editEnterData(String title, int year, String director, String actors, int rating, String review, String favourite) {
        SQLiteDatabase dataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title",title);
        contentValues.put("year",year);
        contentValues.put("director",director);
        contentValues.put("actors",actors);
        contentValues.put("rating",rating);
        contentValues.put("review",review);
        contentValues.put("favourite",favourite);

        long result=dataBase.update("Films",contentValues,"title=?"  ,new String[]{title});

        if(result==-1){
            return false;
        } else{
            return true;
        }
    }
}
