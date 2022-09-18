package com.example.librarymanagementsystemstudents;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.librarymanagementsystemstudents.Globals.loadingDialog;
import static com.example.librarymanagementsystemstudents.Globals.resetpassresult;
import static com.example.librarymanagementsystemstudents.Globals.sendlinkresult;
import static com.example.librarymanagementsystemstudents.Globals.status;
import static com.example.librarymanagementsystemstudents.Globals.user_email;

public class ForgotActivity extends AppCompatActivity {

    String senpass="";
    String senotpreplace="";
    String existingMail="";

    public char[] pass = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static Random random=new Random();




    Button sendlinkbtn,resetpasswordbtn;
    EditText emailreset,enteredotp;
    TextInputEditText resetpassword1,resetpassword2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
         Context context=getApplicationContext();

         loadingDialog=new LoadingDialog(this);


        sendlinkbtn=findViewById(R.id.sendlinkbutton);
        resetpasswordbtn=findViewById(R.id.resetpasswordbutton);

        //textfields

        emailreset=findViewById(R.id.emailresetfield);
        enteredotp=findViewById(R.id.otpfield);
        resetpassword1=findViewById(R.id.resetpasswordfield1);
        resetpassword2=findViewById(R.id.resetpasswordfield2);

        sendlinkbtn.setOnClickListener(new View.OnClickListener() {
           

            @Override
            public void onClick(View v) {

                NetworkUtil.setConnectivityStatus(context);
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);

                if (emailreset.getText().toString().isEmpty()) {

                    Context context = getApplicationContext();
                    
                    CharSequence text = ":( Enter Email ! ):";
                    int duration = Toast.LENGTH_SHORT;


                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" +text + "</b> </font>"), duration);

                    toast.show();

                } else  if(status!=0) {
                    
                    senpass="";
                    for(int i=0;i<10;i++)
                    {
                        int pos=random.nextInt(pass.length);
                        Character ch=pass[pos];
                        senpass+=ch.toString();

                    }

                    Context context = getApplicationContext();
                     Globals.user_email = emailreset.getText().toString().trim();


                    AlertDialog percentage1 =  loadingDialog.startLoadingDialog();
                    TextView per1= percentage1.findViewById(R.id.progress_percentage);
                    per1.setText("50.00 %");

                    final ExecutorService executorServiceSendLink= Executors.newSingleThreadExecutor();
                    executorServiceSendLink.execute(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                String login_url="https://stalinism-noun.000webhostapp.com/sendlink.php";
                                //System.out.println("running-----------------------------------------------------------------------");
                                URL url=new URL(login_url);
                                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                httpURLConnection.setRequestMethod("POST");
                                httpURLConnection.setDoOutput(true);
                                httpURLConnection.setDoInput(true);
                                OutputStream outputStream=httpURLConnection.getOutputStream();
                                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                String post_data=URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(user_email,"UTF-8")+"&"+
                                        URLEncoder.encode("user_otp","UTF-8")+"="+URLEncoder.encode(senpass,"UTF-8");


                                bufferedWriter.write(post_data);
                                bufferedWriter.flush();
                                bufferedWriter.close();
                                outputStream.close();

                                InputStream inputStream = httpURLConnection.getInputStream();
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                                String line="";
                                int i=0;
                                while((line = bufferedReader.readLine())!= null) {
                                    sendlinkresult[i]="";
                                    sendlinkresult[i] += line;
                                    i++;
                                }
                                // //System.out.println("result[0]:"+blresult[0]+"result[1]:"+blresult[1]);
                                bufferedReader.close();
                                inputStream.close();
                                httpURLConnection.disconnect();

                            } catch (MalformedURLException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();

                            }


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if((sendlinkresult[0]==null) || sendlinkresult[0].equals("null"))
                                    {
                                        // //System.out.println("null Executing");
                                        CharSequence text = ":( Please Register ! ):";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);
                                        toast.show();



                                    }
                                    else
                                    {
                                        //  //System.out.println("SENMAIL EXECUTED:");
                                        senEmail(context,sendlinkresult,user_email);

                                        CharSequence text = ":) Please Check Your MailBox (:";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);
                                        toast.show();
                                        existingMail=user_email;


                                    }
                                    per1.setText("100.00 %");
                                    //System.out.println("Link Sent!");
                                    loadingDialog.dismissDialog();
                                    //System.out.println("SendLink Dialog Dismissed!");





                                }
                            });

                        }
                    });


