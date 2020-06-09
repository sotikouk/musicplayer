package com.example.musicplayer.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Helpers.Storage;
import com.example.musicplayer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.musicplayer.ViewHolder.HomeAdapter;
import com.example.musicplayer.ViewHolder.LibraryAdapter;
import com.example.musicplayer.data.Model.Playlist;
import com.example.musicplayer.data.Model.Track;
import java.util.List;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter ladapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Playlist> playlists= Storage.playlists;
    private FirebaseFirestore fdb;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        fdb = FirebaseFirestore.getInstance();

        recyclerView =  root.findViewById(R.id.playlist_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        ladapter= new HomeAdapter(playlists);
        recyclerView.setAdapter(ladapter);
        ladapter.notifyDataSetChanged();

        final TextView textView = root.findViewById(R.id.text_home);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPlaylistDialog();
            }
        });
        return root;
    }

    private void addPlaylistDialog(){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.add_playlist,null);

        final EditText playlistNameField = subView.findViewById(R.id.addNewPlaylistEditText);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add new Playlist");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("Add Playlist", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String playlistName = playlistNameField.getText().toString().trim();
                System.out.println(playlistName);
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
