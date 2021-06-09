package com.example.coursework02_2021_04_09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NameActivityPage extends AppCompatActivity {
    TextView textView;
    TextView textView1;
    EditText editText;
    EditText editText1;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_page);

        textView = findViewById(R.id.tv);
        editText = findViewById(R.id.edt);
        imageView = findViewById(R.id.imgview);
        textView1 = findViewById(R.id.tv2);
        editText1 = findViewById(R.id.edt2);
    }

    public void getNames(View view) {
        String movieNameSearch = editText.getText().toString();

        new Thread(new MovieNames(movieNameSearch)).start();
    }

    public void displayImage(View view) {
        String movieNameSearch = editText1.getText().toString();
        Intent intentNew = new Intent(this, ImageActivityPage.class);
        intentNew.putExtra("name", movieNameSearch);
        startActivity(intentNew);
    }

    class MovieNames implements Runnable {
        String movieNameSearch;

        MovieNames(String movNames) {
            movieNameSearch = movNames;
        }

        @Override
        public void run() {
            StringBuilder builder = new StringBuilder("");  // contains all json
            StringBuilder builder2 = new StringBuilder("");  // contains all drink names

            try {
                // make the connection and receive the input stream
                URL searchURL = new URL("https://imdb-api.com/en/API/SearchMovie/k_ncr5jmia/" + movieNameSearch.trim());
                HttpURLConnection openConnection = (HttpURLConnection) searchURL.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(openConnection.getInputStream()));
                // read all lines info in a StringBuilder
                String row;
                while ((row = reader.readLine()) != null) {
                    builder.append(row);
                }

                // do the JSON parsing
                JSONObject jsonObject = new JSONObject(builder.toString());
                JSONArray objectJSONArray = jsonObject.getJSONArray("results");

                // find the movie names entries and put it in builder2
                for (int i = 0; i < objectJSONArray.length(); i++) {
                    JSONObject jsonMovie = objectJSONArray.getJSONObject(i);
                    String movieString = jsonMovie.getString("title");
                    builder2.append(movieString + "\n");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }

//            } catch (MalformedURLException ex) {
//                ex.printStackTrace();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            } catch (JSONException ex) {
//                ex.printStackTrace();
//            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(builder2.toString());
                }
            });
        }
    }
}