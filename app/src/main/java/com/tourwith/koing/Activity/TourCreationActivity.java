package com.tourwith.koing.Activity;

import android.app.DatePickerDialog;
import android.app.FragmentTransaction;
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
    private LinearLayout secondLanguageLayout;
    private ImageView firstMinusImageView;
    private ImageView languagePlusImageView;
    private Spinner areaSpinner;
    private Spinner typeSpinner;
    private TextView periodText;
    private ImageView datePickImageView;
    private TextView periodText2;
    private ImageView datePickImageView2;
    private Button registerButton;
    private int year, month, day;
    Calendar cal, cal2;

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
                if(tour.getLang1().equals(tour.getLang2()))
                    tour.setLang2("");


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
        datePickImageView2 = (ImageView) findViewById(R.id.date_pick_imageView2);
        periodText2 = (TextView) findViewById(R.id.period_text_in_tour_creation2);
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

        tour.setStart_timestamp(cal.getTimeInMillis());
        tour.setEnd_timestamp(cal.getTimeInMillis());

        datePickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TourCreationActivity.this, dateSetListener, year, month, day).show();

            }
        });
        datePickImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TourCreationActivity.this, dateSetListener2, year, month, day).show();

            }
        });
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            periodText.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
            cal.set(year, monthOfYear, dayOfMonth);
            tour.setStart_timestamp(cal.getTimeInMillis());
        }
    };
    private DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            periodText2.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
            cal2.set(year, monthOfYear, dayOfMonth);
            tour.setEnd_timestamp(cal2.getTimeInMillis());

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
        secondLanguageSpinner.setSelection(1);

        secondLanguageLayout = (LinearLayout)findViewById(R.id.second_language_layout2);

        firstMinusImageView = (ImageView)findViewById(R.id.first_minus_image_view2);

        languagePlusImageView = (ImageView)findViewById(R.id.language_plus_image_view2);
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



    }

}
