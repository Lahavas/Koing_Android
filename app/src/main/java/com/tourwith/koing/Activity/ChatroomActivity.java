package com.tourwith.koing.Activity;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tourwith.koing.Firebase.FirebaseMessenger;
import com.tourwith.koing.Firebase.FirebasePicture;
import com.tourwith.koing.Firebase.FirebaseProfile;
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

    Button backButton;
    ImageView oProfileImage;
    TextView oNameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        chatroom = new Chatroom();
        initView(); //initialize views
        initAuth(); //initialize Authentication, DB, and Storage

    }

    private void initView() {
        intent = getIntent();
        chatroom.setoUID(intent.getStringExtra("ouid"));
        chatroom.setKey(intent.getStringExtra("key"));
        getSupportActionBar().hide();

        sendButton = (Button) findViewById(R.id.msg_send_button);
        msgEdit = (EditText) findViewById(R.id.msg_edit_text);
        recyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);


        backButton = (Button) findViewById(R.id.back_button_in_chatroom);
        oProfileImage = (ImageView) findViewById(R.id.o_profile_image_in_chatroom);
        oNameText = (TextView) findViewById(R.id.o_name_text_in_chatroom);
        // + 시간 나면 나중에 editText 리스너 추가하기

        //액션바 이름 지정
        oProfileImage.setBackground(new ShapeDrawable(new OvalShape()));
        oProfileImage.setClipToOutline(true);
        FirebaseProfile firebaseProfile = new FirebaseProfile();
        firebaseProfile.getUserName(chatroom.getoUID(), oNameText);

        //액션바 프로필사진 지정
        FirebasePicture firebasePicture = new FirebasePicture(this);
        firebasePicture.downLoadProfileImage(chatroom.getoUID(), FirebasePicture.ORIGINAL, oProfileImage);

        //백버튼 정의
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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


