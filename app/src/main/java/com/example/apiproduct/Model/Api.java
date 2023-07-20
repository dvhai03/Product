package com.example.apiproduct.Model;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    @GET("api/sanpham")
    Single<List<SanPham>> getSP(@Query("key") String tensp);


    @GET("api/users/profile")
    Call<ProFile> getPr(@Header("Authorization") String heaader);

    @GET("api/users/logout")
    Call<ProFile> Logout(@Header("Authorization") String heaader);
    @FormUrlEncoded
    @POST("api/users/login")
    Call<Login> Login(@Field("email") String email , @Field("matkhau") String matkhau);
    @FormUrlEncoded
    @POST("api/users/reg")
    Call<Reg> Reg(@Field("email") String email , @Field("matkhau") String matkhau, @Field("vaitro") String vaitro, @Field("hoten") String hoten);
    @FormUrlEncoded
    @POST("api/users/edit/{id}")
    Call<ProFile>Doimk(@Path("id") String id, @Field("matkhau") String matkhau, @Field("matkhau1") String newmk);

    @GET("api/sanpham/{id}")
    Call<SanPham>chitiettSP(@Path("id") String id);
    @GET("api/sanpham/")
    Single<List<SanPham>> SeachSP(@Query("key") String tensp);
    @FormUrlEncoded
    @POST("api/giohang/add")
    Call<SanPham> addgh( @Field("id_sanpham") String id_sp, @Field("id_user") String id_user,@Field("soluong") int soluong);

    @GET("api/giohang/")
    Single<List<GioHang>> listgiohang(@Query("id_user") String id_user);

    @GET("api/giohang/delete/{id}")
    Call<SanPham> deletegh(@Path("id") String id);

    @GET("api/binhluan/")
    Single<List<Binhluan>> listbinhluan(@Query("key") String id_sanpham);
    @FormUrlEncoded
    @POST("api/binhluan/add")
    Call<Binhluan> addbinhluan(@Field("id_sanpham") String id_sanpham,@Field("id_user") String id_user ,@Field("binhluan") String binhluan);

    @GET("api/hoadon/")
    Single<List<DonHang>> listdonhang(@Query("id_user") String id_user,@Query("trangthai") String trangthai);

    @GET("api/xacnhan/{id}")
    Call<GioHang>huydon(@Path("id") String _id);

    @GET("api/giohang/")
    Call<List<GioHang>> listdathang(@Query("id_user") String id_user, @Query("id_sanpham")ArrayList<String> list_id);

    @FormUrlEncoded
    @POST("api/hoadon/add")
    Call<GioHang> addhd( @Field("id_sanpham") String id_sp, @Field("id_user") String id_user,@Field("diachi") String diachi,@Field("soluong")int soluong,@Field("tongtien") double tongtien);
    @Multipart
    @POST("api/user/ed_anh/{iduser}")
    Call<ResponseBody> uploadFile(@Part MultipartBody.Part file,@Path("iduser") String id,@Part MultipartBody.Part hoten);
}
