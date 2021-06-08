package com.example.librarymanagementsystemteachers;

import android.content.Context;
import android.content.Intent;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.librarymanagementsystemteachers.Globals.loadingDialog;
import static com.example.librarymanagementsystemteachers.Globals.signupresult;
import static com.example.librarymanagementsystemteachers.Globals.status;
import static com.example.librarymanagementsystemteachers.Globals.user_id;
import static com.example.librarymanagementsystemteachers.Globals.user_pwd;
import static com.example.librarymanagementsystemteachers.Globals.user_name;
import static com.example.librarymanagementsystemteachers.Globals.user_email;
import static com.example.librarymanagementsystemteachers.Globals.user_course;


public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        loadingDialog=new LoadingDialog(RegisterActivity.this);



        Button signupbtn;

        EditText registername,registerenrollmentno,registerpassword1,registerpassword2,registeremail,registercourse;
        //buttons

        signupbtn = findViewById(R.id.signupbutton);

        //textfields

        registername =findViewById(R.id.registernamefield);
        registerenrollmentno = findViewById(R.id.registerenrollmentnofield);
        registerpassword1 = findViewById(R.id.registerpasswordfield1);
        registerpassword2 = findViewById(R.id.registerpasswordfield2);
        registeremail =  findViewById(R.id.registeremailfield);
        registercourse =  findViewById(R.id.registercoursefield);


        signupbtn.setOnClickListener(new View.OnClickListener() {

            Context context=getApplicationContext();
            @Override
            public void onClick(View v) {
                NetworkUtil.setConnectivityStatus(context);
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                if (registername.getText().toString().isEmpty() ||  registerenrollmentno.getText().toString().isEmpty() || registerpassword1.getText().toString().isEmpty() || registerpassword2.getText().toString().isEmpty() ||  registeremail.getText().toString().isEmpty() ||  registercourse.getText().toString().isEmpty() ) {


                    Context context = getApplicationContext();
                    CharSequence text = ":( PLease Fill Remaining Fields! ):";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();

                }

                else if(registerpassword1.getText().toString().length() < 8 && registerpassword2.getText().toString().length() < 8 )
                {
                    Context context = getApplicationContext();
                    CharSequence text = ":( Password Length Cannot Be Less Than 8 Char! ):";
                    int duration = Toast.LENGTH_SHORT;


                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' ><b>" + text + "</b></font>"), duration);

                    toast.show();



                }


                else if (!registerpassword1.getText().toString().equals(registerpassword2.getText().toString()))
                {
                    Context context = getApplicationContext();
                    CharSequence text = ":( Entered Passwords Do Not Match! ):";
                    int duration = Toast.LENGTH_SHORT;


                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' ><b>" + text + "</b></font>"), duration);

                    toast.show();

                }


                else if(status!=0){
                    loadingDialog.startLoadingDialog();

                    final Context[] context = {getApplicationContext()};
                    user_id = registerenrollmentno.getText().toString().trim();
                    user_pwd = registerpassword1.getText().toString().trim();
                    user_email = registeremail.getText().toString().trim();
                    user_name = registername.getText().toString().trim();
                    user_course=registercourse.getText().toString().trim().toUpperCase();

                    final ExecutorService executorServiceSignUp= Executors.newSingleThreadExecutor();
                    executorServiceSignUp.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {

                                String login_url="https://rahullour.thats.im/signup.php";
                                //System.out.println("running-----------------------------------------------------------------------");
                                URL url=new URL(login_url);
                                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                httpURLConnection.setRequestMethod("POST");
                                httpURLConnection.setDoOutput(true);
                                httpURLConnection.setDoInput(true);
                                OutputStream outputStream=httpURLConnection.getOutputStream();
                                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                String post_data=
                                        URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"+
                                                URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                                                URLEncoder.encode("user_pwd","UTF-8")+"="+URLEncoder.encode(user_pwd,"UTF-8")+"&"+
                                                URLEncoder.encode("user_course","UTF-8")+"="+URLEncoder.encode(user_course,"UTF-8")+"&"+
                                                URLEncoder.encode("user_email","UTF-8")+"="+URLEncoder.encode(user_email,"UTF-8");


                                bufferedWriter.write(post_data);
                                bufferedWriter.flush();
                                bufferedWriter.close();
                                outputStream.close();


                                InputStream inputStream = httpURLConnection.getInputStream();
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                                String line="";
                                int i=0;
                                while((line = bufferedReader.readLine())!= null) {
                                    signupresult[i]="";
                                    signupresult[i] += line;
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


                                    if((signupresult[0]==null) || signupresult[0].equals("null")) {
                                        CharSequence text = ":( SignUp Failed! Please Check Your Id/Email ):";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast    toast = Toast.makeText(context[0], Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                                        toast.show();

                                    } else {
                                        //System.out.println("signupresult[0]=="+signupresult[0]+"signupresult[1]=="+signupresult[1]);
                                        CharSequence text = signupresult[0];
                                        int duration = Toast.LENGTH_SHORT;
                                        Toast    toast = Toast.makeText(context[0], Html.fromHtml("<font color='#FF0000' > <b>"+ ":) Welcome "  + text + "(: </b> </font>"), duration);

                                        toast.show();
                                        context[0] = getApplicationContext();
                                        Intent intent = new Intent(context[0], InsideActivity.class);
                                        startActivity(intent);



                                    }
                                    Globals.loadingDialog.dismissDialog();
                                    //System.out.println("Signup Dialog Dismissed!");


                                }
                            });

                        }
                    });

                }
                else {
                    Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>"+ ":( " + "Internet Connection Not Found!" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

                    toast.show();


                }

            }
        });


    }



}
