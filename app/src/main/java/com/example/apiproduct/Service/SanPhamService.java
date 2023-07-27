package com.example.apiproduct.Service;



import com.example.apiproduct.Model.Api;
import com.example.apiproduct.Model.Binhluan;
import com.example.apiproduct.Model.DonHang;
import com.example.apiproduct.Model.GioHang;
import com.example.apiproduct.Model.SanPham;

import java.util.ArrayList;
import java.util.List;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SanPhamService {
    private  static  final String BASE_URL="http://192.168.1.190:3000";
//private  static  final String BASE_URL="http://192.168.137.116:3000";
//private  static  final String BASE_URL="http://192.168.137.116:3000";
    private Api api;

    public SanPhamService(){
        api = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(Api.class);
    }

    public Call<List<SanPham>> getSP(String tensp,int limit){
        return api.getSP(tensp,limit);
    }
    public Single<List<SanPham>> Seach(String tensp){
        return api.SeachSP(tensp);
    }
    public Call<SanPham> getChitiet(String id){
        return api.chitiettSP(id);
    }
    public Call<SanPham> addght(String id_sanpham,String id_user,int soluong){
        return api.addgh(id_sanpham,id_user,soluong);
    }
    public  Single<List<GioHang>> listgiohang(String id_usser){
        return api.listgiohang(id_usser);
    }
    public  Call<SanPham> deletegh(String id){
        return api.deletegh(id);
    }
    public  Single<List<Binhluan>> listbinhluan(String id_usser){
        return api.listbinhluan(id_usser);
    }
    public  Single<List<DonHang>> listDonHang(String id_user, String trangthai){
        return api.listdonhang(id_user,trangthai);
    }
    public  Call<List<GioHang>> listDatHang(String id_user, ArrayList<String> list){
        return api.listdathang(id_user,list);
    }
    public Call<GioHang> addhoadon(String id_sanpham,String id_user,String diachi ,int soluong,double tongtien){
        return api.addhd(id_sanpham,id_user,diachi,soluong,tongtien);
    }
    public Call<Binhluan> addbinhluan(String id_sanpham,String id_user,String binhluan ){
        return api.addbinhluan(id_sanpham,id_user,binhluan);
    }
    public Call<GioHang> huydon(String _id ){
        return api.huydon(_id);
    }
}
