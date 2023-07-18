package com.example.apiproduct.View.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.apiproduct.MainActivity;
import com.example.apiproduct.Model.ProFile;
import com.example.apiproduct.R;
import com.example.apiproduct.Service.ProFileService;
import com.example.apiproduct.View.DonHangActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProFileFragment extends Fragment {
    ProFileService proFileService;
    List<ProFile> list;
   public static String id;
    Button btn_don,btn_logout,btn_doimk,btn_doiname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pro_file, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        proFileService = new ProFileService();
        btn_logout = view.findViewById(R.id.btn_logout);

        btn_don = view.findViewById(R.id.btn_donhang);




        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });

        btn_doimk = view.findViewById(R.id.btn_mk);
        btn_doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Doimk(Gravity.CENTER,id);
            }
        });
        TextView hoten = view.findViewById(R.id.txt_hoten);
        list = new ArrayList<>();
        Intent i = getActivity().getIntent();
        Bundle bundle = i.getBundleExtra("data");
        proFileService.ProFile(bundle.getString("token")).enqueue(new Callback<ProFile>() {
            @Override
            public void onResponse(Call<ProFile> call, Response<ProFile> response) {
                hoten.setText(response.body().getHoten());
                id =response.body().get_id();
            }
            @Override
            public void onFailure(Call<ProFile> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
        btn_don.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), DonHangActivity.class);
                i.putExtra("id_user",id);
                startActivity(i);
            }
        });
    }
    private void Logout(){
        Intent i = getActivity().getIntent();
        Bundle bundle = i.getBundleExtra("data");
        proFileService.Logout(bundle.getString("token")).enqueue(new Callback<ProFile>() {
            @Override
            public void onResponse(Call<ProFile> call, Response<ProFile> response) {
                if (response.code()==200){

                    Toast.makeText(getContext(), "Đăng xuất thành công", Toast.LENGTH_SHORT).show();

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("auth_token");
                    editor.apply();


                    Intent i =new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    getActivity().finish();

                }
            }
            @Override
            public void onFailure(Call<ProFile> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });

    }
    public void Doimk(int a,String id){
        Dialog dialog =new Dialog(getContext());

        EditText matkhau,matkhau_new,checkmk;

        Button btn_cancel,btn_yes;
        dialog.setContentView(R.layout.dialog_doimk);


        Window window =dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributest = window.getAttributes();
        windowAttributest.gravity = a;
        window.setAttributes(windowAttributest);
        matkhau = dialog.findViewById(R.id.matkhau);
        matkhau_new = dialog.findViewById(R.id.new_matkhau);
        checkmk = dialog.findViewById(R.id.check_mk);

        btn_yes = dialog.findViewById(R.id.btn_yes);

        btn_cancel = dialog.findViewById(R.id.btn_cancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                proFileService.Doimk(id,matkhau.getText().toString(),matkhau_new.getText().toString()).enqueue(new Callback<ProFile>() {
                    @Override
                    public void onResponse(Call<ProFile> call, Response<ProFile> response) {
                        if (response.code()==200){
                            Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProFile> call, Throwable t) {
                        Log.e("Error",t.getMessage());
                    }
                });

            }
        });
        dialog.show();
    }
}