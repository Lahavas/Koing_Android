package com.tourwith.koing.Fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.tourwith.koing.Activity.SignUpActivity;
import com.tourwith.koing.R;

import java.io.File;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignupFragments extends Fragment {

    //FRAGMENT INDEXES
    //Signup 액티비티에서 띄울 프래그먼트의 타입 구분
    final static int EMAIL_INPUT = 0;
    final static int PASSWORD_INPUT = 1;
    final static int NICKNAME_INPUT = 2;
    final static int NATIONALITY_INPUT = 3;
    final static int LOCAL_LANG_INPUT = 4;
    final static int OTH_LANGUAGES_INPUT = 5;
    final static int COMMENTS_INPUT = 6;
    final static int PICTURE_INPUT = 7;

    private static final int PICK_FROM_ALBUM=101;
    private static final int CROP_FROM_IMAGE=102;


    int fragmentIndex; //프래그먼트를 구분하기 위한 인덱스, 이 값에 따라 레이아웃, 기능 등을 바꿈
    private Uri mImageCaptureUri;
    ImageView profileImageView;
    Bitmap profileBitmap;
    SignUpActivity parentActivity; //프래그먼트를 replace하기 위한 참조변수 선언, *.replace(fragmentIndex+1)로 리플레이스


    public SignupFragments() {


    }

    public SignupFragments(int fragmentIndex, SignUpActivity parentActivity) {
        // Required empty public constructor
        this.fragmentIndex = fragmentIndex;
        this.parentActivity = parentActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = null;
        final TextView instructionText;
        TextView secondInstructionText;
        final EditText inputEditText;
        final View submitButton;
        setActionBarButton();
        Spinner spinner = null;

        switch (fragmentIndex) {
            case EMAIL_INPUT :
                view = inflater.inflate(R.layout.fragment_email_to_name_insert, container, false);
                instructionText = (TextView) view.findViewById(R.id.instruction_text);
                instructionText.setText("Please Enter Your Email");
                inputEditText = (EditText) view.findViewById(R.id.input_edit_text);
                inputEditText.setHint("Email");
                inputEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

                submitButton = view.findViewById(R.id.submit_button);
                submitButton.setVisibility(View.VISIBLE);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String email = inputEditText.getText().toString();
                        if(checkEmailValid(email)){
                            parentActivity.emailForSignup = email;
                            parentActivity.switchFragment(PASSWORD_INPUT);

                            //나중에 이미 존재하는 이메일인지 체크해야될 듯

                        }

                    }
                });
                inputEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(checkEmailValid(s.toString())){
                            submitButton.setBackgroundResource(R.drawable.btn_login_e);
                        } else {
                            submitButton.setBackgroundResource(R.drawable.btn_login_ds);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                break;
            case PASSWORD_INPUT :
                view = inflater.inflate(R.layout.fragment_email_to_name_insert, container, false);
                instructionText = (TextView) view.findViewById(R.id.instruction_text);
                instructionText.setText("Please enter your Password");
                secondInstructionText = (TextView) view.findViewById(R.id.instruction_text_second);
                secondInstructionText.setText("Passwords must contain at least eight characters including one letter and one number.");
                inputEditText = (EditText) view.findViewById(R.id.input_edit_text);
                inputEditText.setVisibility(View.GONE);
                final EditText passwordEditText = (EditText) view.findViewById(R.id.input_password_text);
                passwordEditText.setVisibility(View.VISIBLE);
                submitButton = view.findViewById(R.id.submit_button_floating);
                submitButton.setVisibility(View.VISIBLE);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String password = passwordEditText.getText().toString();
                        if(checkPasswordValid(password)){
                            parentActivity.passwordForSignup = password;
                            parentActivity.switchFragment(NICKNAME_INPUT);

                        }

                    }
                });

                passwordEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(checkPasswordValid(s.toString())){
                            submitButton.setBackgroundResource(R.drawable.btn_login_next_e);
                        } else {
                            submitButton.setBackgroundResource(R.drawable.btn_login_next_ds);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                break;
            case NICKNAME_INPUT :
                view = inflater.inflate(R.layout.fragment_email_to_name_insert, container, false);
                instructionText = (TextView) view.findViewById(R.id.instruction_text);
                instructionText.setText("Please enter your Nickname");
                secondInstructionText = (TextView) view.findViewById(R.id.instruction_text_second);
                secondInstructionText.setText("This nickname will be displayed on Koing.");
                view.findViewById(R.id.input_edit_text).setVisibility(View.GONE);
                inputEditText = (EditText) view.findViewById(R.id.input_name_text);
                inputEditText.setVisibility(View.VISIBLE);
                inputEditText.setHint("Nickname");
                submitButton = view.findViewById(R.id.submit_button_floating);
                submitButton.setVisibility(View.VISIBLE);
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nickName = inputEditText.getText().toString().trim();
                        if(nickName.length() > 0){
                            parentActivity.userForSignup.setNickname(nickName);
                            parentActivity.switchFragment(NATIONALITY_INPUT);

                        }

                    }
                });

                inputEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.toString().trim().length()>0){
                            submitButton.setBackgroundResource(R.drawable.btn_login_next_e);
                        } else {
                            submitButton.setBackgroundResource(R.drawable.btn_login_next_ds);
                        }

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


                break;

            case NATIONALITY_INPUT :
                view = inflater.inflate(R.layout.fragment_email_to_name_insert, container, false);
                instructionText = (TextView) view.findViewById(R.id.instruction_text);
                instructionText.setText("Please select your nationality");
                secondInstructionText = (TextView) view.findViewById(R.id.instruction_text_second);
                secondInstructionText.setText("This nationality information will be open on koing.");
                inputEditText = (EditText) view.findViewById(R.id.input_edit_text);
                inputEditText.setVisibility(View.GONE);
                submitButton = view.findViewById(R.id.submit_button_floating);
                submitButton.setVisibility(View.VISIBLE);
                spinner = (Spinner) view.findViewById(R.id.nationality_spinner);
                spinner.setVisibility(View.VISIBLE);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        if((parent.getItemAtPosition(position)).equals("Choose your country")){
                            submitButton.setBackgroundResource(R.drawable.btn_login_next_ds);
                        } else {
                            submitButton.setBackgroundResource(R.drawable.btn_login_next_e);
                        }
                        parentActivity.userForSignup.setNationality((String) parent.getItemAtPosition(position));

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });


                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!parentActivity.userForSignup.getNationality().equals("Choose your country")){
                            parentActivity.switchFragment(LOCAL_LANG_INPUT);

                        }

                    }
                });
                break;

            case LOCAL_LANG_INPUT :
                view = inflater.inflate(R.layout.fragment_email_to_name_insert, container, false);
                instructionText = (TextView) view.findViewById(R.id.instruction_text);
                instructionText.setText("Please choose your native language");
                secondInstructionText = (TextView) view.findViewById(R.id.instruction_text_second);
                secondInstructionText.setText("This is the native language information available on koing.");
                inputEditText = (EditText) view.findViewById(R.id.input_edit_text);
                inputEditText.setVisibility(View.GONE);
                submitButton = view.findViewById(R.id.submit_button_floating);
                submitButton.setVisibility(View.VISIBLE);
                spinner = (Spinner) view.findViewById(R.id.language_spinner);
                spinner.setVisibility(View.VISIBLE);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view,
                                               int position, long id) {
                        if((parent.getItemAtPosition(position)).equals("Choose your language")){
                            submitButton.setBackgroundResource(R.drawable.btn_login_next_ds);
                        } else {
                            submitButton.setBackgroundResource(R.drawable.btn_login_next_e);
                        }
                        parentActivity.userForSignup.setMainLang((String) parent.getItemAtPosition(position));
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });


                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!parentActivity.userForSignup.getMainLang().equals("Choose your language")){
                            parentActivity.switchFragment(OTH_LANGUAGES_INPUT);

                        }

                    }
                });


                break;

            case OTH_LANGUAGES_INPUT :
                view = inflater.inflate(R.layout.fragment_langs_insert, container, false);
                instructionText = (TextView) view.findViewById(R.id.instruction_text_at_langs);
                instructionText.setText("Please select a language you can speak");
                secondInstructionText = (TextView) view.findViewById(R.id.instruction_text_second_at_langs);
                secondInstructionText.setText("This is the native language information available on koing. You can select up to 3.");
                submitButton = view.findViewById(R.id.submit_button_floating_at_langs);

                Spinner[] spinners = new Spinner[2];
                setSpinners(spinners, view);
                setButtons(view);

                final View finalView = view;
                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if((parentActivity.userForSignup.getLang1().equals("Choose your language") && parentActivity.userForSignup.getLang2().equals("Choose your language")
                                || (parentActivity.userForSignup.getLang1().equals("Choose your language") && finalView.findViewById(R.id.second_lang_layout_in_signin).getVisibility()==View.GONE))){

                            MessageDialogFragment df = new MessageDialogFragment(MessageDialogFragment.NATIONALITY_FAILED);
                            df.show(parentActivity.getFragmentManager(), "");

                        } else {

                            if(parentActivity.userForSignup.getLang1().equals(parentActivity.userForSignup.getLang2())){
                                parentActivity.userForSignup.setLang2("");
                            } else if(parentActivity.userForSignup.getLang1().equals("Choose your language") && finalView.findViewById(R.id.second_lang_layout_in_signin).getVisibility()==View.VISIBLE){
                                parentActivity.userForSignup.setLang1(parentActivity.userForSignup.getLang2());
                                parentActivity.userForSignup.setLang2("");
                            } else if(finalView.findViewById(R.id.second_lang_layout_in_signin).getVisibility()==View.VISIBLE){
                                parentActivity.userForSignup.setLang2("");
                            }

                            parentActivity.switchFragment(COMMENTS_INPUT);
                        }

                    }
                });

                break;

            case COMMENTS_INPUT :
                view = inflater.inflate(R.layout.fragment_email_to_name_insert, container, false);
                instructionText = (TextView) view.findViewById(R.id.instruction_text);
                instructionText.setText("Please let me know about you");
                secondInstructionText = (TextView) view.findViewById(R.id.instruction_text_second);
                secondInstructionText.setText("Please introduce about you to other members whom you are going to meet on Koing. Self introduction allows up to 100 characters.");
                inputEditText = (EditText) view.findViewById(R.id.input_edit_text);
                inputEditText.setHint("ex) Travel Style etc…");
                inputEditText.setMaxLines(3);
                submitButton = view.findViewById(R.id.submit_button_floating);
                submitButton.setVisibility(View.VISIBLE);

                inputEditText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(inputEditText.getText().toString().trim().length()>0){
                            submitButton.setBackgroundResource(R.drawable.btn_login_next_e);
                        } else {

                            submitButton.setBackgroundResource(R.drawable.btn_login_next_ds);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                submitButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String comments = inputEditText.getText().toString().trim();
                        if(comments.length() > 0){
                            parentActivity.userForSignup.setComments(comments);
                            parentActivity.switchFragment(PICTURE_INPUT);

                        } else {
                            MessageDialogFragment df = new MessageDialogFragment(MessageDialogFragment.COMMENT_INVALID);
                            df.show(parentActivity.getFragmentManager(), "");
                        }

                    }
                });
                break;


            case PICTURE_INPUT :
                view = inflater.inflate(R.layout.fragment_picture_insert, container, false);
                profileImageView = (ImageView) view.findViewById(R.id.profile_upload_imageview);
                profileImageView.setBackground(new ShapeDrawable(new OvalShape()));
                profileImageView.setClipToOutline(true);
                final Button submitButtonAtPicture = (Button) view.findViewById(R.id.submit_button_at_picture_insert);

                profileImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doTakeAlbumAction();
                        submitButtonAtPicture.setBackgroundResource(R.drawable.btn_login_e);

                    }
                });
                submitButtonAtPicture.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(profileBitmap != null){
                            parentActivity.attemptSignup(profileBitmap);

                        } else {
                            MessageDialogFragment df = new MessageDialogFragment(MessageDialogFragment.PICTURE_INVALID);
                            df.show(parentActivity.getFragmentManager(), "");
                        }

                    }
                });


                break;
        }


        return view;
    }

    private void setButtons(final View view) {

        view.findViewById(R.id.lang_add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.second_lang_layout_in_signin).setVisibility(View.VISIBLE);
            }
        });

        view.findViewById(R.id.lang_minus_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.findViewById(R.id.second_lang_layout_in_signin).setVisibility(View.GONE);
            }
        });


    }

    private void setSpinners(Spinner[] spinners, View view) {
        spinners[0] = (Spinner) view.findViewById(R.id.language_spinner1);
        spinners[1] = (Spinner) view.findViewById(R.id.language_spinner2);

        spinners[0].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                parentActivity.userForSignup.setLang1((String) parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        spinners[1].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                parentActivity.userForSignup.setLang2((String) parent.getItemAtPosition(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

    }
    private void doTakeAlbumAction(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode != RESULT_OK)
            return;

        switch(requestCode){
            case PICK_FROM_ALBUM:{
                mImageCaptureUri = data.getData();

                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");
                intent.putExtra("outputX", 200);
                intent.putExtra("outputY", 200);
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("scale", true);
                intent.putExtra("circleCrop", "true");
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_IMAGE);

                break;

            } case CROP_FROM_IMAGE:
            {
                if(resultCode != RESULT_OK)
                    return;

                final Bundle extras = data.getExtras();

                if(extras != null){
                    Bitmap photo = extras.getParcelable("data");
                    //Bitmap resized = Bitmap.createScaledBitmap(photo, 500, 500, true);
                    profileBitmap = photo;
                    profileImageView.setScaleX(1.0f);
                    profileImageView.setScaleY(1.0f);
                    profileImageView.setImageBitmap(profileBitmap);

                    break;
                }

                File f = new File(mImageCaptureUri.getPath());
                if(f.exists())
                    f.delete();

            }
        }
    }

    private boolean checkEmailValid(String email){
        final String emailPattern = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\\.[a-zA-Z]{2,3}$";
        return email.matches(emailPattern);
    }

    private boolean checkPasswordValid(String password){
        final String passwordPattern = "^[A-Za-z0-9_-]{8,100}$";
        return password.matches(passwordPattern);
    }

    private void setActionBarButton(){
        Button button = parentActivity.actionBarButton;
        if(fragmentIndex==0) {
            button.setBackgroundResource(R.drawable.btn_close);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentActivity.finish();
                }
            });
        } else if (fragmentIndex==2 && parentActivity.informationNeeded) {
            button.setBackgroundResource(R.drawable.btn_close);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentActivity.finish();
                }
            });
        }else {
            button.setBackgroundResource(R.drawable.btn_back);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentActivity.switchFragment(fragmentIndex-1);
                }
            });
        }
    }

}