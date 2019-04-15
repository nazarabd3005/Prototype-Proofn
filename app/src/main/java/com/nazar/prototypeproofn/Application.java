package com.nazar.prototypeproofn;

import com.nazar.prototypeproofn.utils.Pref;

public class Application extends android.app.Application {
    private static Application INSTANCE;

     public static Application getInstance() {
        return INSTANCE;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;

        Pref.init();
    }
}
