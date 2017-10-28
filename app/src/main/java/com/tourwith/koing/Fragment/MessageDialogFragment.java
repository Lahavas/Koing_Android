package com.tourwith.koing.Fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.tourwith.koing.Activity.LoginActivity;
import com.tourwith.koing.R;

/**
 * Created by hanhb on 2017-10-02.
 */

public class MessageDialogFragment extends DialogFragment {
    public int code = 0;
    public static final int EMAIL_INVALID = 1;
    public static final int PASSWORD_INVALID = 2;
    public static final int NICKNAME_INVALID = 3;
    public static final int SIGN_UP_FAILED = 4;
    public static final int SIGN_UP_SUCCESS = 5;
    public static final int SIGN_IN_FAILED = 6;
    public static final int SIGN_OUT = 7;
    public static final int NATIONALITY_FAILED = 8;
    public static final int COMMENT_INVALID = 9;
    public static final int PICTURE_INVALID = 10;
    public static final int UPLOAD_FAILED = 11;
    public static final int EMAIL_PASSWORD_INVALID = 12;

    public MessageDialogFragment(){}
    public MessageDialogFragment(int code){
        this.code = code;
    }
    public Activity activity = null;

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messagedialog, container);
        Button rButton = (Button) view.findViewById(R.id.button_right);
        TextView messageText = (TextView) view.findViewById(R.id.text_of_message_fragment);
        rButton.setText("Okay");
        rButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        if(code == EMAIL_INVALID)
            messageText.setText("Please enter your email.");
        else if(code == PASSWORD_INVALID)
            messageText.setText("Password must be more than 8 words");
        else if(code == NICKNAME_INVALID)
            messageText.setText("Please enter your nickname.");
        else if(code == SIGN_UP_FAILED)
            messageText.setText("Sign up failed.");
        else if(code == SIGN_UP_SUCCESS) {
            messageText.setText("Sign up success.");
            rButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    activity.finish();
                }
            });
        } else if(code == SIGN_IN_FAILED) {
            messageText.setText("Sign in failed.");
        } else if (code == UPLOAD_FAILED){
            messageText.setText("Sign up success..\nBut, upload task failed.\nYou should add an image in my page!");
            rButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    activity.finish();
                }
            });
        } else if(code == SIGN_OUT) {
            messageText.setText("Are you sure to sign out?");
            rButton.setText("Okay");
            Button lButton = (Button) view.findViewById(R.id.button_left);
            lButton.setText("No");
            lButton.setVisibility(View.VISIBLE);
            lButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getActivity().finish();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    FirebaseAuth.getInstance().signOut();

                }
            });
        } else if(code == NATIONALITY_FAILED){
            messageText.setText("Select your language");
        } else if(code == COMMENT_INVALID){
            messageText.setText("Fill the comments");
        } else if(code == PICTURE_INVALID){
            messageText.setText("Please upload your profile image");
        } else if(code == EMAIL_PASSWORD_INVALID){
            messageText.setText("Please enter your email.\nPassword must be more than 8 words");
        } else
            messageText.setText("error");

        return view;
    }
}
