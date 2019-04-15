package com.nazar.prototypeproofn.utils;

import com.google.gson.Gson;
import com.nazar.prototypeproofn.models.User;

public class Config {
    public static final String APP_NAME = "proofn";
    public static final String DUMMY_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTU0OTU0MzUsImhhc2giOiIxNGZkYjk4NS01YWYyLTQ0NzktOWVlMS0xNmRhYzAyMTA1OTciLCJpYXQiOjE1NTUyMzYyMzUsIm5hZiI6MTU1NzgyODIzNX0.glp5GT1gi7Z_0OIb3Ken40H4FQ-0OCRUmRsDgc9uScM";
    public static final String DUMMY_AVATAR =  "/avatar/14fdb985-5af2-4479-9ee1-16dac0210597/76df5a79314603026a0aa5bbf83391807bad1e5aa1c0788bb0a638e715c61229.jpeg";
    public static final String URL_IMAGE = "https://d8q6v9t02if7z.cloudfront.net";
    public static final String TOKEN = "token";
    public static final String USER = "user";

    public static String token = "";
    public static User user;

    public static String authorization;
    public static void loadAuth(){
        Config.token = Pref.getString(Config.TOKEN);
        Config.user = new Gson().fromJson(Pref.getString(Config.USER),User.class);
        Config.authorization = "bearer "+ token;
    }

    public static void saveAuth(String token,User user){
        Pref.setString(Config.TOKEN,token);
        Pref.setString(Config.USER, new Gson().toJson(user));
    }

    public static boolean isLoginIn(){
        if (!Config.token.equals(""))
            return true;
        return false;
    }
}
