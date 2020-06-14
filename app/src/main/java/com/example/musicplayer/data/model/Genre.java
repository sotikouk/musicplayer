package com.example.musicplayer.data.Model;

import com.google.firebase.firestore.DocumentReference;

public class Genre {
    private String name;
    private Boolean enabled;
    private DocumentReference id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Genre() {
    }

    public Genre(String name, Boolean enabled, DocumentReference id) {
        this.name = name;
        this.enabled = enabled;
        this.id = id;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public DocumentReference getId() {
        return id;
    }

    public void setId(DocumentReference id) {
        this.id = id;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
