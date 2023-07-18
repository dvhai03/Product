package com.example.apiproduct.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.apiproduct.Adapter.ListDonHangAdapter;
import com.example.apiproduct.Model.DonHang;
import com.example.apiproduct.R;
import com.example.apiproduct.Service.SanPhamService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class DangGiaoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dang_giao, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        Intent i = getActivity().getIntent();
        String id_user =  i.getStringExtra("id_user");
        ListView lv = view.findViewById(R.id.lv_danggiao);
        List<DonHang> donHang1 = new ArrayList<>();
        ListDonHangAdapter adapter = new ListDonHangAdapter(donHang1,"Đang giao",id_user);
        lv.setAdapter(adapter);
        SanPhamService sanPhamService = new SanPhamService();
        sanPhamService.listDonHang(id_user,"Đã xác nhận").subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<DonHang>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<DonHang> donHangs) {
                        for(DonHang dh :donHangs){
                            donHang1.add(dh);
                            adapter.notifyDataSetInvalidated();
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG","Fail"+e.getMessage());
                    }
                });
        super.onViewCreated(view, savedInstanceState);
    }
}