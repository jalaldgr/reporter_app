package com.ageny.yadegar.sirokhcms.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class UpdateDialog {

  public   static void show(final Context context, String content, final String downloadUrl) {

        if (isContextValid(context)) {


            new AlertDialog.Builder(context)
                    .setTitle("")
                    .setMessage(content)
                    .setPositiveButton("بروزرسانی", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            goToDownload(context, downloadUrl);
                        }
                    })
                    .setNegativeButton("انصراف", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    })
                    .setCancelable(false)
                    .show();

        }
    }

    private static boolean isContextValid(Context context) {
        return context instanceof Activity && !((Activity) context).isFinishing();
    }
    private static void goToDownload(Context context, String downloadUrl) {
        Intent intent = new Intent(context.getApplicationContext(), DownloadService.class);
        intent.putExtra("downloadurl", downloadUrl);
        context.startService(intent);
    }


}
