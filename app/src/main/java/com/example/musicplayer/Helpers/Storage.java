package com.example.musicplayer.Helpers;

import com.example.musicplayer.data.Model.Artist;
import com.example.musicplayer.data.Model.Genre;
import com.example.musicplayer.data.Model.Review;
import com.example.musicplayer.data.Model.Track;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class Storage {
    public static List<Track> tracks;
    public static List<Artist> artists;
    public static List<Genre>  genres;
    public static List<Review> reviews;
    public static FirebaseUser User;
}
