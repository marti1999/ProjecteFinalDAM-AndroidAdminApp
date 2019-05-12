package com.example.marti.projecte_uf1.model;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Announcement {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("dateCreated")
    @Expose
    public String dateCreated;
    @SerializedName("language")
    @Expose
    public String language;
    @SerializedName("recipient")
    @Expose
    public String recipient;


    public Announcement() {
    }


    public Announcement(String $id, Integer id, String title, String message, String dateCreated, String language, String recipient) {
        super();
        this.$id = $id;
        this.id = id;
        this.title = title;
        this.message = message;
        this.dateCreated = dateCreated;
        this.language = language;
        this.recipient = recipient;
    }

}