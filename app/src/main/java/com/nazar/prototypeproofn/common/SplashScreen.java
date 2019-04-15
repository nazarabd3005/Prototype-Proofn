package com.nazar.prototypeproofn.common;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nazar.prototypeproofn.MainActivity;
import com.nazar.prototypeproofn.R;
import com.nazar.prototypeproofn.auths.LoginActivity;
import com.nazar.prototypeproofn.base.BaseActivity;
import com.nazar.prototypeproofn.utils.Config;

public class SplashScreen extends BaseActivity {
    private ImageView logo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        logo = (ImageView) findViewById(R.id.logo);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.fade_in_smooth);
        logo.setAnimation(animation);
        logo.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Config.isLoginIn()){
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashScreen.this,LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        },2000);
    }



}
