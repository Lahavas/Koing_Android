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
    private TextView firstLanguageTextView;
    private TextView secondLanguageTextView;
    private TextView languageOKTextView;
    private LinearLayout firstLanguageLayout;
    private LinearLayout secondLanguageLayout;
    private Button firstMinusButton;
    private Button secondMinusButton;
    private String language1;
    private String language2;
    private Button languagePlusButton;

    private LanguageDialogListener dialogListener;


    public MyPageLanguageDialog(@NonNull Context context, String language1, String language2){
        super(context);
        this.context = context;
        this.language1 = language1;
        this.language2 = language2;
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

        firstLanguageTextView = (TextView)findViewById(R.id.first_language_text_view);
        secondLanguageTextView = (TextView)findViewById(R.id.second_language_text_view);

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
                    firstLanguageTextView.setText(secondLanguageTextView.getText().toString());
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
                String language1="", language2="";
                if(firstLanguageLayout.getVisibility() == View.VISIBLE){
                    language1=firstLanguageTextView.getText().toString();
                }
                if(secondLanguageLayout.getVisibility() == View.VISIBLE){
                    language2=secondLanguageTextView.getText().toString();
                }

                dialogListener.onPositiveClicked(language1,language2);
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

    }
}