package com.example.marti.projecte_uf1.interfaces;


import com.example.marti.projecte_uf1.model.Administrator;
import com.example.marti.projecte_uf1.model.Announcement;
import com.example.marti.projecte_uf1.model.Classification;
import com.example.marti.projecte_uf1.model.Cloth;
import com.example.marti.projecte_uf1.model.Color;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.model.Gender;
import com.example.marti.projecte_uf1.model.Requestor;
import com.example.marti.projecte_uf1.model.Reward;
import com.example.marti.projecte_uf1.model.Size;
import com.example.marti.projecte_uf1.model.Warehouse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiMecAroundInterfaces {


    @POST("administrator/login")
    Call<Boolean> doLogin(@Body Administrator a);


    @POST("donor/login")
    Call<Boolean> doLoginDonor(@Body Donor d);

    @POST("donor/loginBoth")
    Call<String> doLoginBoth(@Body Donor d);

    @POST("donor/isUserDuplicated")
    Call<Boolean> isUserDuplicated(@Body Donor d);

    @POST("donor")
    Call<Donor> insertDonor(@Body Donor d);

    @POST("requestor")
    Call<Requestor> insertRequestor(@Body Requestor r);

    @GET("rewards")
    Call<List<Reward>> getRewards();

    @GET("warehouses")
    Call<List<Warehouse>> getWarehoues();

    @PUT("reward/claim")
    Call<Boolean> claimReward(@Query("rewardId") int rewardId,
                              @Body int donorId);

    @POST("warehouse/byCloth")
    Call<List<Warehouse>> getWarehousesByCloth(@Body Cloth c);


    @GET("announcementsUserType")
    Call<List<Announcement>> getAnnouncements(@Query("userType") String userType);

    @PUT("reward/availableDonor")
    Call<List<Reward>> getAvailableRewardByDonor(@Query("donorId") int donorId);

    @GET("donor")
    Call<Donor> getDonorById(@Query("id") int donorId);

    @GET("requestor")
    Call<Requestor> getRequestorById(@Query("id") int requestorId);

    @POST("donor/question")
    Call<String> getQuestionBothByMail(@Body String email);

    @POST("donor/requestNewPassword")
    Call<String> getNewPassword(@Body String emailAnswer);

    @PUT("donorPassword")
    Call<Donor> updateDonor(@Query("id") int donorId,
                            @Body Donor donor);

    @PUT("requestorPassword")
    Call<Requestor> updateRequestor(@Query("id") int requestorId,
                            @Body Requestor requestor);
    //Cloth Attributes
    @GET("sizes")
    Call<List<Size>> getClothSizes();

    @GET("genders")
    Call<List<Gender>> getClothGenders();

    @GET("colors")
    Call<List<Color>> getClothColors();

    @GET("classifications")
    Call<List<Classification>> getClothClassifications();

}
