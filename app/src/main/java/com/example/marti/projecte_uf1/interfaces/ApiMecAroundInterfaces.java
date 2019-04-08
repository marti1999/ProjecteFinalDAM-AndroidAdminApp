package com.example.marti.projecte_uf1.interfaces;




import com.example.marti.projecte_uf1.model.Administrator;

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




}
