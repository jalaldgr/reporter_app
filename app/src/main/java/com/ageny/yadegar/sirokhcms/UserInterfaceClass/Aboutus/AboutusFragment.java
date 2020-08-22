package com.ageny.yadegar.sirokhcms.UserInterfaceClass.Aboutus;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ageny.yadegar.sirokhcms.HTTPRequestHandlre;
import com.ageny.yadegar.sirokhcms.UserInterfaceClass.MarginsActivity;
import com.ageny.yadegar.sirokhcms.update.AppUtils;
import com.ageny.yadegar.sirokhcms.update.UpdateDialog;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AboutusFragment extends Fragment {

    private AboutusViewModel shareViewModel;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        shareViewModel =
                ViewModelProviders.of(this).get(AboutusViewModel.class);
        View root = inflater.inflate(R.layout.fragment_aboutus, container, false);

        shareViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
             //   textView.setText(s);
            }
        });

        TextView VersionNametxt = (TextView)root.findViewById(R.id.Aboutus_Version_textView);
        VersionNametxt.setText(getResources().getString(R.string.aboutus_version_name)+AppUtils.getVersionName(getActivity()));
        Button updatebtn = (Button)root.findViewById(R.id.updatebtn);
        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckUpdateUrl(URLs.getBaseURL()+URLs.getAppUpdate(), getActivity());
//                Toast.makeText(getActivity(), "404فایل مورد نظر یافت نشد." +
//                        "\nhttps:/anynews.com/SirokhApp/AppUpdater/json/update-changelog.json", Toast.LENGTH_LONG).show();
            }
        });
        return root;


    }
    ///////////////change fonts to yekan/////////////////////

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
               getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void CheckUpdateUrl (String url , final Context cntx){
        String Urlstr = url;

        class  CheckUpdateUrl extends AsyncTask<Void ,Void ,String> {
            private final ProgressDialog dialog = new ProgressDialog(cntx);
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
                if (s == "404") {
                    Toast.makeText(cntx, "404: فایل بروزرسانی یافت نشد." +
                            "\nhttps:/anynews.com/SirokhApp/AppUpdater/update-changelog.json", Toast.LENGTH_LONG).show();
                    return;
                }
                parseJson(s);

            }
            private void parseJson(String result) {
                try {

                    JSONObject obj = new JSONObject(result);
                    String latestVersion = obj.getString("latestVersion");
                    String apkUrl = obj.getString("url");
                    String releasenotesstr="نسخه جدید "+latestVersion+" موجود است"+"\n\n";
                    int latestVersionCode = obj.getInt("latestVersionCode");
                    int versionCode = AppUtils.getVersionCode(cntx);
                    JSONArray releasenotes = obj.getJSONArray("releaseNotes");
                    for (int i=0 ;i<releasenotes.length();i++){
                        releasenotesstr += releasenotes.get(i)+"\n";
                    }
                    Log.d("jjj", "parseJson: ");
                    if (latestVersionCode > versionCode) {
                        if (dialog.isShowing())dialog.dismiss();
                        UpdateDialog.show(cntx, releasenotesstr, apkUrl);

                    } else {
                        if (dialog.isShowing())dialog.dismiss();
                        Toast.makeText(cntx, "نرم افزار بروز است", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    Log.e("jjjj", "parse json error :"+e.toString());
                }
            }
            @Override
            protected String doInBackground(Void... voids) {
                HTTPRequestHandlre requestHandler = new HTTPRequestHandlre();
                String resd = requestHandler.sendGetRequestUpdate(URLs.getBaseURL()+URLs.getAppUpdate(),cntx);
                Log.d("jjj", "onClick: "+resd);
                return resd;
            }
        }
        CheckUpdateUrl checkUpdateUrl=new CheckUpdateUrl();
        checkUpdateUrl.execute();
    }


}




