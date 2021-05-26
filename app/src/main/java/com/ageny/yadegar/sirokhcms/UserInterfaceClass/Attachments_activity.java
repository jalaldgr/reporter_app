package com.ageny.yadegar.sirokhcms.UserInterfaceClass;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ageny.yadegar.sirokhcms.DataModelClass.ReferralFolderAttachmentShowDataModelClass;
import com.ageny.yadegar.sirokhcms.HTTPRequestHandlre;
import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Attachments_activity extends AppCompatActivity {
    String RID,UID;
    public Context Cntx;
    AttachmentsItemAdapter AttachmentsTableAdabptor;
    RecyclerView AtchRecyclerView;
    FloatingActionButton FlActBtn;
    ArrayList<ReferralFolderAttachmentShowDataModelClass> AttachmentsList;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachments_activity);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("فایل ضمیمه");
        Cntx=this.getApplicationContext();
        Intent intent=getIntent();
        RID=intent.getStringExtra("cartableid");
        UID=intent.getStringExtra("userid");
        FlActBtn=findViewById(R.id.AttachmentfloatingActionButton);
        FlActBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cntx, AddAttachmentActivity.class);
                intent.putExtra("cartableid",RID);
                intent.putExtra("userid",UID);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Cntx.startActivity(intent);
            }
        });
        AttachmentsTableAdabptor= new AttachmentsItemAdapter(AttachmentsList,Cntx,Attachments_activity.this);
        AtchRecyclerView = (RecyclerView) findViewById(R.id.attachment_recycleview);
        JSONHandlre.isConnectedtoInternet(getApplicationContext());
        ReferralFolderAttachmentShow(RID);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Cntx);
        AtchRecyclerView.setLayoutManager(mLayoutManager);
        AtchRecyclerView.setItemAnimator(new DefaultItemAnimator());


    }

//    ///////////////change fonts to yekan/////////////////////
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//    }
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



    public void ReferralFolderAttachmentShow( String RefferalID ){
        final String ParamRID           = RefferalID;

        class ReferralFolderAttachmentShow extends AsyncTask<Void, Void, String> {
            private final ProgressDialog dialog = new ProgressDialog(Attachments_activity.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage("دریافت اطلاعات...");
                this.dialog.setIndeterminate(true);
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.show();
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                ArrayList<ReferralFolderAttachmentShowDataModelClass> ReturnList= new ArrayList<ReferralFolderAttachmentShowDataModelClass>();
                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    if (obj.getInt("State")>0) {
                        JSONArray TmpJsonArr = obj.getJSONArray("Data");
                        JSONObject JsonStr=null;
                        for (int i=0; i<TmpJsonArr.length(); i++){
                            JsonStr = TmpJsonArr.getJSONObject(i);
                            ReferralFolderAttachmentShowDataModelClass Tmp = new ReferralFolderAttachmentShowDataModelClass(
                                    JsonStr.getString("Path"),
                                    JsonStr.getString("Description"),
                                    JsonStr.getString("created_at"),
                                    JsonStr.getString("first_name"),
                                    JsonStr.getString("last_name"));
                            ReturnList.add(Tmp);
                        }
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();  Log.d("hhh", "ReferralFolderAttachmentShow: "+ex.toString());
                }
                AttachmentsList= ReturnList;
                if(AttachmentsList.size()>0) {
                    AttachmentsTableAdabptor = new AttachmentsItemAdapter(AttachmentsList, Cntx,Attachments_activity.this);
                    AtchRecyclerView.setItemAnimator(new DefaultItemAnimator());//add and delete records animation
                    AtchRecyclerView.setHasFixedSize(true);//fix control size's
                    AtchRecyclerView.setAdapter(AttachmentsTableAdabptor);
                }
                if(this.dialog.isShowing()){
                    this.dialog.dismiss();

                }
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    }
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            AddAttachmentActivity.PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    return;
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
                String st =  requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getReferralFolderAttachmentShowURL(), params);
                return st;
            }
        }

        ReferralFolderAttachmentShow TM = new ReferralFolderAttachmentShow();
        TM.execute();

    }


}
