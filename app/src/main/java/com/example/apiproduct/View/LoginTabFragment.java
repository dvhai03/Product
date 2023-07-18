package com.example.apiproduct.View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.apiproduct.Model.Login;
import com.example.apiproduct.R;
import com.example.apiproduct.Service.LoginService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginTabFragment extends Fragment {
EditText email , matkhau;
Button btn_login;
LoginService loginService;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_login,container,false);
        email = viewGroup.findViewById(R.id.ed_email);
        matkhau = viewGroup.findViewById(R.id.matkhau);
        btn_login = viewGroup.findViewById(R.id.btn_login);
        loginService = new LoginService();
        email.setTranslationX(300);
        matkhau.setTranslationX(300);

        email.setAlpha(0);
        matkhau.setAlpha(0);

        email.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(2500).start();
        matkhau.animate().translationX(0).alpha(1).setDuration(1000).setStartDelay(2000).start();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        return viewGroup;
    }
    private void Login(){
        String taikhoan = email.getText().toString().trim();
        String pass = matkhau.getText().toString().trim();
        if (taikhoan.isEmpty()) {
            email.setError("Vui lòng nhập địa chỉ email");
        }else if (pass.isEmpty()){
            matkhau.setError("Vui lòng nhập mật khẩu");
        }

        else {
            // Kiểm tra định dạng email bằng biểu thức chính quy
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (!taikhoan.matches(emailPattern)) {
                email.setError("Địa chỉ email không hợp lệ");
            } else {
                loginService.Login(taikhoan,matkhau.getText().toString()).enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.code()==200){
                            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("auth_token", response.body().getToken());
                            editor.apply();
                            Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getActivity(),HomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("token",response.body().getToken());
                            i.putExtra("data",bundle);
                            startActivity(i);
                        }else {
                            Toast.makeText(getContext(), "Sai Thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                        Log.e("DeBug",""+t);
                    }
                });
            }
        }

    }
}
