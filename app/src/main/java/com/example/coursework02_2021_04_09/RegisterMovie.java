package com.example.coursework02_2021_04_09;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterMovie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_movie);

        //pass save button ID to variable
        Button buttonSave = findViewById(R.id.buttonSave);
    }

    public void buttonSaveRegister(View view) {
        //-----Movie Name------
        EditText textBoxMovie = findViewById(R.id.editTextSearch);
        String inputMovie = textBoxMovie.getText().toString();

        //-----Movie year------
        EditText textBoxYear = findViewById(R.id.editTextYear);
        String inputYear = textBoxYear.getText().toString();


        //-----director------
        EditText textBoxDirector = findViewById(R.id.editTextDirector);
        String inputDirector = textBoxDirector.getText().toString();

        //----- list of actors/actresses------
        EditText textBoxActors = findViewById(R.id.editTextActors);
        String inputActors = textBoxActors.getText().toString();

        //-----rating------
        EditText textBoxRating = findViewById(R.id.editTextRating);
        String inputRating = textBoxRating.getText().toString();


        //-----review------
        EditText textBoxReview = findViewById(R.id.editTextReview);
        String inputReview = textBoxReview.getText().toString();
        if(!inputMovie.equals("") && !inputActors.equals("") && !inputDirector.equals("") && !inputYear.equals("")
                && !inputRating.equals("") && !inputReview.equals("")) {
            int intInputYear=Integer.parseInt(inputYear);
            int intInputRating=Integer.parseInt(inputRating);
            SQLiteManager dataBase = new SQLiteManager(this);

            try {
                if (intInputRating >= 1 && intInputRating <= 10 && intInputYear >= 1895) {
                    //add data to database table
                    Boolean checkData = dataBase.addDetails(inputMovie, intInputYear, inputDirector, inputActors, intInputRating, inputReview);
                    if (checkData == true) {
                        Toast.makeText(this, "New Data Inserted.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Failed!", Toast.LENGTH_LONG).show();
                    }

                    //add validations
                } else if ((intInputRating < 1 || intInputRating > 10) && intInputYear < 1895) {
                    Toast.makeText(this, "Failed! Enter Year  greater than 1895 and Rating Range Of 1−10.", Toast.LENGTH_LONG).show();
                    textBoxYear.getText().clear();
                    textBoxRating.getText().clear();
                } else if (intInputYear < 1895) {
                    Toast.makeText(this, "Failed! Enter Year greater than 1895.", Toast.LENGTH_LONG).show();
                    textBoxYear.getText().clear();
                } else if (intInputRating < 1 || intInputRating > 10) {
                    Toast.makeText(this, "Failed! Enter Rating Range Of 1−10.", Toast.LENGTH_LONG).show();
                    textBoxRating.getText().clear();
                } else if (textBoxMovie.equals("null") || textBoxYear == null || textBoxDirector.equals("null") || textBoxActors.equals("null") || textBoxReview.equals("null") || textBoxRating == null) {
                    Toast.makeText(this, "Fill all textBoxes.", Toast.LENGTH_LONG).show();
                } else if (textBoxMovie.equals("") || textBoxDirector.equals("") || textBoxActors.equals("") || textBoxReview.equals("")) {
                    Toast.makeText(this, "Fill all textBoxes.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Failed! Enter Correct Values", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_LONG).show();
        }
    }
}