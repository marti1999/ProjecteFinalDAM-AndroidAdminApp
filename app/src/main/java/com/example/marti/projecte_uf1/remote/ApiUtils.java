package com.example.marti.projecte_uf1.remote;


import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://192.168.0.50:45456/api/";

    public static ApiMecAroundInterfaces getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(ApiMecAroundInterfaces.class);
    }
}