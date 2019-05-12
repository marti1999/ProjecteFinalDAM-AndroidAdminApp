
package com.example.marti.projecte_uf1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Status {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("status1")
    @Expose
    public String status1;
    @SerializedName("reason")
    @Expose
    public String reason;


    public Status() {
    }


    public Status(String $id, Integer id, String status1, String reason) {
        super();
        this.$id = $id;
        this.id = id;
        this.status1 = status1;
        this.reason = reason;
    }

}
