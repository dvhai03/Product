package com.example.apiproduct.Service;

import com.example.apiproduct.Model.Api;
import com.example.apiproduct.Model.Chat;
import com.example.apiproduct.Model.Diachi;
import com.example.apiproduct.Model.Reg;

import java.io.File;
import java.io.IOException;
import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserService {
    private  static  final String BASE_URL="http://192.168.1.190:3000";
//    private  static  final String BASE_URL="http://192.168.137.116:3000";
//private  static  final String BASE_URL="http://192.168.137.116:3000";
    private  MultipartBody.Part filePart = null;
    private Api api;
    public UserService(){
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }
    public Call<ResponseBody> update(String filepath ,String id,String hoten){
        File file = new File(filepath);
        if (file.exists()){
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
            MultipartBody.Part name = MultipartBody.Part.createFormData("hoten",hoten);
            return api.uploadFile(filePart,id,name);
        }else {
            MultipartBody.Part name = MultipartBody.Part.createFormData("hoten",hoten);
            return api.uploadFile(filePart,id,name);
        }


    }
    public Call<ResponseBody> address(String hoten,String id_user,String sodienthoai,String diachi){
        return api.address(hoten,id_user,sodienthoai,diachi);
    }
    public Single<List<Diachi>> getList(String id){
        return api.getlistdress(id);
    }

    public Call<ResponseBody> postchat(String id_user,String mess){
        return api.addchat(id_user,mess);
    }
    public Call<List<Chat>> getchat(){
        return api.getlistchat();
    }
}
