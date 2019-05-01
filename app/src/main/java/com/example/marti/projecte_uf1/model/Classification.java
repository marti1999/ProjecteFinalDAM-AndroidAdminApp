
package com.example.marti.projecte_uf1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Classification {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("classificationType")
    @Expose
    public String classificationType;
    @SerializedName("active")
    @Expose
    public Boolean active;
    @SerializedName("value")
    @Expose
    public Integer value;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Classification() {
    }

    /**
     * 
     * @param id
     * @param classificationType
     * @param value
     * @param active
     * @param $id
     */
    public Classification(String $id, Integer id, String classificationType, Boolean active, Integer value) {
        super();
        this.$id = $id;
        this.id = id;
        this.classificationType = classificationType;
        this.active = active;
        this.value = value;
    }
    @Override
    public String toString() {
        return classificationType;
    }

}
