package com.example.apiproduct.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

;

import com.example.apiproduct.Adapter.SanPhamAdapter;
import com.example.apiproduct.Adapter.SelectListener;
import com.example.apiproduct.Model.ProFile;
import com.example.apiproduct.Model.SanPham;
import com.example.apiproduct.R;
import com.example.apiproduct.Service.ProFileService;
import com.example.apiproduct.Service.SanPhamService;
import com.example.apiproduct.View.ChitietSP;
import com.example.apiproduct.View.GioHangActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements SelectListener {
    RecyclerView recyclerView;
    SanPhamService apiService;
    ImageView giohang,search;
    List<SanPham> list;
    SanPhamAdapter sanPhamAdapter;
    EditText ed_search;

    ProFileService proFileService;
    public String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView =view.findViewById(R.id.rcy_litsSP);
        giohang = view.findViewById(R.id.img_giohang);
        ed_search = view.findViewById(R.id.ed_search);
        search = view.findViewById(R.id.searchsp);
        apiService= new SanPhamService();
       recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        search(ed_search.getText().toString());
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), GioHangActivity.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(ed_search.getText().toString());
            }
        });


        Intent in = getActivity().getIntent();
        Bundle bundle = in.getBundleExtra("data");
        proFileService=new ProFileService();
        proFileService.ProFile(bundle.getString("token")).enqueue(new Callback<ProFile>() {
            @Override
            public void onResponse(Call<ProFile> call, Response<ProFile> response) {
                id= response.body().get_id();
            }
            @Override
            public void onFailure(Call<ProFile> call, Throwable t) {
                Log.e("DEBUG",t.getMessage());
            }
        });

    }

    private void search(String tensp){
        list = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getContext(),list,this);
        recyclerView.setAdapter(sanPhamAdapter);
        apiService.getSP(tensp)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<SanPham>>() {
                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<SanPham> sanPhams) {
                        Log.d("DEBUG","Succes");
                        for(SanPham sp : sanPhams){
                            list.add(sp);
                            sanPhamAdapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        Log.d("DEBUG","Fail"+e.getMessage());
                    }
                });

    }

    @Override
    public void onItemClicked(SanPham sanPham) {
        Intent i = new Intent(getContext(), ChitietSP.class);
        i.putExtra("_id",sanPham.get_id());
        i.putExtra("id_user",id);
        startActivity(i);
    }
}