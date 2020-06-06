package com.example.musicplayer.data.Model;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class Playlist {
    private String artwork, genre,user;
    private List<Track> trackList;
    private List<DocumentReference> songs;
    private DocumentReference id;

    public String getArtwork() {
        return artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public DocumentReference getId() {
        return id;
    }

    public void setId(DocumentReference id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public Playlist(String artwork, String genre, String user, List<Track> trackList, List<DocumentReference> songs, DocumentReference id) {
        this.artwork = artwork;
        this.genre = genre;
        this.user = user;
        this.trackList = trackList;
        this.songs = songs;
        this.id = id;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public List<DocumentReference> getSongs() {
        return songs;
    }

    public void setSongs(List<DocumentReference> songs) {
        this.songs = songs;
    }
}
