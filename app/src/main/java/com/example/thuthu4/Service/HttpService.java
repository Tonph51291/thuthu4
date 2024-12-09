package com.example.thuthu4.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpService {
    com.example.thuthu4.Service.ApiSevice apiService;
    public HttpService() {
        apiService = new Retrofit.Builder().baseUrl(apiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiSevice.class);

    }
    public com.example.thuthu4.Service.ApiSevice getApiService() {
        return apiService;
    }

}
