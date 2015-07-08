package com.seatech.alam.collegeregistration;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by Sadar-e- on 7/2/2015.
 */
public class DataPrint {
    File basehtml ;
    Data data ;
    public DataPrint(File file,Data data){
        this.basehtml = file ;
        this.data=data ;
    }

    public File setData(){
        try {
            Document doc = Jsoup.parse(basehtml, "UTF-8") ;

            Element main = doc.getElementById("mainForm");
            Element date = main.child(0).child(0).child(0).child(3);
            date.text(data.getSubmitDate());
            Log.e("DataPrinr",date.text());

            Element sinfo = main.child(1).child(0);

            Element enroll = sinfo.child(0).child(1);
            enroll.text(data.getEnroll());

            Element name = sinfo.child(1).child(1);
            name.text(data.getName());

            Element dob = sinfo.child(2).child(1);
            dob.text(data.getDob());

            Element course = sinfo.child(3).child(1);
            course.text(data.getCourse());

            Element branch = sinfo.child(4).child(1);
            branch.text(data.getBranch());

            Element session = sinfo.child(5).child(1);
            session.text(data.getSession());

            Element sem = sinfo.child(6).child(1);
            sem.text(data.getSemester());

            Element scontact = sinfo.child(7).child(1);
            scontact.text(data.getScontact());

            Element email = sinfo.child(8).child(1);
            email.text(data.getEmail());

            Element pcontact = sinfo.child(9).child(1);
            pcontact.text(data.getPcontact());

            Element reciept = sinfo.child(10).child(1);
            reciept.text(data.getReceiptNo());

            Element paidAmout = sinfo.child(11).child(1);
            paidAmout.text(data.getPaidAmount());

            Element balAmount = sinfo.child(12).child(1);
            balAmount.text(data.getPaidAmount());

            Element libraryForm = doc.getElementById("libraryForm");
            Element linfo = libraryForm.child(0).child(0);

            Element lenroll = linfo.child(0).child(1);
            lenroll.text(data.getEnroll());

            Element lname = linfo.child(1).child(1);
            lname.text(data.getName());

            Element ldob = linfo.child(2).child(1);
            ldob.text(data.getDob());

            Element lcourse = linfo.child(3).child(1);
            lcourse.text(data.getCourse());

            Element lbranch = linfo.child(4).child(1);
            lbranch.text(data.getBranch());

            Element lsession = linfo.child(5).child(1);
            lsession.text(data.getSession());

            Element lsem = linfo.child(6).child(1);
            lsem.text(data.getSemester());

            Element lscontact = linfo.child(7).child(1);
            lscontact.text(data.getScontact());

            Element lemail = linfo.child(8).child(1);
            lemail.text(data.getEmail());

            Log.e("DataPrint", lname.text());
            Log.e("DataPrint", lenroll.text());

            BufferedWriter htmlWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(basehtml), "UTF-8"));
            String result = doc.outerHtml();
            Log.e("DataPrint",result);
            htmlWriter.write(result);
            htmlWriter.flush();
            htmlWriter.close();

            Log.e("DataPrint", lsem.text());
            Log.e("DataPrint", lemail.text());
            return basehtml ;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return basehtml ;

    }


}
