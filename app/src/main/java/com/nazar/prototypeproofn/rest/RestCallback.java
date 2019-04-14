package com.nazar.prototypeproofn.rest;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RestCallback<T> implements Callback<T> {
    @Override

    public void onResponse(Call<T> call, Response<T> response) {
        if (response.code() == 200) {
                onSuccess(response.code(), response.body());
        } else {
            try {
                JSONObject body = new JSONObject(response.errorBody().toString());
                onFailed(response.code(), body.getString("messages"));
            } catch (JSONException e) {
                e.printStackTrace();
                onFailed(response.code(), "data corrupt");
            }
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onFailed(600, t.getMessage());
        t.printStackTrace();
    }


    public abstract void onSuccess(int code, T body);

    public abstract void onFailed(int code, String message);


}
