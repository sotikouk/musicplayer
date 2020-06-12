package com.example.musicplayer.ViewHolder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Helpers.Storage;
import com.example.musicplayer.R;
import com.example.musicplayer.data.Model.Genre;
import com.example.musicplayer.data.Model.Playlist;
import com.example.musicplayer.data.Model.Track;
import com.example.musicplayer.editActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MyViewHolder> {
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

    public LibraryAdapter(List<Track> tracks){
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
                final Track trck =tracks.get(position);
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                View subView = inflater.inflate(R.layout.add_to_playlist,null);
                addPlaylistDialog(subView,trck);
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
                                    Storage.tracks.remove(trck);
                                    tracks.remove(trck);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, tracks.size());
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
    private void addPlaylistDialog(View subView, final Track trck){
        fdb = FirebaseFirestore.getInstance();

        final Spinner playlists = subView.findViewById(R.id.spinnerPlaylist);
        final List<String> playList=new ArrayList<>();
        for(Playlist p: Storage.playlists){
            if(!p.getTracks().contains(fdb.document(trck.getId()))){
                playList.add(p.getName());
            }
        }
        ArrayAdapter<String> pAdapter = new ArrayAdapter<String>(subView.getContext(),android.R.layout.simple_list_item_1, playList);
        playlists.setAdapter(pAdapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(subView.getContext());
        builder.setTitle("Add to Playlist");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Add to Playlist", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(Playlist p: Storage.playlists) {
                    if(playlists.getSelectedItem().toString().equals(p.getName())){
                        p.getTracks().add(fdb.document(trck.getId()));
                        p.setDuration(p.getDuration()+trck.getDuration());
                        fdb.document(p.getId().getPath()).update("tracks", p.getTracks());
                        fdb.document(p.getId().getPath()).update("duration", p.getDuration());

                    }
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.show();
    }
}
