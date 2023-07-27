package com.example.apiproduct.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apiproduct.MainActivity;
import com.example.apiproduct.Model.Binhluan;
import com.example.apiproduct.Model.SanPham;
import com.example.apiproduct.Model.SanPham1;
import com.example.apiproduct.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdapterFind extends BaseAdapter {
    private List<SanPham> list;

    public AdapterFind(List<SanPham> list) {
        this.list = list;
    }

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
            itemView=View.inflate(viewGroup.getContext(), R.layout.item_find,null);

        }else {
            itemView = view;
        }
        SanPham sanPham = list.get(i);
        ImageView img = itemView.findViewById(R.id.img_anh_find);
        TextView name = itemView.findViewById(R.id.txt_name_find);
        Picasso.get().load(MainActivity.Apidress +sanPham.getAnh()).into(img);
        name.setText(sanPham.getTensp());
        return itemView;
    }
}
