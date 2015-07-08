package com.seatech.alam.collegeregistration;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener,FinishRegister,FinishRegisterPost {

    EditText etenroll;
    EditText etmothername;
    EditText etsname;
    EditText etcourse;
    EditText etbranch;
    EditText etsession;
    EditText etsem;
    EditText etdob;
    EditText etscontact;
    EditText etrecieptNo;
    EditText etemail;
    EditText etpaidAmount;
    EditText etpcontact;
    EditText etbalAmount;

    TextView status ;

    Button btfetch;
    Button btsubmit;

    LinearLayout secondLayout ;

    Data submit ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etenroll = (EditText) findViewById(R.id.etenroll);
        etmothername  = (EditText) findViewById(R.id.etmothername);
        etsname = (EditText) findViewById(R.id.etsname);
        etcourse = (EditText) findViewById(R.id.etcourse);
        etbranch = (EditText) findViewById(R.id.etbranch);
        etsession = (EditText) findViewById(R.id.etsession);
        etsem = (EditText) findViewById(R.id.etsem);
        etdob = (EditText) findViewById(R.id.etdob);
        etscontact = (EditText) findViewById(R.id.etscontact);
        etrecieptNo = (EditText) findViewById(R.id.etreceiptNo);
        etemail = (EditText) findViewById(R.id.etemail);
        etpaidAmount = (EditText) findViewById(R.id.etpaidAmount);
        etpcontact = (EditText) findViewById(R.id.etpcontact);
        etbalAmount = (EditText) findViewById(R.id.etbalAmount);

        status = (TextView) findViewById(R.id.status);

        btfetch = (Button) findViewById(R.id.btfetch);
        btsubmit = (Button) findViewById(R.id.btsubmit);

        setData();

        secondLayout = (LinearLayout) findViewById(R.id.secondLayout);

        secondLayout.setVisibility(View.GONE);


        btfetch.setOnClickListener(this);
        btsubmit.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btfetch:
                status.setVisibility(View.GONE);
                //dofetching
                String url = "http://www.bistbpl.in/onlineSemesterRegistration/studentRegistration_form.php?enrollment_id="+etenroll.getText()+"%20&%20mother_name="+etmothername.getText();
                Log.e("URL", url);
                new NetworkFetch(this,this).execute(url);
                break;
            case R.id.btsubmit:
                //dosubmit
                submit.setScontact(etscontact.getText().toString());
                submit.setReceiptNo(etrecieptNo.getText().toString());
                submit.setPaidAmount(etpaidAmount.getText().toString());
                submit.setEmail(etemail.getText().toString());
                submit.setPcontact(etpcontact.getText().toString());
                submit.setBalAmount(etbalAmount.getText().toString());
                new NetworkPost(this,this,submit).execute();
                break;
            default:
        }
    }

    @Override
    public void setValue(Data result) {

           submit = new Data(result);
           etsname.setText(result.getName());
           etcourse.setText(result.getCourse());
           etbranch.setText(result.getBranch());
           etsession.setText(result.getSession());
           etsem.setText(result.getSemester());
           etdob.setText(result.getDob());
           btfetch.setVisibility(View.GONE);
         secondLayout.setVisibility(View.VISIBLE);


    }


    public void wrongInfo(Response rs) {
        status.setVisibility(View.VISIBLE);
        if(rs==Response.COMPLETE){
            status.setText("Already Registered");
            status.setTextColor(Color.GREEN);
        } else {
            Toast.makeText(this,"Enroll no and mother name did't match with record",Toast.LENGTH_LONG).show();
            status.setText("Enrollment and Mother's name not matched !");
            status.setTextColor(Color.RED);
        }


    }


    @Override
    public void finishedPost(String param) {
        if(param == null) {
          Toast.makeText(this,"Something Gone Wrong",Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, SubmitResult.class);
            intent.putExtra("HTML", param);
            Globals.data = submit;
            startActivity(intent);
        }
    }

    public void setData(){
        etenroll.setText("0112cs131076");
        etmothername.setText("zarina khatoon");
         etscontact.setText("8982384594");
         etrecieptNo.setText("462026");
         etemail.setText("itisalam@gmail.com");
         etpaidAmount.setText("462026");
         etpcontact.setText("8982384596");
         etbalAmount.setText("000");

    }

}
