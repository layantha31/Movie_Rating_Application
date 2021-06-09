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

public class DisplayMovie extends AppCompatActivity {

    ArrayList<String> arrayData, favouriteItems;
    ArrayAdapter dataAdapter;

    ListView filmListView;

    SQLiteManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_movie);

        Button addFavourites = findViewById(R.id.buttonAddFavourites);

        database = new SQLiteManager(this);
        filmListView = findViewById(R.id.userList);
        filmListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        arrayData = new ArrayList<>();
        favouriteItems = new ArrayList<>();

        viewFilmDetails();

        //check check box and identify selected items with list view
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

    private void viewFilmDetails(){
        Cursor cursor = database.displayData();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        } else {
            //get title from database and add to arraylist
            while (cursor.moveToNext()){
                arrayData.add(cursor.getString(0));
                //list of movies sorted in alphabetical order
                Collections.sort(arrayData, String.CASE_INSENSITIVE_ORDER);
            }
            //initialize arrayadapter
            dataAdapter = new ArrayAdapter(this, R.layout.check_layout, R.id.textCheck,arrayData);
            //set list to array adapter
            filmListView.setAdapter(dataAdapter);
        }
    }

    public void addToFavourites(View view){
        SQLiteManager dataBase02 = new SQLiteManager(this);

        for (String fav :favouriteItems){
            //update favourite coloum with user data
            Boolean checkData = dataBase02.addFavourites(fav);
            if (checkData==true){
                Toast.makeText(this,"New Data Inserted.",Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(this,"Failed.",Toast.LENGTH_LONG).show();
            }
        }
    }
}