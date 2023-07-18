package com.example.apiproduct.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apiproduct.R;


public class SanPhamHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView tensp,gia;
    public CardView cardView;
    public SanPhamHolder(@NonNull View itemView) {
        super(itemView);
        imageView =itemView.findViewById(R.id.img_anh);
        tensp = itemView.findViewById(R.id.txt_tensp);
        gia = itemView.findViewById(R.id.txt_gia);
        cardView = itemView.findViewById(R.id.card_view);
    }
}
