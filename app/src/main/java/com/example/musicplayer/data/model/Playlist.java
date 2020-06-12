package com.example.musicplayer.data.Model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Playlist  implements Serializable {
    private String artwork, genre,user,name;
    private Integer duration;
    private List<DocumentReference> tracks;
    private DocumentReference id;

    public String getArtwork() {
        return artwork;
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

    public Playlist(String artwork, String genre, String user, String name, List<DocumentReference> tracks, Integer duration) {
        this.artwork = artwork;
        this.genre = genre;
        this.user = user;
        this.name = name;
        this.tracks = tracks;
        this.duration = duration;
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

    public List<DocumentReference> getTracks() {
        return tracks;
    }

    public Playlist() { }

    public void setTracks(List<DocumentReference> tracks) {
        this.tracks = tracks;
    }
    @Override
    public String toString(){
        return this.name;
    }
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("artwork",artwork);
        result.put("duration", duration);
        result.put("genre",genre);
        result.put("name",name);
        result.put("tracks",tracks);
        result.put("user",user);
        return result;
    }
}
