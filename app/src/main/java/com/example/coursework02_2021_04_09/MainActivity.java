package com.example.coursework02_2021_04_09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //connect with main activity XML file
        setContentView(R.layout.activity_main);

        //pass register movie button ID to variable
        Button btRegister = findViewById(R.id.buttonRegister);
        //set action to register movie button
        btRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startDataBasApp();
            }
        });

        //pass display movies button ID to variable
        Button btDisplay = findViewById(R.id.buttonDisplay);
        //set action to display movies button
        btDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openDisplayMovie();
            }
        });

        //pass Favourites button ID to variable
        Button btFavourites = findViewById(R.id.buttonFavourites);
        //set action to Favourites button
        btFavourites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openFavourites();
            }
        });

        //pass edit movies button ID to variable
        Button btEdit = findViewById(R.id.buttonEdit);
        //set action to edit movies button
        btEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openEditMovies();
            }
        });

        //pass edit movies button ID to variable
        Button btSearch = findViewById(R.id.buttonSearch);
        //set action to edit movies button
        btSearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openSearch();
            }
        });

        //pass Ratings button ID to variable
        Button btRatings = findViewById(R.id.buttonRatings);
        //set action to Ratings button
        btRatings.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                openRatings();
            }
        });
    }

//    public void openRegisterMovie(){
//        //create a new object to start new activity
//        Intent intent = new Intent(this, RegisterMovie.class);
//        //pass object to startActivity method
//        startActivity(intent);
//    }

    public void startDataBasApp(){
        new SQLiteManager(this);
        startActivity(new Intent(this,RegisterMovie.class));
    }

    public void openDisplayMovie(){
        new SQLiteManager(this);
        //pass object to startActivity method
        startActivity(new Intent(this, DisplayMovie.class));
    }

    public void openFavourites(){
        new SQLiteManager(this);
        //pass object to startActivity method
        startActivity(new Intent(this, Favourites.class));
    }

    public void openEditMovies(){
        new SQLiteManager(this);
        //pass object to startActivity method
        startActivity(new Intent(this, EditMovies.class));
    }

    public void openSearch(){
        new SQLiteManager(this);
        //pass object to startActivity method
        startActivity(new Intent(this, Search.class));
    }

    public void openRatings(){
        //create a new object to start new activity
        Intent intent = new Intent(this, NameActivityPage.class);
        //pass object to startActivity method
        startActivity(intent);
    }
}