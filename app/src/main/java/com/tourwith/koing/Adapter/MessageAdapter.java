package com.tourwith.koing.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tourwith.koing.Model.Message;
import com.tourwith.koing.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hanhb on 2017-09-29.
 */

public class MessageAdapter extends RecyclerView.Adapter {

    private static final int MY_MESSAGE = 1;
    private static final int OPPONENT_MESSAGE = 2;
    private Context context;
    private List<Message> list;
    private SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
    //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private final String mUid;
    private final String oUid;

    public MessageAdapter(Context context, List<Message> list, String mUid, String oUid) {
        this.context = context;
        this.list = list;
        this.mUid = mUid;
        this.oUid = oUid;
    }

    public void setList(List<Message> list) {
        this.list = list;
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        ImageView shareImage;

        public ReceivedMessageHolder(View itemView) {
            super(itemView);
            messageText = (TextView) itemView.findViewById(R.id.o_msg_content);
            timeText = (TextView) itemView.findViewById(R.id.o_time_text);
            shareImage = (ImageView) itemView.findViewById(R.id.o_image_to_share);
        }

        void bind(Message vo){
            messageText.setText(vo.getContent());
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis((long)vo.timestamp);
            timeText.setText(fmt.format(cal.getTime()));
            if(vo.getshare()) {
                messageText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                shareImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(vo.getPictureUrl()).override(1000,600).into(shareImage);
                //이미지뷰에 Url 띄우기
            }
        }
    }

    private  class SentMessageHolder extends RecyclerView.ViewHolder{
        TextView mMsgText;
        TextView mTimewText;
        ImageView shareImage;


        public SentMessageHolder(View itemView) {
            super(itemView);
            mMsgText = (TextView) itemView.findViewById(R.id.m_msg_content);
            mTimewText = (TextView) itemView.findViewById(R.id.m_time_text);
            shareImage = (ImageView) itemView.findViewById(R.id.m_image_to_share);
        }

        void bind(Message vo){
            mMsgText.setText(vo.getContent());
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis((long)vo.timestamp);
            mTimewText.setText(fmt.format(cal.getTime()));
            if(vo.getshare()){
                mMsgText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                shareImage.setVisibility(View.VISIBLE);
                Glide.with(context).load(vo.getPictureUrl()).override(1000,600).into(shareImage);
                //이미지 띄우기
            }


        }

    }

    @Override
    public int getItemViewType(int position) {
        Message vo = list.get(position);

        if(vo.getUid().equals(mUid)){ //my message
            return MY_MESSAGE;
        } else {// opponent message
            return OPPONENT_MESSAGE;
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if(viewType == MY_MESSAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_to_send, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == OPPONENT_MESSAGE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Message vo = list.get(position);

        if(holder.getItemViewType() == MY_MESSAGE){
            ((SentMessageHolder) holder).bind(vo);
        } else {
            ((ReceivedMessageHolder)holder).bind(vo);
        }

    }




    @Override
    public int getItemCount() {
        return list.size();
    }
}
