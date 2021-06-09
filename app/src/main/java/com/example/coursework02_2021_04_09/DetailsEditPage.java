package com.example.coursework02_2021_04_09;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailsEditPage extends AppCompatActivity {
    ArrayList<String> arrayTitle, arrayYear, arrayDirector, arrayActors, arrayRating, arrayReview, arrayFavourite;
    ArrayList<String> arrayEditedData;
    String passedTitle;
    String title,year,director,actors,rating,review,favourite;
    EditText textBoxMovie,textBoxYear,textBoxDirector,textBoxActors,textBoxReview;
    RatingBar ratingBarEdit;
    CheckBox checkFav;

    SQLiteManager database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_details);

        checkFav = findViewById(R.id.checkBox);

        //get title which user clicked in previous page
        passedTitle = getIntent().getStringExtra("title");

        database = new SQLiteManager(DetailsEditPage.this);

        //initialize arrayListS
        arrayTitle = new ArrayList<>();
        arrayYear = new ArrayList<>();
        arrayDirector = new ArrayList<>();
        arrayActors = new ArrayList<>();
        arrayRating = new ArrayList<>();
        arrayReview = new ArrayList<>();
        arrayFavourite = new ArrayList<>();

        arrayEditedData = new ArrayList<>();

        displayFilmDetails();

        //identify correct details in movie which user selected
        if(arrayTitle.contains(passedTitle)){
            int index = arrayTitle.indexOf(passedTitle);
            title = arrayTitle.get(index);
            year = arrayYear.get(index);
            director = arrayDirector.get(index);
            actors = arrayActors.get(index);
            rating = arrayRating.get(index);
            review = arrayReview.get(index);
            favourite = arrayFavourite.get(index);
        }

        //check that movie is favourite or not favourite
        if (favourite == null || favourite.equals("null")){
            checkFav.setChecked(false);
        } else if (favourite.equals("1")){
            checkFav.setChecked(true);
        } else{
            checkFav.setChecked(false);
        }
        //-----Movie Name------
        textBoxMovie = findViewById(R.id.editTextTitleEdit);
        textBoxMovie.setText(title);

        //-----Movie year------
        textBoxYear = findViewById(R.id.editTextYearEdit);
        textBoxYear.setText(year);

        //-----director------
        textBoxDirector = findViewById(R.id.editTextDirectorEdit);
        textBoxDirector.setText(director);

        //----- list of actors/actresses------
        textBoxActors = findViewById(R.id.editTextActorsEdit);
        textBoxActors.setText(actors);

        //-----rating------
        ratingBarEdit = findViewById(R.id.ratingBarEdit);
        ratingBarEdit.setRating(Float.parseFloat(rating));

        //-----review------
        textBoxReview = findViewById(R.id.editTextReviewEdit);
        textBoxReview.setText(review);
    }

    public void buttonEditRegister(View view){

        //get data from text boxes
        String inputMovie = textBoxMovie.getText().toString();

        String inputYear = textBoxYear.getText().toString();
        int intYear=Integer.parseInt(inputYear);

        String inputDirector = textBoxDirector.getText().toString();

        String inputActors = textBoxActors.getText().toString();

        String inputReview = textBoxReview.getText().toString();

        //get data from rating bar
        float getRating = ratingBarEdit.getRating();
        int ratingCount = (int) getRating;

        //get data from check box
        String inputFavResult = null;
        if(checkFav.isChecked()){
            inputFavResult = "1";
        }

        //update data base
        Boolean checkData = database.editEnterData(inputMovie,intYear,inputDirector,inputActors,ratingCount,inputReview,inputFavResult);
        if (checkData==true){
            Toast.makeText(this,"New Data Inserted.",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(this,"Failed.",Toast.LENGTH_LONG).show();
        }
    }

    void displayFilmDetails(){
        Cursor cursor = database.displayEditPageData();
        if(cursor.getCount()== 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        } else {
            //get data from data base and add to arrays
            while (cursor.moveToNext()){
                arrayTitle.add(cursor.getString(0));
                arrayYear.add(cursor.getString(1));
                arrayDirector.add(cursor.getString(2));
                arrayActors.add(cursor.getString(3));
                arrayRating.add(cursor.getString(4));
                arrayReview.add(cursor.getString(5));
                arrayFavourite.add(cursor.getString(6));
            }
        }
    }
}