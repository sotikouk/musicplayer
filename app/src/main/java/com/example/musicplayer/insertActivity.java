package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.musicplayer.Helpers.MinMaxF;
import com.example.musicplayer.Helpers.Storage;
import com.example.musicplayer.data.Model.Genre;
import com.example.musicplayer.data.Model.Track;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class insertActivity extends AppCompatActivity {
    private Button addButton;
    private Spinner spinner ;
    private EditText title,artist, duration, rating,link,review;
    private FirebaseFirestore fdb;
    private List<String> genreList=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        title=  findViewById(R.id.title_song);
        artist=  findViewById(R.id.singer);
        duration=  findViewById(R.id.time_of_song);
        rating = findViewById(R.id.grade);
        rating.setFilters(new InputFilter[]{new MinMaxF("1","5")});
        link = findViewById(R.id.link_of_song);
        review = findViewById(R.id.review);
        spinner = findViewById(R.id.spinner_genre);
        addButton=findViewById(R.id.addButton);
        genreList.add(getString(R.string.selectgenre));
        for(Genre g: Storage.genres){
            genreList.add(g.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, genreList);
        spinner.setAdapter(adapter);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAdd();
                Toast.makeText(getApplicationContext(), "Song Added To Library!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void handleAdd(){
        fdb = FirebaseFirestore.getInstance();
        String artistn =artist.getText().toString().toLowerCase();
        String artwork ="";
        Integer durationn = Integer.parseInt(duration.getText().toString());
        String genren = spinner.getSelectedItem().toString();
        String titlen = title.getText().toString().toLowerCase();
        String linkn = link.getText().toString().toLowerCase();
        String reviewn = review.getText().toString().toLowerCase();
        Integer ratingn =Integer.parseInt(rating.getText().toString());
        final Track track = new Track(artistn,artwork,durationn,titlen,genren,linkn,reviewn,0,ratingn);
        Map<String,Object> sendValues = track.toMap();
        fdb.collection("tracks").add(sendValues).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isComplete()){
                    if(task.isSuccessful()){
                       Storage.tracks.add(track);
                       Storage.tracks.get(Storage.tracks.size()-1).setId(task.getResult());
                    }
                }
            }
        });
    }
}
