package com.example.apiproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apiproduct.Adapter.DiachiAdapter;
import com.example.apiproduct.Model.Diachi;
import com.example.apiproduct.Service.UserService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Address_Activity extends AppCompatActivity {
    private FloatingActionButton btn_add;
    UserService api;
    ListView lv;
    List<Diachi> list;
    DiachiAdapter adapter;
    Intent i ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Anhxa();
        lv.setAdapter(adapter);

        String id = i.getStringExtra("id");
        getdress(id);
        Onclick();
    }

    private void Onclick() {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog_add();
            }
        });
    }

    private void Anhxa() {
        api = new UserService();

        btn_add=findViewById(R.id.btn_add);

        i =getIntent();

        list=new ArrayList<>();

        adapter = new DiachiAdapter(list,Address_Activity.this);

        lv=findViewById(R.id.lv_listadress);

    }
    public void Dialog_add(){
        Dialog dialog =new Dialog(this);
        dialog.setContentView(R.layout.dialog_address);

        TextInputEditText ed_name,ed_sdt,ed_diachi;
        Button btn_cancel,btn_ok;

        ed_name = dialog.findViewById(R.id.txt_nameaddress);
        ed_sdt = dialog.findViewById(R.id.txt_sdtaddress);
        ed_diachi=dialog.findViewById(R.id.txt_diachiaddress);

        btn_cancel = dialog.findViewById(R.id.btn_canceladdress);
        btn_ok = dialog.findViewById(R.id.btn_yesaddress);

        String id_user = i.getStringExtra("id");
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Address(ed_name.getText().toString(),id_user,ed_sdt.getText().toString(),ed_diachi.getText().toString());
                dialog.dismiss();
            }
        });

        Window window =dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributest = window.getAttributes();
        windowAttributest.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributest);

        dialog.show();
    }
    public void Address(String name,String id,String sdt,String diachi){
        api.address(name,id,sdt,diachi).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code()==201){
                    Toast.makeText(Address_Activity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }
    public void getdress(String id){

      api.getList(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Diachi>>() {
                    @Override
                    public void onSuccess(@NonNull List<Diachi> diachis) {
                        for (Diachi a : diachis){
                            list.add(a);
                            adapter.notifyDataSetChanged();
                        }

                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("debug", "onError: "+e);
                    }
                });
    }
}