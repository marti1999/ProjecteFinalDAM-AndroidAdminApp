
package com.example.marti.projecte_uf1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gender {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("gender1")
    @Expose
    public String gender1;
    @SerializedName("active")
    @Expose
    public Boolean active;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Gender() {
    }

    /**
     * 
     * @param id
     * @param active
     * @param gender1
     * @param $id
     */
    public Gender(String $id, Integer id, String gender1, Boolean active) {
        super();
        this.$id = $id;
        this.id = id;
        this.gender1 = gender1;
        this.active = active;
    }

}
