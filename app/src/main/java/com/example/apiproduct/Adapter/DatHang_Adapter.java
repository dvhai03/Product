package com.example.apiproduct.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apiproduct.MainActivity;
import com.example.apiproduct.Model.GioHang;
import com.example.apiproduct.R;

import com.example.apiproduct.View.ChitietSP;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DatHang_Adapter extends BaseAdapter {
    List<GioHang> list;

    public DatHang_Adapter(List<GioHang> list) {
        this.list = list;
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
            itemView=View.inflate(viewGroup.getContext(), R.layout.item_dathang,null);

        }else {
            itemView = view;
        }
   GioHang sp = list.get(i);
        ImageView img = itemView.findViewById(R.id.img_dathang);
        TextView tensp=itemView.findViewById(R.id.txt_tensp_dathang);
        TextView gia=itemView.findViewById(R.id.txt_giatien_dathang);
        TextView soluong = itemView.findViewById(R.id.txt_soluong_dathang);
        TextView tong = itemView.findViewById(R.id.txt_sum_dathang);

            soluong.setText(sp.getSoluong()+"");
            tensp.setText(sp.getSanPham().getTensp());
            gia.setText(ChitietSP.Fomatprice(sp.getSanPham().getGiatien())+" VND");
            tong.setText("Tổng tiền hàng : "+ChitietSP.Fomatprice(sp.getSanPham().getGiatien()*Integer.parseInt(soluong.getText().toString()))+"VND");
            Picasso.get().load(MainActivity.Apidress +sp.getSanPham().getAnh()).into(img);

        return itemView;
    }
}
