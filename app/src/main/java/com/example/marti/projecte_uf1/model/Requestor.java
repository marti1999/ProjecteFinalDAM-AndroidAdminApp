
package com.example.marti.projecte_uf1.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Requestor {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("clothesRequests")
    @Expose
    public List<Object> clothesRequests = null;
    @SerializedName("language")
    @Expose
    public Language language;
    @SerializedName("maxClaim")
    @Expose
    public MaxClaim maxClaim;
    @SerializedName("orders")
    @Expose
    public List<Order> orders = null;
    @SerializedName("status")
    @Expose
    public Status status;
    @SerializedName("dni")
    @Expose
    public String dni;
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
    @SerializedName("householdIncome")
    @Expose
    public Double householdIncome;
    @SerializedName("householdMembers")
    @Expose
    public Integer householdMembers;
    @SerializedName("picturePath")
    @Expose
    public Object picturePath;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("points")
    @Expose
    public Integer points;
    @SerializedName("language_Id")
    @Expose
    public Integer languageId;
    @SerializedName("maxClaims_Id")
    @Expose
    public Integer maxClaimsId;
    @SerializedName("status_Id")
    @Expose
    public Integer statusId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Requestor() {
    }

    /**
     * 
     * @param maxClaimsId
     * @param password
     * @param securityQuestion
     * @param householdMembers
     * @param statusId
     * @param id
     * @param picturePath
     * @param householdIncome
     * @param name
     * @param gender
     * @param birthDate
     * @param points
     * @param dni
     * @param $id
     * @param securityAnswer
     * @param maxClaim
     * @param lastName
     * @param status
     * @param languageId
     * @param email
     * @param dateCreated
     * @param active
     * @param orders
     * @param clothesRequests
     * @param language
     */
    public Requestor(String $id, List<Object> clothesRequests, Language language, MaxClaim maxClaim, List<Order> orders, Status status, String dni, String name, String lastName, String birthDate, String gender, String password, String email, String securityAnswer, String securityQuestion, String dateCreated, Boolean active, Double householdIncome, Integer householdMembers, Object picturePath, Integer id, Integer points, Integer languageId, Integer maxClaimsId, Integer statusId) {
        super();
        this.$id = $id;
        this.clothesRequests = clothesRequests;
        this.language = language;
        this.maxClaim = maxClaim;
        this.orders = orders;
        this.status = status;
        this.dni = dni;
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
        this.householdIncome = householdIncome;
        this.householdMembers = householdMembers;
        this.picturePath = picturePath;
        this.id = id;
        this.points = points;
        this.languageId = languageId;
        this.maxClaimsId = maxClaimsId;
        this.statusId = statusId;
    }

}
