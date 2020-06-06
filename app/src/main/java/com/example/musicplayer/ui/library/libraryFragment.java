package com.example.musicplayer.ui.library;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Helpers.Storage;
import com.example.musicplayer.R;
import com.example.musicplayer.ViewHolder.LibraryAdapter;
import com.example.musicplayer.data.Model.Track;
import com.example.musicplayer.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
//import com.example.musicplayer.editActivity;
//import com.example.musicplayer.insertActivity;

public class libraryFragment extends Fragment {
    private static final String TAG = "LIBRARY FRAGMENT" ;
    private Button buttonEdit;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter ladapter;
    private RecyclerView.LayoutManager layoutManager;

    private libraryViewModel libraryViewModel;
    private List<Track> tracks=Storage.tracks;
    private FirebaseFirestore fdb;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        libraryViewModel = ViewModelProviders.of(this).get(libraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);

        final Button logoutButton = root.findViewById(R.id.logoutBtn);

        fdb = FirebaseFirestore.getInstance();

        recyclerView =  root.findViewById(R.id.track_recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        ladapter= new LibraryAdapter(tracks);
        recyclerView.setAdapter(ladapter);
        ladapter.notifyDataSetChanged();

       // final Intent intent = new Intent(getActivity(), editActivity.class);
        final Button button = root.findViewById(R.id.buttonEdit);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        /*button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });*/
        return root;
    }


}
