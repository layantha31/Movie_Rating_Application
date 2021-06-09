package com.example.coursework02_2021_04_09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class EditMovies extends AppCompatActivity {

    ArrayList<String> arrayData;
    ArrayAdapter dataAdapter;

    ListView filmListView;

    SQLiteManager database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movies);

        database = new SQLiteManager(this);
        filmListView = findViewById(R.id.editListView);

        arrayData = new ArrayList<>();

        viewFilmDetailsList();

        //show list and check checkbox and identify selected items with list view
        filmListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String films = filmListView.getItemAtPosition(i).toString();
                Toast.makeText(EditMovies.this,""+films,Toast.LENGTH_SHORT).show();

                //open new intent
                Intent intnt = new Intent(getBaseContext(), DetailsEditPage.class);
                //put selected title in to new intent
                intnt.putExtra("title", films);
                startActivity(intnt);
            }
        });
    }

    private void viewFilmDetailsList(){
        Cursor cursor = database.displayEditData();
        if (cursor.getCount() == 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        } else {
            //get film titles with database
            while (cursor.moveToNext()){
                //add film title to arraylist
                arrayData.add(cursor.getString(0));
                //list of movies sorted in alphabetical order
                Collections.sort(arrayData, String.CASE_INSENSITIVE_ORDER);
            }
            //initialize arrayadapter
            dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayData);
            //set list to array adapter
            filmListView.setAdapter(dataAdapter);
        }
    }
}