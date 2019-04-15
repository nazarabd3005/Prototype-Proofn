package com.nazar.prototypeproofn.auths;


import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.nazar.prototypeproofn.R;
import com.nazar.prototypeproofn.base.BaseActivity;
import com.nazar.prototypeproofn.common.SplashScreen;
import com.nazar.prototypeproofn.models.Auth;
import com.nazar.prototypeproofn.rest.APITask;
import com.nazar.prototypeproofn.rest.RestCallback;

public class LoginActivity extends BaseActivity {
    private AppCompatButton btnLogin;
    private EditText inputEmail,inputPassword;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (AppCompatButton) findViewById(R.id.btnLogin);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Auth.Request request = new Auth.Request();
                request.identifier = inputEmail.getText().toString();
                request.password = inputPassword.getText().toString();
                APITask.doLogin(request, new RestCallback<Auth.Response>() {
                    @Override
                    public void onSuccess(int code, Auth.Response body) {
                        Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailed(int code, String message) {
                        Log.e("API",message);
                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
