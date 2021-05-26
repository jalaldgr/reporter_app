package com.ageny.yadegar.sirokhcms.UserInterfaceClass;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.ageny.yadegar.sirokhcms.R;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent i =new Intent(Splash.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        },300);
    }

//    ///////////////change fonts to yekan/////////////////////
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }

}
