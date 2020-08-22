package com.ageny.yadegar.sirokhcms.UserInterfaceClass;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.ageny.yadegar.sirokhcms.DataModelClass.PreNewsUpdateDataModelClass;
import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.MYSQlDBHelper;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;
import com.ageny.yadegar.sirokhcms.UserInterfaceClass.Cartable.CartableFragment;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class SubmitInformationActivity extends AppCompatActivity {

    String JsonResult,UID,RID,RSID=null;
    public Context Cntx;
    MYSQlDBHelper mysQlDBHelper;

    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE =1 ;
    static Uri selectedimage=null;
    String selectedFilePath=null;
    PreNewsUpdateDataModelClass preNewsUpdateDataModelClass= new PreNewsUpdateDataModelClass();
    Button news_submit_button_in_code;
    TextView MainTitrTxt;
    TextView TitrTxt;
    TextView SubTitrTxt;
    TextView CategoriTxt;
    TextView news_content_txt_in_code;
    TextView news_summary_Txt;
    TextView citytxt;
    String SelectedResult;
    private int RESULT_LOAD_IMAGE=1;
     int checkedcategory=0;
     int checkedprovince=0;
     int checkednewstyp=0;
     int checkedcity=0;
     int checkedreporter=0;



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_information);
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("ورود اطلاعات");
        Cntx=this.getApplicationContext();
        Intent intent=getIntent();
        mysQlDBHelper = new MYSQlDBHelper(Cntx);
        RID=intent.getStringExtra("cartableid");
        UID=intent.getStringExtra("userid");
        RSID=intent.getStringExtra("referralstateid");

        LinearLayout news_main_titr_layout_in_code = (LinearLayout)findViewById(R.id.news_main_titr_layout);
        LinearLayout news_titr_layout_in_code = (LinearLayout)findViewById(R.id.news_titr_layout);
        LinearLayout news_sub_titr_layout_in_code = (LinearLayout)findViewById(R.id.news_sub_titr_layout);
        LinearLayout news_categori_layout_in_code = (LinearLayout)findViewById(R.id.news_categori_layout);
        LinearLayout news_province_layout_in_code = (LinearLayout)findViewById(R.id.news_province_layout);
        LinearLayout news_type_layout_in_code = (LinearLayout)findViewById(R.id.news_type_layout);
        LinearLayout news_summary_layout_in_code = (LinearLayout)findViewById(R.id.news_summary_layout);
        final LinearLayout news_city_layout_in_code = (LinearLayout)findViewById(R.id.news_city_layout);
        LinearLayout news_reporters_layout_in_code = (LinearLayout)findViewById(R.id.news_person_layout);

        final Button news_button_in_code =  (Button)findViewById(R.id.news_image_button);
        news_submit_button_in_code =  (Button)findViewById(R.id.news_submit_bttn);

        final AlertDialog.Builder MainTitrAlertDialog =new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        final AlertDialog.Builder MainDialogAlertDialog =new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));
        final AlertDialog.Builder ReportersDialogAlertDialog =new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.myDialog));

        preNewsUpdateDataModelClass.setSub_Title(null);
        preNewsUpdateDataModelClass.setTop_Title(null);
        preNewsUpdateDataModelClass.setNews_MainPic_File(null);

        MainTitrTxt = (TextView) findViewById(R.id.news_main_titr_textview);
        TitrTxt = (TextView) findViewById(R.id.news_titr_textView);
        SubTitrTxt = (TextView) findViewById(R.id.news_sub_titr_textView);
        news_content_txt_in_code = (TextView)findViewById(R.id.news_content_textView);
        news_summary_Txt = (TextView)findViewById(R.id.news_summary_textView);
        citytxt = (TextView)findViewById(R.id.news_city_textView);
        citytxt.setEnabled(false);
        news_city_layout_in_code.setEnabled(false);

        /////////////////////// Layout Click ////////////////////////
        /////////////////////////////////////////////////////////////

        news_main_titr_layout_in_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View customLayout = getLayoutInflater().inflate(R.layout.main_titr_popup_layout ,null);
                final TextView editText = customLayout.findViewById(R.id.popupztxt2);
                MainTitrAlertDialog.setTitle("رو تیتر خبر");
                //MainTitrAlertDialog.setMessage("رو تیتر خبر را وارد کنید.");
                editText.setText(MainTitrTxt.getText());
                MainTitrAlertDialog.setView(customLayout);

                MainTitrAlertDialog.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View customLayout = getLayoutInflater().inflate(R.layout.main_titr_popup_layout ,null);

                        MainTitrTxt.setText(editText.getText());
                        preNewsUpdateDataModelClass.setTop_Title(editText.getText().toString());
                    }
                });
                MainTitrAlertDialog.setNegativeButton("انصراف" , null);
                final AlertDialog al = MainTitrAlertDialog.create();

                al.show();

            }
            });



        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        news_titr_layout_in_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------------
                View customLayout = getLayoutInflater().inflate(R.layout.main_titr_popup_layout ,null);
                final TextView PopUpTxt = customLayout.findViewById(R.id.popupztxt2);
                MainTitrAlertDialog.setTitle("تیتر خبر");
                //MainTitrAlertDialog.setMessage("تیتر خبر را وارد کنید.");
                PopUpTxt.setText(TitrTxt.getText());
                MainTitrAlertDialog.setView(customLayout);
                MainTitrAlertDialog.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View customLayout = getLayoutInflater().inflate(R.layout.main_titr_popup_layout ,null);

                        TitrTxt.setText(PopUpTxt.getText());
                        preNewsUpdateDataModelClass.setNews_Title(TitrTxt.getText().toString());
                    }
                });
                MainTitrAlertDialog.setNegativeButton("انصراف" , null);
                final AlertDialog al = MainTitrAlertDialog.create();

                al.show();
                //----------------------------------------------------------------
            }
        });

        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        news_sub_titr_layout_in_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------------
                View customLayout = getLayoutInflater().inflate(R.layout.main_titr_popup_layout ,null);
                final TextView PopUpTxt = customLayout.findViewById(R.id.popupztxt2);
                MainTitrAlertDialog.setTitle("زیر تیتر خبر");
                //MainTitrAlertDialog.setMessage("زیر تیتر خبر را وارد کنید.");
                PopUpTxt.setText(SubTitrTxt.getText());
                MainTitrAlertDialog.setView(customLayout);
                MainTitrAlertDialog.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View customLayout = getLayoutInflater().inflate(R.layout.main_titr_popup_layout ,null);

                        SubTitrTxt.setText(PopUpTxt.getText());
                        preNewsUpdateDataModelClass.setSub_Title(SubTitrTxt.getText().toString());
                    }
                });
                MainTitrAlertDialog.setNegativeButton("انصراف" , null);

                final AlertDialog al = MainTitrAlertDialog.create();

                al.show();
                //----------------------------------------------------------------
            }
        });

        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        news_categori_layout_in_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------------
                List<String> stringList = mysQlDBHelper.GetALLNewsCategoriesList();

                MainDialogAlertDialog.setTitle("سرویس خبری");
                CharSequence[] ListArray = new CharSequence[stringList.size()];
                for(int i = 0; i < stringList.size(); i++){
                    ListArray[i] = stringList.get(i).toString();
                }

                MainDialogAlertDialog.setSingleChoiceItems(ListArray, checkedcategory, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        CategoriTxt = (TextView) findViewById(R.id.news_categori_textView);
                        CategoriTxt.setText(SelectedResult);
                        ListView lw = ((AlertDialog)dialog).getListView();
                        Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        SelectedResult=checkedItem.toString();
                        CategoriTxt.setText(SelectedResult);
                        checkedcategory = lw.getCheckedItemPosition();
                        preNewsUpdateDataModelClass.setNews_Category_Id(Integer.toString(
                                lw.getCheckedItemPosition()+1
                        ));
                        dialog.dismiss();
                    }
                });
                final AlertDialog al = MainDialogAlertDialog.create();
                al.show();
                //----------------------------------------------------------------
            }
        });

        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////

        /////////////////////////////////////////////////////////////
        /////////////////////////////////////////////////////////////
        news_province_layout_in_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------------
                List<String> stringList = mysQlDBHelper.GetProvincesList();

                MainDialogAlertDialog.setTitle("استان");
                CharSequence[] ListArray = new CharSequence[stringList.size()];
                for(int i = 0; i < stringList.size(); i++){
                    ListArray[i] = stringList.get(i).toString();
                }

                MainDialogAlertDialog.setSingleChoiceItems(ListArray,checkedprovince, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CategoriTxt = (TextView) findViewById(R.id.news_province_textView);
                        CategoriTxt.setText(SelectedResult);
                        ListView lw = ((AlertDialog)dialog).getListView();
                        Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        SelectedResult=checkedItem.toString();
                        CategoriTxt.setText(SelectedResult);
                        checkedprovince = lw.getCheckedItemPosition();
                        preNewsUpdateDataModelClass.setProvince_Id(Integer.toString(
                                lw.getCheckedItemPosition()+1
                        ));
                        List<String> citystringList = mysQlDBHelper.GetCitiesList(preNewsUpdateDataModelClass.getProvince_Id());
                        if (citystringList.size()>0){
                            news_city_layout_in_code.setEnabled(true);
                            citytxt.setEnabled(true);
                            citytxt.setText(citystringList.get(0));
                            preNewsUpdateDataModelClass.setCity_Id("1");
                        }else{
                            news_city_layout_in_code.setEnabled(false);
                            citytxt.setEnabled(false);
                            citytxt.setText(" ");
                            preNewsUpdateDataModelClass.setCity_Id(" ");

                        }
                        dialog.dismiss();

                    }
                });
                final AlertDialog al = MainDialogAlertDialog.create();
                al.show();
                //----------------------------------------------------------------
            }
        });

        /////////////////////////////////////////////////////////////
        news_reporters_layout_in_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------------
                String [][]StrArray = null;
                StrArray = mysQlDBHelper.GetNewsReportersList();
                Log.d("hhh reporters", StrArray[1][0]);

                ReportersDialogAlertDialog.setTitle("خبرنگار");
                CharSequence[] ListArray = new CharSequence[StrArray.length];
                for(int i = 0; i < StrArray.length; i++){

                    ListArray[i] = StrArray[i][0];
                    Log.d("hhh reporters", StrArray[i][0]+StrArray[i][1]);

                }
                ReportersDialogAlertDialog.setSingleChoiceItems(ListArray,checkedreporter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CategoriTxt = (TextView) findViewById(R.id.news_person_textView);
                        CategoriTxt.setText(SelectedResult);
                        ListView lw = ((AlertDialog) dialog).getListView();
                        Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        SelectedResult = checkedItem.toString();
                        CategoriTxt.setText(SelectedResult);
                        checkedprovince = lw.getCheckedItemPosition();

                        checkedreporter = lw.getCheckedItemPosition();
                        dialog.dismiss();

                    }
                });
                 preNewsUpdateDataModelClass.setUser_Id(StrArray [checkedreporter][1] );
                final AlertDialog al = ReportersDialogAlertDialog.create();
                al.show();
            }
        });

        /////////////////////////////////////////////////////////////

        news_city_layout_in_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------------
                List<String> stringList = mysQlDBHelper.GetCitiesList(preNewsUpdateDataModelClass.getProvince_Id());

                MainDialogAlertDialog.setTitle("شهر");
                CharSequence[] ListArray = new CharSequence[stringList.size()];

                for(int i = 0; i < stringList.size(); i++){

                    ListArray[i] = stringList.get(i).toString();
                }
                Log.d("ttt",Integer.toString(stringList.size()));

                MainDialogAlertDialog.setSingleChoiceItems(ListArray,checkedcity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        citytxt = (TextView) findViewById(R.id.news_city_textView);
                        citytxt.setText(SelectedResult);
                        ListView lw = ((AlertDialog)dialog).getListView();
                        Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        SelectedResult=checkedItem.toString();
                        citytxt.setText(SelectedResult);
                        checkedcity = lw.getCheckedItemPosition();
                        preNewsUpdateDataModelClass.setCity_Id(Integer.toString(
                                lw.getCheckedItemPosition()+1
                        ));
                        dialog.dismiss();

                    }
                });
                final AlertDialog al = MainDialogAlertDialog.create();
                al.show();
            }
        });



        news_type_layout_in_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----------------------------------------------------------------

                MainDialogAlertDialog.setSingleChoiceItems(R.array.SubmitInformationNewsType,checkednewstyp, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CategoriTxt = (TextView) findViewById(R.id.news_type_textView_);
                        CategoriTxt.setText(SelectedResult);
                        ListView lw = ((AlertDialog)dialog).getListView();
                        Object checkedItem = lw.getAdapter().getItem(lw.getCheckedItemPosition());
                        SelectedResult=checkedItem.toString();
                        CategoriTxt.setText(SelectedResult);
                        checkednewstyp = lw.getCheckedItemPosition();
                        preNewsUpdateDataModelClass.setNews_Type_Id(Integer.toString(
                                lw.getCheckedItemPosition()+1
                        ));
                        dialog.dismiss();

                    }
                });
                final AlertDialog al = MainDialogAlertDialog.create();
                al.show();
                //----------------------------------------------------------------

            }
        });

        //////////////summary text view////////////////////////////
        news_summary_layout_in_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customLayout = getLayoutInflater().inflate(R.layout.main_titr_popup_layout ,null);
                final TextView PopUpTxt = customLayout.findViewById(R.id.popupztxt2);
                MainTitrAlertDialog.setTitle("خلاصه خبر");
                PopUpTxt.setText(news_summary_Txt.getText());
                MainTitrAlertDialog.setView(customLayout);
                MainTitrAlertDialog.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        news_summary_Txt.setText(PopUpTxt.getText());
                        preNewsUpdateDataModelClass.setNews_Summary(news_summary_Txt.getText().toString());
                    }
                });
                MainTitrAlertDialog.setNegativeButton("انصراف" , null);
                final AlertDialog al = MainTitrAlertDialog.create();

                al.show();
                //----------------------------------------------------------------
            }
        });


        //////////// Refresh Image View When Rotate////////////////////
        if(selectedFilePath!=null){
            ImageView news_imageview = (ImageView)findViewById(R.id.news_image_view);
            news_imageview.setImageURI(selectedimage);
            news_button_in_code.setVisibility(View.INVISIBLE);
            registerForContextMenu(news_imageview);
            preNewsUpdateDataModelClass.setNews_MainPic_File(selectedFilePath);
        }
        ///////////////////////// Load Image////////////////////////////
        ////////////////////////////////////////////////////////////////
        news_button_in_code.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (shouldShowRequestPermissionRationale(
                            Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    }
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    return;
                }

                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
        });

        final ImageView imageView = (ImageView)findViewById(R.id.news_image_view);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContextMenu(imageView);
            }
        });

        //////////////////Submit Pre News////////////////////////
        /////////////////////////////////////////////////////////
        news_submit_button_in_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preNewsUpdateDataModelClass.setUser_Id(UID);
                preNewsUpdateDataModelClass.setReferral_Id(RID);
                preNewsUpdateDataModelClass.setMainContent(news_content_txt_in_code.getText().toString());

                if (!preNewsUpdateDataModelClass.isanyUnset()) {

                    PreNewsUpdate( preNewsUpdateDataModelClass);

                }else{
                    Toast.makeText(Cntx, "لطفا فیلدهای ورودی را پر کنید", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    ///////////////change fonts to yekan/////////////////////
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            selectedimage = data.getData(); //The uri with the location of the file
            selectedFilePath = FilePath.getPath(Cntx, selectedimage);
            ImageView news_imageview = (ImageView)findViewById(R.id.news_image_view);
            news_imageview.setImageURI(selectedimage);
            Button news_button_in_code = (Button)findViewById(R.id.news_image_button);
            news_button_in_code.setVisibility(View.INVISIBLE);
            registerForContextMenu(news_imageview);
            preNewsUpdateDataModelClass.setNews_MainPic_File(selectedFilePath);

        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.press_image_menu, menu);
    }

    @Override
    public boolean onContextItemSelected (MenuItem item) {
        switch (item.getItemId()) {
            case R.id.select_image: {
                Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,RESULT_LOAD_IMAGE);
            }
            break;
            case R.id.remove_image: {
            ImageView imageView =  (ImageView)findViewById(R.id.news_image_view);
            imageView.setImageResource(0);
            Button button = (Button)findViewById(R.id.news_image_button);
            button.setVisibility(View.VISIBLE);
            selectedFilePath=null;
            selectedimage=null;
            preNewsUpdateDataModelClass.setNews_MainPic_File(null);
            }
            default:
                return false;
        }
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                selectedFilePath=null;
                selectedimage=null;
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




    public void PreNewsUpdate( PreNewsUpdateDataModelClass PreNews){
        final PreNewsUpdateDataModelClass ParamPreNews = PreNews;

        class PreNewsUpdate extends AsyncTask<Void, Void, String> {
            private final ProgressDialog dialog = new ProgressDialog(SubmitInformationActivity.this);
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                  news_submit_button_in_code.setEnabled(false);
                  this.dialog.setMessage("ارسال اطلاعات...");
                  this.dialog.show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject obj = new JSONObject(s);
                    if (obj.getInt("State")>0) {
                        JsonResult = "خبر با موفقیت ارسال شد.";
                        Log.d("hhh", "onPostExecute: " + s.toString());

                        Intent intent= new Intent(Cntx, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Cntx.startActivity(intent);
                    }
                    else {
                        JsonResult = "خطا در ارسال اطلاعات";
                        Log.d("hhh", "onPostExecute: " + s.toString());
                        news_submit_button_in_code.setEnabled(true);
                    }
                } catch (Exception e) {
                    Log.d("hhh", "PreNewsUpdate EEEnd cacht: "+e.toString());
                    e.printStackTrace();JsonResult = "CATCH " + e.toString();
                    news_submit_button_in_code.setEnabled(true);

                }
                if(dialog.isShowing())this.dialog.dismiss();
                Toast.makeText(Cntx,JsonResult,Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpPost poster = new HttpPost(URLs.getBaseURL()+URLs.getPreNewsUpdateURL());
                    Charset utf8 = Charset.forName("UTF-8");
                    //MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE, null, utf8);
                    MultipartEntityBuilder entity = MultipartEntityBuilder.create()
                            .setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
                            .setCharset(utf8);
                    entity.addTextBody("UID",ParamPreNews.getUser_Id());
                    entity.addTextBody("RID",ParamPreNews.getReferral_Id());
                    entity.addTextBody("news_category_id", ParamPreNews.getNews_Category_Id());
                    entity.addTextBody("news_type_id", ParamPreNews.getNews_Type_Id());
                    entity.addTextBody("province_id", ParamPreNews.getProvince_Id());
                    entity.addTextBody("city_id",ParamPreNews.getCity_Id());
                    if(preNewsUpdateDataModelClass.getNews_MainPic_File()!=null){
                        final File image = new File(ParamPreNews.getNews_MainPic_File());  //get the actual file from the device
                        entity.addPart("fileupload", new FileBody(image));}
                    entity.addPart("MainContent",new StringBody( ParamPreNews.getMainContent(),utf8));
                    entity.addPart("NewsTitle",new StringBody(ParamPreNews.getNews_Title(),utf8));
                    if(preNewsUpdateDataModelClass.getTop_Title()!=null){
                    entity.addPart("TopTitle",new StringBody(ParamPreNews.getTop_Title(),utf8));}
                    if (preNewsUpdateDataModelClass.getSub_Title()!=null){
                    entity.addPart("BottomTitle",new StringBody(ParamPreNews.getSub_Title(),utf8));}
                    entity.addPart("ContentSummary",new StringBody(ParamPreNews.getNews_Summary(),utf8));

                    HttpEntity httpmultypartEntity = entity.build();
                    poster.setEntity(httpmultypartEntity);
                   //                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             Log.d("hhh refferal image path", image.getAbsolutePath());

                    return client.execute(poster, new ResponseHandler<Object>() {
                        public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                            HttpEntity respEntity = response.getEntity();
                            String responseString = EntityUtils.toString(respEntity);

                            return responseString;
                        }
                    }).toString();
                } catch (Exception e){
                    return e.toString();
                }
            }
        }
        PreNewsUpdate TM = new PreNewsUpdate();
        TM.execute();
    }

}