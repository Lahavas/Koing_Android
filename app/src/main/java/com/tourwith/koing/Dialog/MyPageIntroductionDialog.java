package com.tourwith.koing.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.R;

/**
 * Created by MSI on 2017-10-27.
 */

public class MyPageIntroductionDialog extends Dialog{
    private Context context;
    private static final int LAYOUT = R.layout.mypage_introduction_dialog;
    private EditText introductionEditText;
    private TextView introductionOKTextView;
    private TextView introductionTextView;
    private String uid;
    public MyPageIntroductionDialog(@NonNull Context context, TextView introductionTextView, String uid){
        super(context);
        this.context = context;
        this.introductionTextView = introductionTextView;
        this.uid = uid;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        introductionEditText = (EditText)findViewById(R.id.introduction_edit_text);
        introductionOKTextView = (TextView)findViewById(R.id.introduction_ok_text_view);

        String s = introductionTextView.getText().toString();
        if(s!=null){
            introductionEditText.setText(s);
        }
        introductionOKTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introductionTextView.setText(introductionEditText.getText().toString());
                FirebaseProfile firebaseProfile = new FirebaseProfile();
                firebaseProfile.updateUserIntroduction(uid, introductionEditText.getText().toString());
                dismiss();
            }
        });

    }
}
