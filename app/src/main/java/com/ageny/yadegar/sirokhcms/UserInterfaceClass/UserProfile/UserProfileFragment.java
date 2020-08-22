package com.ageny.yadegar.sirokhcms.UserInterfaceClass.UserProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ageny.yadegar.sirokhcms.DataModelClass.UserDataModelClass;
import com.ageny.yadegar.sirokhcms.DownloadImageTask;
import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.MYSQlDBHelper;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;
import com.ageny.yadegar.sirokhcms.UserInterfaceClass.LoginActivity;
import com.ageny.yadegar.sirokhcms.UserInterfaceClass.Splash;

import java.net.MalformedURLException;
import java.net.URL;

public class UserProfileFragment extends Fragment {

    private UserProfileViewModel galleryViewModel;
    MYSQlDBHelper mysQlDBHelper ;
    Context Cntx;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Cntx = getContext();
        mysQlDBHelper =new MYSQlDBHelper(getContext());
        UserDataModelClass userDataModelClass;

        galleryViewModel =
                ViewModelProviders.of(this).get(UserProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_userprofile, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        ImageView nav_img = (ImageView)root.findViewById((R.id.User_Profile_imageView));
        TextView UsernameText  = (TextView)root.findViewById(R.id.User_Profile_FullName_textView);
        TextView EmailText = (TextView)root.findViewById(R.id.User_Profile_Email_textView);
        TextView UserCode = (TextView)root.findViewById(R.id.User_Profile_User_Code_textView);
        JSONHandlre.isConnectedtoInternet(getContext());
        userDataModelClass=mysQlDBHelper.GetCurrentUser();
        URL url=null ;
        try {
            url =new URL(URLs.getBaseURL()+ userDataModelClass.getImage());
            UsernameText.setText(userDataModelClass.getFirst_name()+" "+userDataModelClass.getLast_name());
            EmailText.setText(userDataModelClass.getEmail());
            UserCode.setText(userDataModelClass.getId());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.d("hhh DWndImg", url.toString());
        new DownloadImageTask(nav_img).execute(url.toString());
        Button button = (Button)root.findViewById(R.id.User_profile_Exit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mysQlDBHelper.DeleteUser();
                Intent i =new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        return root;
    }
}