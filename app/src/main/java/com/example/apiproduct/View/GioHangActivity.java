package com.example.apiproduct.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
    TextView delete, sumprice;
    Button btn_mua;
    ImageView btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        api();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              finish();
            }
        });
    }

    @Override
    public void onQuantityChange(List<GioHang> list1) {



        double sum=0;
        id_user=i.getStringExtra("id");
        arrlistid.clear();
        arrlist.clear();
        for (int i =0;i<list1.size();i++){
            sum+=list1.get(i).getSanPham().getGiatien()*list1.get(i).getSoluong();
            arrlist.add(list1.get(i).getSanPham().get_id());
            arrlistid.add(list1.get(i).getId());
        }

        sumprice.setText("₫"+ChitietSP.Fomatprice(sum)+" VND");

        OnClick(list1);
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

    public void Anhxa(){
        delete = findViewById(R.id.txt_delete);
        sumprice = findViewById(R.id.txt_sumprice);
        btn_mua= findViewById(R.id.btn_dat);
        btn_back = findViewById(R.id.out_giohang);
        arrlist= new ArrayList<String>();
        arrlistid = new ArrayList<String>();
    }

    public void OnClick(List<GioHang> list1){
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open(list1);
            }
        });

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
    public void open(List<GioHang> list1){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Chăc chắn xóa ! ");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
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

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onResume() {
       gioHangAdapter.notifyDataSetInvalidated();
        super.onResume();
    }
}