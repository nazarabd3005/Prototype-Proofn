package com.nazar.prototypeproofn.base;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nazar.prototypeproofn.R;
import com.nazar.prototypeproofn.helpers.FragmentHelper;
import com.nazar.prototypeproofn.utils.Config;

public class BaseActivity extends AppCompatActivity {
    public FragmentHelper fragmentHelper;
    public ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        fragmentHelper = new FragmentHelper();
        fragmentHelper.setFragmentManager(getSupportFragmentManager());
        Config.loadAuth();
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(this.getResources().getColor(R.color.colorPrimary));
        }
        progressDialog = new ProgressDialog(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    public FragmentHelper getFragmentHelper() {
        return fragmentHelper;
    }
}
