package com.example.marti.projecte_uf1.remote;


import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://reqres.in/api/";

    public static ApiMecAroundInterfaces getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ApiMecAroundInterfaces.class);
    }
}