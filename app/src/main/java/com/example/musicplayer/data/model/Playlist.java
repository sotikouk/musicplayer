package com.example.musicplayer.data.Model;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;
import java.util.Map;

public class Playlist {
    private String artwork, genre,user,name;
    private Integer duration;
    private List<Track> trackList;
    private DocumentReference id;
    private List<Map<String, Object>> tracks;

    public String getArtwork() {
        return artwork;
    }

    public List<Map<String, Object>> getTracks() {
        return tracks;
    }

    public String getName() {
        return name;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTracks(List<Map<String, Object>> tracks) {
        this.tracks = tracks;
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

    public Playlist(String artwork, String genre, String user, List<Track> trackList, DocumentReference id) {
        this.artwork = artwork;
        this.genre = genre;
        this.user = user;
        this.trackList = trackList;
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

    public Playlist() {
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

}
