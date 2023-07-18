package com.example.apiproduct.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apiproduct.Model.Binhluan;
import com.example.apiproduct.R;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BinhluanAdapter extends BaseAdapter {
    final List<Binhluan> list;

    public BinhluanAdapter(List<Binhluan> list) {
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
            itemView=View.inflate(viewGroup.getContext(), R.layout.item_binhluan,null);

        }else {
            itemView = view;
        }


        Binhluan binhluan = list.get(i);
        ImageView img = itemView.findViewById(R.id.img_anh);
        TextView name = itemView.findViewById(R.id.txt_name_user);
        TextView commen = itemView.findViewById(R.id.txt_comment);
        Picasso.get().load("http://192.168.1.190:3000/"+binhluan.getId_sanpham().getAnh()).into(img);
        name.setText(binhluan.getId_user().getHoten());
        commen.setText(binhluan.getBinhluan());
        return itemView;
    }
}
