package com.example.musicplayer.ViewHolder;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.R;
import com.example.musicplayer.data.Model.Playlist;
import com.example.musicplayer.data.Model.Track;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<Playlist> playlists;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name,duration;
        private Context context;
        public MyViewHolder(@NonNull View item) {
            super(item);
            name=item.findViewById(R.id.playlist_name);
            duration=item.findViewById(R.id.playlist_duration);
            context = item.getContext();
        }
    }

    public HomeAdapter(List<Playlist> playlists){
       this.playlists = playlists;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_data,parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(playlists.get(position).getName());
        holder.duration.setText(playlists.get(position).getDuration());
    }

    @Override
    public int getItemCount() {
        return 1;
    }

}
