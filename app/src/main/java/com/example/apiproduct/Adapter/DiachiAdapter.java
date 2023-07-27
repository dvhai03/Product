package com.example.apiproduct.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apiproduct.Model.Binhluan;
import com.example.apiproduct.Model.Diachi;
import com.example.apiproduct.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DiachiAdapter extends BaseAdapter {
    private List<Diachi> list;
    private Context context;

    public DiachiAdapter(List<Diachi> list,Context context) {
        this.list = list;
        this.context = context;
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
            itemView=View.inflate(viewGroup.getContext(), R.layout.item_address,null);

        }else {
            itemView = view;
        }
        Diachi diachi = list.get(i);
        TextView hoten = itemView.findViewById(R.id.txt_hotendress);
        TextView sdt = itemView.findViewById(R.id.txt_sdtdress);
        TextView address = itemView.findViewById(R.id.txt_diachidress);

        hoten.setText(diachi.getHoten());
        sdt.setText(diachi.getSodienthoai());
        address.setText(diachi.getDiachi());



        return itemView;
    }

}

