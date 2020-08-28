package com.ageny.yadegar.sirokhcms;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ageny.yadegar.sirokhcms.DataModelClass.MarginDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.PreNewsUpdateDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.ReferralFolderAttachmentShowDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.ShowReferralFolderDataModel;
import com.ageny.yadegar.sirokhcms.DataModelClass.UserReferralDataModel;
import com.ageny.yadegar.sirokhcms.UserInterfaceClass.CartableDetailActivity;
import com.ageny.yadegar.sirokhcms.UserInterfaceClass.LoginActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class JSONHandlre {
    static MYSQlDBHelper myDBHelper;
    static Context myCONTEXT;
    ////////////////////////////////////////////////////////////////////////////////////////////
    public static ArrayList<UserReferralDataModel> LoadCartable(Context c, String UserID) {
        myCONTEXT = c;
        final String ParamUID = UserID;
        ArrayList<UserReferralDataModel> ReturnList = new ArrayList<UserReferralDataModel>();

        class LoadCartable extends AsyncTask<Void, Void, String> {
            private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();//Log.d("hhh: " , "onPreExecute");
                this.dialog.setMessage("دریافت اطلاعات...");
                this.dialog.setIndeterminate(true);
                this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(this.dialog.isShowing())
                    this.dialog.dismiss();
                //Log.d("hhh: " , "ss is the: "+ s.toString());

                myDBHelper = new MYSQlDBHelper(myCONTEXT);
                try {
                    myDBHelper.getWritableDatabase();
                } catch (Exception e) {
                    Log.i("MainActivity Says:", "create bank in load cartable" + e.toString());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("UID", ParamUID);

                //returing the response
                String st = requestHandler.sendPostRequest(URLs.getCarTableURL(), params);
                //Log.d("hhh: " , "do in bak ground and STRING is the: "+st);
                return st;
            }
        }

        LoadCartable lc = new LoadCartable();
        try {
            JSONObject obj = new JSONObject(lc.execute().get());
            if (obj.getInt("State") > 0) {
                //getting the user from the response
                JSONArray cartablearray = obj.getJSONArray("Data");
                //Log.d("hhh", "objct lengh iiiiiiiiiiiiiiis: " + cartablearray.get(0));
                JSONObject temobj;
                for (int i = 0; i < cartablearray.length(); i++) {

                    temobj = cartablearray.getJSONObject(i);
                    UserReferralDataModel ur = new UserReferralDataModel(
                            (temobj.getString("id")),
                            (temobj.getString("Subject")),
                            (temobj.getString("Description")),
                            (temobj.getString("from_user_id")),
                            (temobj.getString("to_user_id")),
                            (temobj.getString("referral_folder_state_id")),
                            (temobj.getString("urgency_id")),
                            (temobj.getString("Seen")),
                            (temobj.getString("news_type_id")),
                            (temobj.getString("ActionDate")),
                            (temobj.getString("created_at")),
                            (temobj.getString("updated_at")),
                            (temobj.getString("ReferralFolderStateTitle")),
                            (temobj.getString("UrgencyTitle")),
                            (temobj.getString("Color")));
                    ReturnList.add(ur);
//                            Log.d("hhh TTEEES: " , ReturnList.get(0).getSubject());
                }
                Log.d("hhh TTEEES: ", ReturnList.get(0).getSubject());
            } else { ReturnList = null;
            }
            return ReturnList;
        } catch (ExecutionException e) {
            e.printStackTrace();return ReturnList;
        } catch (Exception e) {
            e.printStackTrace();return  ReturnList;
        }
        //Log.d("hhh YYYYY: " , Integer.toString(ReturnList.size()));
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////////////
    public static ShowReferralFolderDataModel ShowReferralFolder(final Context c, String RefferalID, String UserID) {
        myCONTEXT = c;
        final String ParamUID = UserID;
        final String ParamRID = RefferalID;
        ShowReferralFolderDataModel Returnlist = null;

        class ShowReferralFolder extends AsyncTask<Void, Void, String> {
            private final ProgressDialog dialog = new ProgressDialog(c);


             @Override
            protected void onPreExecute() {
                 super.onPreExecute();

                 dialog.setMessage("دریافت اطلاعات...");
                 dialog.setIndeterminate(true);
                 dialog.show();
             }

            @Override
            protected void onPostExecute(String s) {
                 super.onPostExecute(s);
                if(dialog.isShowing()){
                    dialog.dismiss();

                }

            }
//            @Override
//            protected void setDialog(){
//
//            }

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
                String st =  requestHandler.sendPostRequest(URLs.getShowRefferalFolderURL(), params);
                //Log.d("hhh: " , "do in bak ground and STRING is the: "+st);

                return st;

            }
        }
    ShowReferralFolder lc = new ShowReferralFolder();
        try {
            //converting response to json object
            String sr = lc.execute().get();
            Log.d("hhh", " ShowReferralFolder: try "+ sr);
            JSONObject obj = new JSONObject(sr);
            //if no error in response
            if (obj.getInt("State")>0) {
                //getting the user from the response
                JSONObject obj1, obj2, obj3 =null;
                //JSONArray rflobj
                obj1  = obj.getJSONObject("ReferralFolder");

                JSONArray Plpobj = obj.getJSONArray("People");
                JSONArray orgobj = obj.getJSONArray("Organs");
                /*obj1 = rflobj.getJSONObject(0);*/
                obj2 = Plpobj.getJSONObject(0);
                obj3 = orgobj.getJSONObject(0);
                ShowReferralFolderDataModel tmp = new ShowReferralFolderDataModel(
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
                        Returnlist = tmp;
                }else {}
        } catch (Exception ex) {
            ex.printStackTrace();  Log.d("hhh", "ERROR ShowReferralFolder: "+ex.toString());
        }
        return Returnlist;

    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    public static ArrayList<MarginDataModelClass> GetReferralFolderMargins(Context c, String RefferalID, String UserID){
        myCONTEXT = c;
        ArrayList<MarginDataModelClass> RetirnList= new ArrayList<MarginDataModelClass>();
        final String ParamRID = RefferalID;
        final String ParamUID = UserID;
        class GetReferralFolderMargins extends AsyncTask<Void, Void, String> {
            private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);
           @Override
            protected void onPreExecute() {
                super.onPreExecute();
//
//               this.dialog.setMessage("دریافت اطلاعات...");
//               this.dialog.show();
           }

            @Override
            protected void onPostExecute(String s) {
                //this.dialog.dismiss();
               super.onPostExecute(s);

            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("RID", ParamRID);
                String st =  requestHandler.sendPostRequest(URLs.getReferralFolderMarginsURL(), params);
                return st;
            }
        }

        GetReferralFolderMargins TM = new GetReferralFolderMargins();
        //TM.execute();
        try {
            //converting response to json object
            JSONObject obj = new JSONObject(TM.execute().get());
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
            }
        } catch (Exception ex) {
            ex.printStackTrace();  Log.d("hhh", "ERROR in Load Margins: "+ex.toString());
        }
        return RetirnList;
    }
    public static String ReferralFolderReturnSubmit(Context c, String RefferalID, String UserID, String Description, String ReturnReasonID){
        myCONTEXT = c;
        final String ParamRID           = RefferalID;
        final String ParamUID           = UserID;
        final String ParamDescription   = Description;
        final String ParamRetRsnID      = ReturnReasonID;
        class ReferralFolderReturnSubmit extends AsyncTask<Void, Void, String> {
            // private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //his.dialog.setMessage("ارسال اطلاعات...");
                //this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                //this.dialog.dismiss();
                super.onPostExecute(s);
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
        String str;
        try {
            str = TM.execute().get();
            Log.d("hhh submit return", str +"||"+  ReturnReasonID +"///");
            JSONObject obj = new JSONObject(str);
            if (obj.getInt("State")>0)
                str = "برگشت مدرک با موفقیت ثبت شد.";
            else
                str = "خطایی رخ داده، برگشت مدرک ثبت نشد.";
        } catch (Exception e) {
            e.printStackTrace();
            str = "خطایی رخ داده، برگشت مدرک ثبت نشد.";
        }
        return str;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    //This Method Send a Refferal Return for server
    ////////////////////////////////////////////////////////////////////////////////////////////

    public static String ReferralFolderMarginAdd(Context c, String RefferalID, String UserID, String ReferralFolderMarginDescription ,
                                               String ReferralFolderMarginStateID, String RememberDate ){
        myCONTEXT = c;
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
            //private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this.dialog.setMessage("ارسال اطلاعات...");
                //this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
               // this.dialog.dismiss();
                super.onPostExecute(s);

                myDBHelper = new MYSQlDBHelper(myCONTEXT);
                try {
                    myDBHelper.getWritableDatabase();
                }catch (Exception e){
                    Log.i("hhh Says:", "ReferralFolderMarginAdd():"+ e.toString());
                }

                try {
                    //converting response to json object
                   ////JSONObject obj = new JSONObject(s);
                    //if no error in response
                   //// Toast.makeText(myCONTEXT, "وضعیت ثبت برگشت مدرک"+s, Toast.LENGTH_SHORT).show();

                  //  if (obj.getInt("State")>0) {
                        //getting the user from the response
                      //  JSONArray TmpJsonArr = obj.getJSONArray("Data");
                      //  JSONObject JsonStr=null;
                        ContentValues TmpCV = new ContentValues();
                     //   for (int i=0; i<TmpJsonArr.length(); i++){
                            //JsonStr = TmpJsonArr.getJSONObject(i);
                            TmpCV.put("created_at", Paramcreated_at);
                            TmpCV.put("RememberDate", ParamRememberDate);
                            TmpCV.put("Title", ParamTitle);
                            TmpCV.put("first_name", Paramfirst_name);
                            TmpCV.put("last_name", Paramlast_name);
                            TmpCV.put("RID", ParamRID);
                            TmpCV.put("UID", ParamUID);
                            TmpCV.put("ReferralFolderMarginDescription", ParamDescription);
                            TmpCV.put("ReferralFolderMarginStateID", ParamStateID);
                           // myDBHelper.InsertMargins(TmpCV);
                     //  // }

                } catch (Exception ex) {
                    ex.printStackTrace();  Log.d("hhh", "ReferralFolderMarginAdd: "+ex.toString());
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
                params.put("UID", ParamUID);
                params.put("ReferralFolderMarginDescription", ParamDescription);
                params.put("ReferralFolderMarginStateID", ParamStateID);
                params.put("RememberDate", ParamRememberDate);
                String st =  requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getReferralFolderMarginAddURL(), params);
                return st;
            }
        }
        ReferralFolderMarginAdd TM = new ReferralFolderMarginAdd();
        String str= null;
        try {
             str = TM.execute().get();
            JSONObject obj = new JSONObject(str);
            if (obj.getInt("State")>0)
                str = "هامش جدید با موفقیت ثبت شد.";
            else
                str = "خطایی رخ داده، هامش ثبت نشد.";
        } catch (Exception e) {
            str = "خطایی در ارتباط رخ داده، هامش ثبت نشد.";
            e.printStackTrace();
        }
        return str;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////


    public static ArrayList<ReferralFolderAttachmentShowDataModelClass> ReferralFolderAttachmentShow(Context c, String RefferalID ){
        myCONTEXT = c;
        ArrayList<ReferralFolderAttachmentShowDataModelClass> ReturnList= new ArrayList<ReferralFolderAttachmentShowDataModelClass>();
        final String ParamRID           = RefferalID;

        class ReferralFolderAttachmentShow extends AsyncTask<Void, Void, String> {
            //private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this.dialog.setMessage("دریافت اطلاعات...");
               // this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
               // this.dialog.dismiss();
                super.onPostExecute(s);
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
        try {
            String str = TM.execute().get();
            //converting response to json object
            JSONObject obj = new JSONObject(str);
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
        return ReturnList;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    public static String ReferralFolderAttachmentAdd(String ReferralFolderFile, String RID,
                                                     String UID,String ReferralFolderFileDescription){
        final String ParamReferralFile = ReferralFolderFile;
        final String ParamRID = RID;
        final String ParamUID = UID;
        final String ParamFileDes = ReferralFolderFileDescription;

        class ReferralFolderAttachmentAdd extends AsyncTask<Void, Void, String> {
            //private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this.dialog.setMessage("ارسال اطلاعات...");
               // this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
               // this.dialog.dismiss();
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                // String responseString;
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost poster = new HttpPost(URLs.getBaseURL()+URLs.getReferralFolderAttachmentAddURL());
                    final File image = new File(ParamReferralFile);  //get the actual file from the device
                    final MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                    entity.addPart("RID", new StringBody(ParamRID));
                    entity.addPart("UID", new StringBody(ParamUID));
                    entity.addPart("ReferralFolderFile", new FileBody(image));
                    entity.addPart("ReferralFolderFileDescription", new StringBody(ParamFileDes));
                    poster.setEntity(entity );
                    Log.d("hhh upload" , image.getAbsolutePath().toString() );
                    return client.execute(poster, new ResponseHandler<Object>() {
                        public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                            HttpEntity respEntity = response.getEntity();
                            String responseString = EntityUtils.toString(respEntity);
                            // do something with the response string

                            return responseString;

                        }
                    }).toString();
                } catch (Exception e){
                    return e.toString();
                }
            }
        }
        ReferralFolderAttachmentAdd lc = new ReferralFolderAttachmentAdd();
        String str = null;
        try {
            str = lc.execute().get();
            JSONObject obj = new JSONObject(str);
            if (obj.getInt("State")>0)
                str = "فایل با موفقیت ضمیمه شد.";
            else
                str = obj.toString(); /* "خطایی رخ داده، فایل ضمیمه نشد."*/
        } catch (Exception e) {
            e.printStackTrace();str = "CATCH " + e.toString();
        }
        return str;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    public static void ReferralFolderStartInterview(Context c, String RefferalID){
        myCONTEXT = c;
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
                    //if no error in response
                    Toast toast= Toast.makeText(myCONTEXT,
                            "وضعیت شروع مصاحبه"+s , Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    ViewGroup group = (ViewGroup) toast.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(18);
                    toast.show();

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

                String st =  requestHandler.sendPostRequest(URLs.getReferralFolderStartInterviewURL(), params);
                return st;
            }
        }
        ReferralFolderStartInterview TM = new ReferralFolderStartInterview();
        TM.execute();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    public static void ReferralFolderEndInterview(Context c, String RefferalID){
        myCONTEXT = c;
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
                    //if no error in response
                    Toast toast= Toast.makeText(myCONTEXT,
                            "وضعیت پایان مصاحبه"+s, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                    ViewGroup group = (ViewGroup) toast.getView();
                    TextView messageTextView = (TextView) group.getChildAt(0);
                    messageTextView.setTextSize(18);
                    toast.show();

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

                String st =  requestHandler.sendPostRequest(URLs.getReferralFolderEndInterviewURL(), params);
                return st;
            }
        }
        ReferralFolderEndInterview TM = new ReferralFolderEndInterview();
        TM.execute();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////

    public static String PreNewsUpdate(Context c, PreNewsUpdateDataModelClass PreNews){
        myCONTEXT = c;
        final PreNewsUpdateDataModelClass ParamPreNews = PreNews;

        class PreNewsUpdate extends AsyncTask<Void, Void, String> {
            //private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
             //   this.dialog.setMessage("ارسال اطلاعات...");
              //  this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
               // this.dialog.dismiss();
                super.onPostExecute(s);
            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost poster = new HttpPost(URLs.getBaseURL()+URLs.getPreNewsUpdateURL());
                    File image = new File(ParamPreNews.getNews_MainPic_File());  //get the actual file from the device
                    Charset utf8 = Charset.forName("UTF-8");
                    MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, utf8);
                    /*HttpEntity en = new HttpEntity() {
                    }*/
                    entity.addPart("UID", new StringBody(ParamPreNews.getUser_Id()));
                    entity.addPart("RID", new StringBody(ParamPreNews.getReferral_Id()));
                    entity.addPart("NewsTitle", new StringBody(ParamPreNews.getNews_Title(), Charset.forName("UTF-8")));
                    entity.addPart("fileupload", new FileBody(image));
                    entity.addPart("TopTitle", new StringBody(ParamPreNews.getTop_Title(), Charset.forName("UTF-8")));
                    entity.addPart("BottomTitle", new StringBody(ParamPreNews.getSub_Title(), Charset.forName("UTF-8")));
                    entity.addPart("ContentSummary", new StringBody(ParamPreNews.getNews_Summary(), Charset.forName("UTF-8")));
                    entity.addPart("news_category_id", new StringBody(ParamPreNews.getNews_Category_Id()));
                    entity.addPart("news_type_id", new StringBody(ParamPreNews.getNews_Type_Id()));
                    entity.addPart("province_id", new StringBody(ParamPreNews.getProvince_Id()));
                    entity.addPart("city_id", new StringBody(ParamPreNews.getCity_Id()));
                    entity.addPart("MainContent", new StringBody(ParamPreNews.getMainContent(), Charset.forName("UTF-8")));
                    poster.setEntity(entity);
                    //poster.setEntity()

                    Log.d("hhh upload preNews" , image.getAbsolutePath().toString() );
                    return client.execute(poster, new ResponseHandler<Object>() {
                        public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                            HttpEntity respEntity = response.getEntity();
                            String responseString = EntityUtils.toString(respEntity);
                            // do something with the response string

                            return responseString;
                        }
                    }).toString();
                } catch (Exception e){
                    Log.d("hhh", "PreNewsUpdate bakgrond: "+e.toString());
                    return e.toString();
                }
            }
        }
        PreNewsUpdate TM = new PreNewsUpdate();
        String str = null;

        try {
            str = TM.execute().get();
            JSONObject obj = new JSONObject(str);
            if (obj.getInt("State")>0)
                str = "خبر با موفقیت ارسال شد.";
            else
                str = obj.toString(); /* "خطایی رخ داده، فایل ضمیمه نشد."*/
        } catch (Exception e) {
            Log.d("hhh", "PreNewsUpdate EEEnd cacht: "+e.toString());
            e.printStackTrace();str = "CATCH " + e.toString();
        }
        Log.d("hhh", "PreNewsUpdate return str : "+ str);
        return str;
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
// متد این درخواست GET هست که باید تغییر کنه
    public static void GetNewsCategories(Context c){
        myCONTEXT = c;
        class GetNewsCategories extends AsyncTask<Void, Void, String> {
           // private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //this.dialog.setMessage("دریافت اطلاعات...");
                //this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                //this.dialog.dismiss();
                super.onPostExecute(s);

                myDBHelper = new MYSQlDBHelper(myCONTEXT);
                try {
                    myDBHelper.getWritableDatabase();
                }catch (Exception e){
                    Log.i("hhh Says:", "GetNewsCategories():"+ e.toString());
                }

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    myDBHelper.DeleteNewsCategories();
                    //if no error in response

                      if (obj.getInt("State")>0) {
                            JSONArray TmpJsonArr = obj.getJSONArray("Data");
                            JSONObject JsonStr=null;
                            ContentValues TmpCV = new ContentValues();
                               for (int i=0; i<TmpJsonArr.length(); i++) {
                                   JsonStr = TmpJsonArr.getJSONObject(i);
                                   TmpCV.put("Id", JsonStr.getString("id"));
                                   TmpCV.put("Title",JsonStr.getString("Title"));
                                   Log.d("hhh", "GetNewsCategories: "+JsonStr.getString("id"));
                                   myDBHelper.InsertNewsCategories(TmpCV);
                               }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();  Log.d("hhh", "GetNewsCategories: "+ex.toString());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();

                params.put("Content-Type", "application/json");
                //params.put("RID", ParamRID);

                String st =  requestHandler.sendGetRequest(URLs.getBaseURL()+URLs.getGetNewsCategoriesURL(), params);
                Log.d("hhh", "GetNewsCategorieslllll: "+st);
                return st;
            }
        }
        GetNewsCategories TM = new GetNewsCategories();
        TM.execute();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////


    public static void GetProvincesList(Context c){
        myCONTEXT = c;
        class GetProvincesList extends AsyncTask<Void, Void, String> {
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
                myDBHelper = new MYSQlDBHelper(myCONTEXT);
                try {
                    myDBHelper.getWritableDatabase();
                }catch (Exception e){
                    Log.i("GetProvincesList Says:", "create bank in get margin"+ e.toString());
                }
                try {
                    //converting response to json object
                    myDBHelper.DeleteProvinces();//clear all provinces list to write new list of province
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    if (obj.getInt("State")>0) {
                        //getting the user from the response
                        JSONArray TmpJsonArr = obj.getJSONArray("Data");
                        JSONObject JsonStr=null;
                        for (int i=0; i < TmpJsonArr.length(); i++){
                            ContentValues TmpCV = new ContentValues();
                            JsonStr = TmpJsonArr.getJSONObject(i);
                            TmpCV.put("Id", JsonStr.getString("id"));
                            TmpCV.put("Title", JsonStr.getString("Title"));
                            if(JsonStr.getString("TelegramChannelName") == null)
                                TmpCV.put("TelegramChannelName", "");
                            else
                                TmpCV.put("TelegramChannelName", JsonStr.getString("TelegramChannelName"));
                            TmpCV.put("Disabled", JsonStr.getString("Disabled"));
                            myDBHelper.InsertProvinces(TmpCV);
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();  Log.d("hhh", "GetProvincesList() Says: "+ex.toString());
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
                String st =  requestHandler.sendGetRequest(URLs.getBaseURL()+URLs.getProvincesURL(), params);
                return st;
            }
        }
        GetProvincesList lc = new GetProvincesList();
        lc.execute();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////////////////////////////////////////////////////////////////////
    public static void GetCitiesList(Context c){
        myCONTEXT = c;
        class GetCitiesList extends AsyncTask<Void, Void, String> {
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
                myDBHelper = new MYSQlDBHelper(myCONTEXT);
                try {
                    myDBHelper.getWritableDatabase();
                }catch (Exception e){
                    Log.i("GetCitiesList Says:", "create bank in city"+ e.toString());
                }
                try {
                    //converting response to json object
                    myDBHelper.DeleteCities();//clear all cities list to write new list of province
                    JSONObject obj = new JSONObject(s);
                    //if no error in response
                    if (obj.getInt("State")>0) {
                        //getting the user from the response
                        JSONArray TmpJsonArr = obj.getJSONArray("Data");
                        JSONObject JsonStr=null;
                        for (int i=0; i < TmpJsonArr.length(); i++){
                            ContentValues TmpCV = new ContentValues();
                            JsonStr = TmpJsonArr.getJSONObject(i);
                            TmpCV.put("ID", JsonStr.getString("id"));
                            TmpCV.put("Title", JsonStr.getString("Title"));
                            TmpCV.put("Province_ID", JsonStr.getString("province_id"));
                            //0TmpCV.put("Disabled", JsonStr.getString("Disabled"));
                            Log.d("hhh", "GetCities JSON: "+TmpCV.toString());
                            myDBHelper.InsertCities(TmpCV);
                        }
                    }
                } catch (JSONException ex) {
                    ex.printStackTrace();  Log.d("hhh", "GetCitiesList Says: "+ex.toString());
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
                String st =  requestHandler.sendGetRequest(URLs.getBaseURL()+URLs.getCitiesURL(), params);
                return st;
            }
        }
        GetCitiesList lc = new GetCitiesList();
        lc.execute();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////////////////////////////
    public static void GetNewsReporters(Context c){
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
                myDBHelper = new MYSQlDBHelper(myCONTEXT);
                try {
                    myDBHelper.getWritableDatabase();
                }catch (Exception e){
                    Log.i("GetNewsReporters Says:", "create bank in NewsReporters"+ e.toString());
                }
                try {
                    //converting response to json object
                    myDBHelper.DeleteNewsReporters();//clear all NewsReporters list to write new list of province
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
                            myDBHelper.InsertNewsReporters(TmpCV);
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
    ////////////////////////////////////////////////////////////////////////////////////////////








    public static void UserReferralFolderRememberMargin(Context c, String UserID){
        myCONTEXT = c;
        final String ParamUID           = UserID;

        class UserReferralFolderRememberMargin extends AsyncTask<Void, Void, String> {
          //  private final ProgressDialog dialog = new ProgressDialog(myCONTEXT);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
             //   this.dialog.setMessage("دریافت اطلاعات...");
            //   this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
               // this.dialog.dismiss();
                super.onPostExecute(s);

                myDBHelper = new MYSQlDBHelper(myCONTEXT);
                try {
                    myDBHelper.getWritableDatabase();
                }catch (Exception e){
                    Log.i("hhh Says:", "UserReferralFolderRememberMargin():"+ e.toString());
                }

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    if (obj.getInt("State")>0) {
                        JSONArray TmpJsonArr = obj.getJSONArray("Data");
                        JSONObject JsonStr=null;
                        ContentValues TmpCV = new ContentValues();
                        for (int i=0; i<TmpJsonArr.length(); i++) {
                            JsonStr = TmpJsonArr.getJSONObject(i);
                            TmpCV.put("ReferralFolderMarginDescription", JsonStr.getString("Description"));
                            TmpCV.put("RememberDate",JsonStr.getString("RememberDate"));
                           // TmpCV.put(,JsonStr.getString("Subject"));
                            TmpCV.put("News_Id",JsonStr.getString("news_id"));

                            myDBHelper.InsertMargins(TmpCV);
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();  Log.d("hhh", "UserReferralFolderRememberMargin: "+ex.toString());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();

                params.put("Content-Type", "application/json");
                params.put("UID", ParamUID);

                String st =  requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getUserReferralFolderRememberMarginURL(), params);
                return st;
            }
        }
        UserReferralFolderRememberMargin TM = new UserReferralFolderRememberMargin();
        TM.execute();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////


     public static  String UserLogin(Context c, String email , String password ,String is_logedin, AppCompatActivity act ){
        myCONTEXT = c;
        final AppCompatActivity activity = act;
        final String ParamEmail           = email;
        final String ParamPassword        =password;
        final String ParamIs_logeid=        is_logedin;
        class UserLogin extends AsyncTask<Void, Void, String> {
            private final ProgressDialog dialog = new ProgressDialog(activity);
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

                if(dialog.isShowing())
                    this.dialog.dismiss();

                myDBHelper = new MYSQlDBHelper(myCONTEXT);
                try {
                    myDBHelper.getWritableDatabase();

                }catch (Exception e){
                    Log.i("hhh Says:", "UserLogin():"+ e.toString());
                }

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);
                    Log.d("hhhK", "User Login: "+s.toString());
                    if (obj.getInt("State")>0) {
                        //JSONArray TmpJsonArr = obj.getJSONArray("Data");
                        JSONObject JsonStr= obj.getJSONObject("Data");
                        ContentValues TmpCV = new ContentValues();

                            //JsonStr = TmpJsonArr.getJSONObject(0);
                            TmpCV.put("id", JsonStr.getString("id"));
                        Log.d("hhhK", "User Login: "+TmpCV.get("id"));
                            TmpCV.put("email", JsonStr.getString("email"));
                        Log.d("hhhK", "User Login: "+TmpCV.get("email"));
                            TmpCV.put("first_name", JsonStr.getString("first_name"));
                        Log.d("hhhK", "User Login: "+TmpCV.get("first_name"));
                            TmpCV.put("last_name",JsonStr.getString("last_name"));
                        Log.d("hhhK", "User Login: "+TmpCV.get("last_name"));
                             TmpCV.put("Image",JsonStr.getString("Image"));
                        Log.d("hhhK", "User Login: "+TmpCV.get("Image"));
                             TmpCV.put("is_logedin",ParamIs_logeid);
                        Log.d("hhhK", "User Login: "+ParamIs_logeid);
                            myDBHelper.InsertUser(TmpCV);


                    }
                } catch (Exception ex) {
                    ex.printStackTrace();  Log.d("hhhK", "User Login: "+ex.toString());
                }
            }

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                //creating request parameters
                HashMap<String, String> params = new HashMap<>();

                params.put("Content-Type", "application/json");
                params.put("RequestFromApp", "1");
                params.put("txtUserName", ParamEmail);
                params.put("txtPassword", ParamPassword);


                String st =  requestHandler.sendPostRequest(URLs.getLoginURL(), params);
                return st;
            }
        }
        UserLogin TM= new UserLogin();
        String str = null;
        String returnstr="not run";
        try {
            str = TM.execute().get();
            JSONObject obj = new JSONObject(str);
            if (obj.getInt("State")>0) {
                returnstr = "1";
            }else {
                returnstr = "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnstr;

    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    public static   boolean isConnectedtoInternet(Context cntx){
        final AlertDialog.Builder MainTitrAlertDialog =new AlertDialog.Builder(new ContextThemeWrapper(cntx, R.style.myDialog));

        class ConnectedtoInternet extends AsyncTask<Void, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
            @Override
            protected String doInBackground(Void... voids) {
                InetAddress ipAddr=null;
                try {
                    ipAddr = InetAddress.getByName("sepehrnewspaper.com");
                    return ipAddr.toString();
                } catch (UnknownHostException e) {
                    return "false";
                }
            }
        }
        boolean flag = false;
        String str= null;
        ConnectedtoInternet TM = new ConnectedtoInternet();
        try {
            str =TM.execute().get();
            Log.d("hhh JSON has error", str);
            if (str.equals("false"))
                flag = false;
            else
                flag = true;
        } catch (Exception e) {
            flag = false;
          }
        if (!flag){
            Toast toast= Toast.makeText(cntx,
                    "لطفا اتصال اینترنت را بررسی نمایید." , Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();
        }
        return flag;
    }
}
