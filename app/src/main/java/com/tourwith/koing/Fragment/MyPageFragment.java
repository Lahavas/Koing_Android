package com.tourwith.koing.Fragment;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourwith.koing.Activity.MainActivity;
import com.tourwith.koing.Dialog.LanguageDialogListener;
import com.tourwith.koing.Dialog.MyPageIntroductionDialog;
import com.tourwith.koing.Dialog.MyPageLanguageDialog;
import com.tourwith.koing.Dialog.MyPageNameDialog;
import com.tourwith.koing.Firebase.FirebaseProfile;
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
    private TextView profileLanguage3TextView;
    private MainActivity parent;

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
        profileLanguage3TextView = (TextView)view.findViewById(R.id.profile_language3_text_view);

        profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
        profileImageView.setClipToOutline(true);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        editNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPageNameDialog dialog = new MyPageNameDialog(getContext(),profileNameTextView, profileNationLanguageTextView);
                dialog.show();
            }
        });
        editLanguageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = profileLanguage1TextView.getText().toString();
                String s2 = profileLanguage2TextView.getText().toString();
                String s3 = profileLanguage3TextView.getText().toString();

                MyPageLanguageDialog dialog = new MyPageLanguageDialog(getContext(),s1,s2,s3);
                dialog.setDialogListener(new LanguageDialogListener() {
                    @Override
                    public void onPositiveClicked(String language1, String language2, String language3) {
                        profileLanguage1TextView.setText(language1);
                        profileLanguage2TextView.setText(language2);
                        profileLanguage3TextView.setText(language3);
                        if(!language1.equals("")){
                            profileLanguage1TextView.setVisibility(View.VISIBLE);
                        }else{
                            profileLanguage1TextView.setVisibility(View.INVISIBLE);
                        }
                        if(!language2.equals("")){
                            profileLanguage2TextView.setVisibility(View.VISIBLE);
                        }else{
                            profileLanguage2TextView.setVisibility(View.INVISIBLE);
                        }
                        if(!language3.equals("")){
                            profileLanguage3TextView.setVisibility(View.VISIBLE);
                        }else{
                            profileLanguage3TextView.setVisibility(View.INVISIBLE);
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
                MyPageIntroductionDialog dialog = new MyPageIntroductionDialog(getContext(),introductionTextView);
                dialog.show();
            }
        });

        FirebaseProfile firebaseProfile = new FirebaseProfile();
        firebaseProfile.getUser(parent, parent.uid, profileNameTextView, profileNationLanguageTextView, introductionTextView, profileImageView);



        return view;
    }
}
