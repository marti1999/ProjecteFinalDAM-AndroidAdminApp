
package com.example.marti.projecte_uf1.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Reward {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("rewardInfoLangs")
    @Expose
    public List<RewardInfoLang> rewardInfoLangs = null;
    @SerializedName("donors")
    @Expose
    public List<Object> donors = null;
    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("neededPoints")
    @Expose
    public Long neededPoints;
    @SerializedName("active")
    @Expose
    public Boolean active;
    @SerializedName("dateCreated")
    @Expose
    public String dateCreated;

}
