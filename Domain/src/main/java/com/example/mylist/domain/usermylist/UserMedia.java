package com.example.mylist.domain.usermylist;

import com.example.mylist.domain.media.Media;
import com.example.mylist.domain.media.Status;

import javax.persistence.*;

@Entity
public class UserMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Float ownRating;
    private String ownComment;

    @ManyToOne
    @JoinColumn(name = "userml_id", referencedColumnName = "id")
    private UserMl userml;


    @ManyToOne
    @JoinColumn(name = "media_id", referencedColumnName = "id")
    private Media media;

    public UserMedia() {
    }

    /* getters et setters */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Float getOwnRating() {
        return ownRating;
    }

    public void setOwnRating(Float ownRating) {
        this.ownRating = ownRating;
    }

    public String getOwnComment() {
        return ownComment;
    }

    public void setOwnComment(String ownComment) {
        this.ownComment = ownComment;
    }

    public UserMl getUserml() {
        return userml;
    }

    public void setUserml(UserMl userml) {
        this.userml = userml;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "UserMedia{" +
                "id='" + id + '\'' +
                ", status = " + status +
                ", ownRating = " + ownRating +
                ", ownComment ='" + ownComment + '\'' +
                ", user_id ='" + userml.getId() + '\'' +
                ", media_id =' " + media.getId() + '\'' +
                '}';
    }

}
