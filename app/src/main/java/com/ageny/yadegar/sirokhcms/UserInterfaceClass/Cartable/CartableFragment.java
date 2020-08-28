package com.ageny.yadegar.sirokhcms.UserInterfaceClass.Cartable;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ageny.yadegar.sirokhcms.DataModelClass.UserDataModelClass;
import com.ageny.yadegar.sirokhcms.HTTPRequestHandlre;
import com.ageny.yadegar.sirokhcms.ItemAdapter;
import com.ageny.yadegar.sirokhcms.MYSQlDBHelper;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.DataModelClass.UserReferralDataModel;
import com.ageny.yadegar.sirokhcms.URLs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import saman.zamani.persiandate.PersianDate;
import saman.zamani.persiandate.PersianDateFormat;

public class CartableFragment extends Fragment {
    static MYSQlDBHelper myDBHelper;
    static Context myCONTEXT ;
    LinearLayout MainLay;
    public ArrayList<UserReferralDataModel> URList;
    RecyclerView myRecycler;
    ArrayList<UserReferralDataModel> mItem= new ArrayList<UserReferralDataModel>();
    ItemAdapter mAdapter;
    UserDataModelClass crrntUser;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myCONTEXT = getContext();
        myDBHelper = new MYSQlDBHelper(myCONTEXT);

        try {
            myDBHelper.getWritableDatabase();
            crrntUser = myDBHelper.GetCurrentUser();
            LoadCartable lc = new LoadCartable();
            lc.execute();

        }catch (Exception e){
            Log.i("hhh load cartable Says:", e.toString());
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root1 = inflater.inflate(R.layout.fragment_cartable, null);
        myRecycler = (RecyclerView) root1.findViewById(R.id.myrecycleview);
        PersianDate  persianDate;
        PersianDateFormat pdformater = new PersianDateFormat();
        //final AlertDialog.Builder MainTitrAlertDialog =new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.myDialog));
        MainLay = root1.findViewById(R.id.fragment_cartable_layout);
        MainLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean f = JSONHandlre.isConnectedtoInternet(getActivity());

            }
        });

        MainLay.callOnClick();
        return root1;
    }


///////////////////////////JSON Method/////////////////////////////////////////////////////////////////////

    class LoadCartable extends AsyncTask<Void, Void, String> {
        final String ParamUID = crrntUser.getId();

        private final ProgressDialog dialog = new ProgressDialog(CartableFragment.this.getContext());

        @Override
        protected void onPreExecute() {
            super.onPreExecute();//Log.d("hhh: " , "onPreExecute");
            this.dialog.setMessage("دریافت اطلاعات...");
            this.dialog.setIndeterminate(true);
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(this.dialog.isShowing()) this.dialog.dismiss();

            //Log.d("hhh: " , "ss is the: "+ s.toString());
            ArrayList<UserReferralDataModel> ReturnList = new ArrayList<UserReferralDataModel>();

            try {
                JSONObject obj = new JSONObject(s);

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
                URList= ReturnList;

                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(CartableFragment.this.getContext());
                myRecycler.setLayoutManager(mLayoutManager);
                myRecycler.setItemAnimator(new DefaultItemAnimator());
                mAdapter = new ItemAdapter(URList, getContext());
                myRecycler.setItemAnimator(new DefaultItemAnimator());//add and delete records animation
                myRecycler.setHasFixedSize(true);//fix control size's
                myRecycler.setAdapter(mAdapter);

            } catch (Exception e) {
                e.printStackTrace();URList=  ReturnList;
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
            String st = requestHandler.sendPostRequest(URLs.getBaseURL()+URLs.getCarTableURL(), params);
            //Log.d("hhh: " , "do in bak ground and STRING is the: "+st);
            return st;
        }
    }


    //Log.d("hhh YYYYY: " , Integer.toString(ReturnList.size()));

///////////////////////////////////////////////////////////////////////////////////////////////////////////

}