//

                }
                else {
                    Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>"+ ":( " + "Internet Connection Not Found !" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

                    toast.show();


                }


            }
        });

        resetpasswordbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                NetworkUtil.setConnectivityStatus(context);
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);

                //email
                if (enteredotp.getText().toString().isEmpty() || resetpassword1.getText().toString().isEmpty() || resetpassword2.getText().toString().isEmpty()){
                    
                    Context context = getApplicationContext();
                    CharSequence text = ":( Please Fill Remaining Fields ! ):";
                    int duration = Toast.LENGTH_SHORT;


                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();

                }

                else if(resetpassword1.getText().toString().length() < 8 && resetpassword2.getText().toString().length() <8)
                {   
                    Context context = getApplicationContext();
                    CharSequence text = ":( Password Length Cannot Be Less Than 8 Char ! ):";
                    int duration = Toast.LENGTH_SHORT;


                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();



                }


                else if (!resetpassword1.getText().toString().equals(resetpassword2.getText().toString()))
                {  
                    Context context = getApplicationContext();
                    CharSequence text = ":( Entered Passwords Do Not Match ! ):";
                    int duration = Toast.LENGTH_SHORT;


                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();

                }

                else if(status!=0) {

                    senotpreplace="";
                    for(int i=0;i<10;i++)
                    {
                        int pos=random.nextInt(pass.length);
                        Character ch=pass[pos];
                        senotpreplace+=ch.toString();

                    }

                    Context context = getApplicationContext();

                    AlertDialog percentage2 =  loadingDialog.startLoadingDialog();
                    TextView per2= percentage2.findViewById(R.id.progress_percentage);
                    per2.setText("50.00 %");

                    final ExecutorService executorServiceResetPass= Executors.newSingleThreadExecutor();
                    executorServiceResetPass.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {


                                String login_url="https://stalinism-noun.000webhostapp.com/resetpass.php";
                                //System.out.println("running-----------------------------------------------------------------------");
                                URL url=new URL(login_url);
                                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                httpURLConnection.setRequestMethod("POST");
                                httpURLConnection.setDoOutput(true);
                                httpURLConnection.setDoInput(true);
                                OutputStream outputStream=httpURLConnection.getOutputStream();
                                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                String post_data=
                                        URLEncoder.encode("user_otp","UTF-8")+"="+URLEncoder.encode(enteredotp.getText().toString().trim(),"UTF-8")+"&"+
                                                URLEncoder.encode("user_resetpass","UTF-8")+"="+URLEncoder.encode(resetpassword1.getText().toString().trim(),"UTF-8")+"&"+
                                                URLEncoder.encode("user_otp_replace","UTF-8")+"="+URLEncoder.encode(senotpreplace,"UTF-8")+"&"+
                                                URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(existingMail,"UTF-8");


                                bufferedWriter.write(post_data);
                                bufferedWriter.flush();
                                bufferedWriter.close();
                                outputStream.close();


                                InputStream inputStream = httpURLConnection.getInputStream();
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                                String line="";
                                int i=0;
                                while((line = bufferedReader.readLine())!= null) {
                                    resetpassresult[i]="";
                                    resetpassresult[i] += line;
                                    i++;
                                }
                                bufferedReader.close();
                                inputStream.close();
                                httpURLConnection.disconnect();

                            } catch (MalformedURLException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();

                            }





                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //System.out.println("result[0]=="+resetpassresult[0]+"  result[1]=="+resetpassresult[1]);
                                    if((resetpassresult[0]==null) || resetpassresult[0].equals("null"))
                                    {
                                        // //System.out.println("null Executing");
                                        CharSequence text = ":( Incorrect OTP ! ):";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);
                                        toast.show();



                                    }
                                    else if(resetpassresult[0].equals("same"))
                                    {
                                        // //System.out.println("null Executing");
                                        CharSequence text = ":( New Password Is Same As Old One , Please Use A Different New Password ! ):";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);
                                        toast.show();



                                    }
                                    else
                                    {
                                        CharSequence text = ":) Reset Successfull (:";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);
                                        toast.show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        finish();




                                    }
                                    per2.setText("100.00 %");
                                    loadingDialog.dismissDialog();
                                    //System.out.println("ResetPass Dialog Dismissed!");





                                }
                            });

                        }
                    });

                }
                else {
                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + ":( " + "Internet Connection Not Found !" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

                    toast.show();
                }





                }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this.getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }


    private void senEmail(Context context,String[] result,String user_email) {

        String mEmail = user_email;
        String mSubject ="This mail contains information about your MSI Library App Pass-:";
        String mMessage = "Your current password is: "+result[0]+"\nIf you wish to change your password, your OTP is: "+senpass+
                "\nDon't share this otp with anyone." +
                "\nNote -: Above otp is only valid once !";
        JavaMailAPI javaMailAPI = new JavaMailAPI(context, mEmail, mSubject, mMessage);
        javaMailAPI.execute();
        //System.out.println("Email Sent!");
    }

}
