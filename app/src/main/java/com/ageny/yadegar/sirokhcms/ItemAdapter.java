package com.ageny.yadegar.sirokhcms;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.ageny.yadegar.sirokhcms.DataModelClass.MarginDataModelClass;
import com.ageny.yadegar.sirokhcms.DataModelClass.UserReferralDataModel;
import com.ageny.yadegar.sirokhcms.UserInterfaceClass.CartableDetailActivity;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder> {
    //public MYSQlDBHelper mysQlDBHelper;
    public static ArrayList<UserReferralDataModel> URList;
   //public String str1;
    public Context Cntx;
    int RSID;
    public ItemAdapter(ArrayList<UserReferralDataModel> urList, Context cntx) {
        this.URList = urList;

        Cntx = cntx;
        //Toast.makeText(Cntx,"itemAdapter says:" + urList.toString() , Toast.LENGTH_SHORT).show();

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View aView = LayoutInflater.from(Cntx).inflate(R.layout.recycler_item_list, parent, false);
        //Toast.makeText(Cntx,"onCreate say" , Toast.LENGTH_SHORT).show();
        return new MyViewHolder(aView);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder( MyViewHolder holder, final int position) {

        final UserReferralDataModel UR = URList.get(position);
        RSID =  Integer.parseInt(UR.getReferral_folder_state_id());
        if(UR.getSeen().equals("0")) holder.Txt1.setTypeface(null,Typeface.BOLD);
        holder.Txt1.setText(UR.getSubject());
        holder.Txt3.setText(UR.getUrgencyTitle());
        holder.Txt4.setText(UR.getActionDate());

        if(RSID==1){
           // holder.Txt1.setBackgroundColor(holder.Txt1.getContext().getResources().getColor(R.color.colorOnGoing));
            holder.Txt2.setBackgroundColor(holder.Txt2.getContext().getResources().getColor(R.color.colorOnGoing));
            //holder.Txt3.setBackgroundColor(holder.Txt3.getContext().getResources().getColor(R.color.colorOnGoing));
            //holder.Txt4.setBackgroundColor(holder.Txt4.getContext().getResources().getColor(R.color.colorOnGoing));
            holder.Txt2.setText("در دست اقدام");
        }
        if(RSID==2){
            //holder.Txt1.setBackgroundColor(holder.Txt1.getContext().getResources().getColor(R.color.colorReturned));
            holder.Txt2.setBackgroundColor(holder.Txt2.getContext().getResources().getColor(R.color.colorReturned));
            //holder.Txt3.setBackgroundColor(holder.Txt3.getContext().getResources().getColor(R.color.colorReturned));
            //holder.Txt4.setBackgroundColor(holder.Txt4.getContext().getResources().getColor(R.color.colorReturned));
            holder.Txt2.setText("برگشت خورده");
        }
        if(RSID==3){
            //holder.Txt1.setBackgroundColor(holder.Txt1.getContext().getResources().getColor(R.color.colorDone));
            holder.Txt2.setBackgroundColor(holder.Txt2.getContext().getResources().getColor(R.color.colorDone));
            //holder.Txt3.setBackgroundColor(holder.Txt3.getContext().getResources().getColor(R.color.colorDone));
            //holder.Txt4.setBackgroundColor(holder.Txt4.getContext().getResources().getColor(R.color.colorDone));
            holder.Txt2.setText("انجام شده");
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
                Intent intent = new Intent(Cntx, CartableDetailActivity.class);
                intent.putExtra("cartableid",UR.getId() );
                intent.putExtra("userid",UR.getTo_user_id() );
                intent.putExtra("referralstateid",UR.getReferral_folder_state_id().toString());
                intent.putExtra("RefferalNewsTypeTitle",UR.getNews_type_title().toString());

                Cntx.startActivity(intent);
                 }
        });
       /* holder.Txt1.setText(str[position]);
        holder.Txt2.setText(str[position]);
        holder.Txt3.setText(str[position]);
        holder.Txt4.setText(str[position]);*/


    }

    @Override
    public int getItemCount() {
        return URList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView Txt1;
        public TextView Txt2;
        public TextView Txt3;
        public TextView Txt4;
        public LinearLayout MyLinearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);

            Txt1 = (TextView) itemView.findViewById(R.id.textView2);
            Txt2 = (TextView) itemView.findViewById(R.id.textView3);
            Txt3 = (TextView) itemView.findViewById(R.id.textView4);
            Txt4 = (TextView) itemView.findViewById(R.id.textView5);
            MyLinearLayout = (LinearLayout) itemView.findViewById(R.id.cartable_recycler_item_layout);
        }


    }
}




