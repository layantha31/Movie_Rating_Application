package com.example.coursework02_2021_04_09;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class Favourites extends AppCompatActivity {
    ArrayList<String> favouriteItems,favouritesInDatabase;
    ArrayAdapter dataAdapter;

    ListView filmListView;

    SQLiteManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        Button addFavourites = findViewById(R.id.buttonSaveFavourites);

        database = new SQLiteManager(this);
        filmListView = findViewById(R.id.userListFavourites);
        filmListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        favouriteItems = new ArrayList<>();
        favouritesInDatabase = new ArrayList<>();

        viewFavouriteFilms();

        //check check box and identify favourite items with list view
        filmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String favourites = ((TextView)view).getText().toString();
                if (favouriteItems.contains(favourites)){
                    favouriteItems.remove(favourites);
                } else {
                    favouriteItems.add(favourites);
                }
            }
        });
    }


    public void viewFavouriteFilms(){
        Cursor cursor = database.favouriteFilms();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        } else {
            //get title to arraylist from database
            while (cursor.moveToNext()){
                //identify correct titles
                favouritesInDatabase.add(cursor.getString(0));
                //list of movies sorted in alphabetical order
                Collections.sort(favouritesInDatabase, String.CASE_INSENSITIVE_ORDER);
            }

            //initialize arrayadapter
            dataAdapter = new ArrayAdapter(this, R.layout.favourite_save_layout, R.id.textCheckSave,favouritesInDatabase);
            //set list to array adapter
            filmListView.setAdapter(dataAdapter);

            //to default all items are selected in favourite page
            for (int i=0; i < filmListView.getAdapter().getCount(); i++) {
                filmListView.setItemChecked(i, true);
            }
        }
    }

    public void saveFavourites(View view){
        SQLiteManager dataBase02 = new SQLiteManager(this);

        for (String fav :favouriteItems){
            //update database with favourite items
            Boolean checkData = dataBase02.changeFavouriteFilms(fav);
            if (checkData==true){
                Toast.makeText(this,"Data Updated.",Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(this,"Data Not Updated.",Toast.LENGTH_LONG).show();
            }
        }
    }
}