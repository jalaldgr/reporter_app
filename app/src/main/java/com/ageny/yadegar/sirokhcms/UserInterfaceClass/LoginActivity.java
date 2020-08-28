package com.ageny.yadegar.sirokhcms.UserInterfaceClass;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ageny.yadegar.sirokhcms.DataModelClass.UserDataModelClass;
import com.ageny.yadegar.sirokhcms.HTTPRequestHandlre;
import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.MYSQlDBHelper;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;

import org.json.JSONObject;

import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {
    EditText UsernameTxt,PasswordTxt;
   MYSQlDBHelper mysQlDBHelper;
    CheckBox chbx;
    UserDataModelClass userDataModelClass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);

        UsernameTxt = findViewById(R.id.UsernameTxt);
         PasswordTxt = findViewById(R.id.PasswordTxt);
         chbx = findViewById(R.id.saveLoginCheckBox);

         mysQlDBHelper = new MYSQlDBHelper(getApplicationContext());

        try {
            mysQlDBHelper.getWritableDatabase();
            userDataModelClass = mysQlDBHelper.GetCurrentUser();
            Log.i("hhh Saved user login:", "Loged IN "+ userDataModelClass.getIs_logedin());

            if( userDataModelClass != null){
                Log.i("hhh Saved user login:", "Loged name "+ userDataModelClass.getLast_name());

                if (userDataModelClass.getIs_logedin().equals("1")){
                    Log.i("hhh Saved user login:", "Loged Pass:"+ userDataModelClass.getIs_logedin());

                    finish();
                    Intent myIntent;
                    myIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(myIntent);
                }

            }

        }catch (Exception e){
            Log.i("hhh Saved user login:", "UserLogin():"+ e.toString());
        }


        findViewById(R.id.LoginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String statestr;
                String username = UsernameTxt.getText().toString();
                String password = PasswordTxt.getText().toString();
                userLogin();

            }
        });
    }

    ///////////////change fonts to yekan/////////////////////
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
        private void userLogin() {

            final String username = UsernameTxt.getText().toString();
            final String password = PasswordTxt.getText().toString();

            //validating inputs
            if (TextUtils.isEmpty(username)) {
                UsernameTxt.setError("لطفا نام کاربری را وارد نمایید");
                UsernameTxt.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(password)) {
                PasswordTxt.setError("لطفا گذرواژه خود را وارد نمایید");
                PasswordTxt.requestFocus();
                return;
            }

            if (!JSONHandlre.isConnectedtoInternet(getApplicationContext())) {
                return;
            }

            //if everything is fine

            class UserLogin extends AsyncTask<Void, Void, String> {

                private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    dialog.setMessage("دریافت اطلاعات...");
                    dialog.setIndeterminate(true);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    //progressBar.setVisibility(View.GONE);
                    if(dialog.isShowing())
                        this.dialog.dismiss();

                    mysQlDBHelper = new MYSQlDBHelper(getApplicationContext());
                    try {
                        mysQlDBHelper.getWritableDatabase();

                    }catch (Exception e){
                        Log.i("hhh Says:", "UserLogin():"+ e.toString());
                    }

                    try {
                        JSONObject obj = new JSONObject(s);
                        if (obj.getInt("State")>0) {
                            JSONObject JsonStr= obj.getJSONObject("Data");
                            ContentValues TmpCV = new ContentValues();
                            TmpCV.put("id", JsonStr.getString("id"));
                            TmpCV.put("email", JsonStr.getString("email"));
                            TmpCV.put("first_name", JsonStr.getString("first_name"));
                            TmpCV.put("last_name",JsonStr.getString("last_name"));
                            TmpCV.put("Image",JsonStr.getString("Image"));
                            TmpCV.put("is_logedin",chbx.isChecked());

                            mysQlDBHelper.InsertUser(TmpCV);
                            userDataModelClass = mysQlDBHelper.GetCurrentUser();
                            //Log.d("hhh login activity", "????????" + statestr);
                            if( userDataModelClass != null){
                                finish();
                                Intent myIntent;
                                myIntent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(myIntent);
                            }
                            else{
                                Toast toast= Toast.makeText(getApplicationContext(),
                                        "پایگاه اطلاعاتی برنامه یافت نشد.", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                                ViewGroup group = (ViewGroup) toast.getView();
                                TextView messageTextView = (TextView) group.getChildAt(0);
                                messageTextView.setTextSize(18);
                                toast.show();
                            }
                        }else {
                            Toast toast= Toast.makeText(getApplicationContext(),
                                    "نام کاربری یا رمز عبور اشتباه است", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
                            ViewGroup group = (ViewGroup) toast.getView();
                            TextView messageTextView = (TextView) group.getChildAt(0);
                            messageTextView.setTextSize(18);
                            toast.show();
                            PasswordTxt.setText("");
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();  Log.d("hhhK", "User Login: "+ex.toString());
                    }
                }

                @Override
                protected String doInBackground(Void... voids) {
                    HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("RequestFromApp", "1");
                    params.put("txtUserName", username);
                    params.put("txtPassword", password);
                    return requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getLoginURL(), params);
                }
            }
            UserLogin ul = new UserLogin();
            ul.execute();
        }
}
