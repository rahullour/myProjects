package com.example.librarymanagementsystemstudents;

import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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


    public char[] pass = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    public static Random random=new Random();




    Button sendlinkbtn,resetpasswordbtn;
    EditText emailreset,enteredotp,resetpassword1,resetpassword2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
         Context context=getApplicationContext();


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
                    
                    CharSequence text = ":( Enter Email! ):";
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

                    loadingDialog.startLoadingDialog();

                    final ExecutorService executorServiceSendLink= Executors.newSingleThreadExecutor();
                    executorServiceSendLink.execute(new Runnable() {
                        @Override
                        public void run() {

                            try {

                                String login_url="https://rahullour.thats.im/sendlink.php";
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
                                        CharSequence text = ":( Please Register! ):";
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



                                    }

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
                    Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>"+ ":( " + "Internet Connection Not Found!" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

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
                    CharSequence text = ":( Please Fill Remaining Fields! ):";
                    int duration = Toast.LENGTH_SHORT;


                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();

                }

                else if(resetpassword1.getText().toString().length() < 8 && resetpassword2.getText().toString().length() <8)
                {   
                    Context context = getApplicationContext();
                    CharSequence text = ":( Password Length Cannot Be Less Than 8 Char! ):";
                    int duration = Toast.LENGTH_SHORT;


                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();



                }


                else if (!resetpassword1.getText().toString().equals(resetpassword2.getText().toString()))
                {  
                    Context context = getApplicationContext();
                    CharSequence text = ":( Entered Passwords Do Not Match! ):";
                    int duration = Toast.LENGTH_SHORT;


                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();

                }

                else if(status!=0) {

                    
                    Context context = getApplicationContext();

                    loadingDialog.startLoadingDialog();

                    final ExecutorService executorServiceResetPass= Executors.newSingleThreadExecutor();
                    executorServiceResetPass.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {


                                String login_url="https://rahullour.thats.im/resetpass.php";
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
                                                URLEncoder.encode("user_resetpass","UTF-8")+"="+URLEncoder.encode(resetpassword1.getText().toString().trim(),"UTF-8");


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
                                        CharSequence text = ":( Incorrect OTP! ):";
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




                                    }

                                    loadingDialog.dismissDialog();
                                    //System.out.println("ResetPass Dialog Dismissed!");





                                }
                            });

                        }
                    });

                }
                else {
                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + ":( " + "Internet Connection Not Found!" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

                    toast.show();
                }





                }
        });


    }
    private void senEmail(Context context,String[] result,String user_email) {

        String mEmail = user_email;
        String mSubject ="This mail contains information about your MSI Library App Pass-:";
        String mMessage = "Your old password is:"+result[0]+"\n If you wish to change your password,your OTP is:"+senpass+
                "\n Don't share this otp with anyone.";
        JavaMailAPI javaMailAPI = new JavaMailAPI(context, mEmail, mSubject, mMessage);
        javaMailAPI.execute();
        //System.out.println("Email Sent!");
    }

}