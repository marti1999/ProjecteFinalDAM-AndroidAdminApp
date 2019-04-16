package com.example.marti.projecte_uf1.remote;

import com.example.marti.projecte_uf1.utils.UnsafeOkHttpClient;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String baseUrl) {
        if (retrofit==null) {

            OkHttpClient okHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient();


            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)

                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}