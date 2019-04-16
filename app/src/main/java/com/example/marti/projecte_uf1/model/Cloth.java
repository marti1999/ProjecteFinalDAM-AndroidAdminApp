
package com.example.marti.projecte_uf1.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cloth {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("classification")
    @Expose
    public Classification classification;
    @SerializedName("clothesRequests")
    @Expose
    public List<Object> clothesRequests = null;
    @SerializedName("orders")
    @Expose
    public List<Order_> orders = null;
    @SerializedName("color")
    @Expose
    public Color color;
    @SerializedName("gender")
    @Expose
    public Gender gender;
    @SerializedName("size")
    @Expose
    public Size size;
    @SerializedName("warehouse")
    @Expose
    public Warehouse warehouse;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("dateCreated")
    @Expose
    public String dateCreated;
    @SerializedName("active")
    @Expose
    public Boolean active;
    @SerializedName("warehouse_Id")
    @Expose
    public Integer warehouseId;
    @SerializedName("size_Id")
    @Expose
    public Integer sizeId;
    @SerializedName("color_Id")
    @Expose
    public Integer colorId;
    @SerializedName("gender_Id")
    @Expose
    public Integer genderId;
    @SerializedName("classification_Id")
    @Expose
    public Integer classificationId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Cloth() {
    }

    /**
     * 
     * @param classificationId
     * @param colorId
     * @param genderId
     * @param warehouseId
     * @param warehouse
     * @param size
     * @param id
     * @param color
     * @param classification
     * @param gender
     * @param dateCreated
     * @param active
     * @param clothesRequests
     * @param orders
     * @param $id
     * @param sizeId
     */
    public Cloth(String $id, Classification classification, List<Object> clothesRequests, List<Order_> orders, Color color, Gender gender, Size size, Warehouse warehouse, Integer id, String dateCreated, Boolean active, Integer warehouseId, Integer sizeId, Integer colorId, Integer genderId, Integer classificationId) {
        super();
        this.$id = $id;
        this.classification = classification;
        this.clothesRequests = clothesRequests;
        this.orders = orders;
        this.color = color;
        this.gender = gender;
        this.size = size;
        this.warehouse = warehouse;
        this.id = id;
        this.dateCreated = dateCreated;
        this.active = active;
        this.warehouseId = warehouseId;
        this.sizeId = sizeId;
        this.colorId = colorId;
        this.genderId = genderId;
        this.classificationId = classificationId;
    }

}
