
package com.example.marti.projecte_uf1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Warehouse {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("street")
    @Expose
    public String street;
    @SerializedName("number")
    @Expose
    public String number;
    @SerializedName("city")
    @Expose
    public String city;
    @SerializedName("postalCode")
    @Expose
    public String postalCode;
    @SerializedName("name")
    @Expose
    public String name;

}
