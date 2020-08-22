package com.ageny.yadegar.sirokhcms;
import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class Calligraphy extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/yekan.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

    }

}