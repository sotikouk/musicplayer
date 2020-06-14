package com.example.musicplayer.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Helpers.Storage;
import com.example.musicplayer.R;
import com.example.musicplayer.data.Model.Playlist;
import com.example.musicplayer.data.Model.Track;
import com.example.musicplayer.editActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    private List<Track> tracks;
    private FirebaseFirestore fdb;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,url,artist,rating,duration,genre,numRating,review;
        public Button editSong,deleteSong,addToPlaylist;
        public Context context;
        public MyViewHolder(@NonNull View item) {
            super(item);
            title=item.findViewById(R.id.song_name);
            artist=item.findViewById(R.id.song_artist);
            rating=item.findViewById(R.id.song_rate);
            duration= item.findViewById(R.id.song_duration);
            genre=item.findViewById(R.id.song_genre);
            review=item.findViewById(R.id.song_review);
            url = item.findViewById(R.id.song_link);
            addToPlaylist= item.findViewById(R.id.addToPlaylist);
            editSong = item.findViewById(R.id.editSongButton);
            deleteSong = item.findViewById(R.id.deleteSongButton);
            context = item.getContext();
        }
    }

    public SearchAdapter(List<Track> tracks){
       this.tracks =tracks;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tracklist_data,parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.title.setText(tracks.get(position).getTitle());
        holder.genre.setText(tracks.get(position).getGenre());
        holder.artist.setText(tracks.get(position).getArtist());
        holder.duration.setText(tracks.get(position).getDuration().toString());
        holder.review.setText(tracks.get(position).getReview());
        holder.url.setText(tracks.get(position).getUrl());
        holder.rating.setText(tracks.get(position).getRating().toString());
        holder.editSong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Track trck =tracks.get(position);
                Intent intent = new Intent(view.getContext(), editActivity.class);
                intent.putExtra("track", (Serializable) trck);
                view.getContext().startActivity(intent);
            }
        });
        holder.addToPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        holder.deleteSong.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                final Track trck =tracks.get(position);
                fdb = FirebaseFirestore.getInstance();
                for(Playlist s: Storage.playlists ){
                    s.getTracks().remove(fdb.document(trck.getId()));
                }
                fdb.collection("playlists").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().size() > 0) {
                                for (QueryDocumentSnapshot snapIn : task.getResult()) {
                                    for(Playlist s: Storage.playlists) {
                                        if(snapIn.getReference().getId().equals(s.getId().getId())){
                                            fdb.document(s.getId().getPath()).update("tracks", s.getTracks());
                                        }
                                     }
                                }
                            }
                            fdb.document(trck.getId()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Storage.tracks.remove(Storage.tracks.indexOf(trck));
                                }
                            });
                        }
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

}
