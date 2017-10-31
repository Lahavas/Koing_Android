package com.tourwith.koing.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.tourwith.koing.R;

/**
 * Created by MSI on 2017-10-27.
 */

public class GpsSetDialog extends Dialog{
    private Context context;
    private static final int LAYOUT = R.layout.gps_set_dialog;
    private TextView gpsSetOKTextView;
    public GpsSetDialog(@NonNull Context context){
        super(context);
        this.context = context;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);

        gpsSetOKTextView = (TextView)findViewById(R.id.gps_set_ok_text_view);

        gpsSetOKTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
