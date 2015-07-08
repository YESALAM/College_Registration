package com.seatech.alam.collegeregistration;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class NetworkFetch extends AsyncTask<String,Integer, Data>{
		FinishRegister fr;
		Context context;
		private ProgressDialog pdia;
		Response rs ;
		final String TAG = "NetworkFetch" ;

		Data da = null ;
		private String url = null ;

	    public NetworkFetch(FinishRegister fr, Context context){
		    this.fr = fr ;
		    this.context = context ;
	    }
	
	
	    @Override
	    protected void onPreExecute(){
	        super.onPreExecute();
	            pdia = new ProgressDialog(context);
	            pdia.setMessage("Loading...");
	            pdia.show();
	    }
	
	    protected Data doInBackground(String... params) {
		    // TODO Auto-generated method stub
		    url = params[0] ;
					
		    doWork(url);
				
		    return da;
	    }
	
	    protected void onProgressUpdate(Integer... values) {
	            Log.d(TAG,"onProgressUpdate - " + values[0]);
        }
	
	    protected void onPostExecute(Data result) {
		    super.onPostExecute(result);
		    pdia.dismiss();
		    if(rs==Response.OK){
                //thay means flag didn't change and user input the right credential pair;
			    fr.setValue(result);
			
            } else if(rs == Response.COMPLETE) {
				//Wrong credential pair was provided by user;
				fr.wrongInfo(rs);
			} else {
				fr.wrongInfo(rs);
			}
		
        }
	 
	private void doWork(String url) {
		
		try{
			publishProgress (00);
			
			Document doc = Jsoup.connect(url).get();
			
			Element main = doc.getElementById("section");
            //if user has input wrong enroll and mother name pair then there will be no element by id "Section"
            //and that will make getElementById("section") to return null value to main ;
			if(main==null){
                //flag will set to 1 to later send a wrong info message to caller ;

				Element contair = doc.getElementsByClass("container").first();
				try{
					Element span = contair.child(4);
					rs = Response.COMPLETE ;
				} catch (IndexOutOfBoundsException iob){
					rs = Response.WORNG;
				}
				return   ;
			} else {
				Element table = main.child(1);
				Element tbody = table.child(0);

				Element firstrow = tbody.child(0);
				Element enrolldata = firstrow.child(0);
				Element coursedata = firstrow.child(1);
				Element nameinput = enrolldata.getElementsByTag("input").first();
				String enroll = nameinput.attr("value");
				String course = coursedata.getElementsByTag("input").first().attr("value");
                publishProgress (20);

				Element secondrow = tbody.child(1);
				Element namedata = secondrow.child(0);
				Element branchdata = secondrow.child(1);
				String name = namedata.getElementsByTag("input").first().attr("value");
				String branch = branchdata.getElementsByTag("input").first().attr("value");
                publishProgress (40);

				Element thirdrow = tbody.child(2);
				Element sectiondata = thirdrow.child(0);
				Element semesterdata = thirdrow.child(1);
				String section = sectiondata.getElementsByTag("input").first().attr("value");
				String semester = semesterdata.getElementsByTag("input").first().attr("value");
                publishProgress (60);

				Element fourthrow = tbody.child(3);
                Element dobdata = fourthrow.child(0);
                String dob = dobdata.getElementsByTag("input").first().attr("value");
                publishProgress (80);

                da = new Data();
                da.setEnroll(enroll);
                da.setBranch(branch);
                da.setCourse(course);
                da.setDob(dob);
                da.setName(name);
                da.setSemester(semester);
                da.setSession(section);

				rs = Response.OK ;

				Log.e(TAG,enroll);
				publishProgress (100);
				} 
			} catch(SocketTimeoutException ste){
				doWork(url);
			}catch (IOException e) {
				Log.e(TAG,"Did not work");
				Log.e(TAG,e.toString());
			} 
		
	
	}
	}
