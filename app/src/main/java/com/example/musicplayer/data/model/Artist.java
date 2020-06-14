package com.example.musicplayer.data.Model;

import com.google.firebase.firestore.DocumentReference;

public class Artist {
    private String name,genre;
    private DocumentReference id;

    public Artist() {
    }

    public Artist(String name, String genre, DocumentReference id) {
        this.name = name;
        this.genre = genre;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public DocumentReference getId() {
        return id;
    }

    public void setId(DocumentReference id) {
        this.id = id;
    }
}
