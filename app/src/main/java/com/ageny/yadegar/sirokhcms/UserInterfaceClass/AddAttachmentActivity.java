package com.ageny.yadegar.sirokhcms.UserInterfaceClass;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.AbstractSequentialList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static java.util.Collections.copy;


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
                        Log.d(TAG, "onClick selected File: "+selectedFilePath);
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



    public  void ReferralFolderAttachmentAdd(String ReferralFolderFile, String RID,
                                                     String UID,String ReferralFolderFileDescription){
        final String ParamReferralFile = ReferralFolderFile;
        final String ParamRID = RID;
        final String ParamUID = UID;
        final String ParamFileDes = ReferralFolderFileDescription;

        class ReferralFolderAttachmentAdd extends AsyncTask<Void, Void, String> {
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
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                    if (obj.getInt("State")>0)
                        resultstr = "فایل با موفقیت ضمیمه شد.";
                    else
                        resultstr = obj.toString(); /* "خطایی رخ داده، فایل ضمیمه نشد."*/
                } catch (Exception e) {
                    e.printStackTrace();resultstr = "CATCH " + e.toString();
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
            protected String doInBackground(Void... voids) {
                // String responseString;
                try {
                    Log.d(TAG, "doInBackground started");
                    HttpClient client = new DefaultHttpClient();
                    HttpPost poster = new HttpPost(URLs.getBaseURL()+URLs.getReferralFolderAttachmentAddURL());
                    final File image = new File(ParamReferralFile);
                    Charset utf8 = Charset.forName("UTF-8");
                    //get the actual file from the device
                    //final MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE,null, Charset.forName("UTF-8"));
                    MultipartEntityBuilder entity = MultipartEntityBuilder.create()
                            .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                            .setCharset(utf8);

                    entity.addTextBody("RID", ParamRID);
                    entity.addTextBody("UID", ParamUID);
                    entity.addPart("ReferralFolderFileDescription",new StringBody(ParamFileDes,utf8));
                    entity.addPart("ReferralFolderFile", new FileBody(image));
                    HttpEntity multypartentity = entity.build();
                    poster.setEntity(multypartentity);
                    Log.d("hhh" ," upload image path"+ image.getAbsolutePath() );
                    //Important for android version 9 pie/
                    client.getConnectionManager().getSchemeRegistry().register(
                            new Scheme("https", SSLSocketFactory.getSocketFactory(), 443)
                    );
                    return client.execute(poster, new ResponseHandler<Object>() {
                        public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                            HttpEntity respEntity = response.getEntity();
                            String responseString = EntityUtils.toString(respEntity);
                            Log.d(TAG, "handleResponse response: "+responseString);
                            return responseString;
                        }
                    }).toString();
                } catch (Exception e){
                    Log.d("hhh", "doInBackground Exception e: "+e.toString());
                    return e.toString();
                }
            }
        }
        ReferralFolderAttachmentAdd lc = new ReferralFolderAttachmentAdd();
        lc.execute();

    }
}

