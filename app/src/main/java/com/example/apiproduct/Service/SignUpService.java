package com.example.apiproduct.Service;



import com.example.apiproduct.Model.Api;
import com.example.apiproduct.Model.Reg;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpService {
    private  static  final String BASE_URL="http://192.168.1.190:3000";
//private  static  final String BASE_URL="http://192.168.137.116:3000";
//private  static  final String BASE_URL="http://192.168.137.116:3000";
    private Api api;
    public SignUpService(){
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }
    public Call<Reg> Reg(String email , String matkhau, String hoten){
        return api.Reg(email,matkhau,"nguoidung",hoten,email);
    }

}

