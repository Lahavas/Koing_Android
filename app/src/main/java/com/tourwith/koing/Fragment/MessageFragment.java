package com.tourwith.koing.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.tourwith.koing.Activity.ChatroomActivity;
import com.tourwith.koing.Activity.MainActivity;
import com.tourwith.koing.Firebase.FirebaseChatroom;
import com.tourwith.koing.Model.Chatroom;
import com.tourwith.koing.R;

/**
 * Created by Munak on 2017. 9. 23..
 */

public class MessageFragment extends Fragment {

    ListView listView;
    FirebaseChatroom firebaseChatroom;
    ImageView background_if_none;
    public MessageFragment()
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
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        listView = (ListView) view.findViewById(R.id.message_list_view);
        background_if_none = (ImageView) view.findViewById(R.id.background_img_none_in_msg);

        firebaseChatroom = new FirebaseChatroom(((MainActivity)MessageFragment.this.getActivity()), listView, ((MainActivity)MessageFragment.this.getActivity()).uid, ((MainActivity)MessageFragment.this.getActivity()).getFragmentManager(), background_if_none);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chatroom chatroom = firebaseChatroom.getChatroomList().get(position);
                Intent intent = new Intent(((MainActivity)MessageFragment.this.getActivity()), ChatroomActivity.class);
                intent.putExtra("key", chatroom.getKey());
                if(chatroom.getmUID().equals(((MainActivity)MessageFragment.this.getActivity()).uid)) {
                    intent.putExtra("ouid", chatroom.getoUID()); //내가 상대방에게 채팅신청을 했을 경우

                }
                else {
                    intent.putExtra("ouid", chatroom.getmUID()); //상대방이 내게 채팅신청을 했을 경우

                }

                startActivity(intent);

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Chatroom chatroom = firebaseChatroom.getChatroomList().get(position);

                //다이얼로그로 물어보기
                MessageDialogFragment messageDialogFragment = new MessageDialogFragment();
                messageDialogFragment.setCode(MessageDialogFragment.CHECK_CHATROOM_DESTROY);
                messageDialogFragment.setFirebaseChatroomAndKey(firebaseChatroom, chatroom.getKey());
                messageDialogFragment.show(((MainActivity)MessageFragment.this.getActivity()).getFragmentManager(), "");

                return true;
            }
        });

        return view;
    }

}
