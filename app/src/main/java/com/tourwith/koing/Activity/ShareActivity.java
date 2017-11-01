package com.tourwith.koing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.tourwith.koing.Firebase.FirebaseChatroom;
import com.tourwith.koing.Firebase.FirebaseMessenger;
import com.tourwith.koing.Model.Chatroom;
import com.tourwith.koing.R;

public class ShareActivity extends AppCompatActivity {

    private Button backButton;
    private ListView listView;

    private String uid;
    private String country;
    private String title;
    private String addr;
    private FirebaseChatroom firebaseChatroom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        country = intent.getStringExtra("country");
        title = intent.getStringExtra("title");
        addr = intent.getStringExtra("addr");

        listView = (ListView) findViewById(R.id.message_list_view_in_share);
        backButton = (Button) findViewById(R.id.back_button_in_share);
        firebaseChatroom = new FirebaseChatroom(this, listView, uid, getFragmentManager(), null);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chatroom chatroom = firebaseChatroom.getChatroomList().get(position);
                chatroom.setmUID(uid);
                FirebaseMessenger firebaseMessenger = new FirebaseMessenger(chatroom);
                firebaseMessenger.share(country, title, addr);
                Toast.makeText(ShareActivity.this, "Complete!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


    }
}