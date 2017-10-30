package com.tourwith.koing.Firebase;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tourwith.koing.Model.Chatroom;
import com.tourwith.koing.R;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hanhb on 2017-10-07.
 */

/*
    FirebaseChatroom
    firebase의 채팅룸 목록에 접근합니다.
 */
public class FirebaseChatroom {

    Context context;
    ListView listView;
    List<Chatroom> chatroomList = new LinkedList<>();
    FirebaseDatabase database;
    DatabaseReference boardRef;
    FragmentManager fragmentManager;
    String uid;
    ImageView background_if_none;
    public FirebaseChatroom(){}

    public FirebaseChatroom(Context context){
        this.context = context;
        database = FirebaseDatabase.getInstance();
        boardRef = database.getReference("chatroom");
    }


    //한 번만 DB에서 불러옴.
    public void refresh() {
        boardRef = database.getReference("chatroom");
        boardRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getChatroomUpdates(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List<Chatroom> getChatroomList() {
        return chatroomList;
    }

    public FirebaseChatroom(Context context, ListView listView, String uid, FragmentManager fragmentManager, ImageView background_if_none) {
        this.context = context;
        this.listView = listView;
        database = FirebaseDatabase.getInstance();
        this.background_if_none = background_if_none;
        this.uid = uid;
        this.fragmentManager = fragmentManager;

        boardRef = database.getReference("chatroom");
        boardRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                getChatroomUpdates(dataSnapshot);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void writeChatroom(final String mUID, final String oUID){

        ValueEventListener checkDuplicateListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean isDup = false;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    Chatroom chatroom = ds.getValue(Chatroom.class); //중복 체크
                    if((chatroom.getmUID().equals(mUID) && chatroom.getoUID().equals(oUID))
                            || (chatroom.getmUID().equals(oUID)  && chatroom.getoUID().equals(mUID))
                            || mUID.equals(oUID)) {
                        isDup = true;
                        break;
                    }
                }
                if(!isDup) { //중복되는 chatroom이 없다면, chatroom 생성
                    DatabaseReference ref = boardRef.push(); //chatroom 생성
                    Chatroom vo = new Chatroom(mUID, oUID, ref.getKey());
                    ref.setValue(vo);
                    Toast.makeText(context, "succeeded : Chatroom created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "failed : Chatroom duplicated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        boardRef.addListenerForSingleValueEvent(checkDuplicateListener);
    }

    public void destroyChatroom(String key){
        FirebaseDatabase.getInstance().getReference("chatroom").child(key).removeValue();
    }

    private void getChatroomUpdates(DataSnapshot dataSnapshot) {

        class ChatroomAdapter extends BaseAdapter {
            FirebasePicture firebasePicture = new FirebasePicture(context);
            LayoutInflater inflater;

            @Override
            public int getCount() {
                return chatroomList.size();
            }

            @Override
            public Chatroom getItem(int position) {
                return chatroomList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return chatroomList.get(position).hashCode();
            }


            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(inflater == null)
                    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                if(convertView == null) {
                    convertView = inflater.inflate(R.layout.item_chatroom_list, parent, false);
                    TextView oNameText = (TextView) convertView.findViewById(R.id.o_name_text_in_list);
                    TextView recentTimeText = (TextView) convertView.findViewById(R.id.recent_time_text);
                    TextView recentMessageText = (TextView) convertView.findViewById(R.id.recent_message_text);
                    final Chatroom chatroom = chatroomList.get(position);

                    //프로필사진 넣기
                    ImageView profileImage = (ImageView) convertView.findViewById(R.id.ProfileImageAtChatroomList);
                    profileImage.setBackground(new ShapeDrawable(new OvalShape()));
                    profileImage.setClipToOutline(true);
                    firebasePicture.downLoadProfileImage(uid.equals(chatroom.getmUID()) ? chatroom.getoUID() : chatroom.getmUID() , FirebasePicture.ORIGINAL, profileImage);

                    // 이름 넣기
                    FirebaseProfile firebaseProfile = new FirebaseProfile();
                    firebaseProfile.getUserName((uid.equals(chatroom.getmUID()) ? chatroom.getoUID() : chatroom.getmUID()), oNameText);

                    //마지막 메시지 불러오기
                    FirebaseMessenger firebaseMessenger = new FirebaseMessenger();
                    firebaseMessenger.getLastRecevedMessage((uid.equals(chatroom.getmUID()) ? chatroom.getoUID() : chatroom.getmUID()), chatroom.getKey(), recentMessageText, recentTimeText);


                }
                return convertView;
            }
        }
        boolean isExist = false;
        chatroomList.clear();
        for(DataSnapshot ds : dataSnapshot.getChildren()) {
            Chatroom vo = ds.getValue(Chatroom.class);
            if(uid.equals(vo.getmUID()) || uid.equals(vo.getoUID())) {
                chatroomList.add(vo);
                isExist = true;
            }
        }

        if(isExist) {
            background_if_none.setVisibility(View.GONE);
        }else {
            background_if_none.setVisibility(View.VISIBLE);
        }

        ChatroomAdapter adaptor = new ChatroomAdapter();
        if(listView!=null)
            listView.setAdapter(adaptor);

    }
}
