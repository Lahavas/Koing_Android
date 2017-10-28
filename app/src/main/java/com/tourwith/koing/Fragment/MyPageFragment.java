package com.tourwith.koing.Fragment;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tourwith.koing.Dialog.LanguageDialogListener;
import com.tourwith.koing.Dialog.MyPageIntroductionDialog;
import com.tourwith.koing.Dialog.MyPageLanguageDialog;
import com.tourwith.koing.Dialog.MyPageNameDialog;
import com.tourwith.koing.R;

/**
 * Created by Munak on 2017. 10. 8..
 */

public class MyPageFragment extends Fragment {
    private View view;
    private ImageView introductionImageView;
    private TextView introductionTextView;
    private ImageView settingImageView;
    private ImageView profileImageView;
    private ImageView languageImageView;
    private ImageView editNameImageView;
    private TextView profileNameTextView;
    private TextView profileNationLanguageTextView;
    private TextView profileLanguage1TextView;
    private TextView profileLanguage2TextView;
    private TextView profileLanguage3TextView;
    private CardView profileLanguage1CardView;
    private CardView profileLanguage2CardView;
    private CardView profileLanguage3CardView;

    public MyPageFragment()
    {
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
        introductionImageView = (ImageView)view.findViewById(R.id.edit_introduction_image_view);
        introductionTextView = (TextView)view.findViewById(R.id.introduction_text_view);

        settingImageView = (ImageView)view.findViewById(R.id.setting_image_view);

        profileImageView = (ImageView)view.findViewById(R.id.profile_image_view);
        languageImageView = (ImageView)view.findViewById(R.id.edit_language_image_view);
        editNameImageView = (ImageView)view.findViewById(R.id.edit_name_image_view);

        profileNameTextView = (TextView)view.findViewById(R.id.profile_name_text_view);
        profileNationLanguageTextView = (TextView)view.findViewById(R.id.profile_nation_language_text_view);

        profileLanguage1TextView = (TextView)view.findViewById(R.id.profile_language1_text_view);
        profileLanguage2TextView = (TextView)view.findViewById(R.id.profile_language2_text_view);
        profileLanguage3TextView = (TextView)view.findViewById(R.id.profile_language3_text_view);

        profileLanguage1CardView = (CardView)view.findViewById(R.id.profile_language1_card_view);
        profileLanguage2CardView = (CardView)view.findViewById(R.id.profile_language2_card_view);
        profileLanguage3CardView = (CardView)view.findViewById(R.id.profile_language3_card_view);

        profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
        profileImageView.setClipToOutline(true);
        profileImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


        editNameImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPageNameDialog dialog = new MyPageNameDialog(getContext(),profileNameTextView, profileNationLanguageTextView);
                dialog.show();
            }
        });
        languageImageView.setOnClickListener(new View.OnClickListener() {
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
                            profileLanguage1CardView.setVisibility(View.VISIBLE);
                        }else{
                            profileLanguage1CardView.setVisibility(View.INVISIBLE);
                        }
                        if(!language2.equals("")){
                            profileLanguage2CardView.setVisibility(View.VISIBLE);
                        }else{
                            profileLanguage2CardView.setVisibility(View.INVISIBLE);
                        }
                        if(!language3.equals("")){
                            profileLanguage3CardView.setVisibility(View.VISIBLE);
                        }else{
                            profileLanguage3CardView.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onNegativeClicked() {

                    }
                });
                dialog.show();
            }
        });
        introductionImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPageIntroductionDialog dialog = new MyPageIntroductionDialog(getContext(),introductionTextView);
                dialog.show();
            }
        });
        return view;
    }
}
