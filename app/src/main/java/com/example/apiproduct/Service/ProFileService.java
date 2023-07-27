package com.example.apiproduct.Service;



import com.example.apiproduct.Model.Api;
import com.example.apiproduct.Model.ProFile;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProFileService {
    private  static  final String BASE_URL="http://192.168.1.190:3000";
//    private  static  final String BASE_URL="http://192.168.137.116:3000";
//private  static  final String BASE_URL="http://192.168.137.116:3000";
    private Api api;

    public ProFileService(){
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }
    public Call<ProFile> ProFile(String token){
        return api.getPr("Bearer "+token);
    }
    public Call<ProFile> Logout(String token){
        return api.Logout("Bearer "+token);
    }
    public Call<ProFile> Doimk(String id , String matkhau , String matkhau_new){
        return api.Doimk(id,matkhau,matkhau_new);
    }
}
