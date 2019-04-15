package com.nazar.prototypeproofn.common;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.nazar.prototypeproofn.R;
import com.nazar.prototypeproofn.base.BaseActivity;
import com.nazar.prototypeproofn.models.Message;
import com.nazar.prototypeproofn.rest.APITask;
import com.nazar.prototypeproofn.rest.RestCallback;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

public class ComposeActivity extends BaseActivity {
    Message selectedMessage;

    //toolbarItem
    MenuItem menuSearch, menuCompose, menuDelete, menuCancel;

    EditText subject,to,content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);
        subject = (EditText) findViewById(R.id.subject);
        to = (EditText) findViewById(R.id.recipients);
        content = (EditText) findViewById(R.id.content);
        initBundle();
        initData();
    }

    private void initData() {
        if (selectedMessage !=null)
            to.setText(selectedMessage.sender.activeEmail);
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
        if (id == R.id.home){
            onBackPressed();
            return true;
        }
        if (id == R.id.search) {
            return true;
        }

        if (id == R.id.compose) {
            send();
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
    
    private void send(){
        Message.RequestSend requestSend = new Message.RequestSend();
        requestSend.subject = subject.getText().toString();
        requestSend.content = content.getText().toString();
        requestSend.contentPreview = content.getText().toString().length() > 50 ?content.getText().toString().substring(0,50):content.getText().toString();
        requestSend.recipients = new String[1];
        requestSend.recipients[0] = to.getText().toString();

        APITask.doSendMessage(requestSend, new RestCallback<Message.Response>() {
            @Override
            public void onSuccess(int code, Message.Response body) {
                Toast.makeText(ComposeActivity.this,"success",Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailed(int code, String message) {
                Toast.makeText(ComposeActivity.this,message,Toast.LENGTH_LONG).show();

            }
        });
    }


}
