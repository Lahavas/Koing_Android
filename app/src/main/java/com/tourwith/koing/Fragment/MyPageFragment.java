package com.tourwith.koing.Fragment;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tourwith.koing.Activity.MainActivity;
import com.tourwith.koing.Dialog.LanguageDialogListener;
import com.tourwith.koing.Dialog.MyPageIntroductionDialog;
import com.tourwith.koing.Dialog.MyPageLanguageDialog;
import com.tourwith.koing.Dialog.MyPageNameDialog;
import com.tourwith.koing.Firebase.FirebaseProfile;
import com.tourwith.koing.Firebase.FirebaseTour;
import com.tourwith.koing.R;

/**
 * Created by Munak on 2017. 10. 8..
 */

public class MyPageFragment extends Fragment {
    private View view;
    private Button editIntroductionButton;
    private TextView introductionTextView;
    private Button settingButton;
    private ImageView profileImageView;
    private Button editLanguageButton;
    private Button editNameButton;
    private TextView profileNameTextView;
    private TextView profileNationLanguageTextView;
    private TextView profileLanguage1TextView;
    private TextView profileLanguage2TextView;
    private MainActivity parent;

    private LinearLayout []cardLayouts = new LinearLayout[3];
    private TextView []cardAreaTexts = new TextView[3];
    private TextView []cardTypeTexts = new TextView[3];
    private TextView []cardLangTexts = new TextView[3];


    public MyPageFragment()
    {
    }

    public MyPageFragment(MainActivity mainActivity) {
        parent = mainActivity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        editIntroductionButton = (Button)view.findViewById(R.id.edit_introduction_button);
        introductionTextView = (TextView)view.findViewById(R.id.introduction_text_view);

        settingButton = (Button)view.findViewById(R.id.setting_button);

        profileImageView = (ImageView)view.findViewById(R.id.profile_image_view);
        editLanguageButton = (Button)view.findViewById(R.id.edit_language_button);
        editNameButton = (Button)view.findViewById(R.id.edit_name_button);

        profileNameTextView = (TextView)view.findViewById(R.id.profile_name_text_view);
        profileNationLanguageTextView = (TextView)view.findViewById(R.id.profile_nation_language_text_view);

        profileLanguage1TextView = (TextView)view.findViewById(R.id.profile_language1_text_view);
        profileLanguage2TextView = (TextView)view.findViewById(R.id.profile_language2_text_view);

        profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
        profileImageView.setClipToOutline(true);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        initCards(view);

        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageDialogFragment messageDialogFragment = new MessageDialogFragment(MessageDialogFragment.SIGN_OUT);
                messageDialogFragment.show(parent.getFragmentManager(), "");
            }
        });
        editNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPageNameDialog dialog = new MyPageNameDialog(getContext(),profileNameTextView, profileNationLanguageTextView, parent.uid);
                dialog.show();
            }
        });
        editLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = profileLanguage1TextView.getText().toString();
                String s2 = profileLanguage2TextView.getText().toString();

                MyPageLanguageDialog dialog = new MyPageLanguageDialog(getContext(),s1,s2, parent.uid);
                dialog.setDialogListener(new LanguageDialogListener() {
                    @Override
                    public void onPositiveClicked(String language1, String language2) {
                        profileLanguage1TextView.setText(language1);
                        profileLanguage2TextView.setText(language2);
                        if(!language1.equals("")){
                            profileLanguage1TextView.setVisibility(View.VISIBLE);
                        }else{
                            profileLanguage1TextView.setVisibility(View.GONE);

                        }
                        if(!language2.equals("")){
                            profileLanguage2TextView.setVisibility(View.VISIBLE);
                        }else{
                            profileLanguage2TextView.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });
                dialog.show();
            }
        });
        editIntroductionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPageIntroductionDialog dialog = new MyPageIntroductionDialog(getContext(),introductionTextView, parent.uid);
                dialog.show();
            }
        });

        FirebaseProfile firebaseProfile = new FirebaseProfile();
        firebaseProfile.getUser(parent, parent.uid, profileNameTextView, profileNationLanguageTextView, introductionTextView, profileImageView,
                profileLanguage1TextView, profileLanguage2TextView);

        return view;
    }

    private void initCards(View view) {

        cardLayouts[0] = (LinearLayout) view.findViewById(R.id.card_layout1);
        cardLayouts[1] = (LinearLayout) view.findViewById(R.id.card_layout2);
        cardLayouts[2] = (LinearLayout) view.findViewById(R.id.card_layout3);

        cardAreaTexts[0] = (TextView) view.findViewById(R.id.card_area_text1);
        cardAreaTexts[1] = (TextView) view.findViewById(R.id.card_area_text2);
        cardAreaTexts[2] = (TextView) view.findViewById(R.id.card_area_text3);

        cardTypeTexts[0] = (TextView) view.findViewById(R.id.card_type_text1);
        cardTypeTexts[1] = (TextView) view.findViewById(R.id.card_type_text2);
        cardTypeTexts[2] = (TextView) view.findViewById(R.id.card_type_text3);

        cardLangTexts[0] = (TextView) view.findViewById(R.id.card_lang_text1);
        cardLangTexts[1] = (TextView) view.findViewById(R.id.card_lang_text2);
        cardLangTexts[2] = (TextView) view.findViewById(R.id.card_lang_text3);

        FirebaseTour firebaseTour = new FirebaseTour();
        firebaseTour.getToursOfUser(parent.uid, cardLayouts, cardAreaTexts, cardTypeTexts, cardLangTexts);

    }
}