package com.example.apiproduct.Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apiproduct.MainActivity;
import com.example.apiproduct.Model.Chat;
import com.example.apiproduct.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatAdapter extends BaseAdapter {
    private List<Chat> list;
    private  String id_user ;

    public ChatAdapter(List<Chat> list,String id_user) {
        this.list = list;
        this.id_user = id_user;
    }

    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View itemView;
        Chat chat = list.get(i);
//        if(view == null){
//            if ()
//            itemView=View.inflate(viewGroup.getContext(), R.layout.item_chat_left,null);
//        }else {
//            itemView = view;
//        }
      if(chat.getId_user().get_id().equals(id_user)){
          itemView = View.inflate(viewGroup.getContext(), R.layout.item_chat_left,null);
      }else {
          itemView = View.inflate(viewGroup.getContext(), R.layout.item_chat_right,null);
      }
        ImageView img = itemView.findViewById(R.id.img_user_chat);
        TextView name = itemView.findViewById(R.id.txt_mess);
        Picasso.get().load(MainActivity.Apidress +chat.getId_user().getAnh()).into(img);
        name.setText(chat.getMess());
        return itemView;
    }
}
