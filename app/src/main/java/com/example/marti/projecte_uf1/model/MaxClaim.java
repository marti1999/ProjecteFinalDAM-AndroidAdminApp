
package com.example.marti.projecte_uf1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MaxClaim {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("value")
    @Expose
    public Integer value;
    @SerializedName("operationResult")
    @Expose
    public Double operationResult;


    public MaxClaim() {
    }


    public MaxClaim(String $id, Integer id, Integer value, Double operationResult) {
        super();
        this.$id = $id;
        this.id = id;
        this.value = value;
        this.operationResult = operationResult;
    }

}
