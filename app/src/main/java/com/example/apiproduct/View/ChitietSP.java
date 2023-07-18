package com.example.apiproduct.View;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.apiproduct.Adapter.BinhluanAdapter;
import com.example.apiproduct.Model.Binhluan;
import com.example.apiproduct.Model.SanPham;
import com.example.apiproduct.Model.SanPham1;
import com.example.apiproduct.R;
import com.example.apiproduct.Service.SanPhamService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChitietSP extends AppCompatActivity {
    SanPhamService apiService;
   TextView tensp , mota,giattien;
   Button btn_addgio;
   Button btn_thanhtoan;
  String anh,namesp,idsp;
  BinhluanAdapter adapter;
  List<Binhluan> list_coment;
    int s= 1;
    String id_user;
    ImageView anhSp;
   double gia;
   SanPham1 sp;
   ListView lv_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_sp);
        anhxa();


        Intent i = getIntent();
        id_user= i.getStringExtra("id_user");
        apiService= new SanPhamService();
        idsp = i.getStringExtra("_id");

        ImageView back = findViewById(R.id.icon_out);
        TextView txt = findViewById(R.id.txt);
        txt.setText("Chi tiết sản phẩm");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChitietSP.this.finish();
            }
        });

        apiService.getChitiet(idsp).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                anh = response.body().getAnh();
                namesp = response.body().getTensp();
                gia = response.body().getGiatien();
                sp = new SanPham1(namesp,idsp,anh,response.body().getMota(),response.body().getGiatien());
                Picasso.get().load("http://192.168.1.190:3000/"+anh).into(anhSp);
                tensp.setText(namesp);
                mota.setText(response.body().getMota());
                giattien.setText("₫"+gia+"");
            }
            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
        btn_addgio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogadd();
            }
        });
        binhluan(idsp);


        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              dialogdathang();
            }
        });
    }
    private void addgio(String id_sp,String id_user,int sl){
        apiService.addght(id_sp,id_user,sl).enqueue(new Callback<SanPham>() {
            @Override
            public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                if(response.code()==201){
                    Toast.makeText(ChitietSP.this, "Thêm vào giỏ thành công", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SanPham> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }
    private void dialogadd(){
        Dialog dialog =new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_gio);
        Button btn_addgh = dialog.findViewById(R.id.addgio);
        TextView sl = dialog.findViewById(R.id.txt_soluong);
        ImageView img = dialog.findViewById(R.id.img_anh);
        TextView txt_namesp = dialog.findViewById(R.id.txt_namesp);

        Button cong = dialog.findViewById(R.id.btn_tang);

        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s++;

            sl.setText(s+"");
            }
        });

        Button giam = dialog.findViewById(R.id.btn_giam);

        giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s--;
                if (s<=0){
                    Toast.makeText(ChitietSP.this, "số lượng k hợp lệ", Toast.LENGTH_SHORT).show();
                    s=1;
                }
                sl.setText(s+"");
            }
        });

        btn_addgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addgio(idsp,id_user,s);
                dialog.dismiss();
            }
        });

        Picasso.get().load("http://192.168.1.190:3000/"+anh).into(img);
        txt_namesp.setText(namesp);
        Window window =dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getAttributes().windowAnimations = R.style.DialogStyle;
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }
    private void dialogdathang(){
        Dialog dialog =new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_gio);
        Button btn_addgh = dialog.findViewById(R.id.addgio);
        TextView sl = dialog.findViewById(R.id.txt_soluong);
        ImageView img = dialog.findViewById(R.id.img_anh);
        TextView txt_namesp = dialog.findViewById(R.id.txt_namesp);
        Button cong = dialog.findViewById(R.id.btn_tang);
        cong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s++;

                sl.setText(s+"");
            }
        });

        Button giam = dialog.findViewById(R.id.btn_giam);

        giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s--;
                if (s<=0){
                    Toast.makeText(ChitietSP.this, "số lượng k hợp lệ", Toast.LENGTH_SHORT).show();
                    s=1;
                }
                sl.setText(s+"");
            }

        });
         btn_addgh.setText("Mua ngay");
        btn_addgh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
              Intent i = new Intent(getBaseContext(),DatHang_Activity.class);
              i.putExtra("data",sp);
              i.putExtra("sl",s);
                i.putExtra("id",id_user);
              startActivity(i);
            }
        });

        Picasso.get().load("http://192.168.1.190:3000/"+anh).into(img);
        txt_namesp.setText(namesp);
        Window window =dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getAttributes().windowAnimations = R.style.DialogStyle;
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }
    public void anhxa(){
        btn_addgio = findViewById(R.id.btn_themgio);
        lv_list = findViewById(R.id.lv_đanhgia);
         anhSp= findViewById(R.id.img_sanpham);
        tensp = findViewById(R.id.txt_tensp);
        mota = findViewById(R.id.txt_mota);
        giattien = findViewById(R.id.txt_gia);
        list_coment = new ArrayList<>();
        adapter = new BinhluanAdapter(list_coment);
        lv_list.setAdapter(adapter);
        btn_thanhtoan = findViewById(R.id.btn_thanhtoan);
    }
    public void binhluan(String id){
        apiService.listbinhluan(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Binhluan>>() {
                    @Override
                    public void onSuccess(@NonNull List<Binhluan> binhluans) {
                        for (Binhluan bl : binhluans){
                            list_coment.add(bl);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("DEBUG","Fail"+e.getMessage());
                    }
                });
    }

}