package com.example.coursework02_2021_04_09;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageActivityPage extends AppCompatActivity {
    ImageView apiImageView;
    String urlPicture;
    Bitmap movieImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_page);

        apiImageView = findViewById(R.id.imgview);

        Intent in =  getIntent();
        String movieName = in.getStringExtra("name");

        new Thread(new MovieRating(movieName)).start();
    }

    class MovieRating implements Runnable {
        String reqMovie;

        MovieRating(String movName) {
            reqMovie = movName;
        }

        @Override
        public void run() {
            StringBuilder stringBuilder = new StringBuilder("");
            StringBuilder movieDescription = new StringBuilder("");
            StringBuilder moviesTitle = new StringBuilder("");
            StringBuilder movieID = new StringBuilder("");

            try {
                // make the connection and get the input stream
                URL url = new URL("https://imdb-api.com/en/API/SearchMovie/k_ncr5jmia/" + reqMovie.trim());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                // read all lines in a StringBuilder
                String row;
                while ((row = reader.readLine()) != null) {
                    stringBuilder.append(row);
                }

                /* do the JSON parsing */
                JSONObject json = new JSONObject(stringBuilder.toString());
                JSONArray jsonArray = json.getJSONArray("results");

                // find the matching cocktail name entry and extract recipe
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject movieJson = jsonArray.getJSONObject(i);
                    String movieTitle = movieJson.getString("title");

                    if (movieTitle.toLowerCase().equals(reqMovie.toLowerCase())) {
                        movieID.append(movieJson.getString("id"));
                        moviesTitle.append(movieJson.getString("title"));
                        movieDescription.append(movieJson.getString("description"));
                        urlPicture = movieJson.getString("image");
                    }

                }

            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (JSONException ex) {
                ex.printStackTrace();
            }

            movieImage = getBitmap();

            // update the bitmap image from url in json
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    apiImageView.setImageBitmap(movieImage);
                }
            });
        }


        // retrieve a bitmap image from the URL in JSON
        Bitmap getBitmap() {
            Bitmap bit = null;
            try {
                URL url = new URL(urlPicture);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedInputStream inputStream = new BufferedInputStream(connection.getInputStream());

                bit = BitmapFactory.decodeStream(inputStream);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }

            return bit;
        }
    }
}