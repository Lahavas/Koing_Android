package com.tourwith.koing.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tourwith.koing.R;

/**
 * Created by MSI on 2017-10-27.
 */

public class MyPageLanguageDialog extends Dialog{
    private Context context;
    private static final int LAYOUT = R.layout.mypage_language_dialog;
    private static final String[] lans = {"English", "Korean", "Japanese", "Chinese", "Spanish", "French"};
    private Spinner firstLanguageSpinner;
    private Spinner secondLanguageSpinner;
    private Spinner thirdLanguageSpinner;
    private TextView firstLanguageTextView;
    private TextView secondLanguageTextView;
    private TextView thirdLanguageTextView;
    private TextView languageOKTextView;
    private LinearLayout firstLanguageLayout;
    private LinearLayout secondLanguageLayout;
    private LinearLayout thirdLanguageLayout;
    private ImageView firstMinusImageView;
    private ImageView secondMinusImageView;
    private ImageView thirdMinusImageView;
    private String language1;
    private String language2;
    private String language3;
    private ImageView languagePlusImageView;

    private LanguageDialogListener dialogListener;



    public MyPageLanguageDialog(@NonNull Context context, String language1, String language2, String language3){
        super(context);
        this.context = context;
        this.language1 = language1;
        this.language2 = language2;
        this.language3 = language3;
    }

    public void setDialogListener(LanguageDialogListener dialogListener){
        this.dialogListener = dialogListener;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        languageOKTextView = (TextView)findViewById(R.id.language_ok_text_view);

        firstLanguageSpinner = (Spinner)findViewById(R.id.first_language_spinner);
        secondLanguageSpinner = (Spinner)findViewById(R.id.second_language_spinner);
        thirdLanguageSpinner = (Spinner)findViewById(R.id.third_language_spinner);

        firstLanguageTextView = (TextView)findViewById(R.id.first_language_text_view);
        secondLanguageTextView = (TextView)findViewById(R.id.second_language_text_view);
        thirdLanguageTextView = (TextView)findViewById(R.id.third_language_text_view);

        firstLanguageLayout = (LinearLayout)findViewById(R.id.first_language_layout);
        secondLanguageLayout = (LinearLayout)findViewById(R.id.second_language_layout);
        thirdLanguageLayout = (LinearLayout)findViewById(R.id.third_language_layout);

        firstMinusImageView = (ImageView)findViewById(R.id.first_minus_image_view);
        secondMinusImageView = (ImageView)findViewById(R.id.second_minus_image_view);
        thirdMinusImageView = (ImageView)findViewById(R.id.third_minus_image_view);

        languagePlusImageView = (ImageView)findViewById(R.id.language_plus_image_view);

        for(int i=0;i<lans.length;i++){
            if(lans[i].equals(language1)){
                firstLanguageSpinner.setSelection(i);
            }
            if(lans[i].equals(language2)){
                secondLanguageSpinner.setSelection(i);
            }
            if(lans[i].equals(language3)){
                thirdLanguageSpinner.setSelection(i);
            }
        }
        if(language1==""){
            firstLanguageLayout.setVisibility(View.GONE);
        }
        if(language2==""){
            secondLanguageLayout.setVisibility(View.GONE);
        }
        if(language3==""){
            thirdLanguageLayout.setVisibility(View.GONE);
        }

        languagePlusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstLanguageLayout.getVisibility()==View.GONE){
                    firstLanguageLayout.setVisibility(View.VISIBLE);
                    firstLanguageSpinner.setSelection(0);
                }else if(secondLanguageLayout.getVisibility()==View.GONE){
                    secondLanguageLayout.setVisibility(View.VISIBLE);
                    secondLanguageSpinner.setSelection(0);
                }else if(thirdLanguageLayout.getVisibility()==View.GONE){
                    thirdLanguageLayout.setVisibility(View.VISIBLE);
                    thirdLanguageSpinner.setSelection(0);
                }
            }
        });

        firstMinusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(secondLanguageLayout.getVisibility()!=View.GONE){
                    firstLanguageTextView.setText(secondLanguageTextView.getText().toString());

                    if(thirdLanguageLayout.getVisibility()!=View.GONE){
                        secondLanguageTextView.setText(thirdLanguageTextView.getText().toString());
                        thirdLanguageLayout.setVisibility(View.GONE);
                    }else{
                        secondLanguageLayout.setVisibility(View.GONE);
                    }
                }else{
                    firstLanguageLayout.setVisibility(View.GONE);
                }
            }
        });

        secondMinusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thirdLanguageLayout.getVisibility()!=View.GONE){
                    secondLanguageTextView.setText(thirdLanguageTextView.getText().toString());
                    thirdLanguageLayout.setVisibility(View.GONE);
                }else{
                    secondLanguageLayout.setVisibility(View.GONE);
                }
            }
        });

        thirdMinusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thirdLanguageLayout.setVisibility(View.GONE);
            }
        });


        languageOKTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String language1="", language2="", language3="";
                if(firstLanguageLayout.getVisibility() != View.GONE){
                    language1=firstLanguageTextView.getText().toString();
                }
                if(secondLanguageLayout.getVisibility() != View.GONE){
                    language2=secondLanguageTextView.getText().toString();
                }
                if(thirdLanguageLayout.getVisibility() != View.GONE){
                    language3=thirdLanguageTextView.getText().toString();
                }

                dialogListener.onPositiveClicked(language1,language2,language3);
                dismiss();
            }
        });



        firstLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                firstLanguageTextView.setText(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        secondLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                secondLanguageTextView.setText(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        thirdLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                thirdLanguageTextView.setText(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
