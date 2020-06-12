package com.example.musicplayer.ui.login;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicplayer.Helpers.Storage;
import com.example.musicplayer.MainActivity;
import com.example.musicplayer.R;
import com.example.musicplayer.data.Model.Genre;
import com.example.musicplayer.data.Model.Playlist;
import com.example.musicplayer.data.Model.Track;
import com.example.musicplayer.data.Model.Artist;
import com.example.musicplayer.data.Model.User;
import com.example.musicplayer.ui.home.HomeFragment;
import com.example.musicplayer.ui.home.HomeViewModel;
import com.example.musicplayer.ui.login.LoginViewModel;
import com.example.musicplayer.ui.login.LoginViewModelFactory;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore fdb;
    private Task<QuerySnapshot> query;
    private static final String TAG = "LoginActivity";
    private ProgressBar loadingProgressBar;

    private void SignInFirebase(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Storage.User = mAuth.getCurrentUser();
                            // Sign in success, update UI with the signed-in user's information
                            firebaseData(); //get firebaseData @ Login
                            Log.d(TAG, "signInWithEmail:success");
                        } else {
                            // If sign in fails, display a message to the user.

                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            loadingProgressBar.setVisibility(View.GONE);

                            //updateUiWithUser(null);
                        }
                    }
                });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        fdb = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        loadingProgressBar=findViewById(R.id.loading);
        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(Storage.User);
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                SignInFirebase(usernameEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        Storage.User = mAuth.getCurrentUser();
        if (Storage.User != null) {
            firebaseData();
        }
    }

    private void updateUiWithUser(FirebaseUser model) {
        String welcome = getString(R.string.welcome) + model.getEmail();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        // Εμφανίζουμε την Main Activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }

    private void firebaseData() {
    fdb.collection("genres").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() > 0) {
                        Storage.genres = new ArrayList<>();
                        for (QueryDocumentSnapshot snapIn : task.getResult()) {
                            Storage.genres.add(snapIn.toObject(Genre.class));
                            Storage.genres.get(Storage.genres.size() - 1).setId(snapIn.getReference());
                        }
                    }
                }
            }
        });
       fdb.collection("artists").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() > 0) {
                        Storage.artists = new ArrayList<>();
                        for (QueryDocumentSnapshot snapIn : task.getResult()) {
                            Storage.artists.add(snapIn.toObject(Artist.class));
                            Storage.artists.get(Storage.artists.size() - 1).setId(snapIn.getReference());
                        }
                    }
                }
            }
        });
        fdb.collection("playlists").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() > 0) {
                        Storage.playlists = new ArrayList<>();
                        for (QueryDocumentSnapshot snapIn : task.getResult()) {
                            Storage.playlists.add(snapIn.toObject(Playlist.class));
                            Storage.playlists.get(Storage.playlists.size() - 1).setId(snapIn.getReference());
                        }
                    }else Storage.playlists = new ArrayList<>();
                    loadingProgressBar.setVisibility(View.GONE);
                    updateUiWithUser(Storage.User);
                }
            }
        });

        fdb.collection("tracks").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    if (task.getResult().size() > 0) {
                        Storage.tracks = new ArrayList<>();
                        for (QueryDocumentSnapshot snapIn : task.getResult()) {
                            Storage.tracks.add(snapIn.toObject(Track.class));
                            Storage.tracks.get(Storage.tracks.size() - 1).setId(snapIn.getReference());
                        }
                    }else Storage.tracks = new ArrayList<>();
                }
            }
        });
    }
}
