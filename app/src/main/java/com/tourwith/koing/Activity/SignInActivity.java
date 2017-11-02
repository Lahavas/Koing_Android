package com.tourwith.koing.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.Fragment.MessageDialogFragment;
import com.tourwith.koing.R;


public class SignInActivity extends AppCompatActivity {

    Button backButton, loginButton;
    EditText emailEdit, passwordEdit;
    ProgressDialog progressDialog;
    MessageDialogFragment messageDialogFragment;
    private final Activity activity = this;
    private static String TAG = "Authentication";
    private boolean flag1 = false;
    private boolean flag2 = false;

    //Authentication : FirebaseAuth와 AuthStateListener 선언
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initView();
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

    private void initAuth() {

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

    private void initView() {
        //progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Please wait ...");
        progressDialog.setCanceledOnTouchOutside(false);

        getSupportActionBar().hide();
        backButton = (Button) findViewById(R.id.back_button_in_signin);
        loginButton = (Button) findViewById(R.id.login_button_in_signin);
        emailEdit = (EditText) findViewById(R.id.email_edit_in_signin);
        passwordEdit = (EditText) findViewById(R.id.password_edit_in_signin);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                attemptSignIn(email, password);

            }
        });

        emailEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(checkEmailValid(s.toString()))
                    flag1 =true;
                else
                    flag1 = false;

                if(flag1 && flag2){
                    loginButton.setBackgroundResource(R.drawable.btn_login_e);

                } else {
                    loginButton.setBackgroundResource(R.drawable.btn_login_ds);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(checkPasswordValid(s.toString()))
                    flag2 = true;
                else
                    flag2 = false;

                if(flag1 && flag2){
                    loginButton.setBackgroundResource(R.drawable.btn_login_e);

                } else {
                    loginButton.setBackgroundResource(R.drawable.btn_login_ds);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean checkEmailValid(String email){
        final String emailPattern = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";
        return email.matches(emailPattern);
    }

    private boolean checkPasswordValid(String password){
        final String passwordPattern = "^[A-Za-z0-9_-]{8,100}$";
        return password.matches(passwordPattern);
    }

    private void attemptSignIn(String email, String password) {

        if(!checkValid(email, password)){ //valid하지 않으면, 다이얼로그 띄움
            MessageDialogFragment df = new MessageDialogFragment(MessageDialogFragment.EMAIL_PASSWORD_INVALID);
            df.show(getFragmentManager(), TAG);
            return;
        }

        progressDialog.show(); //프로그레스 다이얼로그 띄움.

        //sign in
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (!task.isSuccessful()) { //로그인 실패
                            progressDialog.dismiss();
                            MessageDialogFragment md = new MessageDialogFragment(MessageDialogFragment.SIGN_IN_FAILED);
                            md.show(getFragmentManager(), TAG);

                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //일치하는 회원정보를 찾지 못했을 경우, 추정보를 입력하도록 함.
                            FirebaseProfile firebaseProfile = new FirebaseProfile();
                            firebaseProfile.checkUserAndMove(user.getUid(), activity, progressDialog);
                        }

                    }
                });


    }


    private boolean checkValid(String email, String password){
        if(email == null || email.equals("") || password == null || password.equals(""))
            return false;
        final String emailPattern = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";
        return email.matches(emailPattern) && password.length() > 7;
    }
}