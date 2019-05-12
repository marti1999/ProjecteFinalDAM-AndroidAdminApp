
package com.example.marti.projecte_uf1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("cloth")
    @Expose
    public Cloth cloth;
    @SerializedName("requestor")
    @Expose
    public Requestor_ requestor;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("dateCreated")
    @Expose
    public String dateCreated;
    @SerializedName("requestor_Id")
    @Expose
    public Integer requestorId;
    @SerializedName("clothes_Id")
    @Expose
    public Integer clothesId;


    public Order() {
    }


    public Order(String $id, Cloth cloth, Requestor_ requestor, Integer id, String dateCreated, Integer requestorId, Integer clothesId) {
        super();
        this.$id = $id;
        this.cloth = cloth;
        this.requestor = requestor;
        this.id = id;
        this.dateCreated = dateCreated;
        this.requestorId = requestorId;
        this.clothesId = clothesId;
    }

}
