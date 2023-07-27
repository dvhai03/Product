package com.example.apiproduct.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.example.apiproduct.R;
import com.example.apiproduct.Service.SignUpService;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigUpFragment extends Fragment {
    TextInputEditText email,matkhau,hoten,matkhau2;
    SignUpService signUpService;
    Button btn_reg;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.activity_sigup_acitivity,container,false);
        email = viewGroup.findViewById(R.id.ed_emailr);
        matkhau = viewGroup.findViewById(R.id.matkhaur);
        hoten = viewGroup.findViewById(R.id.ed_name);
        matkhau2 = viewGroup.findViewById(R.id.matkhau2);

        signUpService = new SignUpService();
        btn_reg = viewGroup.findViewById(R.id.btn_reg);

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reg1();
            }
        });
        return viewGroup;
    }
    private void Reg1(){

        String taikhoa = email.getText().toString().trim();
        String pass = matkhau.getText().toString().trim();
        String checkpass = matkhau2.getText().toString().trim();

        if (taikhoa.isEmpty()||pass.isEmpty()||checkpass.isEmpty()) {
            // EditText trống
            Toast.makeText(getContext(), "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();

        } else {
            // Kiểm tra định dạng email bằng biểu thức chính quy
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (!taikhoa.matches(emailPattern)) {
                email.setError("Địa chỉ email không hợp lệ");
            } else {
                signUpService.Reg(email.getText().toString(),matkhau.getText().toString(),hoten.getText().toString()).enqueue(new Callback() {
                    @Override
                    public void onResponse(Call call, Response response) {
                        if (response.code()==201){
                            Toast.makeText(getContext(), "Dang Ki thanh cong", Toast.LENGTH_SHORT).show();
                            email.setText("");
                            hoten.setText("");
                            matkhau.setText("");
                            matkhau2.setText("");
                        }else{
                            Toast.makeText(getContext(), "email đã được đăng kí", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(getContext(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }



    }
}
