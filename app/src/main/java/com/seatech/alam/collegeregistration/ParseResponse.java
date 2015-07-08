package com.seatech.alam.collegeregistration;

import android.content.Context;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Sadar-e- on 7/3/2015.
 */
public class ParseResponse {
    public static File parse(String html,Context context){
        File output = new File( "/sdcard/","response.html");
        Document doc = Jsoup.parse(html);

        try {
            Element  form = doc.getElementById("formPrint");

            Elements brtags = form.getElementsByTag("br");
            Elements ptags = form.getElementsByTag("p");

            for (Element br : brtags) {


                br.after("<br>");


            }

            Elements newbr = form.getElementsByTag("br");
            for (Element br : newbr) {
                br.appendText("</br>");
                br.text("");
                Log.e("ParseResponse", br.toString());
            }


            for (Element p : ptags) {

                System.out.println(p.toString());
                System.out.println();
                p.remove();
            }

            Element footer = form.getElementById("footer");
            Element credit = footer.child(0);
            credit.text("App registration is Developed by:- Sadar-e-Alam(CSE 2013-2017)");

            try {

                if (!(output.exists())) {
                    output.createNewFile();
                }

                String result = form.toString();
                BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"));
                htmlWriter.write(result);
                htmlWriter.flush();
                htmlWriter.close();

                return output;
            }catch (IOException ioe){
                Log.e("ParseResponse",ioe.getMessage());
            }

        }catch(Exception e){

            try {

                if (!(output.exists())) {
                    output.createNewFile();
                }

                String result = doc.toString();
                BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(output), "UTF-8"));
                htmlWriter.write(result);
                htmlWriter.flush();
                htmlWriter.close();
            }catch (IOException ioe){
                Log.e("ParseResponse",ioe.getMessage());
            }

        }


        return output ;
    }

}
