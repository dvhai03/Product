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

import com.example.apiproduct.Address_Activity;
import com.example.apiproduct.MainActivity;
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
    TextView tensp,gia,sl,sum,txt,address;
    ImageView img,imga;
    Button btn_mua;
    String id_user;
    int soluong;
    SanPham1 sanp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dat_hang);

        Anhxa();

        Intent intent = getIntent();
        sanp  = (SanPham1) intent.getSerializableExtra("data");
        soluong = intent.getIntExtra("sl",0);
        id_user  = intent.getStringExtra("id");


        txt.setText("Thanh toán");
        tensp.setText(sanp.getTensp());
        gia.setText(ChitietSP.Fomatprice(sanp.getGiatien())+" VND");
        sl.setText(soluong+"");
        sum.setText(ChitietSP.Fomatprice(sanp.getGiatien()*soluong)+" VND");
        Picasso.get().load(MainActivity.Apidress +sanp.getAnh()).into(img);

        Onclick();

    }
    public void Anhxa(){
        tensp = findViewById(R.id.txt_tensp_muangay);
        gia = findViewById(R.id.txt_giatien_muangay);
        sl = findViewById(R.id.txt_soluong_muangay);
        sum = findViewById(R.id.txt_sum_muangay);
        address = findViewById(R.id.id_address);
        img = findViewById(R.id.img_muangay);
        btn_mua = findViewById(R.id.btn_muangay);
        txt = findViewById(R.id.txt);
        imga = findViewById(R.id.icon_out);
        api = new SanPhamService();
    }

    public void Onclick(){
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
        imga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatHang_Activity.this.finish();
            }
        });

        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getBaseContext(), Address_Activity.class);
                i.putExtra("id",id_user);
                startActivity(i);
            }
        });
    }

}