package com.tourwith.koing.Firebase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tourwith.koing.Activity.MainActivity;
import com.tourwith.koing.Activity.SignUpActivity;
import com.tourwith.koing.Model.User;
import com.tourwith.koing.Util.SharedPreferenceHelper;
import com.tourwith.koing.ViewPager.LanguageToFlag;

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

    public void updateUserMainProfile(User user, String uid){

        DatabaseReference specificUser = userRef.child(uid);
        specificUser.child("mainLang").setValue(user.getMainLang());
        specificUser.child("nationality").setValue(user.getNationality());
        specificUser.child("nickname").setValue(user.getNickname());

    }

    public void updateUserLangs(String uid, String language1, String language2){

        DatabaseReference specificUser = userRef.child(uid);
        specificUser.child("lang1").setValue(language1);
        specificUser.child("lang2").setValue(language2);
    }


    public void updateUserIntroduction(String uid, String introduction){

        DatabaseReference specificUser = userRef.child(uid);
        specificUser.child("comments").setValue(introduction);
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
                    SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(activity);
                    sharedPreferenceHelper.putBoolean(uid, true); //빠른 자동 로그인을 위해 서버를 거치지 않는 방식 사용

                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();

                } else { //일치하는 회원정보를 찾지 못했을 경우, 추정보를 입력하도록 함.
                    Intent intent = new Intent(activity, SignUpActivity.class);
                    intent.putExtra("InformationNeeded", true);
                    activity.startActivity(intent);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void getUser(Context context, String uid, final TextView nameText, final TextView profileNationLanguageTextView,
                        final TextView introductionText, ImageView profileImage, final TextView languageText1, final TextView languageText2){

        FirebasePicture firebasePicture = new FirebasePicture(context);
        firebasePicture.downLoadProfileImage(uid, FirebasePicture.ORIGINAL, profileImage);

        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(nameText!=null) nameText.setText(user.getNickname());
                if(profileNationLanguageTextView!=null) {
                    profileNationLanguageTextView.setText(user.getNationality());
                    profileNationLanguageTextView.append(" \u2022 " + user.getMainLang());
                }
                if(introductionText!=null) introductionText.setText(user.getComments());
                if(!user.getLang1().equals("")){
                    languageText1.setText(user.getLang1());
                    languageText1.setVisibility(View.VISIBLE);
                }
                if(!user.getLang2().equals("")){
                    languageText2.setText(user.getLang2());
                    languageText2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





    }

    public void getUserName(String uid, final TextView nameText){
        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(nameText!=null) nameText.setText(user.getNickname());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void getUserInfo(String uid, final TextView nameText, final TextView nationalityText, final TextView
            mainLangText, final TextView informationText, final ImageView flagImage, final Context context){
        userRef.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                nameText.setText(user.getNickname());
                nationalityText.setText(user.getNationality());
                mainLangText.setText(user.getMainLang());
                informationText.setText(user.getComments());
                flagImage.setBackgroundResource(new LanguageToFlag(context).Converter(user.getNationality()));

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }




}
