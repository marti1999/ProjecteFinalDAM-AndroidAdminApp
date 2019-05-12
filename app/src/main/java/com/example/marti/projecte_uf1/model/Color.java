
package com.example.marti.projecte_uf1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Color {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("colorCode")
    @Expose
    public String colorCode;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("active")
    @Expose
    public Boolean active;


    public Color() {
    }

    public Color(String $id, Integer id, String colorCode, String name, Boolean active) {
        super();
        this.$id = $id;
        this.id = id;
        this.colorCode = colorCode;
        this.name = name;
        this.active = active;
    }

    @Override
    public String toString() {
        return name;
    }

}
