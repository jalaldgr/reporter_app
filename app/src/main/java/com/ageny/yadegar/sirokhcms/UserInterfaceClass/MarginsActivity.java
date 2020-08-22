package com.ageny.yadegar.sirokhcms.UserInterfaceClass;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ageny.yadegar.sirokhcms.DataModelClass.MarginDataModelClass;
import com.ageny.yadegar.sirokhcms.HTTPRequestHandlre;
import com.ageny.yadegar.sirokhcms.ItemAdapter;
import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.MYSQlDBHelper;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MarginsActivity extends AppCompatActivity {
    String RID,UID;
    static MYSQlDBHelper zmyDBHelper;
    public Context Cntx;
    MarginsItemAdapter MarginTableAdabptor;
    RecyclerView MrgRecyclerView;
    FloatingActionButton FlActBtn;
    public ArrayList<MarginDataModelClass> MarginsList=null;
    ArrayList<MarginDataModelClass> RemindersList = new ArrayList<MarginDataModelClass>();

    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_margins);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("هامش");
        Cntx=this.getApplicationContext();
        Intent intent=getIntent();
        RID=intent.getStringExtra("cartableid");
        UID=intent.getStringExtra("userid");
        boolean reminderswtch=false;
        FlActBtn=findViewById(R.id.floatingActionButton);
        FlActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cntx, AddMarginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("cartableid",RID);
                intent.putExtra("userid",UID);
                Cntx.startActivity(intent);
            }
        });


        MrgRecyclerView = (RecyclerView) findViewById(R.id.margin_recycleview);
        if( JSONHandlre.isConnectedtoInternet(Cntx)) {
            GetReferralFolderMargins(RID, UID);


            Switch reminderswt = (Switch) findViewById(R.id.MarginsActivityRmdSwtch);
            reminderswt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {


                        if (RemindersList.size() > 0) {
                            MarginTableAdabptor = new MarginsItemAdapter(RemindersList, Cntx, MarginsActivity.this);
                            MrgRecyclerView.setItemAnimator(new DefaultItemAnimator());//add and delete records animation
                            MrgRecyclerView.setHasFixedSize(true);//fix control size's
                            MrgRecyclerView.setAdapter(MarginTableAdabptor);
                        }
                    } else {
                        if (MarginsList.size() > 0) {
                            MarginTableAdabptor = new MarginsItemAdapter(MarginsList, Cntx, MarginsActivity.this);
                            MrgRecyclerView.setItemAnimator(new DefaultItemAnimator());//add and delete records animation
                            MrgRecyclerView.setHasFixedSize(true);//fix control size's
                            MrgRecyclerView.setAdapter(MarginTableAdabptor);
                        }
                    }
                }
            });

        }
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


    ///////////////////// Asyyyyyyyyyyyyyync Method/////////////////////////////////
    public void GetReferralFolderMargins( String RefferalID, String UserID){
        final String ParamRID = RefferalID;
        final String ParamUID = UserID;
        class GetReferralFolderMargins extends AsyncTask<Void, Void, String> {
            private final ProgressDialog dialog = new ProgressDialog(MarginsActivity.this);
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
                    JSONObject obj = new JSONObject(s);
                    ArrayList<MarginDataModelClass> RetirnList= new ArrayList<MarginDataModelClass>();

                    Log.d("hhh", "KKKKKKKKKK: "+ obj.toString());
                    //if no error in response
                    if (obj.getInt("State")>0) {
                        //getting the user from the response
                        JSONArray TmpJsonArr = obj.getJSONArray("Data");
                        JSONObject JsonStr=null;
                        for (int i=0; i<TmpJsonArr.length(); i++){
                            JsonStr = TmpJsonArr.getJSONObject(i);
                            MarginDataModelClass TmpCV = new MarginDataModelClass(
                                    JsonStr.getString("Description"),
                                    JsonStr.getString("RememberDate"),
                                    JsonStr.getString("created_at"),
                                    JsonStr.getString("Title"),
                                    JsonStr.getString("first_name"),
                                    JsonStr.getString("last_name"));
                            RetirnList.add(TmpCV);
                        }Log.d("hhh", "KKKKKKKKKK: "+RetirnList.toString());
                        MarginsList =RetirnList;
                        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Cntx);
                        MrgRecyclerView.setLayoutManager(mLayoutManager);
                        MrgRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        if (MarginsList!=null && MarginsList.size() > 0) {
                            MarginTableAdabptor = new MarginsItemAdapter(MarginsList, Cntx, MarginsActivity.this);
                            MrgRecyclerView.setItemAnimator(new DefaultItemAnimator());//add and delete records animation
                            MrgRecyclerView.setHasFixedSize(true);//fix control size's
                            MrgRecyclerView.setAdapter(MarginTableAdabptor);
                        }


                        if (MarginsList!=null && MarginsList.size() > 0) {
                            for (int kkk = 0; kkk < MarginsList.size(); kkk++) {
                                if (MarginsList.get(kkk).getTitle().equals("یادآوری")) {
                                    RemindersList.add(MarginsList.get(kkk));
                                }

                            }
                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();  Log.d("hhh", "ERROR in Load Margins: "+ex.toString());
                }
                if(this.dialog.isShowing()){
                    this.dialog.dismiss();

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
                String st =  requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getReferralFolderMarginsURL(), params);
                return st;
            }
        }

        GetReferralFolderMargins TM = new GetReferralFolderMargins();
        TM.execute();

    }

}
