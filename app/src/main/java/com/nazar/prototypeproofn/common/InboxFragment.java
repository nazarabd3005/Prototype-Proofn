package com.nazar.prototypeproofn.common;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.nazar.prototypeproofn.MainActivity;
import com.nazar.prototypeproofn.R;
import com.nazar.prototypeproofn.base.BaseFragment;
import com.nazar.prototypeproofn.common.adapter.InboxAdapter;
import com.nazar.prototypeproofn.events.RecyclerItemClickListener;
import com.nazar.prototypeproofn.models.Message;
import com.nazar.prototypeproofn.rest.APITask;
import com.nazar.prototypeproofn.rest.RestCallback;
import com.nazar.prototypeproofn.utils.Config;

import java.util.ArrayList;
import java.util.List;

public class InboxFragment extends BaseFragment{
    private RecyclerView listInbox;
    private InboxAdapter inboxAdapter;
    private List<Message> messages = new ArrayList<>();
    private List<Message> selectedMessages = new ArrayList<>();
    private MainActivity mainActivity;

    private boolean isMultiSelect = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inbox,container,false);
        mainActivity = (MainActivity) getActivity();
        listInbox = (RecyclerView) v.findViewById(R.id.lisInbox);
        listInbox.setHasFixedSize(true);
        listInbox.setLayoutManager(new LinearLayoutManager(getContext()));
        inboxAdapter = new InboxAdapter(getContext(),messages);
        listInbox.setAdapter(inboxAdapter);
        listInbox.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), listInbox, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isMultiSelect){
                    messages.get(position).selected = !messages.get(position).selected;
                    inboxAdapter.notifyDataSetChanged();
                }else {
                    Intent intent = new Intent(getActivity(),MessageDetailActivity.class);
                    intent.putExtra("data",messages.get(position));
                    startActivity(intent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {
                if (!isMultiSelect){
                    isMultiSelect = true;
                    mainActivity.setInSelectionMode(true);
                    mainActivity.toolbarUpdate();
                }
                messages.get(position).selected = !messages.get(position).selected;
                inboxAdapter.notifyDataSetChanged();
            }
        }));
        initData();
//        dummyList();
        return v;
    }

    private void initData() {
        Message.RequestList requestList = new Message.RequestList();
        requestList.limit = 30;
        requestList.skip = 0;
        requestList.format = Message.RequestList.FORMAT_VALUE.MINIMAL;
        APITask.doInboxList(requestList, new RestCallback<ArrayList<Message.Response>>() {
            @Override
            public void onSuccess(int code, ArrayList<Message.Response> body) {
                messages.addAll(body);
                inboxAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String message) {

            }
        });
    }

    private void dummyList(){
        Message message1 = new Message();
        message1.content = "input item";
        message1.subject = "membaca";
        message1.sender.firstName = "nazar";
        Message message2 = new Message();
        message2.content = "input inbox";
        message2.subject = "membuat";
        message2.sender.firstName = "abdullah";
        Message message3 = new Message();
        message3.content = "input message";
        message3.subject = "menulis";
        message3.sender.firstName = "banget";
        messages.add(message1);
        messages.add(message2);
        messages.add(message3);
        inboxAdapter.notifyDataSetChanged();
    }

    public void doDelete(){
        final List<Message> deleteItems = new ArrayList<>();
        for (Message message:messages
             ) {
            if (message.selected)
                deleteItems.add(message);
        }
        for (int i = 0; i < deleteItems.size(); i++) {
            final int finalI = i;
            APITask.doInboxDelete(deleteItems.get(i).id+"", new RestCallback<Message.Response>() {
                @Override
                public void onSuccess(int code, Message.Response body) {
                    messages.remove(deleteItems.get(finalI));
                    inboxAdapter.notifyDataSetChanged();
                }

                @Override
                public void onFailed(int code, String message) {

                }
            });

        }
        isMultiSelect = false;
    }

    public void doCancel(){
        if (isMultiSelect){
            isMultiSelect = false;
            for (int i = 0; i < messages.size(); i++) {
                messages.get(i).selected = false;
            }
        }

        inboxAdapter.notifyDataSetChanged();
    }
}
