
package com.example.marti.projecte_uf1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Requestor_ {

    @SerializedName("$ref")
    @Expose
    public String $ref;


    public Requestor_() {
    }


    public Requestor_(String $ref) {
        super();
        this.$ref = $ref;
    }

}
