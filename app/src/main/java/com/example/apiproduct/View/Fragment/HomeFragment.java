package com.example.apiproduct.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

;

import com.example.apiproduct.Adapter.SanPhamAdapter;
import com.example.apiproduct.Adapter.SelectListener;
import com.example.apiproduct.ChatActivity;
import com.example.apiproduct.Find_Activity;
import com.example.apiproduct.Model.Chat;
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
    ImageView giohang,chat;
    List<SanPham> list;
    SanPhamAdapter sanPhamAdapter;
    EditText ed_search;

    ProFileService proFileService;
    private Parcelable recyclerViewState;
    public String id;
    public boolean is = true;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    RelativeLayout topnavigation ;
    private static int limit = 8;
    private  ProFile intenl = new ProFile();
    public  int  previousScrollY = 0;


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
        chat = view.findViewById(R.id.icon_mess);
        topnavigation = view.findViewById(R.id.topnavogation);
        apiService= new SanPhamService();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        recyclerView.setLayoutManager(layoutManager);
//        search(ed_search.getText().toString(),limit);
        search(ed_search.getText().toString(),limit);

        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), GioHangActivity.class);
                i.putExtra("id",id);
                startActivity(i);
            }
        });

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ChatActivity.class);
                i.putExtra("profile",intenl);
                startActivity(i);
//                Toast.makeText(getContext(), intenl.get_id()+"", Toast.LENGTH_SHORT).show();
            }
        });
        ed_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent i = new Intent(getActivity().getBaseContext(), Find_Activity.class);
            i.putExtra("id_user",id);
            startActivity(i);
            getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });


        Intent in = getActivity().getIntent();
        Bundle bundle = in.getBundleExtra("data");
        proFileService=new ProFileService();
        proFileService.ProFile(bundle.getString("token")).enqueue(new Callback<ProFile>() {
            @Override
            public void onResponse(Call<ProFile> call, Response<ProFile> response) {
                id= response.body().get_id();
                intenl =response.body();

            }
            @Override
            public void onFailure(Call<ProFile> call, Throwable t) {
                Log.e("DEBUG",t.getMessage());
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    // Cuộn xuống, ẩn Bottom Navigation Bar
                    if (is) {
                      topnavigation.setVisibility(View.GONE);
                         is= false;
                    }
                } else if (dy < 0) {
                    // Cuộn lên, hiển thị lại Bottom Navigation Bar
                    if (!is) {
                        topnavigation.setVisibility(View.VISIBLE);
                        is = true;
                    }
                }
                previousScrollY = dy;
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();



                // Nếu không có dữ liệu đang được tải và đã kéo đến cuối danh sách và chưa đến trang cuối cùng
                if (!isLoading && !isLastPage && (visibleItemCount + firstVisibleItemPosition) >=totalItemCount
                        && firstVisibleItemPosition >=0
                     ) {

                    Toast.makeText(getContext(), limit+"", Toast.LENGTH_SHORT).show();
                    recyclerViewState = layoutManager.onSaveInstanceState();
                    limit +=2;
                    search(ed_search.getText().toString(),limit);

                }



            }
        });

    }

    private void search(String tensp,int limit1){
        isLoading = true;
        list = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getContext(),list,this);
        recyclerView.setAdapter(sanPhamAdapter);
        apiService.getSP(tensp,limit1).enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                isLoading = false;
                if(response.isSuccessful()){
                    List<SanPham> sp_new = response.body();
                    if(sp_new!=null&& sp_new.size()>0){
                        list.clear();
                        list.addAll(sp_new);
                        sanPhamAdapter.notifyDataSetChanged();
                        if (list.size() < limit1) {
                            isLastPage = true;
                        }
                    }else {
                        Toast.makeText(getContext(), "Đã hết dữ liêu", Toast.LENGTH_SHORT).show();
                        isLastPage = true;
                    }
                    restoreScrollPosition();
                }else {

                }
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                isLoading = false;
                restoreScrollPosition();
                Log.d("Debug", "onFailure: "+t);
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
    private void restoreScrollPosition() {
        if (recyclerViewState != null) {
            recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        }
    }
}