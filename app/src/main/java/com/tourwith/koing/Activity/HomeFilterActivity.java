package com.tourwith.koing.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.tourwith.koing.Fragment.MessageDialogFragment;
import com.tourwith.koing.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFilterActivity extends AppCompatActivity {

    private Spinner firstLanguageSpinner;
    private Spinner secondLanguageSpinner;
    private LinearLayout secondLanguageLayout;
    private ImageView firstMinusImageView;
    private ImageView languagePlusImageView;
    private Spinner areaSpinner;
    private Spinner typeSpinner;
    private Spinner nationalitySpinner;
    private TextView periodText;
    private ImageView datePickImageView;
    private TextView periodText2;
    private ImageView datePickImageView2;
    private Button backButton;
    private int year, month, day;
    Calendar cal, cal2;
    private String nationality, language1, language2, area, type;
    private long period1, period2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_filter);
        getSupportActionBar().hide();
        initView();


    }

    private void initView() {
        getSupportActionBar().hide();

        initNationalityViews();
        initLanguageSelectViews();
        initAreaAndTypeViews();
        initDatePickView();
        initBackButton();


    }

    private void initNationalityViews() {

        nationalitySpinner = (Spinner) findViewById(R.id.nationality_pick_spinner);

        nationalitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nationality = parent.getItemAtPosition(position)+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void initBackButton() {

        backButton = (Button) findViewById(R.id.back_button_in_home_filter);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(language1.equals(language2) || secondLanguageLayout.getVisibility()==View.GONE)
                    language2 = "";

                if(period1 > period2){
                    MessageDialogFragment messageDialogFragment = new MessageDialogFragment(MessageDialogFragment.DATE_INVALID);
                    messageDialogFragment.show(getFragmentManager(), "");

                } else {

                    //Toast.makeText(HomeFilterActivity.this, nationality+language1+language2+area+type+"\n"+period1+"\n"+period2, Toast.LENGTH_SHORT).show();

                    Intent data = new Intent();
                    data.putExtra("nationality", nationality).putExtra("language1", language1).putExtra("language2", language2).putExtra("area", area)
                            .putExtra("type", type).putExtra("period1", period1).putExtra("period2",period2);

                    setResult(1001, data);
                    finish();



                }

            }
        });


    }

    private void initDatePickView() {
        datePickImageView = (ImageView) findViewById(R.id.date_pick_imageView11);
        periodText = (TextView) findViewById(R.id.period_text_in_tour_creation11);
        datePickImageView2 = (ImageView) findViewById(R.id.date_pick_imageView22);
        periodText2 = (TextView) findViewById(R.id.period_text_in_tour_creation22);
        //format :  "2017/11/22 - 2017/11/24"

        //temp, date 입력받는 부분
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        cal = Calendar.getInstance();
        cal2 = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day= cal.get(Calendar.DAY_OF_MONTH);

        cal.set(year, month, day);
        periodText.setText(fmt.format(cal.getTime()));
        cal2.set(year, month, day);
        periodText2.setText(fmt.format(cal.getTime()));

        period1 = cal.getTimeInMillis();
        period2 = cal.getTimeInMillis();

        datePickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(HomeFilterActivity.this,R.style.DialogTheme ,dateSetListener, year, month, day).show();

            }
        });
        datePickImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(HomeFilterActivity.this,R.style.DialogTheme, dateSetListener2, year, month, day).show();

            }
        });
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            periodText.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
            cal.set(year, monthOfYear, dayOfMonth);
            period1 = cal.getTimeInMillis();
        }
    };
    private DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            periodText2.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
            cal2.set(year, monthOfYear, dayOfMonth);
            period2 = cal2.getTimeInMillis();

        }
    };

    private void initAreaAndTypeViews() {
        areaSpinner = (Spinner)findViewById(R.id.area_pick_spinner2);
        typeSpinner = (Spinner)findViewById(R.id.type_pick_spinner2);

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                area = parent.getItemAtPosition(position)+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position)+"";

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initLanguageSelectViews() {

        firstLanguageSpinner = (Spinner)findViewById(R.id.first_language_spinner22);
        secondLanguageSpinner = (Spinner)findViewById(R.id.second_language_spinner22);
        secondLanguageSpinner.setSelection(1);

        secondLanguageLayout = (LinearLayout)findViewById(R.id.second_language_layout22);

        firstMinusImageView = (ImageView)findViewById(R.id.first_minus_image_view22);

        languagePlusImageView = (ImageView)findViewById(R.id.language_plus_image_view22);
        languagePlusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondLanguageLayout.setVisibility(View.VISIBLE);
            }
        });

        firstMinusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondLanguageLayout.setVisibility(View.GONE);

            }
        });


        firstLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                language1 =parent.getItemAtPosition(position)+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        secondLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(secondLanguageLayout.getVisibility()!=View.GONE)
                    language2 = parent.getItemAtPosition(position)+"";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }



}
