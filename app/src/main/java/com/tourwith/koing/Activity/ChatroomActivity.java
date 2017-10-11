package com.tourwith.koing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tourwith.koing.Firebase.FirebaseMessenger;
import com.tourwith.koing.Firebase.FirebasePicture;
import com.tourwith.koing.Model.Chatroom;
import com.tourwith.koing.R;

public class ChatroomActivity extends AppCompatActivity {


    private Intent intent; //opponent UID와(oUID) Chatroom Key 포함(key), my UID는 FirebaseAuth에서 가져옴.
    private FirebaseMessenger firebaseMessenger;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    //private Chatroom
    private Chatroom chatroom;
    private final String TAG = "인증";

    FirebasePicture firebasePicture;

    Button sendButton;
    EditText msgEdit;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        initView(); //initialize views
        initAuth(); //initialize Authentication, DB, and Storage

    }

    private void initView() {
        intent = getIntent();
        chatroom = new Chatroom(intent.getStringExtra("ouid"), intent.getStringExtra("key"));
        getSupportActionBar().setTitle(chatroom.getoUID() + "의 대화");

        sendButton = (Button) findViewById(R.id.msg_send_button);
        msgEdit = (EditText) findViewById(R.id.msg_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);

        // + 시간 나면 나중에 editText 리스너 추가하기

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //vaildity
                if(msgEdit.getText().toString().length()>0 && (chatroom.getmUID() !=null && !chatroom.getmUID().equals(""))){
                    String messageContent = msgEdit.getText().toString();
                    msgEdit.setText("");
                    firebaseMessenger.writeMessage(messageContent);
                }
            }
        });

    }

    private void initAuth() {

        firebasePicture = new FirebasePicture(this);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    chatroom.setmUID(user.getUid());
                    firebaseMessenger = new FirebaseMessenger(ChatroomActivity.this, recyclerView, chatroom);
                    firebasePicture.downLoadProfileImageToChatRoom(chatroom.getoUID(), FirebasePicture.ORIGINAL, firebaseMessenger); //프로필사진 다운로드
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    chatroom.setmUID("");
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }

    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}


