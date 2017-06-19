package france.bosch.estelle.android_hotlemon.Class;

/**
 * Created by ESTEL on 09/06/2017.
 */

public class Topic {

    private Long id;
    protected String title, Author, Body, CreatedDate, PublishedDate;
    int VoteFor, VoteAgainst;
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
}



