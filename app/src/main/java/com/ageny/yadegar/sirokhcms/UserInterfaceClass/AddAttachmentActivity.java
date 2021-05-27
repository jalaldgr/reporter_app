package com.ageny.yadegar.sirokhcms.UserInterfaceClass;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.MultipartUtility;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;

import org.json.JSONObject;

import java.io.File;
import java.util.Arrays;
import java.util.List;


public class AddAttachmentActivity extends AppCompatActivity {
    static final String TAG="hhh";
    public static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE =1 ;
    Context Cntx;
    String RID,UID=null;
    TextView AttachmentDescriptionTxt;
    TextView AttachmentFilenameTxt;
    Uri selectedfile;
    String resultstr;
    Activity ac;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attachment);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        Button ChooseFileBttn = (Button) findViewById(R.id.AddAttachementChooseFile);
        Button CancelBttn = (Button)findViewById(R.id.AddAttachmentCancelBttn);
        Button SubmitBttn = (Button)findViewById(R.id.AddAttachmentSubmitBttn);
        AttachmentDescriptionTxt= (TextView)findViewById(R.id.AddAttachmentDescriptionTxt);
        AttachmentFilenameTxt = (TextView)findViewById(R.id.AddattachmentfilenameTxt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("افزودن فایل ضمیمه به مدرک");
        Cntx = this.getApplicationContext();
        Intent intent=getIntent();
        RID=intent.getStringExtra("cartableid");
        UID=intent.getStringExtra("userid");

        //ac = getAc



        ChooseFileBttn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent().setType("*/*").setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent2, "Select a file"), 123);
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {

                    // Should we show an explanation?
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        // Explain to the user why we need to read the contacts
                    }
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    return;
                }

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
                if(selectedfile!=null) {

                    if (JSONHandlre.isConnectedtoInternet(getApplicationContext())) {
                        String selectedFilePath = FilePath.getPath(Cntx, selectedfile);
                        ReferralFolderAttachmentAdd(selectedFilePath, RID, UID, AttachmentDescriptionTxt.getText().toString());
                    }
                }
                else
                    {
                        Toast toast= Toast.makeText(Cntx,
                                "لطفا فایل ضمیمه را انتخاب کنید" , Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                        ViewGroup group = (ViewGroup) toast.getView();
                        TextView messageTextView = (TextView) group.getChildAt(0);
                        messageTextView.setTextSize(18);
                        toast.show();

                    }


            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK) {
            selectedfile = data.getData(); //The uri with the location of the file
            AttachmentFilenameTxt.setText( selectedfile.getPath());

        }
    }

    ///////////////change fonts to yekan/////////////////////
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



    public  void ReferralFolderAttachmentAdd(String ReferralFolderFile, String RID,
                                                     String UID,String ReferralFolderFileDescription){
        final String ParamReferralFile = ReferralFolderFile;
        final String ParamRID = RID;
        final String ParamUID = UID;
        final String ParamFileDes = ReferralFolderFileDescription;

        class ReferralFolderAttachmentAdd extends AsyncTask<Void, Void, List<String>> {
            private final ProgressDialog dialog = new ProgressDialog(AddAttachmentActivity.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.d(TAG, "onPreExecute dialog shows");

                this.dialog.setMessage("ارسال اطلاعات...");
                this.dialog.setCanceledOnTouchOutside(false);
                 this.dialog.show();
            }

            @Override
            protected void onPostExecute(List<String> s) {
                super.onPostExecute(s);
                try {
                    Log.d(TAG, "onPostExecute s :"+s.toString());
                    JSONObject obj = new JSONObject(s.get(0));
                    if (obj.getInt("State")>0)
                        resultstr = "فایل با موفقیت ضمیمه شد.";
                    else
                        resultstr = obj.toString();

                } catch (Exception e) {
                    e.printStackTrace();resultstr = "خطا در ارسال اطلاعات...";
                }
                if(this.dialog.isShowing()) this.dialog.dismiss();
                Toast toast= Toast.makeText(Cntx,
                        resultstr, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                ViewGroup group = (ViewGroup) toast.getView();
                TextView messageTextView = (TextView) group.getChildAt(0);
                messageTextView.setTextSize(18);
                toast.show();
                finish();
            }

            @Override
            protected List<String> doInBackground(Void... voids) {

                try {
                    String requesturl = URLs.getBaseURL()+URLs.getReferralFolderAttachmentAddURL();
                    Log.d(TAG, "doInBackground: file address"+ParamReferralFile);

                    final File file = new File(ParamReferralFile);
                    MultipartUtility multipart = new MultipartUtility(requesturl, "UTF-8");
                    multipart.addFormField("RID",ParamRID);
                    multipart.addFormField("UID",ParamUID);
                    multipart.addFormField("ReferralFolderFileDescription",ParamFileDes);
                    multipart.addFilePart("ReferralFolderFile",file.getAbsoluteFile());

                    List<String> response = multipart.finish();
                    Log.d(TAG, "doInBackground: file end");
                    for (String line : response) {
                        Log.d(TAG, "Upload Files Response:::" + line);
                        String responseString = line;
                    }

                    return response;
                } catch (Exception e){
                    List<String> list = Arrays.asList(e.toString());
                    return list;
                }
            }
        }
        ReferralFolderAttachmentAdd lc = new ReferralFolderAttachmentAdd();
        lc.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,null);

    }
}

