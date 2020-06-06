package com.example.musicplayer.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Helpers.Storage;
import com.example.musicplayer.R;
import com.example.musicplayer.data.Model.Track;

import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.MyViewHolder> {
    private List<Track> tracks;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,url,artist,rating,duration,genre,numRating,review;
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
            //numRating;
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.title.setText(tracks.get(position).getTitle());
        holder.genre.setText(tracks.get(position).getGenre());
        holder.artist.setText(tracks.get(position).getArtist());
        holder.duration.setText(tracks.get(position).getDuration());
        holder.review.setText(tracks.get(position).getReview());
        holder.url.setText(tracks.get(position).getUrl());
       // holder.rating.setText(tracks.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }

}
