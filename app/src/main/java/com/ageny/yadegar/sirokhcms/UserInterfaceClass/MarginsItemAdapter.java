package com.ageny.yadegar.sirokhcms.UserInterfaceClass;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ageny.yadegar.sirokhcms.DataModelClass.MarginDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.UserReferralDataModel;
import com.ageny.yadegar.sirokhcms.R;

import java.util.ArrayList;

public class MarginsItemAdapter extends RecyclerView.Adapter<MarginsItemAdapter.MyViewHolder> {
    //public MYSQlDBHelper mysQlDBHelper;
    public static ArrayList<MarginDataModelClass> MrgList;
   //public String str1;
    public Context Cntx;
    View customLayout;
    Activity Paramac;
     AlertDialog.Builder InfoAlertDialog;
    View aView;



    public MarginsItemAdapter(ArrayList<MarginDataModelClass> MrgList, Context cntx, Activity ac) {
        this.MrgList=MrgList;
        Cntx = cntx;
        Paramac =ac;
        InfoAlertDialog =new AlertDialog.Builder(new ContextThemeWrapper(ac, R.style.myDialog));



    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
/*
        if(aView != null) {
            if(aView.getParent() != null){
                ((ViewGroup)aView.getParent()).removeView(aView); // <- fix
                aView = LayoutInflater.from(Cntx).inflate(R.layout.margin_recycler_item_list, parent, false);
                parent.addView(aView);
            }
        }*/
        aView = LayoutInflater.from(Paramac).inflate(R.layout.margin_recycler_item_list, parent, false);
        customLayout =LayoutInflater.from(Paramac).inflate(R.layout.margin_alert_dialog_show, parent, false);

        return new MyViewHolder(aView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {

        final MarginDataModelClass MRG = MrgList.get(position);
        if (true && MRG.getTitle().equals("یادآوری")){
//        holder.Txt1.setText(Integer.toString(position+1));
        holder.Txt2.setText(MRG.getDescription());
        holder.Txt3.setText(MRG.getRememberDate());
        holder.Txt4.setText(MRG.getFirst_name()+" "+MRG.getLast_name());
        }else {
//            holder.Txt1.setText(Integer.toString(position+1));
            holder.Txt2.setText(MRG.getDescription());
            //holder.Txt3.setText(MRG.getRememberDate());
            holder.Txt4.setText(MRG.getFirst_name()+" "+MRG.getLast_name());
        }
        /*String strdate=(UR.getActionDate());
        PersianDateFormat persianDateFormat=new PersianDateFormat("yyyy-MM-dd HH:mm:ss");
        PersianDate pdate;
        try {
            pdate=persianDateFormat.parseGrg(strdate,"yyyy-MM-dd HH:mm:ss");
            holder.Txt4.setText(pdate.toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        // holder.Txt4.setText(persianDate.toString());
        holder.MyLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(aView != null) {
                    if(aView.getParent() != null){
                        ((ViewGroup)aView.getParent()).removeView(aView); // <- fix
                        //aView = LayoutInflater.from(Cntx).inflate(R.layout.margin_recycler_item_list, parent, false);
                    }
                }
                if(customLayout != null) {
                    if(customLayout.getParent() != null){
                        ((ViewGroup)customLayout.getParent()).removeView(customLayout); // <- fix
                        //aView = LayoutInflater.from(Cntx).inflate(R.layout.margin_recycler_item_list, parent, false);
                    }
                }



                TextView FullNameTxt = customLayout.findViewById(R.id.margin_alertdialog_username_textview);
                TextView MarginTxt = customLayout.findViewById(R.id.margin_alertdialog_margintext_textview);
                TextView RememberTxt = customLayout.findViewById(R.id.margin_alertdialog_rememberdate_textview);

                FullNameTxt.setText(MRG.getFirst_name()+" "+ MRG.getLast_name());

                MarginTxt.setText(MRG.getDescription());
                RememberTxt.setText(MRG.getRememberDate());
                /*if(Ur.getPeople_Mobile() != "null")
                    MobileTxt.setText(Ur.getPeople_Mobile());
                else
                    MobileTxt.setText("نامشخص");
                if(Ur.getPeople_Phone() != "null")
                    PhoneTxt.setText(Ur.getPeople_Phone());
                else
                    PhoneTxt.setText("نامشخص");
                if(Ur.getPeople_Address() != "null")
                    AddressTxt.setText(Ur.getPeople_Address());
                else
                    AddressTxt.setText("نامشخص");*/

                InfoAlertDialog.setTitle("اطلاعات هامش");
                InfoAlertDialog.setView(customLayout);
                InfoAlertDialog.setPositiveButton("تایید", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });




                AlertDialog al = InfoAlertDialog.create();
                if(al.isShowing()){
                    al.cancel();
                }
                al.show();
                /*Intent intent = new Intent(Cntx, CartableDetailActivity.class);
                intent.putExtra("cartableid",UR.getId() );
                intent.putExtra("userid",UR.getTo_user_id() );

                Cntx.startActivity(intent);*/
                 }
        });
       /* holder.Txt1.setText(str[position]);
        holder.Txt2.setText(str[position]);
        holder.Txt3.setText(str[position]);
        holder.Txt4.setText(str[position]);*/


    }

    @Override
    public int getItemCount() {
        return MrgList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

//        public TextView Txt1;
        public TextView Txt2;
        public TextView Txt3;
        public TextView Txt4;
        public LinearLayout MyLinearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);
//            Txt1 = (TextView) itemView.findViewById(R.id.MarginActivityRowNumberTxt);
            Txt2 = (TextView) itemView.findViewById(R.id.MarginActivityDescriptionTxt);
            Txt3 = (TextView) itemView.findViewById(R.id.MarginActivityRememberDateTxt);
            Txt4 = (TextView) itemView.findViewById(R.id.MarginActivityFirstnameLastnameTxt);
            MyLinearLayout = (LinearLayout) itemView.findViewById(R.id.margins_recycleview);

            // TEXTVIEW
//            if(MyLinearLayout.getParent() != null) {
//                ((ViewGroup)MyLinearLayout.getParent()).removeView(MyLinearLayout); // <- fix
//            }
//            aView.addView(MyLinearLayout);



        }


    }
}



