package com.nazar.prototypeproofn.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.nazar.prototypeproofn.R;
import com.nazar.prototypeproofn.base.BaseActivity;
import com.nazar.prototypeproofn.models.Message;
import com.nazar.prototypeproofn.rest.APITask;
import com.nazar.prototypeproofn.rest.RestCallback;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class MessageDetailActivity extends BaseActivity {
    MenuItem menuSearch, menuCompose, menuDelete, menuCancel;
    Message selectedMessage;
    private TextView subject;
    private TextView sender;
    private TextView content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        subject = (TextView) findViewById(R.id.subject);
        sender = (TextView) findViewById(R.id.sender);
        content = (TextView) findViewById(R.id.content);
        initBundle();
        initData();
    }

    private void initData() {
        APITask.doInboxDetail(selectedMessage.id+"", new RestCallback<Message.Response>() {
            @Override
            public void onSuccess(int code, Message.Response body) {
                selectedMessage = body;
                subject.setText(selectedMessage.subject);
                sender.setText(selectedMessage.sender.firstName);
                content.setText(selectedMessage.content);
            }

            @Override
            public void onFailed(int code, String message) {

            }
        });

    }

    private void initBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey("data")) {
                selectedMessage = (Message) bundle.getParcelable("data");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        menuDelete = menu.findItem(R.id.delete);
        menuCancel = menu.findItem(R.id.cancel);
        menuSearch = menu.findItem(R.id.search);
        menuCompose = menu.findItem(R.id.compose);
        menuCancel.setVisible(false);
        menuSearch.setVisible(false);
        menuDelete.setVisible(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            onBackPressed();
            return true;
        }
        if (id == R.id.search) {
            return true;
        }

        if (id == R.id.compose) {
            Intent intent = new Intent(MessageDetailActivity.this, ComposeActivity.class);
            if (getIntent().getExtras() != null)
                intent.putExtras(getIntent().getExtras());
            startActivity(intent);
            return true;
        }
        if (id == R.id.delete) {
            return true;
        }
        if (id == R.id.cancel) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
