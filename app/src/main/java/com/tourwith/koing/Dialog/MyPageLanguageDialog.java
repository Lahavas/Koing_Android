package com.tourwith.koing.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.R;

/**
 * Created by MSI on 2017-10-27.
 */

public class MyPageLanguageDialog extends Dialog{
    private Context context;
    private static final int LAYOUT = R.layout.mypage_language_dialog;
    private String[] lans;
    private Spinner firstLanguageSpinner;
    private Spinner secondLanguageSpinner;
    private TextView languageOKTextView;
    private LinearLayout firstLanguageLayout;
    private LinearLayout secondLanguageLayout;
    private Button firstMinusButton;
    private Button secondMinusButton;
    private String language1;
    private String language2;
    private Button languagePlusButton;

    private LanguageDialogListener dialogListener;
    private String uid;

    public MyPageLanguageDialog(@NonNull Context context, String language1, String language2, String uid){
        super(context);
        this.context = context;
        this.language1 = language1;
        this.language2 = language2;
        this.uid = uid;
    }

    public void setDialogListener(LanguageDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        lans = getContext().getResources().getStringArray(R.array.language);

        languageOKTextView = (TextView)findViewById(R.id.language_ok_text_view);

        firstLanguageSpinner = (Spinner)findViewById(R.id.first_language_spinner);
        secondLanguageSpinner = (Spinner)findViewById(R.id.second_language_spinner);

        firstLanguageLayout = (LinearLayout)findViewById(R.id.first_language_layout);
        secondLanguageLayout = (LinearLayout)findViewById(R.id.second_language_layout);

        firstMinusButton = (Button)findViewById(R.id.first_minus_button);
        secondMinusButton = (Button)findViewById(R.id.second_minus_button);

        languagePlusButton = (Button)findViewById(R.id.language_plus_button);


        for(int i=0;i<lans.length;i++){
            if(lans[i].equals(language1)){
                firstLanguageSpinner.setSelection(i);
            }
            if(lans[i].equals(language2)){
                secondLanguageSpinner.setSelection(i);
                languagePlusButton.setBackground(context.getDrawable(R.drawable.btn_plus_button_ds));
            }
        }

        if(language1.equals("")){
            firstLanguageLayout.setVisibility(View.GONE);
        }
        if(language2.equals("")){
            secondLanguageLayout.setVisibility(View.GONE);
        }

        languagePlusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstLanguageLayout.getVisibility()==View.GONE){
                    firstLanguageLayout.setVisibility(View.VISIBLE);
                    firstLanguageSpinner.setSelection(0);
                }else if(secondLanguageLayout.getVisibility()==View.GONE){
                    secondLanguageLayout.setVisibility(View.VISIBLE);
                    secondLanguageSpinner.setSelection(0);
                    languagePlusButton.setBackground(context.getDrawable(R.drawable.btn_plus_button_ds));
                }
            }
        });

        firstMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languagePlusButton.setBackground(context.getDrawable(R.drawable.btn_plus_button_e));
                if(secondLanguageLayout.getVisibility()==View.VISIBLE){
                    secondLanguageLayout.setVisibility(View.GONE);
                }else{
                    firstLanguageLayout.setVisibility(View.GONE);
                }
            }
        });

        secondMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languagePlusButton.setBackground(context.getDrawable(R.drawable.btn_plus_button_e));
                secondLanguageLayout.setVisibility(View.GONE);

            }
        });


        languageOKTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                language1 = firstLanguageLayout.getVisibility() == View.VISIBLE ? language1 : "";
                language2 = secondLanguageLayout.getVisibility() == View.VISIBLE ? language2 : "";

                if(language1.equals(language2))
                    language2 = "";
                else if(language1.equals("")) {
                    language1 = language2;
                    language2 = "";
                }

                FirebaseProfile firebaseProfile = new FirebaseProfile();
                firebaseProfile.updateUserLangs(uid, language1, language2);

                dialogListener.onPositiveClicked(language1,language2);
                dismiss();
            }
        });



        firstLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                language1 = parent.getItemAtPosition(position)+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        secondLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                language2 = parent.getItemAtPosition(position)+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
}