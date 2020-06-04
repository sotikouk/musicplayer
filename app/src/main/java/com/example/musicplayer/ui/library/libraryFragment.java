package com.example.musicplayer.ui.library;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.musicplayer.R;
import com.example.musicplayer.editActivity;
import com.example.musicplayer.insertActivity;

public class libraryFragment extends Fragment {
    private Button buttonEdit;

    private libraryViewModel libraryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        libraryViewModel =
                ViewModelProviders.of(this).get(libraryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_library, container, false);
        final TextView textView = root.findViewById(R.id.text_library);
        libraryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final Intent intent = new Intent(getActivity(), editActivity.class);
        final Button button = root.findViewById(R.id.buttonEdit);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        return root;
    }
}
