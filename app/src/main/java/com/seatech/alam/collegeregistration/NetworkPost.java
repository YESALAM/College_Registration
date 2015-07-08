package com.seatech.alam.collegeregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sadar-e- on 7/2/2015.
 */
class NetworkPost extends AsyncTask<String, Void, String> {
    Context context;
    FinishRegisterPost finishRegister;
    Data data;
    private ProgressDialog pdia;
    public NetworkPost(Context context,FinishRegisterPost finishRegister,Data tosubmitdata){
        this.context = context ;
        this.finishRegister = finishRegister ;
        this.data = tosubmitdata ;
    }

    @Override
    protected String doInBackground(String... params) {




        HttpClient httpClient = new DefaultHttpClient();

        // In a POST request, we don't pass the values in the URL.
        //Therefore we use only the web page URL as the parameter of the HttpPost argument
        HttpPost httpPost = new HttpPost("http://192.168.1.102/upload_student_registrationForm_data.php");

        // Because we are not passing values over the URL, we should have a mechanism to pass the values that can be
        //uniquely separate by the other end.
        //To achieve that we use BasicNameValuePair
        //Things we need to pass with the POST request


        // We add the content that we want to pass with the POST request to as name-value pairs
        //Now we put those sending details to an ArrayList with type safe of NameValuePair
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        nameValuePairList.add(new BasicNameValuePair("enroll",data.getEnroll()));
        nameValuePairList.add(new BasicNameValuePair("sname",data.getName()));
        nameValuePairList.add(new BasicNameValuePair("course",data.getCourse()));
        nameValuePairList.add(new BasicNameValuePair("dob",data.getDob()));
        nameValuePairList.add(new BasicNameValuePair("branch",data.getBranch()));
        nameValuePairList.add(new BasicNameValuePair("sem",data.getSemester()));
        nameValuePairList.add(new BasicNameValuePair("session",data.getSession()));
        nameValuePairList.add(new BasicNameValuePair("reciptNo",data.getReceiptNo()));
        nameValuePairList.add(new BasicNameValuePair("scontact",data.getScontact()));
        nameValuePairList.add(new BasicNameValuePair("email",data.getEmail()));
        nameValuePairList.add(new BasicNameValuePair("pcontact",data.getPcontact()));
        nameValuePairList.add(new BasicNameValuePair("paidAmount",data.getPaidAmount()));
        nameValuePairList.add(new BasicNameValuePair("balAmount",data.getBalAmount()));

        try {
            // UrlEncodedFormEntity is an entity composed of a list of url-encoded pairs.
            //This is typically useful while sending an HTTP POST request.
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

            // setEntity() hands the entity (here it is urlEncodedFormEntity) to the request.
            httpPost.setEntity(urlEncodedFormEntity);

            try {
                // HttpResponse is an interface just like HttpPost.
                //Therefore we can't initialize them
                HttpResponse httpResponse = httpClient.execute(httpPost);

                // According to the JAVA API, InputStream constructor do nothing.
                //So we can't initialize InputStream although it is not an interface
                InputStream inputStream = httpResponse.getEntity().getContent();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();

                String bufferedStrChunk = null;

                while((bufferedStrChunk = bufferedReader.readLine()) != null){
                    stringBuilder.append(bufferedStrChunk);
                }

                return stringBuilder.toString();

            } catch (ClientProtocolException cpe) {
                Log.e("NetworkPost",cpe.getMessage());
            }catch (SocketTimeoutException ste){
                Log.e("NetwoekPost",ste.getMessage());
            } catch (IOException ioe) {
                Log.e("NetworkPost", ioe.getMessage());
            }

        } catch (UnsupportedEncodingException uee) {
            Log.e("NetworkPost",uee.getMessage());
        }

        return null;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pdia = new ProgressDialog(context);
        pdia.setMessage("Submitting You Data...");
        pdia.show();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        pdia.dismiss();
        finishRegister.finishedPost(result);

    }
}
