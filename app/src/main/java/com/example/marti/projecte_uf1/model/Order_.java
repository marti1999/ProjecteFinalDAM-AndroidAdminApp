
package com.example.marti.projecte_uf1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order_ {

    @SerializedName("$ref")
    @Expose
    public String $ref;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Order_() {
    }

    /**
     * 
     * @param $ref
     */
    public Order_(String $ref) {
        super();
        this.$ref = $ref;
    }

}
