package com.example.marti.projecte_uf1.interfaces;




import com.example.marti.projecte_uf1.model.Administrator;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.model.Requestor;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface  ApiMecAroundInterfaces {

//   @FormUrlEncoded
//    @POST("users")
//    Call<User> doRegister(@Field("name") String name,
//                          @Field("job") String job);
//
//
//

    @POST("administrator/login")
    Call<Boolean> doLogin(@Body Administrator a);


    //todo login donor i requestor
    @POST("donor/login")
    Call<Boolean> doLoginDonor(@Body Donor d);

    @POST("requestor/login")
    Call<Boolean> doLoginRequestor(@Body Requestor r);

}
