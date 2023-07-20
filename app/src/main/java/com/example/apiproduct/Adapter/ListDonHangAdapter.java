package com.example.apiproduct.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apiproduct.Model.Binhluan;
import com.example.apiproduct.Model.DonHang;
import com.example.apiproduct.Model.GioHang;
import com.example.apiproduct.R;
import com.example.apiproduct.Service.SanPhamService;

import com.example.apiproduct.View.ChitietSP;
import com.example.apiproduct.View.DonHangActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListDonHangAdapter extends BaseAdapter {
    final List<DonHang> list;
    String trangthai;
    String id_user;
    public ListDonHangAdapter(List<DonHang> list,String trangthai,String id_user) {
        this.list = list;
        this.trangthai = trangthai;
        this.id_user = id_user;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;
        if(view == null){
            itemView=View.inflate(viewGroup.getContext(), R.layout.item_donhang,null);

        }else {
            itemView = view;
        }

        DonHang donHang = list.get(i);
        ImageView img = itemView.findViewById(R.id.img_donhang);
        Button btn_action = itemView.findViewById(R.id.btn_aciton);
        TextView tensp = itemView.findViewById(R.id.txt_tensp_donhang);
        TextView giatien = itemView.findViewById(R.id.txt_giatien_donhang);
        TextView soluong = itemView.findViewById(R.id.txt_soluong_donhang);
        SanPhamService api = new SanPhamService();
        TextView sum =itemView.findViewById(R.id.txt_sum_donhang);
        Picasso.get().load("http://192.168.1.190:3000/"+donHang.getSanpham().getAnh()).into(img);
        tensp.setText(donHang.getSanpham().getTensp());
        giatien.setText(ChitietSP.Fomatprice(donHang.getSanpham().getGiatien())+" VND");
        soluong.setText(donHang.getSoluong()+"");
        sum.setText("Tổng  : "+ChitietSP.Fomatprice(donHang.getSanpham().getGiatien()*donHang.getSoluong())+" VND");
        if(trangthai=="Đã Giao"){
            btn_action.setText("Đánh giá sản phẩm");
            btn_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addbinhluan(itemView,donHang.getSanpham().getAnh(),donHang.getSanpham().getTensp(),donHang.getSanpham().get_id());
                }
            });
        }else if(trangthai=="Chờ xác nhận"){
            btn_action.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(itemView.getContext());
                    alertDialogBuilder.setMessage("Are you sure,You wanted to make decision");

                    alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {

                            api.huydon(donHang.get_id()).enqueue(new Callback<GioHang>() {
                                @Override
                                public void onResponse(Call<GioHang> call, Response<GioHang> response) {
                                    if(response.code()==200){
                                        Toast.makeText(itemView.getContext(), "Huy thanh cong", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(itemView.getContext(), "Lỗi", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override
                                public void onFailure(Call<GioHang> call, Throwable t) {
                                    Log.e("Error",t.getMessage());
                                }
                            });
                        }
                    });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
            });

        }
        else {
            btn_action.setVisibility(itemView.GONE);
        }
        return itemView;
    }
    public void addbinhluan(View view,String anh,String name,String id){
        Dialog dialog =new Dialog(view.getContext());
        dialog.setContentView(R.layout.dialog_binhluan);
       ImageView img_anh = dialog.findViewById(R.id.img_binhyluan);
       EditText binhluan = dialog.findViewById(R.id.txt_binhluan);
       Button add = dialog.findViewById(R.id.btn_add);
        Picasso.get().load("http://192.168.1.190:3000/"+anh).into(img_anh);
        TextView tensp = dialog.findViewById(R.id.txt_tenspbl);
        tensp.setText(name);
        Window window =dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributest = window.getAttributes();
        windowAttributest.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributest);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SanPhamService api = new SanPhamService();
                api.addbinhluan(id,id_user,binhluan.getText().toString()).enqueue(new Callback<Binhluan>() {
                    @Override
                    public void onResponse(Call<Binhluan> call, Response<Binhluan> response) {
                        if (response.code()==201){
                            Toast.makeText(view.getContext(), "Cảm ơn bạn đã đánh giá", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                    @Override
                    public void onFailure(Call<Binhluan> call, Throwable t) {
                        Log.e("Error",t.getMessage());
                    }
                });
            }
        });
        dialog.show();
    }


    }

