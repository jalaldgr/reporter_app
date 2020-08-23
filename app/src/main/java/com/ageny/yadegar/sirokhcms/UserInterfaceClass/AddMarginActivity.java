package com.ageny.yadegar.sirokhcms.UserInterfaceClass;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.ageny.yadegar.sirokhcms.DataModelClass.UserDataModelClass;
import com.ageny.yadegar.sirokhcms.HTTPRequestHandlre;
import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.time.RadialPickerLayout;
import com.mohamadamin.persianmaterialdatetimepicker.time.TimePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;
import org.apache.http.util.TextUtils;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import saman.zamani.persiandate.PersianDate;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddMarginActivity extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    private Spinner TitleSpinner ;
    Context Cntx;
    UserDataModelClass user;
    String pickeddate,pickedtime,resultdatetime="";
    String strresult;
    public TextView dateedittext;
    public TextView timeedittext;
    TimePickerDialog timePickerDialog;
    DatePickerDialog NewMarginDatepicker;
    public EditText descriptionedittext;
    Date grgdate;
    PersianDate persianDate;
    String strgrgdate="";
    PersianCalendar persianCalendar = new PersianCalendar();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_margin);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        Cntx=this.getApplicationContext();
        dateedittext= (TextView) findViewById(R.id.NewMarginDateEditTxt);
        timeedittext= (TextView) findViewById(R.id.NewMarginTimeEditTxt);
        Button SubmitBttn=(Button) findViewById(R.id.NewMarginSubmitBttn);
        Button CancelBttn=(Button) findViewById(R.id.NewMarginCancelBttn);
        descriptionedittext =(EditText)findViewById(R.id.NewMarginDescriptionEditTxt) ;
        final LinearLayout timedatelyt = (LinearLayout)findViewById(R.id.New_margin_timedate_lyt);
        timedatelyt.setVisibility(View.INVISIBLE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent=getIntent();
        final String RID=intent.getStringExtra("cartableid");
        final String UID=intent.getStringExtra("userid");
        long stateId;
        persianDate = new PersianDate();

        addItemsOnTitleSpinner();
        addListenerOnSpinnerItemSelection();
        NewMarginDatepicker = DatePickerDialog.newInstance(
                AddMarginActivity.this,
                persianCalendar.getPersianYear(),
                persianCalendar.getPersianMonth(),
                persianCalendar.getPersianDay()
        );
        NewMarginDatepicker.setMinDate(persianCalendar);
        timePickerDialog =  TimePickerDialog.newInstance(this,
                persianCalendar.get(PersianCalendar.HOUR_OF_DAY),
                persianCalendar.get(PersianCalendar.MINUTE),
                true);

        TitleSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            if (position==1)timedatelyt.setVisibility(View.VISIBLE);
            else timedatelyt.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dateedittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewMarginDatepicker.show(getFragmentManager(), "Datepickerdialog");

            }
        });

        timeedittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        CancelBttn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        SubmitBttn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
            if (JSONHandlre.isConnectedtoInternet(getApplicationContext())) {
                if ((TitleSpinner.getSelectedItemId() == 0 && TextUtils.isEmpty(descriptionedittext.getText().toString())
                        || ((TitleSpinner.getSelectedItemId() == 1) && (TextUtils.isEmpty(dateedittext.getText().toString())
                        || TextUtils.isEmpty(timeedittext.getText().toString())) || TextUtils.isEmpty(descriptionedittext.getText().toString())))) {


                    Toast toast= Toast.makeText(Cntx,
                            "لطفا ورودی ها را کنترل کنید", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    ViewGroup group = (ViewGroup) toast.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(18);
                    toast.show();
                }else
                {
                    ReferralFolderMarginAdd(RID, UID,descriptionedittext.getText().toString(),
                            Long.toString(TitleSpinner.getSelectedItemId() + 1),pickeddate+" "+pickedtime);
                }
            }

            }
        });
    }
///////////////// spinner ///////////////////////
    public void addItemsOnTitleSpinner() {
        TitleSpinner = (Spinner) findViewById(R.id.margin_title_spinner);
        List<String> list = new ArrayList<String>();
        list.add("عادی");
        list.add("یادآوری");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        TitleSpinner.setAdapter(dataAdapter);
    }

    public void addListenerOnSpinnerItemSelection() {
        TitleSpinner = (Spinner) findViewById(R.id.margin_title_spinner);
    }

    ///////////////change fonts to yekan/////////////////////
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
        pickedtime = hourOfDay+":"+minute+":00";
        timeedittext.setText(pickedtime);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        pickeddate = year+"-"+(monthOfYear+1)+"-"+dayOfMonth;
        dateedittext.setText(pickeddate);
    }


    public void ReferralFolderMarginAdd( String RefferalID, String UserID, String ReferralFolderMarginDescription ,
                                                 String ReferralFolderMarginStateID, String RememberDate ){
        final String ParamRID           = RefferalID;
        final String ParamUID           = UserID;
        final String ParamDescription   = ReferralFolderMarginDescription;
        final String ParamStateID       = ReferralFolderMarginStateID;
        final String ParamRememberDate  = RememberDate;
        final String Paramcreated_at    = Calendar.getInstance().getTime().toString();
        final String ParamTitle         = ReferralFolderMarginDescription.split("", 20).toString();
        final String Paramfirst_name    = RememberDate;
        final String Paramlast_name     = RememberDate;

        class ReferralFolderMarginAdd extends AsyncTask<Void, Void, String> {
            private final ProgressDialog dialog = new ProgressDialog(AddMarginActivity.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                this.dialog.setMessage("ارسال اطلاعات...");
                this.dialog.setCanceledOnTouchOutside(false);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                if(this.dialog.isShowing())this.dialog.dismiss();
                super.onPostExecute(s);

                try {
                    JSONObject obj = new JSONObject(s);
                    if (obj.getInt("State")>0)
                        strresult = "هامش جدید با موفقیت ثبت شد.";
                    else
                        strresult = "خطایی رخ داده، هامش ثبت نشد.";
                } catch (Exception e) {
                    strresult = "خطایی در ارتباط رخ داده، هامش ثبت نشد.";
                    e.printStackTrace();
                }
                Toast toast= Toast.makeText(Cntx,
                        strresult, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                ViewGroup group = (ViewGroup) toast.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(18);
                toast.show();
                finish();

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
                params.put("ReferralFolderMarginDescription", ParamDescription);
                params.put("ReferralFolderMarginStateID", ParamStateID);
                params.put("RememberDate", ParamRememberDate);
                String st =  requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getReferralFolderMarginAddURL(), params);
                return st;
            }
        }
        ReferralFolderMarginAdd TM = new ReferralFolderMarginAdd();
        TM.execute();

    }


}
