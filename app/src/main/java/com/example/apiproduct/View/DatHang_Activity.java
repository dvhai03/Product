package com.example.apiproduct.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apiproduct.Model.GioHang;
import com.example.apiproduct.Model.SanPham1;
import com.example.apiproduct.R;
import com.example.apiproduct.Service.SanPhamService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DatHang_Activity extends AppCompatActivity {
    SanPhamService api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);
        Intent intent = getIntent();
        SanPham1 sanp = (SanPham1) intent.getSerializableExtra("data");
        int soluong = intent.getIntExtra("sl",0);
        String id_user = intent.getStringExtra("id");

        TextView tensp = findViewById(R.id.txt_tensp_muangay);
        TextView gia = findViewById(R.id.txt_giatien_muangay);
        TextView sl = findViewById(R.id.txt_soluong_muangay);
        TextView sum = findViewById(R.id.txt_sum_muangay);
        ImageView img = findViewById(R.id.img_muangay);
        Button btn_mua = findViewById(R.id.btn_muangay);
        TextView txt = findViewById(R.id.txt);
        ImageView imga = findViewById(R.id.icon_out);
        imga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatHang_Activity.this.finish();
            }
        });

        txt.setText("Thanh toán");

        api = new SanPhamService();


        tensp.setText(sanp.getTensp());
        gia.setText(sanp.getGiatien()+"");
        sl.setText(soluong+"");
        sum.setText(sanp.getGiatien()*soluong+"");
        Picasso.get().load("http://192.168.1.190:3000/"+sanp.getAnh()).into(img);

        btn_mua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                api.addhoadon(sanp.get_id(),id_user,"Phuong tu ung hoa",soluong,Double.parseDouble(sum.getText().toString())).enqueue(new Callback<GioHang>() {
                    @Override
                    public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                    }

                    @Override
                    public void onFailure(Call<GioHang> call, Throwable t) {
                        Log.d("DEBUG","Fail"+t.getMessage());
                    }
                });
                Toast.makeText(DatHang_Activity.this, "Đặt thành công", Toast.LENGTH_SHORT).show();
                DatHang_Activity.this.finish();
            }
        });

    }


}