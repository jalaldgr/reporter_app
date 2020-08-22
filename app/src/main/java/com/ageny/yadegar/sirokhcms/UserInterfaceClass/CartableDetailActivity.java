package com.ageny.yadegar.sirokhcms.UserInterfaceClass;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ageny.yadegar.sirokhcms.DataModelClass.ShowReferralFolderDataModel;
import com.ageny.yadegar.sirokhcms.HTTPRequestHandlre;
import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.MYSQlDBHelper;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class CartableDetailActivity extends AppCompatActivity {
    String UID,RID,RSID,RNTT,startinterviewstr,stopinterviewstr=null;;
    String startinterviewresult,stopinterviewresult="";
    static MYSQlDBHelper myDBHelper;
    public Context Cntx;
    public ShowReferralFolderDataModel Ur = null;

    TextView textViewSubject;
    TextView textViewDescription;
    TextView textViewOrgan;
    TextView textViewPeople;
    //        TextView textViewtoUser=findViewById(R.id.CartableDetailtoUserTxt);
    TextView textViewUrgency;
    TextView textViewActiondate;
    TextView textViewNewsType;
    LinearLayout organlayout ;
    LinearLayout peoplelayout ;
    Button startbttn;
    Button stopbtn;
    TextView stopinterviewtxt;
    TextView startinterviewtxt;
    BottomNavigationView bottomNavigationView;

    // ArrayIndexOutOfBoundsException


    @Override
    protected void onStart() {
        super.onStart();
        if(Integer.parseInt(RSID)>1){
            bottomNavigationView.findViewById(R.id.action_return_refrral).setEnabled(false);
            bottomNavigationView.findViewById(R.id.action_return_refrral).setBackgroundColor(
                    getResources().getColor(R.color.colorReturned));
        }
        if(Integer.parseInt(RSID)!=1){
            bottomNavigationView.findViewById(R.id.action_submit_information).setEnabled(false);
            bottomNavigationView.findViewById(R.id.action_submit_information).setBackgroundColor(
                    getResources().getColor(R.color.colorReturned));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartable_detail);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Cntx=CartableDetailActivity.this;
        Intent intent=getIntent();
        RID=intent.getStringExtra("cartableid");
        UID=intent.getStringExtra("userid");
        RSID=intent.getStringExtra("referralstateid");
        RNTT= intent.getStringExtra("RefferalNewsTypeTitle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("مشاهده مدرک");
         bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_return_refrral:
                        Intent intent = new Intent(Cntx, ReturnReferralActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("userid",UID );
                        intent.putExtra("cartableid",RID );
                        intent.putExtra("referralstateid",RSID );
                        if(Integer.parseInt(RSID)==2 )item.setEnabled(false);
                        Cntx.startActivity(intent);
                        break;
                    case R.id.action_margin:
                        Intent intent2 = new Intent(Cntx, MarginsActivity.class);
                        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent2.putExtra("userid",UID );
                        intent2.putExtra("cartableid",RID );
                        Cntx.startActivity(intent2);
                        break;
                    case R.id.action_attachment_file:
                        Intent intent3 = new Intent(Cntx, Attachments_activity.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent3.putExtra("userid",UID );
                        intent3.putExtra("cartableid",RID );
                        //intent.putExtra("cartableid",UR.getId() );
                        Cntx.startActivity(intent3);
                        break;
                    case R.id.action_submit_information:
                        Intent intent4 = new Intent(Cntx, SubmitInformationActivity.class);
                        intent4.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent4.putExtra("userid",UID );
                        intent4.putExtra("cartableid",RID );
                        Cntx.startActivity(intent4);
                        break;
                }
                return true;
            }
        });


         textViewSubject=findViewById(R.id.CartableDetailSubjectTxt);
         textViewDescription=findViewById(R.id.CartableDetailDescriptionTxt);
         textViewOrgan=findViewById(R.id.CartableDetailOrganTxt);
         textViewPeople=findViewById(R.id.CartableDetailPeopleTxt);
//        TextView textViewtoUser=findViewById(R.id.CartableDetailtoUserTxt);
         textViewUrgency=findViewById(R.id.CartableDetailUrgencyTxt);
         textViewActiondate=findViewById(R.id.CartableDetailActionDateTxt);
         textViewNewsType=findViewById(R.id.CartableDetailNewsTypeTxt);
        LinearLayout organlayout = findViewById(R.id.CartableDetailOrganLayout);
        LinearLayout peoplelayout = findViewById(R.id.CartableDetailpeopleLayout);
        startbttn = (Button)findViewById(R.id.startinterviewbtn);
        stopbtn = (Button)findViewById(R.id.stopinterviewbtn);
        startinterviewtxt =(TextView) findViewById(R.id.start_interview_txt);
        stopinterviewtxt = (TextView)findViewById(R.id.stop_interview_txt);

        if(JSONHandlre.isConnectedtoInternet(getApplicationContext())) {
            ShowReferralFolder(RID, UID);
            GetPreNews(RID);
        }
            switch (RSID.toString()){
                case "1":
                    textViewUrgency.setText("در دست اقدام");
                    break;
                case "2":
                    textViewUrgency.setText("برگشت خورده");
                    break;
                case "3":
                    textViewUrgency.setText("انجام شده");
                    break;

            }
            textViewNewsType.setText(RNTT.toString());
         //   startinterview.setText(Ur.get);
//            startinterview.setText();
//            stopinterview.setText();

        final AlertDialog.Builder InfoAlertDialog =new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));

        organlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customLayout = getLayoutInflater().inflate(R.layout.organ_alert_dialog_info ,null);
                TextView TitleTxt = customLayout.findViewById(R.id.textView_organ_alertdialog_organname);
                TextView PhoneTxt = customLayout.findViewById(R.id.textView_organ_alertdialog_phon);
                TextView FaxTxt = customLayout.findViewById(R.id.textView_organ_alertdialog_fax);
                TextView AddressTxt = customLayout.findViewById(R.id.textView_organ_alertdialog_address);
                if(Ur.getOrgan_Title() != null)
                    TitleTxt.setText(Ur.getOrgan_Title());
                else
                    TitleTxt.setText("نامشخص");
                if(Ur.getOrgan_Fax() != "null")
                    FaxTxt.setText(Ur.getOrgan_Fax());
                else
                    FaxTxt.setText("نامشخص");
                if(Ur.getOrgan_Phone() != "null")
                    PhoneTxt.setText(Ur.getOrgan_Phone());
                else
                    PhoneTxt.setText("نامشخص");
                if(Ur.getOrgan_Address() != "null")
                    AddressTxt.setText(Ur.getOrgan_Address());
                else
                    AddressTxt.setText("نامشخص");
                InfoAlertDialog.setTitle("اطلاعات ارگان");
                InfoAlertDialog.setView(customLayout);
                InfoAlertDialog.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog al = InfoAlertDialog.create();
                al.show();
            }
        });

        peoplelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customLayout = getLayoutInflater().inflate(R.layout.people_alert_dialog_info ,null);
                TextView FullNameTxt = customLayout.findViewById(R.id.textView_people_alertdialog_fullname);
                TextView PhoneTxt = customLayout.findViewById(R.id.textView_people_alertdialog_phon);
                TextView MobileTxt = customLayout.findViewById(R.id.textView_people_alertdialog_mobile);
                TextView AddressTxt = customLayout.findViewById(R.id.textView_people_alertdialog_address);
                FullNameTxt.setText(Ur.getPeople_first_name()+" "+Ur.getPeople_last_name());
                if(Ur.getPeople_Mobile() != "null")
                    MobileTxt.setText(Ur.getPeople_Mobile());
                else
                    MobileTxt.setText("نامشخص");
                if(Ur.getPeople_Phone() != "null")
                    PhoneTxt.setText(Ur.getPeople_Phone());
                else
                    PhoneTxt.setText("نامشخص");
                if(Ur.getPeople_Address() != "null")
                    AddressTxt.setText(Ur.getPeople_Address());
                else
                    AddressTxt.setText("نامشخص");

                InfoAlertDialog.setTitle("اطلاعات مصاحبه شونده");
                InfoAlertDialog.setView(customLayout);
                InfoAlertDialog.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog al = InfoAlertDialog.create();
                al.show();
            }
        });

        startbttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(JSONHandlre.isConnectedtoInternet(getApplicationContext())) {

                    ReferralFolderStartInterview(RID);
                    GetPreNews(RID);
                }
            }
        });
        stopbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(JSONHandlre.isConnectedtoInternet(getApplicationContext())) {

                    ReferralFolderEndInterview(RID);
                    GetPreNews(RID);
                }
            }
        });
    }

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




    private void ShowReferralFolder(  String RefferalID, String UserID) {
        final String ParamUID = UserID;
        final String ParamRID = RefferalID;

        class ShowReferralFolder extends AsyncTask<Void, Void, String> {
            private final ProgressDialog dialog = new ProgressDialog(CartableDetailActivity.this);


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                this.dialog.setMessage("دریافت اطلاعات...");
                this.dialog.setIndeterminate(true);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {
                    //converting response to json object

                    Log.d("hhh", " ShowReferralFolder: try "+ s);
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    if (obj.getInt("State")>0) {
                        //getting the user from the response
                        JSONObject obj1, obj2, obj3 =null;
                        //JSONArray rflobj
                        obj1  = obj.getJSONObject("ReferralFolder");

                        JSONArray Plpobj = obj.getJSONArray("People");
                        JSONArray orgobj = obj.getJSONArray("Organs");
                        /*obj1 = rflobj.getJSONObject(0);*/obj2 = Plpobj.getJSONObject(0);obj3 = orgobj.getJSONObject(0);
                        Ur = new ShowReferralFolderDataModel(
                                obj1.get("id").toString(),
                                obj1.get("Subject").toString(),
                                obj1.get("Description").toString(),
                                obj1.get("from_user_id").toString(),
                                obj1.get("to_user_id").toString(),
                                obj1.get("referral_folder_state_id").toString(),
                                obj1.get("urgency_id").toString(),
                                obj1.get("Seen").toString(),
                                obj1.get("news_type_id").toString(),
                                obj1.get("ActionDate").toString(),
                                obj1.get("created_at").toString(),
                                obj1.get("updated_at").toString(),
                                obj2.get("id").toString(),
                                obj2.get("first_name").toString(),
                                obj2.get("last_name").toString(),
                                obj2.get("Phone").toString(),
                                obj2.get("Mobile").toString(),
                                obj2.get("Address").toString(),
                                obj2.get("Image").toString(),
                                obj3.get("id").toString(),
                                obj3.get("Title").toString(),
                                obj3.get("Address").toString(),
                                obj3.get("Phone").toString(),
                                obj3.get("Fax").toString());


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();  Log.d("hhh", "ERROR ShowReferralFolder: "+ex.toString());
                }
                textViewSubject.setText(Ur.getSubject());
                textViewDescription.setText(Ur.getDescription());
                textViewOrgan.setText(Ur.getOrgan_Title());
                textViewPeople.setText(Ur.getPeople_first_name() + " " + Ur.getPeople_last_name());
                textViewActiondate.setText(Ur.getActionDate());
                if(this.dialog.isShowing()){
                    this.dialog.dismiss();

                }

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
                params.put("UID", ParamUID);
                //returing the response
                String st =  requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getShowRefferalFolderURL(), params);
                //Log.d("hhh: " , "do in bak ground and STRING is the: "+st);

                return st;

            }
        }
        ShowReferralFolder lc = new ShowReferralFolder();
        lc.execute();

    }

    public  void ReferralFolderStartInterview( String RefferalID){
        final String ParamRID           = RefferalID;

        class ReferralFolderStartInterview extends AsyncTask<Void, Void, String> {
            //private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // this.dialog.setMessage("ارسال اطلاعات...");
                //  this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                //  this.dialog.dismiss();
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    if(obj.getInt("State")>0) {
                        startinterviewresult = s;
                        Toast.makeText(CartableDetailActivity.this, "شروع مصاحبه ثبت شد", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();  Log.d("hhh", "ReferralFolderStartInterview: "+ex.toString());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();

                params.put("Content-Type", "application/json");
                params.put("RID", ParamRID);

                String st =  requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getReferralFolderStartInterviewURL(), params);
                return st;
            }
        }
        ReferralFolderStartInterview TM = new ReferralFolderStartInterview();
        TM.execute();
    }
    public  void ReferralFolderEndInterview( String RefferalID){
        final String ParamRID           = RefferalID;

        class ReferralFolderEndInterview extends AsyncTask<Void, Void, String> {
            // private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //   this.dialog.setMessage("دریافت اطلاعات...");
                //  this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                // this.dialog.dismiss();
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    if (obj.getInt("State")>0) {
                        Toast.makeText(CartableDetailActivity.this, "پایان مصاحبه ثبت شد", Toast.LENGTH_SHORT).show();
                        stopinterviewresult = s;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();  Log.d("hhh", "ReferralFolderStartInterview: "+ex.toString());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();

                params.put("Content-Type", "application/json");
                params.put("RID", ParamRID);

                String st =  requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getReferralFolderEndInterviewURL(), params);
                return st;
            }
        }
        ReferralFolderEndInterview TM = new ReferralFolderEndInterview();
        TM.execute();
    }



    public void GetPreNews(  String RefferalID) {
        final String ParamRID = RefferalID;

        class GetPreNews extends AsyncTask<Void, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object

                    Log.d("hhh", " GetPreNews: try "+ s);
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    if (!obj.get("Data").toString().equals(null)) {
                        JSONObject kk = new JSONObject(obj.get("Data").toString());
                        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
                        PersianDate pdatestart,pdatestop;

                        pdatestart=persianDateFormat.parseGrg(kk.get("StartInterview").toString(),"yyyy-MM-dd HH:mm:ss");
                        startinterviewtxt.setText(pdatestart.toString());

                        pdatestop=persianDateFormat.parseGrg(kk.get("EndInterview").toString(),"yyyy-MM-dd HH:mm:ss");
                        stopinterviewtxt.setText(pdatestop.toString());

                        if(kk.get("StartInterview").toString().equals("null"))
                            startbttn.setVisibility(View.VISIBLE);
                        else
                            startbttn.setVisibility(View.GONE);

                        if(kk.get("EndInterview").toString().equals("null"))
                            stopbtn.setVisibility(View.VISIBLE);
                        else
                            stopbtn.setVisibility(View.GONE);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();  Log.d("hhh", "ERROR GetPreNews: "+ex.toString());
                }


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