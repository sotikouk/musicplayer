package com.example.musicplayer.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Helpers.Storage;
import com.example.musicplayer.R;
import com.example.musicplayer.data.Model.Playlist;
import com.example.musicplayer.data.Model.Track;
import com.example.musicplayer.songsInPlaylist;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private List<Playlist> playlists;
    private FirebaseFirestore fdb;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name,duration;
        public Button deleteBtn;
        public CardView trackCard;
        public Context context;
        public MyViewHolder(@NonNull View item) {
            super(item);
            name=item.findViewById(R.id.playlist_name);
            duration=item.findViewById(R.id.playlist_duration);
            trackCard=item.findViewById(R.id.playlistCard);
            deleteBtn= item.findViewById(R.id.listDelete);
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
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try {
            int sectomin = (playlists.get(position).getDuration() / 60);
            holder.name.setText(playlists.get(position).getName());
            holder.duration.setText(" " + String.valueOf(sectomin) + " mins");
            holder.trackCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    List<String> temp = new ArrayList<>();
                    for (DocumentReference d : playlists.get(position).getTracks()) {
                        temp.add(d.getPath());
                    }
                    Intent intent = new Intent(view.getContext(), songsInPlaylist.class);
                    intent.putExtra("tracklist", (Serializable) temp);
                    view.getContext().startActivity(intent);

                }
            });
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fdb = FirebaseFirestore.getInstance();
                    fdb.document(playlists.get(position).getId().getPath()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                if (task.isSuccessful()) {
                                    Storage.playlists.remove(playlists.get(position));
                                    //playlists.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, playlists.size());
                                }
                            }
                        }
                    });
                }
            });
        }catch (NullPointerException e){
            System.out.println(e);
        }
    }

    @Override
    public int getItemCount() {
            return playlists.size();
    }

}
