package com.ageny.yadegar.sirokhcms.UserInterfaceClass;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import com.ageny.yadegar.sirokhcms.DataModelClass.UserDataModelClass;
import com.ageny.yadegar.sirokhcms.DownloadImageTask;
import com.ageny.yadegar.sirokhcms.HTTPRequestHandlre;
import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.MYSQlDBHelper;
import com.ageny.yadegar.sirokhcms.R;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.ageny.yadegar.sirokhcms.URLs;
import com.ageny.yadegar.sirokhcms.UserInterfaceClass.Exit.ExitFragment;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    UserDataModelClass userDataModelClass;
    MYSQlDBHelper mysQlDBHelper;
    Context myCONTEXT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        myCONTEXT = this.getApplicationContext();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, /*R.id.nav_slideshow,
                R.id.nav_tools,*/ R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_share);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.NavNameTxt);
        TextView nav_email = (TextView)hView.findViewById((R.id.NavEmailTxt));
        ImageView nav_img = (ImageView)hView.findViewById((R.id.NavAvatarImg));

        URL url=null ;
        mysQlDBHelper = new MYSQlDBHelper(getApplicationContext());
        try {
            mysQlDBHelper.getWritableDatabase();
        }catch (Exception e){
            Log.i("hhh Says:", "Main Activity: "+ e.toString());
        }
        userDataModelClass = mysQlDBHelper.GetCurrentUser();
//        JSONHandlre.GetNewsReporters(Cntx);


        nav_user.setText(userDataModelClass.getFirst_name()+" "+userDataModelClass.getLast_name());
        nav_email.setText(userDataModelClass.getEmail());
        try {
            url =new URL(URLs.getBaseURL()+ userDataModelClass.getImage());
            Log.d("hhh DWndImg MainActvity", url.toString());
            new DownloadImageTask(nav_img).execute(url.toString());


        }catch (Exception e){
        }


        JSONHandlre.GetNewsCategories(myCONTEXT);
        JSONHandlre.GetProvincesList(myCONTEXT);
        JSONHandlre.GetCitiesList(myCONTEXT);
        JSONHandlre.GetNewsReporters(myCONTEXT);


    }

    ///////////////change fonts to yekan/////////////////////
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    public void onBackPressed() {
        Fragment webview = getSupportFragmentManager().findFragmentByTag("webview");

        if ( (webview instanceof ExitFragment) && ExitFragment.canGoBack() ) {
            ExitFragment.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }





    ////////////////////////////////////////////////////////////////////////////////////////////
    public void GetNewsReporters(Context c){
        myCONTEXT = c;
        class GetNewsReporters extends AsyncTask<Void, Void, String> {
            //private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this.dialog.setMessage("دریافت اطلاعات...");
                // this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                //  this.dialog.dismiss();
                super.onPostExecute(s);
                mysQlDBHelper = new MYSQlDBHelper(myCONTEXT);
                try {
                    mysQlDBHelper.getWritableDatabase();
                }catch (Exception e){
                    Log.i("GetNewsReporters Says:", "create bank in NewsReporters"+ e.toString());
                }
                try {
                    //converting response to json object
                    mysQlDBHelper.DeleteNewsReporters();//clear all NewsReporters list to write new list of province
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    if (obj.getInt("State")>0) {
                        //getting the user from the response
                        JSONArray TmpJsonArr = obj.getJSONArray("Data");

                        JSONObject JsonStr=null;
                        for (int i=0; i < TmpJsonArr.length(); i++){
                            ContentValues TmpCV = new ContentValues();
                            JsonStr = TmpJsonArr.getJSONObject(i);
                            TmpCV.put("id", JsonStr.getString("id"));
                            TmpCV.put("first_name", JsonStr.getString("first_name"));
                            TmpCV.put("last_name", JsonStr.getString("last_name"));
                            TmpCV.put("Email", JsonStr.getString("Email"));
                            TmpCV.put("Image", JsonStr.getString("Image"));
                            TmpCV.put("Bio", JsonStr.getString("Bio"));
                            TmpCV.put("news_category_id", JsonStr.getString("news_category_id"));




                            //0TmpCV.put("Disabled", JsonStr.getString("Disabled"));
                            Log.d("hhh", "GetNewsReporters JSON: "+TmpCV.toString());
                            mysQlDBHelper.InsertNewsReporters(TmpCV);
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();  Log.d("hhh", "GetNewsReporters Says: "+ex.toString());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                Log.d("hhh: " , "do in bak ground ");
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                String st =  requestHandler.sendGetRequest(URLs.getBaseURL()+URLs.getGetNewsReportersURL(), params);
                return st;
            }
        }
        GetNewsReporters lc = new GetNewsReporters();
        lc.execute();
    }






}
