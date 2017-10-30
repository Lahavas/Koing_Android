package com.tourwith.koing.Activity;

import android.app.DatePickerDialog;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
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
import android.widget.Toast;

import com.tourwith.koing.Firebase.FirebasePicture;
import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.Fragment.MessageDialogFragment;
import com.tourwith.koing.Model.Tour;
import com.tourwith.koing.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TourCreationActivity extends AppCompatActivity {

    private Tour tour;


    //views
    ImageView profileImage;
    TextView nameText;

    private Spinner firstLanguageSpinner;
    private Spinner secondLanguageSpinner;
    private Spinner thirdLanguageSpinner;
    private LinearLayout secondLanguageLayout;
    private LinearLayout thirdLanguageLayout;
    private ImageView firstMinusImageView;
    private ImageView secondMinusImageView;
    private ImageView languagePlusImageView;
    private Spinner areaSpinner;
    private Spinner typeSpinner;
    private TextView periodText;
    private ImageView datePickImageView;
    private Button registerButton;
    private int year, month, day;
    private boolean startPicked = false, endPicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_creation);
        initView();

    }

    private void initView() {
        getSupportActionBar().hide();
        tour = new Tour();
        tour.setUid(getIntent().getStringExtra("uid"));
        profileImage = (ImageView) findViewById(R.id.profile_image_view_in_tour_creation);
        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
        profileImage.setClipToOutline(true);
        nameText = (TextView)findViewById(R.id.name_text_in_tour_creation);
        findViewById(R.id.back_button_in_tour_creation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish(); }
        });


        FirebasePicture firebasePicture = new FirebasePicture(this);
        firebasePicture.downLoadProfileImage(tour.getUid(), FirebasePicture.ORIGINAL, profileImage);
        FirebaseProfile firebaseProfile = new FirebaseProfile();
        firebaseProfile.getUserName(tour.getUid(), nameText);

        initLanguageSelectViews();
        initAreaAndTypeViews();
        initDatePickView();
        initRegisterButton();



    }

    private void initRegisterButton() {
        registerButton = (Button) findViewById(R.id.register_button_in_tour_creation);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                MessageDialogFragment messageDialogFragment = new MessageDialogFragment(MessageDialogFragment.CHECK_TOUR_CREATE);
                messageDialogFragment.setTour(tour);
                messageDialogFragment.setActivity(TourCreationActivity.this);
                messageDialogFragment.show(getFragmentManager(), "");

            }
        });


    }

    private void initDatePickView() {
        datePickImageView = (ImageView) findViewById(R.id.date_pick_imageView);
        periodText = (TextView) findViewById(R.id.period_text_in_tour_creation);

        //format :  "2017/11/22 - 2017/11/24"

        //temp, date 입력받는 부분
        long currentTimeStamp = System.currentTimeMillis();
        tour.setStart_timestamp(currentTimeStamp);
        tour.setEnd_timestamp(currentTimeStamp + 24*60*60);

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();


        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day= cal.get(Calendar.DAY_OF_MONTH);


        cal.setTimeInMillis(tour.getStart_timestamp());
        periodText.setText(fmt.format(cal.getTime()));
        cal.setTimeInMillis(tour.getEnd_timestamp() + 24*60*60);
        periodText.append(" - " + fmt.format(cal.getTime()));

        datePickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TourCreationActivity.this, dateSetListener, year, month, day).show();

            }
        });

    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            // TODO Auto-generated method stub
            String msg = String.format("%d / %d / %d", year,monthOfYear+1, dayOfMonth);
            Toast.makeText(TourCreationActivity.this, msg, Toast.LENGTH_SHORT).show();

        }
    };


    private void initAreaAndTypeViews() {
        areaSpinner = (Spinner)findViewById(R.id.area_pick_spinner);
        typeSpinner = (Spinner)findViewById(R.id.type_pick_spinner);

        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tour.setArea(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tour.setTour_type(parent.getItemAtPosition(position)+"");

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initLanguageSelectViews() {

        firstLanguageSpinner = (Spinner)findViewById(R.id.first_language_spinner2);
        secondLanguageSpinner = (Spinner)findViewById(R.id.second_language_spinner2);
        thirdLanguageSpinner = (Spinner)findViewById(R.id.third_language_spinner2);
        secondLanguageSpinner.setSelection(1);
        thirdLanguageSpinner.setSelection(2);

        secondLanguageLayout = (LinearLayout)findViewById(R.id.second_language_layout2);
        thirdLanguageLayout = (LinearLayout)findViewById(R.id.third_language_layout2);

        firstMinusImageView = (ImageView)findViewById(R.id.first_minus_image_view2);
        secondMinusImageView = (ImageView)findViewById(R.id.second_minus_image_view2);

        languagePlusImageView = (ImageView)findViewById(R.id.language_plus_image_view2);
        languagePlusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(secondLanguageLayout.getVisibility()==View.GONE){
                    secondLanguageLayout.setVisibility(View.VISIBLE);
                }else if(thirdLanguageLayout.getVisibility()==View.GONE){
                    thirdLanguageLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        firstMinusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thirdLanguageLayout.getVisibility()!=View.GONE){
                    thirdLanguageLayout.setVisibility(View.GONE);
                }else{
                    secondLanguageLayout.setVisibility(View.GONE);
                }
            }
        });

        secondMinusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thirdLanguageLayout.setVisibility(View.GONE);
            }
        });

        firstLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tour.setLang1(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        secondLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(secondLanguageLayout.getVisibility()!=View.GONE)
                    tour.setLang2(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        thirdLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(thirdLanguageLayout.getVisibility()!=View.GONE)
                    tour.setLang3(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }



}
