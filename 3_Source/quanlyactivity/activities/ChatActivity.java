package com.example.quanlyactivity.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.quanlyactivity.R;
import com.example.quanlyactivity.utils.Utils;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView imgSend;
    EditText edtMess;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initView();
        initControl();
    }

    private void initControl() {
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessToirire();
            }
        });
    }

    private void sendMessToirire() {
        String str_mess = edtMess.getText().toString().trim();
        if (TextUtils.isEmpty(str_mess)){

        }else {
            HashMap<String, Object> message = new HashMap<>();
            message.put(Utils.SENDID,String.valueOf(Utils.user_current.getId()));
            message.put(Utils.RECEIVEDID,Utils.ID_RECEIVED);
            message.put(Utils.MESS,str_mess);
            message.put(Utils.DATETIME,new Date());
            db.collection(Utils.PATH_CHAT).add(message);
        }
    }

    private void initView() {
        db = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.recycleview_chat);
        imgSend = findViewById(R.id.imgchat);
        edtMess = findViewById(R.id.editinputtext);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }
}