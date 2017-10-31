package com.tourwith.koing.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.Model.User;
import com.tourwith.koing.R;

/**
 * Created by MSI on 2017-10-27.
 */

public class MyPageNameDialog extends Dialog{
    private static final String[] nations ={"United Kingdom","Korea","Japan","China","Spain","France"};
    private static final String[] lans = {"English", "Korean", "Japanese", "Chinese", "Spanish", "French"};
    private Context context;
    private static final int LAYOUT = R.layout.mypage_name_dialog;
    private TextView nameOKTextView;
    private Spinner nationalitySpinner;
    private Spinner nativeLanguageSpinner;
    private TextView nationalityTextView;
    private TextView nativeLanguageTextView;
    private EditText profileNameEditText;
    private TextView profileNameTextView;
    private TextView profileNationLanguageTextView;
    private User user;
    private String uid;

    public MyPageNameDialog(@NonNull Context context, TextView profileNameTextView, TextView profileNationLanguageTextView, String uid){
        super(context);
        user = new User();
        this.uid = uid;
        this.context = context;
        this.profileNameTextView = profileNameTextView;
        this.profileNationLanguageTextView = profileNationLanguageTextView;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        nationalitySpinner = (Spinner)findViewById(R.id.nationality_spinner);
        nativeLanguageSpinner = (Spinner)findViewById(R.id.native_language_spinner);
        nationalityTextView = (TextView)findViewById(R.id.nationality_text_view);
        nameOKTextView = (TextView)findViewById(R.id.name_ok_text_view);
        nativeLanguageTextView = (TextView)findViewById(R.id.native_language_text_view);
        profileNameEditText = (EditText)findViewById(R.id.profile_name_edit_text);
        profileNameEditText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        profileNameEditText.setText(profileNameTextView.getText().toString());

        String s = profileNationLanguageTextView.getText().toString();
        String nation="",language="";
        if(s!=null) {
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '\u2022') {
                    nation = s.substring(0, i - 1);
                    language = s.substring(i + 2, s.length());
                    break;
                }
            }

            for (int i = 0; i < nations.length; i++) {
                if (nations[i].equals(nation)) {
                    nationalitySpinner.setSelection(i);
                }
            }
            for (int i = 0; i < lans.length; i++) {
                if (lans[i].equals(language)) {
                    nativeLanguageSpinner.setSelection(i);
                }
            }

        }
        nameOKTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileNameTextView.setText(profileNameEditText.getText().toString());
                profileNationLanguageTextView.setText(nationalityTextView.getText().toString() + " \u2022 " + nativeLanguageTextView.getText().toString());
                user.setNickname(profileNameEditText.getText().toString().trim());
                FirebaseProfile firebaseProfile = new FirebaseProfile();
                firebaseProfile.updateUserMainProfile(user, uid);
                dismiss();
            }
        });

        nationalitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nationalityTextView.setText(parent.getItemAtPosition(position)+"");
                user.setNationality(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        nativeLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nativeLanguageTextView.setText(parent.getItemAtPosition(position)+"");
                user.setMainLang(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}
