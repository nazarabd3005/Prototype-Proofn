package com.nazar.prototypeproofn.rest;

import android.app.Activity;
import android.net.Uri;

import com.nazar.prototypeproofn.models.Auth;
import com.nazar.prototypeproofn.models.Message;
import com.nazar.prototypeproofn.models.User;
import com.nazar.prototypeproofn.utils.Config;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class APITask {

    public static void doLogin(Auth.Request request, final RestCallback<Auth.Response> responseRestCallback){
        RestConnect connect = RestService.getInstance().getConnections();
        Call<Auth.Response> call =  connect.login(request);
        call.enqueue(new RestCallback<Auth.Response>() {
            @Override
            public void onSuccess(int code, Auth.Response body) {
                Config.saveAuth(body.token,body.user);
                responseRestCallback.onSuccess(code,body);
            }

            @Override
            public void onFailed(int code, String message) {
                responseRestCallback.onFailed(code,message);
            }
        });
    }

    public static void doInboxList(Message.RequestList requestList, final RestCallback<ArrayList<Message.Response>> arrayListRestCallback){
        RestConnect connect = RestService.getInstance().getConnections();
        Call<Message.ResponseList> call = connect.inboxList(Config.authorization);
        call.enqueue(new RestCallback<Message.ResponseList>() {
            @Override
            public void onSuccess(int code, Message.ResponseList body) {
                arrayListRestCallback.onSuccess(code,body.data);
            }

            @Override
            public void onFailed(int code, String message) {
                arrayListRestCallback.onFailed(code,message);
            }
        });
    }

    public static void doInboxDelete(String id, final RestCallback<Message.Response> responseRestCallback){
        RestConnect connect = RestService.getInstance().getConnections();
        Call<Message.Response> call =  connect.inboxDelete(Config.authorization,id);
        call.enqueue(new RestCallback<Message.Response>() {
            @Override
            public void onSuccess(int code, Message.Response body) {
                responseRestCallback.onSuccess(code,body);
            }

            @Override
            public void onFailed(int code, String message) {
                responseRestCallback.onFailed(code,message);
            }
        });

    }

    public static void doInboxDetail(String id, final RestCallback<Message.Response> responseRestCallback){
        RestConnect connect = RestService.getInstance().getConnections();
        Call<Message.Response> call = connect.inboxDetail(Config.authorization,id);
        call.enqueue(new RestCallback<Message.Response>() {
            @Override
            public void onSuccess(int code, Message.Response body) {
                responseRestCallback.onSuccess(code,body);
            }

            @Override
            public void onFailed(int code, String message) {
                responseRestCallback.onFailed(code,message);
            }
        });
    }

    public static void doSendMessage(Message.RequestSend requestSend, final RestCallback<Message.Response> responseRestCallback){
        RestConnect connect = RestService.getInstance().getConnections();
        Call<Message.Response> call = connect.sendMessage(Config.authorization,requestSend);
        call.enqueue(new RestCallback<Message.Response>() {
            @Override
            public void onSuccess(int code, Message.Response body) {
                responseRestCallback.onSuccess(code,body);
            }

            @Override
            public void onFailed(int code, String message) {
                responseRestCallback.onFailed(code,message);
            }
        });
    }

    public static void updateAvatar(Activity activity, File file, final RestCallback<User.Response> responseRestCallback){

        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        // Create MultipartBody.Part using file request-body,file name and part name
        MultipartBody.Part part = MultipartBody.Part.createFormData("avatar", file.getName(), fileReqBody);
        //Create request body with text description and text media type
        RequestBody description = RequestBody.create(MediaType.parse("text/plain"), "image-type");

        RestConnect connect = RestService.getInstance().getConnectionWithNoTimeout();
        Call<User.Response> call = connect.uploadAvatar(Config.authorization,part,description);
        call.enqueue(new RestCallback<User.Response>() {
            @Override
            public void onSuccess(int code, User.Response body) {
                if (Config.token.equals(""))
                    Config.loadAuth();

                User user = Config.user;
                user.avatarPathSmall = body.avatarPathSmall;
                user.avatarPathLarge = body.avatarPathLarge;
                user.avatarPathMedium = body.avatarPathMedium;

                Config.saveAuth(Config.token,user);
                responseRestCallback.onSuccess(code,body);
            }

            @Override
            public void onFailed(int code, String message) {
                responseRestCallback.onFailed(code,message);
            }
        });
    }
}
