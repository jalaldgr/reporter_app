package com.ageny.yadegar.sirokhcms.UserInterfaceClass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ageny.yadegar.sirokhcms.DataModelClass.GetPreNewsDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.NewsCategoriesDataModel;
import com.ageny.yadegar.sirokhcms.DownloadImageTask;
import com.ageny.yadegar.sirokhcms.HTTPRequestHandlre;
import com.ageny.yadegar.sirokhcms.MYSQlDBHelper;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ShowPrenewsActivity extends AppCompatActivity {
    String TAG="hhh";
    String RID,UID;
    public Context Cntx;
    MYSQlDBHelper mysQlDBHelper;
    public GetPreNewsDataModelClass getprenews=null;
    TextView startinterviewtxt;
    TextView stopinterviewtxt;
    TextView toptitletxt;
    TextView titletxt;
    TextView subtitletxt;
    TextView categoritxt;
    TextView provincetxt;
    TextView citytxt;
    TextView reportertxt;
    TextView newstypetxt;
    TextView summarytxt;
    TextView contenttxt;
    ImageView mainpic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_prenews);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("نمایش مدرک");
        Cntx=this.getApplicationContext();
        Intent intent=getIntent();
        RID=intent.getStringExtra("cartableid");
        UID=intent.getStringExtra("userid");
        mysQlDBHelper = new MYSQlDBHelper(Cntx);
        startinterviewtxt = (TextView)findViewById(R.id.prenews_start_textView_);
        stopinterviewtxt = (TextView)findViewById(R.id.prenews_stop_textView_);
        toptitletxt = (TextView)findViewById(R.id.prenews_main_titr_textview);
        titletxt = (TextView)findViewById(R.id.prenews_titr_textView);
        subtitletxt = (TextView)findViewById(R.id.prenews_sub_titr_textView);
        categoritxt = (TextView)findViewById(R.id.prenews_categori_textView);
        provincetxt = (TextView)findViewById(R.id.prenews_province_textView);
        citytxt = (TextView)findViewById(R.id.prenews_city_textView);
        reportertxt = (TextView)findViewById(R.id.prenews_person_textView);
        newstypetxt = (TextView)findViewById(R.id.prenews_type_textView_);
        summarytxt = (TextView)findViewById(R.id.prenews_summary_textView);
        contenttxt = (TextView)findViewById(R.id.prenews_content_textView);
         mainpic = (ImageView)findViewById((R.id.prenews_image_view));

        GetPreNews(RID);
    }



    ///////////////change fonts to yekan/////////////////////
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void GetPreNews(  String RefferalID) {
        final String ParamRID = RefferalID;


        class GetPreNews extends AsyncTask<Void, Void, String> {

            private final ProgressDialog dialog = new ProgressDialog(ShowPrenewsActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                this.dialog.setMessage("دریافت اطلاعات...");
                this.dialog.setIndeterminate(true);
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                NewsCategoriesDataModel newsCategoriesDataModel = null;

                try {
                    //converting response to json object
                    Log.d("hhh", " GetPreNews: try "+ s);
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    if (!obj.isNull("Data")) {
                        JSONObject kk = new JSONObject(obj.get("Data").toString());
                        getprenews = new GetPreNewsDataModelClass(
                                kk.getString("referral_folder_id"),
                                kk.getString("StartInterview"),
                                kk.getString("EndInterview"),
                                kk.getString("TopTitle"),
                                kk.getString("Title"),
                                kk.getString("BottomTitle"),
                                kk.getString("ContentSummary"),
                                kk.getString("MainContent"),
                                kk.getString("news_id"),
                                kk.getString("Image"),
                                kk.getString("news_category_id"),
                                kk.getString("Seen"),
                                kk.getString("pre_news_state_id"),
                                kk.getString("news_type_id"),
                                kk.getString("province_id"),
                                kk.getString("city_id"),
                                kk.getString("reporter_id"),
                                kk.getString("created_at"),
                                kk.getString("updated_at"),
                                kk.getString("NewsTypeTitle"),
                                kk.getString("PreNewsStateTitle"),
                                kk.getString("ProvinceTitle"),
                                kk.getString("CityTitle"),
                                kk.getString("first_name"),
                                kk.getString("last_name"));

                    }
                    newsCategoriesDataModel = mysQlDBHelper.GetNewsCategorieById(getprenews.getNews_category_id());


                        startinterviewtxt.setText(getprenews.getStartInterview());
                        stopinterviewtxt.setText(getprenews.getEndInterview());
                        toptitletxt.setText(
                                (!getprenews.getTopTitle().equals(null))?getprenews.getTopTitle():" "
                        );
                        titletxt.setText(getprenews.getTitle());
                        subtitletxt.setText(
                                (!getprenews.getBottomTitle().equals(null))?getprenews.getBottomTitle():" "
                        );
                        categoritxt.setText(newsCategoriesDataModel.getTitle());
                        provincetxt.setText(getprenews.getProvinceTitle());
                        citytxt.setText(getprenews.getCityTitle());
                        reportertxt.setText(getprenews.getFirst_name() + " "+getprenews.getLast_name());
                        List<String> news_typ_id = Arrays.asList(getResources().getStringArray(R.array.SubmitInformationNewsType));
                        newstypetxt.setText(getprenews.getNewsTypeTitle());
                        String [][]reporterlist =mysQlDBHelper.GetNewsReportersList();
                        reportertxt.setText(getprenews.getFirst_name()+" "+getprenews.getLast_name());
                        summarytxt.setText(getprenews.getContentSummary());
                        contenttxt.setText(
                                (!getprenews.getMainContent().equals(null))?getprenews.getMainContent():" "
                        );

                } catch (Exception ex) {
                    ex.printStackTrace();  Log.d("hhh", "ERROR GetPreNews: "+ex.toString());
                }
                dialog.dismiss();

                URL url=null ;
                try {
                    url =new URL(URLs.getBaseURL()+ getprenews.getImage());
                    Log.d("hhh", "onCreate: "+getprenews.getImage());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                new DownloadImageTask(mainpic).execute(url.toString());

            }


            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object

                HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                Log.d("hhh: " , "ShowReferralFolder bak ground ");
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("RID", ParamRID);
                //returing the response
                String st =  requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getGetPreNews(), params);
                //Log.d("hhh: " , "do in bak ground and STRING is the: "+st);
                return st;

            }

        }
        GetPreNews lc = new GetPreNews();
        lc.execute();



    }


}
