package com.example.apiproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apiproduct.Adapter.AdapterFind;
import com.example.apiproduct.Model.SanPham;
import com.example.apiproduct.Service.SanPhamService;
import com.example.apiproduct.View.ChitietSP;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Find_Activity extends AppCompatActivity {
    SanPhamService api;
    List<SanPham> list;
    AdapterFind adapter;
    ListView lv;

    String id_user;
EditText search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find);
        Anhxa();
        search.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String key = editable.toString().trim();
                if(key.isEmpty()){
                    list.clear();
                    adapter.notifyDataSetChanged();
                }else {
                    Listfind(key);
                }
            }
        });
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Intent intent = new Intent(getBaseContext(), ChitietSP.class);
               intent.putExtra("id_user",id_user);
               intent.putExtra("_id",list.get(i).get_id());
               startActivity(intent);
           }
       });
    }
    public void Anhxa(){
        Intent i = getIntent();
        api = new SanPhamService();
        search = findViewById(R.id.ed_search);
        list = new ArrayList<>();
        id_user = i.getStringExtra("id_user");
        lv = findViewById(R.id.lv_find);

    }

    public void Listfind(String key){
        api.getSP(key,20).enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                if(response.isSuccessful()){
                    list = response.body();
                    adapter = new AdapterFind(list);
                    lv.setAdapter(adapter);

                }

            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Log.e("Debug", "onFailure: "+t );
            }
        });
    }
}