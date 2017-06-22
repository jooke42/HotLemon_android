package france.bosch.estelle.android_hotlemon.Class;

import com.google.android.gms.location.places.Place;

import java.util.Date;

/**
 * Created by ESTEL on 09/06/2017.
 */

public class Topic {



    private Long id;
    protected String title, Author, Body;
    String CreatedDate, PublishedDate;
    int VoteFor, VoteAgainst, Vote;
    double longitude,laitude;
    Place location;


    public Topic(String title, String user, Place location){
        this.title = title;
        this.Author = user;
        this.location = location;
        this.longitude = location.getLatLng().longitude;
        this.latitude = location.getLatLng().latitude;
    }

    public double getLongtitude() {
        return longitude;
    }

    public void setLongtitude(double longtitude) {
        this.longitude = longtitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    String urlImage;

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getPublishedDate() {
        return PublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        PublishedDate = publishedDate;
    }
    public int getVoteFor() {
        return VoteFor;
    }

    public void setVoteFor(int voteFor) {
        VoteFor = voteFor;
    }
    public void updateVoteFor() {
        VoteFor += 1 ;
    }
    public void updateVoteAgainst() {
        VoteAgainst += 1 ;
    }

    public int getVoteAgainst() {
        return VoteAgainst;
    }

    public void setVoteAgainst(int voteAgainst) {
        VoteAgainst = voteAgainst;
    }

    public int getVote(){
        return VoteFor - VoteAgainst;
    }
    public void setVote(int vote){
        this.Vote = vote;
    }
}



