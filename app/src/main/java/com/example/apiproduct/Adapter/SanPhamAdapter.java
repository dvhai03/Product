package com.example.apiproduct.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiproduct.MainActivity;
import com.example.apiproduct.Model.SanPham;
import com.example.apiproduct.R;
import com.squareup.picasso.Picasso;


import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamHolder> {

    Context context;
    List<SanPham> sanPhamList;
    private SelectListener listener;

    public SanPhamAdapter(Context context, List<SanPham> sanPhamList,SelectListener listener) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.listener=listener;
    }

    @NonNull
    @Override
    public SanPhamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SanPhamHolder(LayoutInflater.from(context).inflate(R.layout.item_sp,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamHolder holder, @SuppressLint("RecyclerView") int position) {
        Locale locale = new Locale("vi", "VN");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        String giatien = numberFormat.format(sanPhamList.get(position).getGiatien());
        Picasso.get().load(MainActivity.Apidress +sanPhamList.get(position).getAnh()).into(holder.imageView);
        holder.tensp.setText(sanPhamList.get(position).getTensp());
        String id = sanPhamList.get(position).get_id();
        holder.gia.setText("₫"+giatien+" VND");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(sanPhamList.get(position));
            }
        });
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(context.getApplicationContext(), ChitietSP.class);
//                i.putExtra("_id",id);
//                context.startActivity(i);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

}
