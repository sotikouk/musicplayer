package com.example.musicplayer.data.Model;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Track implements Serializable {
        private String id;
        private String  artist,genre,title,url,artwork,review;
        private Integer numRatings, rating,duration;
        private List<Track> tracks;

        public Track(String artist, String artwork, Integer duration, String title, String genre, String url, String review, Integer numRatings, Integer rating){
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
        public Track(String id, String artist, String artwork, Integer duration, String title, String genre, String url, String review, Integer numRatings, Integer rating){
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

    public String getId() {
        return id;
    }

    public void setId(DocumentReference id) {
        this.id = id.getPath();
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
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
    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("artist",artist);
        result.put("title", title);
        result.put("url",url);
        result.put("rating",rating);
        result.put("genre",genre);
        result.put("duration",duration);
        result.put("review",review);
        return result;
    }
}
