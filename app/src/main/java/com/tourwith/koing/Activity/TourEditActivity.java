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

import com.tourwith.koing.Firebase.FirebasePicture;
import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.Fragment.MessageDialogFragment;
import com.tourwith.koing.Model.Tour;
import com.tourwith.koing.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by MSI on 2017-11-01.
 */

public class TourEditActivity extends AppCompatActivity {
    private Tour tour;

    private ImageView profileImage;
    private TextView nameText;



    private Spinner firstTourEditLanguageSpinner;
    private Spinner secondTourEditLanguageSpinner;

    private LinearLayout secondTourEditLayout;
    private ImageView tourEditPlusImageView;
    private ImageView tourEditMinusImageView;
    private Spinner tourEditAreaSpinner;
    private Spinner tourEditTypeSpinner;
    private TextView firstTourEditPeriodTextView;
    private ImageView firstTourEditDatePickImageView;
    private TextView secondTourEditPeriodText;
    private ImageView secondTourEditDatePickImageView;
    private Button tourEditRegisterButton;

    private int year, month, day;
    Calendar cal, cal2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_edit);
        initView();
    }


    private void initView() {
        getSupportActionBar().hide();
        tour = new Tour();
        tour.setUid(getIntent().getStringExtra("edituid"));
        tour.setKey(getIntent().getStringExtra("editkey"));
        profileImage = (ImageView) findViewById(R.id.tour_edit_profile_image_view);
        profileImage.setBackground(new ShapeDrawable(new OvalShape()));
        profileImage.setClipToOutline(true);
        nameText = (TextView)findViewById(R.id.tour_edit_name_text_view);
        findViewById(R.id.tour_edit_close_button).setOnClickListener(new View.OnClickListener() {
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

    private void initLanguageSelectViews() {

        firstTourEditLanguageSpinner = (Spinner)findViewById(R.id.first_tour_edit_language_spinner);
        secondTourEditLanguageSpinner = (Spinner)findViewById(R.id.second_tour_edit_language_spinner);
        secondTourEditLanguageSpinner.setSelection(1);

        secondTourEditLayout = (LinearLayout)findViewById(R.id.second_tour_edit_layout);

        tourEditMinusImageView = (ImageView)findViewById(R.id.tour_edit_minus_image_view);

        tourEditPlusImageView = (ImageView)findViewById(R.id.tour_edit_plus_image_view);
        tourEditPlusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(secondTourEditLayout.getVisibility()==View.GONE){
                    secondTourEditLayout.setVisibility(View.VISIBLE);
                    tourEditPlusImageView.setImageResource(R.drawable.btn_plus_button_ds);
                }
            }
        });

        tourEditMinusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(secondTourEditLayout.getVisibility()==View.VISIBLE){
                    secondTourEditLayout.setVisibility(View.GONE);
                    tourEditPlusImageView.setImageResource(R.drawable.btn_plus_button_e);
                }
            }
        });


        firstTourEditLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tour.setLang1(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        secondTourEditLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(secondTourEditLayout.getVisibility()!=View.GONE)
                    tour.setLang2(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }


    private void initAreaAndTypeViews() {
        tourEditAreaSpinner = (Spinner)findViewById(R.id.tour_edit_area_spinner);
        tourEditTypeSpinner = (Spinner)findViewById(R.id.tour_edit_type_spinner);

        tourEditAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tour.setArea(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tourEditTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tour.setTour_type(parent.getItemAtPosition(position)+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initDatePickView() {
        firstTourEditDatePickImageView = (ImageView) findViewById(R.id.first_tour_edit_date_picker_image_view);
        firstTourEditPeriodTextView = (TextView) findViewById(R.id.first_tour_edit_period_text_view);
        secondTourEditDatePickImageView = (ImageView) findViewById(R.id.second_tour_edit_date_picker_image_view);
        secondTourEditPeriodText = (TextView) findViewById(R.id.second_tour_edit_period_text_view);
        //format :  "2017/11/22 - 2017/11/24"

        //temp, date 입력받는 부분
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");
        cal = Calendar.getInstance();
        cal2 = Calendar.getInstance();

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day= cal.get(Calendar.DAY_OF_MONTH);

        cal.set(year, month, day);
        firstTourEditPeriodTextView.setText(fmt.format(cal.getTime()));
        cal2.set(year, month, day);
        secondTourEditPeriodText.setText(fmt.format(cal.getTime()));

        tour.setStart_timestamp(cal.getTimeInMillis());
        tour.setEnd_timestamp(cal.getTimeInMillis());

        firstTourEditDatePickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TourEditActivity.this,R.style.DialogTheme ,dateSetListener, year, month, day).show();

            }
        });
        secondTourEditDatePickImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TourEditActivity.this,R.style.DialogTheme, dateSetListener2, year, month, day).show();

            }
        });
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            firstTourEditPeriodTextView.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
            cal.set(year, monthOfYear, dayOfMonth);
            tour.setStart_timestamp(cal.getTimeInMillis());
        }
    };
    private DatePickerDialog.OnDateSetListener dateSetListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            secondTourEditPeriodText.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
            cal2.set(year, monthOfYear, dayOfMonth);
            tour.setEnd_timestamp(cal2.getTimeInMillis());
        }
    };

    private void initRegisterButton() {
        tourEditRegisterButton = (Button) findViewById(R.id.tour_edit_register_button);
        tourEditRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tour.getLang1().equals(tour.getLang2()) || secondTourEditLayout.getVisibility()==View.GONE)
                    tour.setLang2("");

                if(tour.getStart_timestamp() > tour.getEnd_timestamp()){
                    MessageDialogFragment messageDialogFragment = new MessageDialogFragment(MessageDialogFragment.DATE_INVALID);
                    messageDialogFragment.show(getFragmentManager(), "");

                } else {//edit 기능
                    MessageDialogFragment messageDialogFragment = new MessageDialogFragment(MessageDialogFragment.CHECK_TOUR_EDIT);
                    messageDialogFragment.setTour(tour);
                    messageDialogFragment.setActivity(TourEditActivity.this);
                    messageDialogFragment.show(getFragmentManager(), "");
                }
            }
        });


    }
}
