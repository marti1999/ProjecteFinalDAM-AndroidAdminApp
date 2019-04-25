
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

    public String get$id() {
        return $id;
    }

    public void set$id(String $id) {
        this.$id = $id;
    }

    public List<RewardInfoLang> getRewardInfoLangs() {
        return rewardInfoLangs;
    }

    public void setRewardInfoLangs(List<RewardInfoLang> rewardInfoLangs) {
        this.rewardInfoLangs = rewardInfoLangs;
    }

    public List<Object> getDonors() {
        return donors;
    }

    public void setDonors(List<Object> donors) {
        this.donors = donors;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNeededPoints() {
        return neededPoints;
    }

    public void setNeededPoints(Long neededPoints) {
        this.neededPoints = neededPoints;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
