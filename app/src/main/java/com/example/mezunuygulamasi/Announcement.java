package com.example.mezunuygulamasi;

import java.io.Serializable;

public class Announcement implements Serializable {
     String photoUrl;
     String title;
     String content;
     public  Announcement(){}


    public Announcement(String photoUrl,String title,String content) {

        this.photoUrl = photoUrl;
        this.content=content;
        this.title=title;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
