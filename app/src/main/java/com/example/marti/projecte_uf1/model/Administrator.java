
package com.example.marti.projecte_uf1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Administrator {

    public Administrator() {
    }

    public Administrator(String $id, Language language, Warehouse warehouse, Long id, String email, String name, String lastName, String password, String dateCreated, Boolean isSuper, Boolean active, String workerCode, Long languageId, Long warehouseId) {
        this.$id = $id;
        this.language = language;
        this.warehouse = warehouse;
        this.id = id;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.dateCreated = dateCreated;
        this.isSuper = isSuper;
        this.active = active;
        this.workerCode = workerCode;
        this.languageId = languageId;
        this.warehouseId = warehouseId;
    }

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("language")
    @Expose
    public Language language;
    @SerializedName("warehouse")
    @Expose
    public Warehouse warehouse;
    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("lastName")
    @Expose
    public String lastName;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("dateCreated")
    @Expose
    public String dateCreated;
    @SerializedName("isSuper")
    @Expose
    public Boolean isSuper;
    @SerializedName("active")
    @Expose
    public Boolean active;
    @SerializedName("workerCode")
    @Expose
    public String workerCode;
    @SerializedName("language_Id")
    @Expose
    public Long languageId;
    @SerializedName("warehouse_Id")
    @Expose
    public Long warehouseId;

}
