package com.ageny.yadegar.sirokhcms.UserInterfaceClass;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.ageny.yadegar.sirokhcms.DataModelClass.ReferralFolderAttachmentShowDataModelClass;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;

import java.net.URI;
import java.nio.file.Paths;
import java.util.ArrayList;
public class AttachmentsItemAdapter extends RecyclerView.Adapter<AttachmentsItemAdapter.MyViewHolder> {
    public static ArrayList<ReferralFolderAttachmentShowDataModelClass> AtchList;
    public Context Cntx;
    Activity Paramac;

    public AttachmentsItemAdapter(ArrayList<ReferralFolderAttachmentShowDataModelClass> AtchList, Context cntx , Activity ac) {
        this.AtchList=AtchList;
        Cntx = cntx;
        Paramac =ac;

        //Toast.makeText(Cntx,"itemAdapter says:" + urList.toString() , Toast.LENGTH_SHORT).show();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View aView = LayoutInflater.from(Paramac).inflate(R.layout.attachments_recycler_item_list, parent, false);
        //Toast.makeText(Cntx,"onCreate say" , Toast.LENGTH_SHORT).show();
        return new MyViewHolder(aView);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ReferralFolderAttachmentShowDataModelClass ATCH = AtchList.get(position);
        //holder.Txt1.setText(Integer.toString(position+1));
        //holder.Txt2.setText(URLs.getBaseURL()+ ATCH.getFile_Path());

        holder.Txt3.setText(ATCH.getFirst_Name()+ " " +ATCH.getLast_Name());
        if(ATCH.getFile_Description() == "null"){
            holder.Txt4.setText("بدون توضیحات");
        }else {
            holder.Txt4.setText(ATCH.getFile_Description());
        }
        holder.Txt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        holder.DownloadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = holder.DownloadBtn.getContext();
                String fileName = ATCH.getFile_Path().substring(ATCH.getFile_Path().lastIndexOf("/")+1);
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                try {
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(URLs.getBaseURL()+ATCH.getFile_Path()));
                    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI |
                            DownloadManager.Request.NETWORK_MOBILE);
                    request.setTitle("دانلود فایل");
                    request.setDescription(ATCH.getFile_Description());
                    request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
                    request.setMimeType("*/*");
                    request.setVisibleInDownloadsUi(true);
                    manager.enqueue(request);
                }catch (IllegalArgumentException e) {
                    Log.e("hhh", "downloaf:::"+e.toString());
                }
            }
        });

    }
    @Override
    public int getItemCount() {
        return AtchList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public Button DownloadBtn;
        // public TextView Txt2;
        public TextView Txt3;
        public TextView Txt4;
        public LinearLayout MyLinearLayout;
        public MyViewHolder(View itemView) {
            super(itemView);

            //Txt1 = (TextView) itemView.findViewById(R.id.AttachmentsActivityRowNumTxt);
            // Txt2 = (TextView) itemView.findViewById(R.id.AttachmentsActivityFilesnameTxt);
            Txt3 = (TextView) itemView.findViewById(R.id.AttachmentsActivityUserTxt);
            Txt4 = (TextView) itemView.findViewById(R.id.AttachmentsActivityDescriptionTxt);
            MyLinearLayout = (LinearLayout) itemView.findViewById(R.id.attachment_recycleview);
            DownloadBtn = (Button)itemView.findViewById(R.id.attachment_download_bttn);

        }
    }
}