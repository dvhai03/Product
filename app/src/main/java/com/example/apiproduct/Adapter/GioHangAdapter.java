package com.example.apiproduct.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apiproduct.Model.GioHang;
import com.example.apiproduct.R;

import com.example.apiproduct.View.ChitietSP;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GioHangAdapter extends BaseAdapter {
    final List<GioHang> list;
    QuanityListener quanityListener;

    List<GioHang>list0=new ArrayList<>();
    public GioHangAdapter(List<GioHang> list,QuanityListener quanityListener) {
        this.list = list;
        this.quanityListener=quanityListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView;
        if(convertView == null){
            itemView=View.inflate(parent.getContext(), R.layout.item_list_gio_hang,null);

        }else {
            itemView = convertView;
        }


        GioHang gioHang = list.get(position);
        ImageView img = itemView.findViewById(R.id.img_list_gio);
        TextView txt_tensp = itemView.findViewById(R.id.txt_tensp_listgio);
        TextView txt_gia = itemView.findViewById(R.id.txt_giatien_listgio);
        TextView txt_sl = itemView.findViewById(R.id.txt_soluong);
        CheckBox boxtrue=itemView.findViewById(R.id.check);
        boxtrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(boxtrue.isChecked()){
                    list0.add(gioHang);
                }else {
                    list0.remove(gioHang);
                }
                quanityListener.onQuantityChange(list0);
            }
        });


        Picasso.get().load("http://192.168.1.190:3000/"+gioHang.getSanPham().getAnh()).into(img);

        txt_gia.setText(ChitietSP.Fomatprice(gioHang.getSanPham().getGiatien()) +" VND");
        txt_tensp.setText(gioHang.getSanPham().getTensp());
        txt_sl.setText(gioHang.getSoluong()+"");
        return itemView;
    }

}