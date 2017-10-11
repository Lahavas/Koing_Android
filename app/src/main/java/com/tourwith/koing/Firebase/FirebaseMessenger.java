package com.tourwith.koing.Firebase;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tourwith.koing.Adapter.MessageAdapter;
import com.tourwith.koing.Model.Chatroom;
import com.tourwith.koing.Model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanhb on 2017-09-28.
 */

public class FirebaseMessenger {
    /*
    FirebaseMessenger

    constructor : context, recyclerView, muid, ouid, key(chatroom) 받음.
    + FirebaseDatabase, FirebaseReference, List FragmentManager 등 정의
    + 상대방 uid를 이용하여 프로필사진을 불러옴.

    Create Read 기능만 구현
     */

    private Context context;
    private FirebaseDatabase database;
    private DatabaseReference messageRef;
    private RecyclerView recyclerView;
    private Chatroom chatroom;
    private byte[] oProfileImgData;

    private MessageEventListener listener;
    private List<Message> list;
    private LinearLayoutManager manager;

    public FirebaseMessenger() {}

    public FirebaseMessenger(Context context, RecyclerView recyclerView,Chatroom chatroom) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.chatroom = chatroom;
        list = new ArrayList<>();
        manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);//temp
        database = FirebaseDatabase.getInstance();
        messageRef = database.getReference().child("chatroom").child(chatroom.getKey()).child("message");
        listener = new MessageEventListener();
        messageRef.addValueEventListener(listener);

    }

    class MessageEventListener implements ValueEventListener{
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            getUpdates(dataSnapshot);
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {
        }
    }

    //한 번만 DB에서 불러옴.
    public void refresh() {
        messageRef.addListenerForSingleValueEvent(new MessageEventListener());
    }

    private void getUpdates(DataSnapshot dataSnapshot) {
        list.clear();

        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            Message vo = ds.getValue(Message.class);
            list.add(vo);
        }

        MessageAdapter adapter = new MessageAdapter(context, list, chatroom.getmUID(), chatroom.getoUID(), oProfileImgData);
        adapter.setList(list);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        manager.scrollToPositionWithOffset(list.size() - 1, 0); //마지막 위치로 스크롤

    }

    public void writeMessage(String message){

        if(message.length()==0 || chatroom.getmUID()== null || chatroom.getoUID()==null || chatroom.getKey()==null){
            Log.d("인증", "writeMessage() : 유효하지 않은 arguments.");
            return;
        }

        DatabaseReference contentRef = messageRef.push();
        Message vo = new Message(message, chatroom.getmUID(), false);
        contentRef.setValue(vo);
    }


    public void setoProfileImgData(byte[] oProfileImgData) {
        this.oProfileImgData = oProfileImgData;
    }

}
