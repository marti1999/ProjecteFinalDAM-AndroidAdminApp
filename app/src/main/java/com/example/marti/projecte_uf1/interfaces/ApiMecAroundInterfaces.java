package com.example.marti.projecte_uf1.interfaces;




import com.example.marti.projecte_uf1.model.Administrator;
import com.example.marti.projecte_uf1.model.Announcement;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.model.Requestor;
import com.example.marti.projecte_uf1.model.Reward;

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

public interface  ApiMecAroundInterfaces {


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

    @PUT("reward/claim")
    Call<Boolean> claimReward(@Query("rewardId")int rewardId,
                              @Body int donorId);

    @GET("announcementsUserType")
    Call<List<Announcement>> getAnnouncements(@Query("userType")String userType);

    @PUT("reward/availableDonor")
    Call<List<Reward>> getAvailableRewardByDonor(@Query("donorId")int donorId);

}
