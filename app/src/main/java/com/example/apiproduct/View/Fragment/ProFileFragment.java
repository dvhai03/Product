package com.example.apiproduct.View.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.apiproduct.MainActivity;
import com.example.apiproduct.Model.ProFile;
import com.example.apiproduct.R;
import com.example.apiproduct.Service.ProFileService;
import com.example.apiproduct.Updatethongtin_Activity;
import com.example.apiproduct.View.DonHangActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProFileFragment extends Fragment {

    public static String id;
    public  static  String name;
    public  static  String linkanh;
    ProFileService proFileService;
    List<ProFile> list;

    Intent i;
    Bundle bundle;

    Button btn_don,btn_logout,btn_doimk,btn_updatett;
    ImageView imageView;
    TextView hoten;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pro_file, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Anhxa(view);

        Onclick();

        list = new ArrayList<>();
        i = getActivity().getIntent();
        bundle = i.getBundleExtra("data");

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

        TextInputEditText matkhau,matkhau_new,checkmk;

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

    public void Api(){
        proFileService.ProFile(bundle.getString("token")).enqueue(new Callback<ProFile>() {
            @Override
            public void onResponse(Call<ProFile> call, Response<ProFile> response) {
                hoten.setText(response.body().getHoten());
                Picasso.get().load(MainActivity.Apidress+response.body().getAnh()).into(imageView);
                id =response.body().get_id();
                name = response.body().getHoten();
                linkanh = response.body().getAnh();
            }
            @Override
            public void onFailure(Call<ProFile> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });
    }

    public void Onclick(){
        btn_updatett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), Updatethongtin_Activity.class);
                i.putExtra("id",id);
                i.putExtra("hoten",name);
                i.putExtra("anh",linkanh);
                startActivity(i);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open();
            }
        });

        btn_doimk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Doimk(Gravity.CENTER,id);
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

    public void Anhxa(View view){
        proFileService = new ProFileService();
        btn_logout = view.findViewById(R.id.btn_logout);
        imageView = view.findViewById(R.id.img_user);
        btn_don = view.findViewById(R.id.btn_donhang);
        btn_updatett = view.findViewById(R.id.ed_update);
        btn_doimk = view.findViewById(R.id.btn_mk);
        hoten = view.findViewById(R.id.txt_hoten);
    }

    @Override
    public void onResume() {
        Api();
        super.onResume();
    }
    public void open(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Bạn muốn đăng xuất ? ");

        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Logout();
            }
        });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}