package com.example.musicplayer.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.musicplayer.R;
import com.example.musicplayer.insertActivity;

public class searchFragment extends Fragment {
    private Button buttonInsert;
    private Button searchButton;
    private EditText searchEditText;

    private searchViewModel searchViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchViewModel = new ViewModelProvider(this).get(searchViewModel.class);
               // ViewModelProviders.of(this).get(searchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
        final TextView textView = root.findViewById(R.id.text_search);
        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final Intent intent = new Intent(getActivity(), insertActivity.class);
        final Button button = root.findViewById(R.id.buttonInsert);
        final EditText searchField = root.findViewById(R.id.searchEditText);
        final Button search = root.findViewById(R.id.buttonSearch);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = searchField.getText().toString().trim();
                System.out.println(searchText);
            }
        });
        return root;

    }

}
