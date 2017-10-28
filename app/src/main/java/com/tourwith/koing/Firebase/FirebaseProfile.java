package com.tourwith.koing.Firebase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tourwith.koing.Activity.MainActivity;
import com.tourwith.koing.Activity.SignUpActivity;
import com.tourwith.koing.Model.User;

/**
 * Created by hanhb on 2017-10-11.
 */

/*
    FirebaseProfile
    Create : UID와 User Model을 받은 뒤 파이어베이스 데이터베이스에 write한다.
    Read : UID를 받은 뒤, UID에 해당하는 사람의 정보를 User 형태로 반환한다.
    Update : create와 같음. (push하지 않음.)
    Destroy : UID에 해당하는 정보를 지움.

    Firebase realtime database 경로 :
 */

public class FirebaseProfile {

    private FirebaseDatabase database;
    private DatabaseReference userRef;

    public FirebaseProfile() {
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference().child("user");

    }


    public void writeUser(User user, String uid){
        if(user == null || uid==null || uid.equals(""))
            return;
        DatabaseReference specificUser = userRef.child(uid);
        specificUser.setValue(user);
    }

    public void checkUserAndMove(final String uid, final Activity activity, final ProgressDialog progressDialog){

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean found = false;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.getKey().equals(uid)){
                        found = true;
                        break;
                    }
                }
                progressDialog.dismiss();
                if(found){ //일치하는 회원정보를 찾았을 경우, 메인으로 이동
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();

                } else { //일치하는 회원정보를 찾지 못했을 경우, 추정보를 입력하도록 함.
                    Intent intent = new Intent(activity, SignUpActivity.class);
                    intent.putExtra("InformationNeeded", true);
                    activity.startActivity(intent);
                    activity.finish();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    public void getUser(final String uid, final TextView textView){

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User userByFirebase = null;
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    if(ds.getKey().equals(uid)){
                        textView.setText(ds.getKey());
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }



}
