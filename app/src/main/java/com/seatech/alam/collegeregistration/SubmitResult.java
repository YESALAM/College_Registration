package com.seatech.alam.collegeregistration;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.seatech.alam.collegeregistration.ParseResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class SubmitResult extends ActionBarActivity implements View.OnClickListener {
    Data data ;
    WebView webView ;
    String htmlfile ;
    String fname ;
    Button pdf;
    String shtml;
    final String TAG = "SubmitResult" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_result);
        data = Globals.data ;
        pdf = (Button) findViewById(R.id.pdf);
        pdf.setOnClickListener(this);
        Intent intent = getIntent();
        shtml = intent.getStringExtra("HTML");

        webView = (WebView) findViewById(R.id.webView);
        webView.loadData(shtml,"text/html",null);

        Document doc = Jsoup.parse(shtml);
        Element main = doc.getElementById("mainForm");
        Element date = main.child(0).child(0).child(0).child(3);

        data.setSubmitDate(date.text());






    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void printPdf()  {
        htmlfile = "/sdcard/" + "preview" + ".html";
        fname =  data.getName() ;
        fname = fname.substring(0,fname.indexOf(" "));
      /*  AssetManager mngr = this.getAssets();

        InputStream inputStream = null;
        try {
            inputStream = mngr.open("Registration_Preview.html");
        } catch (IOException e) {
            Log.e(TAG, "on Create ioexception");
        }


        File file = createFileFromInputStream(inputStream);

        DataPrint dataPrint = new DataPrint(file,data);
        */
        File newfile = new File(htmlfile);
        try {
           if(!(newfile.exists())){
               newfile.createNewFile();
           }
        } catch (IOException e) {
           Log.e(TAG,e.getMessage());
        }
        File file = ParseResponse.parse(shtml,getApplication() );
        FileOperations fop = new FileOperations();

        if (fop.write(fname , file)) {

            Toast.makeText(getApplicationContext(),
                    fname + ".pdf created in SdCard root Directory", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(getApplicationContext(), "I/O error",
                    Toast.LENGTH_SHORT).show();
        }
    }


    private File createFileFromInputStream(InputStream inputStream) {

        try{
            File f = new File(htmlfile);

            // If file does not exists, then create it
            if (!f.exists()) {
                f.createNewFile();
            }
            OutputStream outputStream = new FileOutputStream(f.getAbsolutePath());
            byte buffer[] = new byte[1024];
            int length = 0;

            while((length=inputStream.read(buffer)) > 0) {
                outputStream.write(buffer,0,length);
            }

            outputStream.close();
            inputStream.close();

            return f;
        }catch (IOException e) {
            //Logging exception
            Log.e(TAG, "create file error");
        }

        return null;
    }


    @Override
    public void onClick(View v) {
        printPdf();
    }
}
