
package com.example.marti.projecte_uf1.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardInfoLang {

    @SerializedName("$id")
    @Expose
    public String $id;
    @SerializedName("language")
    @Expose
    public Language language;
    @SerializedName("reward")
    @Expose
    public Reward_ reward;
    @SerializedName("id")
    @Expose
    public Long id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("description")
    @Expose
    public String description;
    @SerializedName("language_Id")
    @Expose
    public Long languageId;
    @SerializedName("rewards_Id")
    @Expose
    public Long rewardsId;

}
