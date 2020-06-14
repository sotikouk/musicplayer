package com.example.musicplayer;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Helpers.Storage;
import com.example.musicplayer.ViewHolder.LibraryAdapter;
import com.example.musicplayer.ViewHolder.TracksInPlaylistAdapter;
import com.example.musicplayer.data.Model.Track;
import com.example.musicplayer.ui.library.libraryViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class songsInPlaylist extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter ladapter;
    private RecyclerView.LayoutManager layoutManager;

    private List<Track> tracks;
    private FirebaseFirestore fdb;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs_in_playlist);
        fdb = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        final List<String> tracksInList = (List<String>) intent.getSerializableExtra("tracklist");
        tracks = new ArrayList<>();
        for(Track t: Storage.tracks){
            if(tracksInList.contains(t.getId())){
                tracks.add(t);
            }
        }
        recyclerView =  findViewById(R.id.track_recycler_playlist);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ladapter= new TracksInPlaylistAdapter(tracks);
        recyclerView.setAdapter(ladapter);
        ladapter.notifyDataSetChanged();

    }
    @Override
    public void onResume() {
        super.onResume();
        recyclerView.setLayoutManager(layoutManager);
        ladapter= new TracksInPlaylistAdapter(tracks);
        recyclerView.setAdapter(ladapter);
        ladapter.notifyDataSetChanged();
    }


}
