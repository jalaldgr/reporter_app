package com.ageny.yadegar.sirokhcms.UserInterfaceClass;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ageny.yadegar.sirokhcms.HTTPRequestHandlre;
import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReturnReferralActivity extends AppCompatActivity {
    private Spinner ReasonsSpinner ;
    Context Cntx;
    long ReasonId;
    public String strrespone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_referral);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Cntx=this.getApplicationContext();
        Intent intent=getIntent();
        final String RID=intent.getStringExtra("cartableid");
        final String UID=intent.getStringExtra("userid");
        final String RSID=intent.getStringExtra("referralstateid");
        final EditText descriptiontext= (EditText) findViewById(R.id.ReturnReferralDescription);
        ReasonsSpinner = (Spinner)findViewById(R.id.ReturnReferralReasonsSpnr);
        Button SubmitReturnReferralBttn = (Button) findViewById(R.id.SubmitReturnReferralBttn) ;
        getSupportActionBar().setTitle("بازگشت مدرک");
        addItemsOnReturnReferralReasonsSpinner();
        addListenerOnSpinnerReasonsSelection();


        ///////Click On Submit Button////////////
        SubmitReturnReferralBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(JSONHandlre.isConnectedtoInternet(getApplicationContext()))
                {
                    ReferralFolderReturnSubmit(RID,UID,descriptiontext.getText().toString(),Long.toString(ReasonId) );
                }
            }
        });
    }
//    ///////////////change fonts to yekan/////////////////////
//    @Override
//    protected void attachBaseContext(Context newBase) {
//        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
//
//    }
    ///////////////// spinner ///////////////////////
    public void addItemsOnReturnReferralReasonsSpinner() {
        ReasonsSpinner = (Spinner) findViewById(R.id.ReturnReferralReasonsSpnr);
        List<String> list = new ArrayList<String>();
        list.add("دیگر موارد");
        list.add("برگزار نشدن جلسه");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ReasonsSpinner.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerReasonsSelection() {
        ReasonsSpinner = (Spinner) findViewById(R.id.ReturnReferralReasonsSpnr);
        ReasonsSpinner.setOnItemSelectedListener(new ReturnReferralReasonsOnItemSelectedListener());
    }
    ////////////////////////////////////////////////////////
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


    public class ReturnReferralReasonsOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
            ReasonId=ReasonsSpinner.getSelectedItemId()+1;
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
    /////////////////////////AsyncReferralFolderReturnSubmit////////////////////////
    public void ReferralFolderReturnSubmit( String RefferalID, String UserID, String Description, String ReturnReasonID){

        final String ParamRID           = RefferalID;
        final String ParamUID           = UserID;
        final String ParamDescription   = Description;
        final String ParamRetRsnID      = ReturnReasonID;
        class ReferralFolderReturnSubmit extends AsyncTask<Void, Void, String> {
             private final ProgressDialog dialog = new ProgressDialog(ReturnReferralActivity.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage("در حال ارسال اطلاعات...");
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.show();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                String str;
                try {
                    str=s;
                    JSONObject obj = new JSONObject(str);
                    if (obj.getInt("State")>0)
                        str = "برگشت مدرک با موفقیت ثبت شد.";
                    else
                        str = "خطایی رخ داده، برگشت مدرک ثبت نشد.";
                } catch (Exception e) {
                    e.printStackTrace();
                    str = "خطایی رخ داده، برگشت مدرک ثبت نشد.";
                }



                strrespone= str;
                Toast toast= Toast.makeText(Cntx,
                        strrespone , Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                ViewGroup group = (ViewGroup) toast.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(18);
                toast.show();
                if (this.dialog.isShowing())this.dialog.dismiss();

                finish();
                Intent intent = new Intent(Cntx, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Cntx.startActivity(intent);

            }
            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("RID", ParamRID);
                params.put("UID", ParamUID);
                params.put("ReferralFolderReturnDescription", ParamDescription);
                params.put("ReferralFolderReturnReasonID", ParamRetRsnID);
                String st =  requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getReferralFolderReturnSubmitURL(), params);
                return st;
            }
        }
        ReferralFolderReturnSubmit TM = new ReferralFolderReturnSubmit();
        TM.execute();

    }

}
