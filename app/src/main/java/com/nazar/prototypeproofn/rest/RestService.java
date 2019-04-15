package com.nazar.prototypeproofn.rest;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestService {
    private static RestService instance = new RestService();
    private static final long CONNECTION_TIMEOUT = 60 * 60 * 1000;
    private static final long READ_TIMEOUT = 60 * 60 * 1000;
    private static final long WRITE_TIMEOUT = 60 * 60 * 1000;
    private RestConnect restConnect;
    private RestConnect restConnectNoClient;
    private HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    public GsonBuilder gson;

    public static RestService getInstance() {
        return instance;
    }

    private RestService() {
        gson = new GsonBuilder();
        gson.setLenient();

    }
    public void resetConnections() {
        restConnect = null;
    }

    public RestConnect getConnections() {
        if (restConnect == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Endpoint.getBaseDomain())
                    .client(getClient())
                    .addConverterFactory(GsonConverterFactory.create(gson.create()))
                    .build();
            restConnect = retrofit.create(RestConnect.class);
        }
        return restConnect;
    }

    public RestConnect getConnectionWithNoTimeout(){
        if (restConnectNoClient == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Endpoint.getBaseDomain())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            restConnectNoClient = retrofit.create(RestConnect.class);
        }

        return restConnectNoClient;
    }
    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }
}
