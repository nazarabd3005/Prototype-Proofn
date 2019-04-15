package com.nazar.prototypeproofn.rest;
import com.nazar.prototypeproofn.models.Auth;
import com.nazar.prototypeproofn.models.Message;
import com.nazar.prototypeproofn.models.User;

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
    @POST(Endpoint.AUTH.LOGIN)
    Call<Auth.Response> login(@Body Auth.Request data);


    //region messages
    @Headers({"Accept: application/json"})
    @GET(Endpoint.MESSAGES.INBOX)
    Call<Message.ResponseList> inboxList(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @GET(Endpoint.MESSAGES.INBOX_DETAIL+"{id}")
    Call<Message.Response> inboxDetail(@Header("Authorization") String token,
                                       @Path("id") String id);

    @Headers({"Accept: application/json"})
    @POST(Endpoint.MESSAGES.INBOX_TRASH+"{id}/trash")
    Call<Message.Response> inboxDelete(@Header("Authorization") String token,
                                       @Path("id") String id);

    @Headers({"Accept: application/json"})
    @POST(Endpoint.MESSAGES.SEND)
    Call<Message.Response> sendMessage(@Header("Authorization") String token,
                                       @Body Message.RequestSend data);


    //endregion

    //region user
    @Headers({"Accept: application/json"})
    @Multipart
    @POST(Endpoint.USER.avatar)
    Call<User.Response> uploadAvatar(@Header("Authorization") String token,
                                     @Part MultipartBody.Part file,
                                     @Part("avatar") RequestBody requestBody);


    @Headers({"Accept: application/json"})
    @GET(Endpoint.USER.Profile)
    Call<User.Response> userProfile(@Header("Authorization") String token);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST(Endpoint.USER.Profile)
    Call<User.Response> updateProfile(@Header("Authorization") String token,@Body User user);

    //endregion



}
