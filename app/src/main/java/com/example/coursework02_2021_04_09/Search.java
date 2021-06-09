package com.example.coursework02_2021_04_09;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    ArrayList<String> arrayTitle, arrayYear, arrayDirector, arrayActors, arrayRating, arrayReview;
    ArrayList<String> arrayResult;
    ArrayAdapter<String> listAdapter;
    SQLiteManager database;
    EditText searchName;
    ListView searchListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchName = findViewById(R.id.editTextSearch);
        Button buttonSearch = findViewById(R.id.buttonSearch);

        searchListView = findViewById(R.id.listViewSearch);

        database = new SQLiteManager(Search.this);

        //initialize arraylists
        arrayTitle = new ArrayList<>();
        arrayYear = new ArrayList<>();
        arrayDirector = new ArrayList<>();
        arrayActors = new ArrayList<>();
        arrayRating = new ArrayList<>();
        arrayReview = new ArrayList<>();

        arrayResult = new ArrayList<>();

        searchFilm();
    }

    public void searchFilm(View view) {
        boolean checkAlertBox = false;
        //create 3 new arraylist to store user input
        ArrayList<String> filmNames = new ArrayList<>();
        ArrayList<String> filmDirectors = new ArrayList<>();
        ArrayList<String> filmActors = new ArrayList<>();
        arrayResult.clear();

        //get user input with textbox and convert to lowercase
        String userInputString =  searchName.getText().toString().toLowerCase();

        StringBuffer buffer = new StringBuffer();

        //convert arrayTitle to lowercase and check user input are contains arraylist
        for(int i=0;i<arrayTitle.size();i++) {
            arrayTitle.set(i, arrayTitle.get(i).toLowerCase());
            String str = arrayTitle.get(i);
            if (str.contains(userInputString)){
                filmNames.add(arrayTitle.get(i));
            }
        }
        //check arraylist which store user input string
        for(String filmName : filmNames ){
            if(arrayTitle.contains(filmName)){
                int index = arrayTitle.indexOf(filmName);
                String details = "Title : "+arrayTitle.get(index)+"  Year : "+arrayYear.get(index)+"  Director : "+arrayDirector.get(index)+"  Actors : "+arrayActors.get(index)+"  Rating : "+arrayRating.get(index)+"  Review : "+arrayReview.get(index)+"\n\n";
                buffer.append(details);
                arrayResult.add(arrayTitle.get(index));
                checkAlertBox = true;
            }
        }

        //convert arrayDirector to lowercase and check user input are contains arraylist
        for(int i=0;i<arrayDirector.size();i++) {
            arrayDirector.set(i, arrayDirector.get(i).toLowerCase());
            String str = arrayDirector.get(i);
            if (str.contains(userInputString)){
                filmDirectors.add(arrayDirector.get(i));
            }
        }
        //check arraylist which store user input string
        for (String filmDirector : filmDirectors) {
            if (arrayDirector.contains(filmDirector)) {
                int index = arrayDirector.indexOf(filmDirector);
                String details = "Title : " + arrayTitle.get(index) + "  Year : " + arrayYear.get(index) + "  Director : " + arrayDirector.get(index) + "  Actors : " + arrayActors.get(index) + "  Rating : " + arrayRating.get(index) + "  Review : " + arrayReview.get(index) + "\n\n";
                buffer.append(details);
                arrayResult.add(arrayTitle.get(index));
                checkAlertBox = true;
            }
        }

        //convert arrayActors to lowercase and check user input are contains arraylist
        for(int i=0;i<arrayActors.size();i++) {
            arrayActors.set(i, arrayActors.get(i).toLowerCase());
            String str = arrayActors.get(i);
            if (str.contains(userInputString)){
                filmActors.add(arrayActors.get(i));
            }
        }
        //check arraylist which store user input string
        for (String filmActor : filmActors) {
            if (arrayActors.contains(filmActor)) {
                int index = arrayActors.indexOf(filmActor);
                String details = "Title : " + arrayTitle.get(index) + "  Year : " + arrayYear.get(index) + "  Director : " + arrayDirector.get(index) + "  Actors : " + arrayActors.get(index) + "  Rating : " + arrayRating.get(index) + "  Review : " + arrayReview.get(index) + "\n\n";
                buffer.append(details);
                arrayResult.add(arrayTitle.get(index));
                checkAlertBox = true;
            }
        }

        //add search results to arrayadapter and show it
        listAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayResult);
        searchListView.setAdapter(listAdapter);

        //add search results to alert box and show it
        if (checkAlertBox==true) {
            AlertDialog.Builder dialgBox = new AlertDialog.Builder(Search.this);
            dialgBox.setCancelable(true);
            dialgBox.setTitle("Search Result");
            dialgBox.setPositiveButton("OK", null);
            dialgBox.setMessage(buffer.toString());
            dialgBox.show();
        } else if (checkAlertBox==false) {
            AlertDialog.Builder dialgBox = new AlertDialog.Builder(Search.this);
            dialgBox.setCancelable(true);
            dialgBox.setTitle("Search Result");
            dialgBox.setPositiveButton("OK", null);
            dialgBox.setMessage("Please Enter Correct Information");
            dialgBox.show();
        }
    }

    void searchFilm(){
        Cursor cursor = database.searchData();
        if(cursor.getCount()== 0){
            Toast.makeText(this,"No data",Toast.LENGTH_SHORT).show();
        } else {
            //update favourite coloum with user data
            while (cursor.moveToNext()){
                arrayTitle.add(cursor.getString(0));
                arrayYear.add(cursor.getString(1));
                arrayDirector.add(cursor.getString(2));
                arrayActors.add(cursor.getString(3));
                arrayRating.add(cursor.getString(4));
                arrayReview.add(cursor.getString(5));
            }
        }
    }
}