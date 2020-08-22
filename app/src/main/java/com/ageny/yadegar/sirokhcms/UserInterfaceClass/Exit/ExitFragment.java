package com.ageny.yadegar.sirokhcms.UserInterfaceClass.Exit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ageny.yadegar.sirokhcms.JSONHandlre;
import com.ageny.yadegar.sirokhcms.R;
import com.ageny.yadegar.sirokhcms.URLs;

public class ExitFragment extends Fragment {

    private ExitViewModel sendViewModel;
    public static   WebView myWeb;

    public ExitFragment(){

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(ExitViewModel.class);
        View root = inflater.inflate(R.layout.fragment_exit, container, false);

        WebView myWeb = (WebView)root.findViewById(R.id.main_web_view);
        if(JSONHandlre.isConnectedtoInternet(getContext())) {
            myWeb.loadUrl(URLs.getBaseURL());
            WebSettings websttng = myWeb.getSettings();
            websttng.setJavaScriptEnabled(true);
            myWeb.setWebViewClient(new WebViewClient());
        }


        sendViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
    }

    public static boolean canGoBack() {
        return myWeb.canGoBack();
    }

    public static void goBack() {
        myWeb.goBack();
    }
}