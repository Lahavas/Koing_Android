package com.tourwith.koing.Activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tourwith.koing.Fragment.HomeFragment;
import com.tourwith.koing.Fragment.MessageFragment;
import com.tourwith.koing.Fragment.MyPageFragment;
import com.tourwith.koing.Fragment.TourInfoFragment;
import com.tourwith.koing.R;
import com.tourwith.koing.Util.BackButtonHandler;

public class MainActivity extends AppCompatActivity {

    FrameLayout container2;
    ImageView tab_home, tab_tour, tab_message, tab_profile;
    Fragment homeFragment;
    Fragment messageFragment;
    Fragment mypageFragment;
    Fragment tourinfoFragment;

    //Authentication : FirebaseAuth와 AuthStateListener 선언
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    public String uid;

    private BackButtonHandler backButtonHandler; //171029 추가됨 + 백버튼 종료 추가

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {

            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    1 );
        }
        getSupportActionBar().hide(); //171029 추가됨 + 백버튼 종료 추가
        backButtonHandler = new BackButtonHandler(this);

        homeFragment = new HomeFragment(this);
        messageFragment = new MessageFragment(this);
        mypageFragment = new MyPageFragment(this);
        tourinfoFragment = new TourInfoFragment(this);

        container2 = (FrameLayout) findViewById(R.id.container2);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container2, homeFragment)
                    .commit();
        }

        tab_home = (ImageView) findViewById(R.id.tab_home);
        tab_tour = (ImageView) findViewById(R.id.tab_tour);
        tab_message = (ImageView) findViewById(R.id.tab_message);
        tab_profile = (ImageView) findViewById(R.id.tab_profile);

        tab_home.setBackgroundResource(R.drawable.btn_tap_home_t);

        tab_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, homeFragment)
                        .commit();

                tab_home.setBackgroundResource(R.drawable.btn_tap_home_t);
                tab_tour.setBackgroundResource(R.drawable.btn_tap_tripcard_n);
                tab_message.setBackgroundResource(R.drawable.btn_tap_message_n);
                tab_profile.setBackgroundResource(R.drawable.btn_tap_mypage_n);
            }
        });

        tab_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, tourinfoFragment)
                        .commit();

                tab_home.setBackgroundResource(R.drawable.btn_tap_home_n);
                tab_tour.setBackgroundResource(R.drawable.btn_tap_tripcard_t);
                tab_message.setBackgroundResource(R.drawable.btn_tap_message_n);
                tab_profile.setBackgroundResource(R.drawable.btn_tap_mypage_n);
            }
        });

        tab_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, messageFragment)
                        .commit();

                tab_home.setBackgroundResource(R.drawable.btn_tap_home_n);
                tab_tour.setBackgroundResource(R.drawable.btn_tap_tripcard_n);
                tab_message.setBackgroundResource(R.drawable.btn_tap_message_t);
                tab_profile.setBackgroundResource(R.drawable.btn_tap_mypage_n);
            }
        });

        tab_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container2, mypageFragment)
                        .commit();

                tab_home.setBackgroundResource(R.drawable.btn_tap_home_n);
                tab_tour.setBackgroundResource(R.drawable.btn_tap_tripcard_n);
                tab_message.setBackgroundResource(R.drawable.btn_tap_message_n);
                tab_profile.setBackgroundResource(R.drawable.btn_tap_mypage_t);
            }
        });


        initAuth();

    }

    //initAuth : Firebase Auth 객체 초기화, 자동 로그인
    private void initAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    uid = user.getUid();
                } else {
                    // User is signed out
                    uid = null;
                }
            }
        };

    }

    @Override
    public void onBackPressed() { //두 번 누르면 종료하게 함.
        backButtonHandler.onBackPressed();
    }

    @Override
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(resultCode==1000){
            if(((HomeFragment)homeFragment).getFirebaseTour()!=null)
                ((HomeFragment)homeFragment).getFirebaseTour().refresh();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
