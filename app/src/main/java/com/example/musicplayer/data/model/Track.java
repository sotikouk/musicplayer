package com.example.musicplayer.data.Model;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;

public class Track {
        private DocumentReference id;
        private String  artist,duration,genre,title,url,artwork,review;
        private Integer numRatings, rating;
        private List<Track> tracks;

        public Track(String artist, String artwork, String duration, String title, String genre, String url, String review, Integer numRatings, Integer rating){
            this.artist=artist;
            this.artwork=artwork;
            this.duration=duration;
            this.genre=genre;
            this.title=title;
            this.url=url;
            this.review=review;
            this.numRatings=numRatings;
            this.rating=rating;
        }
        public Track(DocumentReference id, String artist, String artwork, String duration, String title, String genre, String url, String review, Integer numRatings, Integer rating){
            this.artist=artist;
            this.artwork=artwork;
            this.duration=duration;
            this.genre=genre;
            this.title=title;
            this.url=url;
            this.review=review;
            this.numRatings=numRatings;
            this.rating=rating;
            this.id=id;
        }
        public Track(){}

    public DocumentReference getId() {
        return id;
    }

    public void setId(DocumentReference id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtwork() {
        return artwork;
    }

    public void setArtwork(String artwork) {
        this.artwork = artwork;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getNumRatings() {
        return numRatings;
    }

    public void setNumRatings(Integer numRatings) {
        this.numRatings = numRatings;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public List<Track> getTracks(){return tracks;}
}
