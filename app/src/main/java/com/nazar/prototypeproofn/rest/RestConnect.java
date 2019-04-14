package com.nazar.prototypeproofn.rest;
import com.nazar.prototypeproofn.models.Auth;
import com.nazar.prototypeproofn.models.Messages;
import com.nazar.prototypeproofn.models.User;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RestConnect {
    @Headers({"Content-Type: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.AUTH.LOGIN)
    Call<Auth.Request> login(@Body Auth.Request data);


    //region messages
    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.MESSAGES.INBOX)
    Call<ArrayList<Messages.Response>> inboxList(@Header("Authorization") String token,
                                      @Body String data);

    @Headers({"Accept: application/json"})
    @GET(Endpoint.MESSAGES.INBOX_DETAIL+"{id}")
    Call<Messages.Response> inboxDetail(@Header("Authorization") String token,
                                             @Path("id") String id);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.MESSAGES.INBOX_TRASH+"{id}/trash")
    Call<Messages.Response> inboxDelete(@Header("token") String token,
                                                      @Body String data);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.MESSAGES.SEND)
    Call<Messages.Response> sendMessage(@Header("token") String token,
                                        @Body Messages.RequestSend data);


    //endregion

    //region user
    @Headers({"Accept: application/json"})
    @Multipart
    @POST(Endpoint.MESSAGES.INBOX_TRASH+"{id}/trash")
    Call<User.Response> uploadAvatar(@Header("token") String token,
                                                   @Part MultipartBody.Part file, @Part("name") RequestBody requestBody);


    @Headers({"Accept: application/json"})
    @GET(Endpoint.USER.Profile)
    Call<User.Response> userProfile(@Header("token") String token);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.USER.Profile)
    Call<User.Response> updateProfile(@Header("token") String token,@Body User user);

    //endregion



}
