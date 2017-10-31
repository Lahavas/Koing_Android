package com.tourwith.koing.Activity;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourwith.koing.Firebase.FirebaseChatroom;
import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.Firebase.FirebaseTour;
import com.tourwith.koing.Fragment.MessageDialogFragment;
import com.tourwith.koing.R;

public class UserInformationActivity extends AppCompatActivity {
    private TextView introductionTextView;
    private ImageView profileImageView;
    private TextView profileNameTextView;
    private TextView profileNationLanguageTextView;
    private TextView profileLanguage1TextView;
    private TextView profileLanguage2TextView;

    private LinearLayout[]cardLayouts = new LinearLayout[3];
    private TextView []cardAreaTexts = new TextView[3];
    private TextView []cardTypeTexts = new TextView[3];
    private TextView []cardLangTexts = new TextView[3];

    private Button sendMessageButton;
    private Button backButton;
    
    private String oUID;
    private String mUID;



    //인텐트에 mUID, oUID 넣고 사용한다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);

        getSupportActionBar().hide();

        oUID = getIntent().getStringExtra("oUID");
        mUID = getIntent().getStringExtra("mUID");

        if(oUID== null || mUID == null || oUID.equals("") || mUID.equals("")){
            MessageDialogFragment messageDialogFragment = new MessageDialogFragment(MessageDialogFragment.INVALID_ACCESS);
            messageDialogFragment.setActivity(this);
            messageDialogFragment.setCancelable(false);
            messageDialogFragment.show(getFragmentManager(), "");
        }

        initViewInstances();

        FirebaseTour firebaseTour = new FirebaseTour();
        firebaseTour.getToursOfUser(oUID, cardLayouts, cardAreaTexts, cardTypeTexts, cardLangTexts);

        FirebaseProfile firebaseProfile = new FirebaseProfile();
        firebaseProfile.getUser(this, oUID, profileNameTextView, profileNationLanguageTextView, introductionTextView, profileImageView, profileLanguage1TextView, profileLanguage2TextView);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseChatroom firebaseChatroom = new FirebaseChatroom(UserInformationActivity.this);
                firebaseChatroom.writeChatroom(mUID, oUID);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initViewInstances() {
        introductionTextView = (TextView)findViewById(R.id.introduction_text_view11);

        profileImageView = (ImageView)findViewById(R.id.profile_image_view11);

        profileNameTextView = (TextView)findViewById(R.id.profile_name_text_view11);
        profileNationLanguageTextView = (TextView)findViewById(R.id.profile_nation_language_text_view11);

        profileLanguage1TextView = (TextView)findViewById(R.id.profile_language1_text_view11);
        profileLanguage2TextView = (TextView)findViewById(R.id.profile_language2_text_view11);

        profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
        profileImageView.setClipToOutline(true);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        sendMessageButton = (Button) findViewById(R.id.send_message_button11);
        backButton = (Button) findViewById(R.id.back_button_in_profile);

        cardLayouts[0] = (LinearLayout) findViewById(R.id.card_layout11);
        cardLayouts[1] = (LinearLayout) findViewById(R.id.card_layout22);
        cardLayouts[2] = (LinearLayout) findViewById(R.id.card_layout33);

        cardAreaTexts[0] = (TextView) findViewById(R.id.card_area_text11);
        cardAreaTexts[1] = (TextView) findViewById(R.id.card_area_text22);
        cardAreaTexts[2] = (TextView) findViewById(R.id.card_area_text33);

        cardTypeTexts[0] = (TextView) findViewById(R.id.card_type_text11);
        cardTypeTexts[1] = (TextView) findViewById(R.id.card_type_text22);
        cardTypeTexts[2] = (TextView) findViewById(R.id.card_type_text33);

        cardLangTexts[0] = (TextView) findViewById(R.id.card_lang_text11);
        cardLangTexts[1] = (TextView) findViewById(R.id.card_lang_text22);
        cardLangTexts[2] = (TextView) findViewById(R.id.card_lang_text33);

    }
}
