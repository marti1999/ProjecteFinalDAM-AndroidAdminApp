package com.example.marti.projecte_uf1.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Donor {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("birthDate")
    @Expose
    public String birthDate;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("securityAnswer")
    @Expose
    public String securityAnswer;
    @SerializedName("securityQuestion")
    @Expose
    public String securityQuestion;
    @SerializedName("dateCreated")
    @Expose
    public String dateCreated;
    @SerializedName("active")
    @Expose
    public Boolean active;
    @SerializedName("picturePath")
    @Expose
    public Object picturePath;
    @SerializedName("ammountGiven")
    @Expose
    public Integer ammountGiven;
    @SerializedName("dni")
    @Expose
    public String dni;
    @SerializedName("points")
    @Expose
    public Integer points;
    @SerializedName("language_Id")
    @Expose
    public Integer languageId;
    @SerializedName("language")
    @Expose
    public Object language;
    @SerializedName("rewards")
    @Expose
    public List<Object> rewards = null;


    public Donor() {
    }


    public Donor(String $id, Integer id, String name, String lastName, String birthDate, String gender, String password, String email, String securityAnswer, String securityQuestion, String dateCreated, Boolean active, Object picturePath, Integer ammountGiven, String dni, Integer points, Integer languageId, Object language, List<Object> rewards) {
        super();
        this.$id = $id;
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.password = password;
        this.email = email;
        this.securityAnswer = securityAnswer;
        this.securityQuestion = securityQuestion;
        this.dateCreated = dateCreated;
        this.active = active;
        this.picturePath = picturePath;
        this.ammountGiven = ammountGiven;
        this.dni = dni;
        this.points = points;
        this.languageId = languageId;
        this.language = language;
        this.rewards = rewards;
    }

}