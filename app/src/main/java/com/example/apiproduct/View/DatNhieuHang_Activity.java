package com.example.apiproduct.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.apiproduct.Adapter.DatHang_Adapter;
import com.example.apiproduct.Model.GioHang;
import com.example.apiproduct.Model.SanPham;
import com.example.apiproduct.R;
import com.example.apiproduct.Service.SanPhamService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DatNhieuHang_Activity extends AppCompatActivity {
    SanPhamService api;
    TextView sumhang;
    Button dat_hang;
    double sum = 0;
   public List<GioHang> list;
    ArrayList<String > list_id;
    ArrayList<String > list_id_gio;
    String id_user;
    ListView lv;
    DatHang_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_nhieu_hang);
        sumhang = findViewById(R.id.txt_sumdat);
        list = new ArrayList<>();
       lv = findViewById(R.id.lv_listdat);
        lv.setAdapter(adapter);
        api= new SanPhamService();
        Intent i = getIntent();
        id_user = i.getStringExtra("id_user");
       list_id = i.getStringArrayListExtra("id");
       list_id_gio = i.getStringArrayListExtra("id_gh");
        api.listDatHang(id_user,list_id).enqueue(new Callback<List<GioHang>>() {
            @Override
            public void onResponse(Call<List<GioHang>> call, Response<List<GioHang>> response) {
                list = response.body();
                adapter = new DatHang_Adapter(list);
                lv.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<List<GioHang>> call, Throwable t) {
                Log.d("DEBUG","Fail"+t.getMessage());
            }
        });
        Toast.makeText(DatNhieuHang_Activity.this, list.size()+"", Toast.LENGTH_SHORT).show();

    dat_hang= findViewById(R.id.btn_dathang);
    dat_hang.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dathang();
        }
    });
    }
           private void dathang(){
               for (int i = 0;i<list_id.size();i++){
                   api.addhoadon(list_id.get(i),id_user,"Phuong tu dong phi ung hoa",list.get(i).getSoluong(),list.get(i).getSanPham().getGiatien()*list.get(i).getSoluong()).enqueue(new Callback<GioHang>() {
               @Override
               public void onResponse(Call<GioHang> call, Response<GioHang> response) {
               }
               @Override
               public void onFailure(Call<GioHang> call, Throwable t) {
                   Log.d("DEBUG","Fail"+t.getMessage());
               }
           });
           api.deletegh(list_id_gio.get(i)).enqueue(new Callback<SanPham>() {
               @Override
               public void onResponse(Call<SanPham> call, Response<SanPham> response) {
               }
               @Override
               public void onFailure(Call<SanPham> call, Throwable t) {
                   Log.d("DEBUG","Fail"+t.getMessage());
               }
           });
       }
        Toast.makeText(DatNhieuHang_Activity.this, "Dat hang thanh cong", Toast.LENGTH_SHORT).show();
        Intent a = new Intent(getBaseContext(),GioHangActivity.class);
        a.putExtra("id",id_user);
        startActivity(a);
        finish();

    }

}