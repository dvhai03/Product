package com.example.apiproduct;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.apiproduct.Adapter.ChatAdapter;
import com.example.apiproduct.Model.Api;
import com.example.apiproduct.Model.Chat;
import com.example.apiproduct.Model.ProFile;
import com.example.apiproduct.Service.SocketManager;
import com.example.apiproduct.Service.UserService;

import java.util.ArrayList;
import java.util.List;

import io.socket.emitter.Emitter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {
    private EditText megTextInput;
    List<Chat> list;
    UserService api;
    ProFile listpr=new ProFile();
    ListView lv;
    ChatAdapter adapter;
    private Button sendButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_chat);
        SocketManager.getInstance().connect();
        Intent i = getIntent();
       listpr=(ProFile) i.getSerializableExtra("profile");
        megTextInput = findViewById(R.id.megTextInput);
        sendButton = findViewById(R.id.send_button);
        lv = findViewById(R.id.lv_chat);
        list = new ArrayList<>();

        api = new UserService();
        api.getchat().enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(Call<List<Chat>> call, Response<List<Chat>> response) {
                    list=response.body();
                    adapter = new ChatAdapter(list,listpr.get_id());
                    lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Chat>> call, Throwable t) {
                Log.e("Debug", "onFailure: "+t );
            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = megTextInput.getText().toString();
                Chat newchat = new Chat(listpr,message);
                SocketManager.getInstance().emit("TestEvent", message);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.add(newchat);
                        adapter.notifyDataSetChanged();
                        Api(listpr.get_id(),message);
                    }
                });
                megTextInput.setText("");
            }
        });
        SocketManager.getInstance().on("NotifyFromServer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ProFile proFile = new ProFile("Đỗ Văn Hải","dvhai2k3@gmail.com","64abeff31607c81974bd46f4","desktop-wallpaper-luffy-manga-badasse-thumbnail.jpg");
                Chat newchat = new Chat(proFile,args[0].toString());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        list.add(newchat);
                        adapter.notifyDataSetChanged();

                    }
                });

            }
        });

    }
    public void Api(String id,String mess){
        api.postchat(id, mess).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(ChatActivity.this, "Đã gửi", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("Debug", "onFailure: "+t);
            }
        });
    }
}