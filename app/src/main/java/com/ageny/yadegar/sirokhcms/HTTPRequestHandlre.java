package com.ageny.yadegar.sirokhcms;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
//this class manage HTTP request for send and get

public class HTTPRequestHandlre {

    //this method will send a post request to the specified url
    //in this app we are using only post request
    //in the hashmap we have the data to be sent to the server in keyvalue pairs
    public  String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        //Log.d("hhh: HTTPReq", requestURL);
        StringBuilder sb = new StringBuilder();
        HttpURLConnection httpconn = null;

        try {
            url = new URL(requestURL);
             httpconn = (HttpURLConnection) url.openConnection();
            httpconn.setReadTimeout(15000);
            httpconn.setConnectTimeout(15000);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream os = httpconn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            //Log.d("hhh: HTTPReq" , "HASHMap is the: "+postDataParams.toString());
            writer.write(getPostDataString(postDataParams));
            writer.flush();
            writer.close();
            os.close();
            int responseCode = httpconn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpconn.getInputStream()));
                sb = new StringBuilder();
                String response;

                while ((response = br.readLine()) != null) {
                    sb.append(response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();//Log.d("hhh: HTTPReq" , e.toString());
        }
        finally {
         httpconn.disconnect();
        }
        //Log.d("hhh: HTTPReq", "sb is: "+sb.toString());
        return sb.toString();
    }



    public  String sendGetRequest(String requestURL, HashMap<String, String> postDataParams) {
        URL url;
        StringBuilder sb = new StringBuilder();
        HttpURLConnection httpconnget = null;
        //httpconn.disconnect();
        try {
            url = new URL(requestURL);
            httpconnget = (HttpURLConnection) url.openConnection();

            httpconnget.setRequestMethod("GET");
//            httpconnget.setDoOutput(true);
//            httpconnget.setDoInput(true);
            httpconnget.setUseCaches(false);
            httpconnget.setAllowUserInteraction(false);
            httpconnget.setRequestProperty("Content-Type", "application/json");
            httpconnget.addRequestProperty(" "," ");


            int responseCode = httpconnget.getResponseCode();
            Log.d("hhh: Reasponse is", Integer.toString(responseCode) );

            Log.d("hhh: Method is", httpconnget.getRequestMethod() );

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpconnget.getInputStream()));
                sb = new StringBuilder();
                String response;

                while ((response = br.readLine()) != null) {
                    sb.append(response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();Log.d("hhh: HTTPReq catch" , e.toString());
        }finally {
            httpconnget.disconnect();
        }
        Log.d("hhh: HTTPReq", "sb is: "+sb.toString() );
        return sb.toString();
    }



    //this method is converting keyvalue pairs data into a query string as needed to send to the server
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }



    public  String sendGetRequestUpdate(String requestURL , Context context) {
        URL url;
        StringBuilder sb = new StringBuilder();
        HttpURLConnection httpconnget = null;
        //httpconn.disconnect();
        try {
            url = new URL(requestURL);
            httpconnget = (HttpURLConnection) url.openConnection();

            httpconnget.setRequestMethod("GET");
            httpconnget.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
            httpconnget.setUseCaches(false);
            httpconnget.setAllowUserInteraction(false);


            int responseCode = httpconnget.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_NOT_FOUND) {
               return "404";
            }
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(httpconnget.getInputStream()));
                sb = new StringBuilder();
                String response;
                while ((response = br.readLine()) != null) {
                    sb.append(response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();Log.d("hhh: HTTPReq catch" , e.toString());
        }finally {
            httpconnget.disconnect();
        }
        Log.d("hhh: HTTPReq", "sb is: "+sb.toString() );
        return sb.toString();
    }

}




