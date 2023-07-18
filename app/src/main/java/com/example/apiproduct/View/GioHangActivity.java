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


import com.example.apiproduct.Adapter.GioHangAdapter;
import com.example.apiproduct.Adapter.QuanityListener;
import com.example.apiproduct.Model.GioHang;
import com.example.apiproduct.Model.SanPham;
import com.example.apiproduct.R;
import com.example.apiproduct.Service.SanPhamService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangActivity extends AppCompatActivity implements QuanityListener {

    SanPhamService sanPhamService;
    List<GioHang> list;

    GioHangAdapter gioHangAdapter;
    ListView lv;
    Intent i;
    String id_user;
    ArrayList<String> arrlist;
    ArrayList<String> arrlistid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        api();
    }

    @Override
    public void onQuantityChange(List<GioHang> list1) {
        TextView delete = findViewById(R.id.txt_delete);
        double sum=0;
        id_user=i.getStringExtra("id");
        TextView sumprice = findViewById(R.id.txt_sumprice);
        arrlist= new ArrayList<String>();
        arrlistid = new ArrayList<String>();
        for (int i =0;i<list1.size();i++){
            sum+=list1.get(i).getSanPham().getGiatien()*list1.get(i).getSoluong();

            arrlist.add(list1.get(i).getSanPham().get_id());

            arrlistid.add(list1.get(i).getId());
        }
        sumprice.setText("₫"+sum);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0 ;i<list1.size();i++){

                    sanPhamService.deletegh(list1.get(i).getId()).enqueue(new Callback<SanPham>() {
                        @Override
                        public void onResponse(Call<SanPham> call, Response<SanPham> response) {
                            if (response.code()==200){
                            }
                        }
                        @Override
                        public void onFailure(Call<SanPham> call, Throwable t) {
                            Log.d("DEBUG","Fail"+t.getMessage());
                        }
                    });
                }
                api();
                sumprice.setText("₫0");
                Toast.makeText(GioHangActivity.this, "xoa thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
        Button btn_mua = findViewById(R.id.btn_dat);
        btn_mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(),DatNhieuHang_Activity.class);
                i.putStringArrayListExtra("id",arrlist);
                i.putStringArrayListExtra("id_gh",arrlistid);
                i.putExtra("id_user",id_user);
                startActivity(i);

            }
        });
    }
    public void api(){
        lv=findViewById(R.id.lv_list);
        list=new ArrayList<>();
        gioHangAdapter = new GioHangAdapter(list,this);
        lv.setAdapter(gioHangAdapter);
        i = getIntent();
        sanPhamService = new SanPhamService();
        sanPhamService.listgiohang(i.getStringExtra("id"))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<GioHang>>() {
                    @Override
                    public void onSuccess(@NonNull List<GioHang> gioHangs) {
                        for (GioHang sp : gioHangs){
                            list.add(sp);
                            gioHangAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("DEBUG","Fail"+e.getMessage());
                    }
                });
    }
}