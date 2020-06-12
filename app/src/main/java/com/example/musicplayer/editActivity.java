package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.musicplayer.Helpers.MinMaxF;
import com.example.musicplayer.Helpers.Storage;
import com.example.musicplayer.data.Model.Genre;
import com.example.musicplayer.data.Model.Track;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class editActivity extends AppCompatActivity {
    private Button submit;
    private Spinner spinner ;
    private EditText  title,artist, duration, rating,link,review;
    private FirebaseFirestore fdb;

    private List<String> genreList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        title=  findViewById(R.id.title_song);
        artist=  findViewById(R.id.singer);
        duration=  findViewById(R.id.time_of_song);
        rating = findViewById(R.id.grade);
        link = findViewById(R.id.link_of_song);
        review = findViewById(R.id.review);
        submit= findViewById(R.id.updatebutton);
        spinner = findViewById(R.id.spinner_genre);
        Intent intent = getIntent();
        final Track track = (Track) intent.getSerializableExtra("track");
        for(Genre g: Storage.genres){
            genreList.add(g.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, genreList);
        title.setText(track.getTitle().toUpperCase());
        artist.setText(track.getArtist().toUpperCase());
        review.setText(track.getReview());
        duration.setText(String.valueOf(track.getDuration()).toUpperCase());
        rating.setFilters(new InputFilter[]{new MinMaxF("1","5")});
        rating.setText(String.valueOf(track.getRating()));
        link.setText(track.getUrl());
        spinner.setAdapter(adapter);
        submit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                handleUpdate(track);
                finish();
            }
        });
    }
    @Override
    protected  void onDestroy() {
        super.onDestroy();


    }
    private void handleUpdate(Track track){
        Map<String,Object> sendValues;
        for(Track t: Storage.tracks ){
            if(t.getId().equals(track.getId())){
                final Integer index = Storage.tracks.indexOf(t);
                Storage.tracks.get(index).setTitle(title.getText().toString().toLowerCase());
                Storage.tracks.get(index).setArtist(artist.getText().toString().toLowerCase());
                Storage.tracks.get(index).setGenre(spinner.getSelectedItem().toString().toLowerCase());
                Storage.tracks.get(index).setUrl(link.getText().toString().toLowerCase());
                Storage.tracks.get(index).setReview(review.getText().toString());
                Storage.tracks.get(index).setRating(Integer.parseInt(rating.getText().toString()));
                Storage.tracks.get(index).setDuration(Integer.parseInt(duration.getText().toString()));
                sendValues = Storage.tracks.get(index).toMap();
                fdb = FirebaseFirestore.getInstance();
                fdb.document(t.getId()).update(sendValues);
            }
        }

    }
}
