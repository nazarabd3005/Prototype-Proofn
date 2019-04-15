package com.nazar.prototypeproofn;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.navigation.NavigationView;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;
import com.nazar.prototypeproofn.base.BaseActivity;
import com.nazar.prototypeproofn.common.ComposeActivity;
import com.nazar.prototypeproofn.common.InboxFragment;
import com.nazar.prototypeproofn.models.User;
import com.nazar.prototypeproofn.rest.APITask;
import com.nazar.prototypeproofn.rest.RestCallback;
import com.nazar.prototypeproofn.utils.Config;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private boolean isInSelectionMode = false;
    //view

    ImageView avatar;
    AlertDialog alertDialog;
    //fragment
    InboxFragment fragment;
    //toolbarItem
    MenuItem menuSearch, menuCompose, menuDelete, menuCancel;

    //other
    ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        LinearLayout headerView = (LinearLayout) navigationView.getHeaderView(0);
        avatar = (ImageView) headerView.getChildAt(0);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.choosePicture(false);
            }
        });
        imagePicker = new ImagePicker(this, null, new OnImagePickedListener() {
            @Override
            public void onImagePicked(final Uri imageUri) {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog).create();
                alertDialog.setTitle("upload avatar");
                alertDialog.setMessage("do you want to upload it?");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.setMessage("uploading");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        APITask.updateAvatar(MainActivity.this, imagePicker.getImageFile(), new RestCallback<User.Response>() {
                            @Override
                            public void onSuccess(int code, User.Response body) {
                                initData();
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onFailed(int code, String message) {
                                Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();

                            }
                        });
                    }
                });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        }).setWithImageCrop(1,1);
        navigationView.setNavigationItemSelectedListener(this);
        fragment = new InboxFragment();
        getFragmentHelper().replaceFragment(R.id.container, fragment, false);

        initData();
    }

    private void initData() {
        Glide.with(this)
                .load(Config.URL_IMAGE + Config.user.avatarPathSmall).centerInside().apply(RequestOptions.circleCropTransform()).addListener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.e("GLIDE", e.toString());
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(avatar);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (isInSelectionMode) {
            fragment.doCancel();
            isInSelectionMode = false;
            toolbarUpdate();
        }else {
            super.onBackPressed();
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
        toolbarUpdate();
        return true;
    }

    public void toolbarUpdate() {
        if (isInSelectionMode) {
            menuDelete.setVisible(true);
            menuCancel.setVisible(true);
            menuCompose.setVisible(false);
            menuSearch.setVisible(false);
        } else {
            menuDelete.setVisible(false);
            menuCancel.setVisible(false);
            menuCompose.setVisible(true);
            menuSearch.setVisible(true);
        }
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
            Intent intent = new Intent(MainActivity.this, ComposeActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.delete) {
            fragment.doDelete();
            isInSelectionMode = false;
            toolbarUpdate();
            return true;
        }
        if (id == R.id.cancel) {
            fragment.doCancel();
            isInSelectionMode = false;
            toolbarUpdate();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void setInSelectionMode(boolean inSelectionMode) {
        isInSelectionMode = inSelectionMode;
        toolbarUpdate();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.navInbox) {
            // Handle the camera action
        } else if (id == R.id.navNeedResponse) {

        } else if (id == R.id.navimportant) {

        } else if (id == R.id.navDraft) {

        } else if (id == R.id.navTrash) {

        } else if (id == R.id.navDll) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (permissions.length==0)
            return;
        imagePicker.handlePermission(requestCode, grantResults);
    }

}
