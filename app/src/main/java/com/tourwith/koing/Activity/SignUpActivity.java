package com.tourwith.koing.Activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tourwith.koing.Firebase.FirebasePicture;
import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.Fragment.MessageDialogFragment;
import com.tourwith.koing.Fragment.SignupFragments;
import com.tourwith.koing.Model.User;
import com.tourwith.koing.R;

import java.io.ByteArrayOutputStream;

public class SignUpActivity extends AppCompatActivity {

    private SignupFragments[] fragments = new SignupFragments[8];
    public User userForSignup = new User();
    public String emailForSignup, passwordForSignup;
    private static String TAG = "Authentication";
    private SignUpActivity activity = this;
    //views
    public Button actionBarButton;

    //Authentication : FirebaseAuth와 AuthStateListener 선언
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private ProgressDialog progressDialog; //로딩창 띄우기용 다이얼로그

    public boolean informationNeeded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initFragment(); //프래그먼트 인스턴스 생성
        initView(); //사용자 타입에 따라 화면 띄움
        initAuth();

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
    //initAuth : Firebase Auth 객체 초기화, 자동 로그인
    private void initAuth(){
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };

    }
    private void initFragment() {
        //프래그먼트 생성하기
        for(int i=0; i< fragments.length; i++){
            fragments[i] = new SignupFragments();
            fragments[i].setFragment(i, this);
        }

    }

    public void switchFragment(int fragmentIndex){
        if(fragmentIndex>=0 && fragmentIndex < fragments.length) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragments[fragmentIndex])
                    .commit();
        }

    }


    private void initView() {
        getSupportActionBar().hide();
        actionBarButton = (Button) findViewById(R.id.action_bar_button);
        actionBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCanceledOnTouchOutside(false);



        //추가정보가 필요한지 확인 후 처음으로 띄울 프래그먼트 지정
        if(getIntent().getBooleanExtra("InformationNeeded", false)){
            informationNeeded = true;
            switchFragment(2);
        } else {
            switchFragment(0);
        }

    }

    public void attemptSignup(final Bitmap bitmap){

        progressDialog.show();

        if(!informationNeeded) {
            mAuth.createUserWithEmailAndPassword(emailForSignup, passwordForSignup)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                progressDialog.dismiss();
                                MessageDialogFragment df = new MessageDialogFragment();
                                df.setCode(MessageDialogFragment.SIGN_UP_FAILED);
                                df.show(getFragmentManager(), TAG);

                            } else {

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                                byte[] byteArray = stream.toByteArray();
                                progressDialog.setMessage("Uploading...");

                                FirebaseProfile firebaseProfile = new FirebaseProfile();
                                userForSignup.setTimestamp();
                                firebaseProfile.writeUser(userForSignup, user.getUid());

                                FirebasePicture firebasePicture = new FirebasePicture();
                                firebasePicture.uploadProfileImage(user.getUid(), byteArray, FirebasePicture.ORIGINAL, progressDialog, activity);

                            }

                        }

                    });

        } else {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            progressDialog.setMessage("Uploading...");

            FirebaseProfile firebaseProfile = new FirebaseProfile();
            userForSignup.setTimestamp();
            firebaseProfile.writeUser(userForSignup, user.getUid());

            FirebasePicture firebasePicture = new FirebasePicture();
            firebasePicture.uploadProfileImage(user.getUid(), byteArray, FirebasePicture.ORIGINAL, progressDialog, activity);

        }
    }


}
