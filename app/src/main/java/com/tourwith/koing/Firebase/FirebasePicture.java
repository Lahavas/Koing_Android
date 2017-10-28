package com.tourwith.koing.Firebase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.tourwith.koing.Fragment.MessageDialogFragment;
import com.tourwith.koing.Util.SharedPreferenceHelper;

/**
 * Created by hanhb on 2017-09-23.
 */

/*
    FirebasePicture
    Firebase의 Storage에 접근
    사진을 저장하고, 불러오는 기능을 합니다.

 */
public class FirebasePicture {

    public static final int ORIGINAL = 0;
    public static final int THUMNAIL = 1;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReferenceFromUrl("gs://koingandroid.appspot.com/").child("profileImage");
    private Context context;
    public FirebasePicture() {
    }
    public FirebasePicture(Context context) {
        this.context = context;
    }

    /*
        메서드 리스트
        Path : /profileImage/<uid>/<thumbnail or original>

        upload task
        boolean uploadProfileImage(String uid, Uri uri, int imageType)
        boolean uploadProfileImage(String uid, byte[] data, int imageType)

        download task
        boolean downLoadProfileImage(String uid, int imageType, ImageView imageView)

     */

    public boolean uploadProfileImage(String uid, Uri uri, int imageType){
        if(uid == null || uid == "" || uri == null)
            return false;
        StorageReference ref;
        if(imageType == THUMNAIL)
            ref = storageRef.child(uid).child("thumbnail");
        else if(imageType==ORIGINAL)
            ref = storageRef.child(uid).child("original");
        else
            return false;

        UploadTask uploadTask = ref.putFile(uri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "upload failed", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "upload succeed~", Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }

    public boolean uploadProfileImage(String uid, byte[] data, int imageType){
        if(uid == null || uid == "" || data == null)
            return false;
        StorageReference ref;
        if(imageType == THUMNAIL)
            ref = storageRef.child(uid).child("thumbnail");
        else if(imageType==ORIGINAL)
            ref = storageRef.child(uid).child("original");
        else
            return false;

        UploadTask uploadTask = ref.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

            }
        });
        return true;
    }

    public boolean uploadProfileImage(final String uid, byte[] data, int imageType, final ProgressDialog pd, final Activity activity){
        if(uid == null || uid == "" || data == null)
            return false;
        StorageReference ref;
        if(imageType == THUMNAIL)
            ref = storageRef.child(uid).child("thumbnail");
        else if(imageType==ORIGINAL)
            ref = storageRef.child(uid).child("original");
        else
            return false;

        UploadTask uploadTask = ref.putBytes(data);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                MessageDialogFragment md = new MessageDialogFragment(MessageDialogFragment.UPLOAD_FAILED);
                md.setActivity(activity);
                md.show(activity.getFragmentManager(), "");

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                pd.dismiss();
                MessageDialogFragment md = new MessageDialogFragment(MessageDialogFragment.SIGN_UP_SUCCESS);
                md.setActivity(activity);
                md.show(activity.getFragmentManager(), "");
                SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(activity);
                sharedPreferenceHelper.putBoolean(uid, true);
            }
        });
        return true;
    }

    public boolean downLoadProfileImage(String uid, final int imageType, final ImageView imageView){
        if(uid == null || uid == "")
            return false;
        StorageReference ref;
        if(imageType == THUMNAIL)
            ref = storageRef.child(uid).child("thumbnail");
        else if(imageType==ORIGINAL)
            ref = storageRef.child(uid).child("original");
        else
            return false;

        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(imageView);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

        return true;

    }

    public boolean downLoadProfileImageToChatRoom(String uid, final int imageType, final FirebaseMessenger dao){
        if(uid == null || uid == "")
            return false;
        StorageReference ref;
        if(imageType == THUMNAIL)
            ref = storageRef.child(uid).child("thumbnail");
        else if(imageType==ORIGINAL)
            ref = storageRef.child(uid).child("original");
        else
            return false;

        final long ONE_MEGABYTE = 1024 * 1024;
        ref.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                // Data for "images/island.jpg" is returns, use this as needed
                dao.setoProfileImgData(bytes);
                dao.refresh(); //리프레쉬

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        return true;

    }



}
