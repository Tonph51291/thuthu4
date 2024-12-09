package com.example.thuthu4.Service;

import com.example.thuthu4.DTO.Respondata;
import com.example.thuthu4.DTO.XeMayDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiSevice {
    final public  String BASE_URL = "http://192.168.1.10:3000/";


    @POST("xemay/add_xe")
    Call<Respondata<XeMayDTO>> addXe(@Body XeMayDTO xeMayDTO);


    @GET("xemay/get_all_xe")
    Call<Respondata<List<XeMayDTO>>> getAllXe();


    @DELETE("xemay/delete_xe/{id}")
    Call<Respondata<XeMayDTO>> deleteXe(@Path("id") String id);

    @PUT("xemay/update_xe/{id}")
    Call<Respondata<XeMayDTO>> updateXe(@Path("id") String id, @Body XeMayDTO xeMayDTO);

   @GET("xemay/search_xe/{tenxe}")
    Call<Respondata<List<XeMayDTO>>> searchXe(@Path("tenxe") String tenxe);


}
