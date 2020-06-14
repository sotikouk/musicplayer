package com.example.musicplayer.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayer.Helpers.Storage;
import com.example.musicplayer.R;
import com.example.musicplayer.ViewHolder.LibraryAdapter;
import com.example.musicplayer.data.Model.Genre;
import com.example.musicplayer.data.Model.Track;
import com.example.musicplayer.insertActivity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class searchFragment extends Fragment {
    private Button buttonInsert, searchButton;
    private EditText searchEditText;
    private Spinner spinner;
    private searchViewModel searchViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter ladapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Track> tracks= Storage.tracks;
    private FirebaseFirestore fdb;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(searchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        final Intent intent = new Intent(getActivity(), insertActivity.class);
        final Button button = root.findViewById(R.id.buttonInsert);
        final EditText searchField = root.findViewById(R.id.searchEditText);
        final Button search = root.findViewById(R.id.buttonSearch);
        spinner = root.findViewById(R.id.searchGenre);
        List<String> genreList = new ArrayList<>();
        genreList.add(getString(R.string.selectgenre));
        for(Genre g: Storage.genres){
            genreList.add(g.getName());
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(root.getContext(),android.R.layout.simple_list_item_1, genreList);

        fdb = FirebaseFirestore.getInstance();

        recyclerView =  root.findViewById(R.id.searchRecycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(root.getContext());
        recyclerView.setLayoutManager(layoutManager);
        spinner.setAdapter(adapter);
        final List<Track> results = new ArrayList<>();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                results.clear();
                for( Track t:tracks){
                    if(t.getGenre().equals(adapterView.getSelectedItem().toString())){
                        results.add(t);
                    }
                }
                ladapter= new LibraryAdapter(results);
                recyclerView.setAdapter(ladapter);
                ladapter.notifyDataSetChanged();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchField.getText().toString().toLowerCase().trim();
                if( results.size() >0){
                    List<Track> temp = new ArrayList<>();
                    for( Track t: results){
                        if(t.getArtist().contains(searchText) || t.getTitle().contains(searchText)){
                            temp.add(t);
                        }
                    }
                    ladapter= new LibraryAdapter(temp);
                }else{
                    results.clear();
                    for( Track t:tracks){
                        if(t.getArtist().contains(searchText) || t.getTitle().contains(searchText)){
                            results.add(t);
                        }
                    }
                    ladapter= new LibraryAdapter(results);
                }
                recyclerView.setAdapter(ladapter);
                ladapter.notifyDataSetChanged();
            }
        });
        return root;

    }

}
