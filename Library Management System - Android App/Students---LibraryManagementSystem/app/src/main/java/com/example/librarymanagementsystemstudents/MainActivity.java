package com.example.librarymanagementsystemstudents;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.librarymanagementsystemstudents.Globals.loadingDialog;
import static com.example.librarymanagementsystemstudents.Globals.profilepicdownloaded;
import static com.example.librarymanagementsystemstudents.Globals.status;
import static com.example.librarymanagementsystemstudents.Globals.user_id;
import static com.example.librarymanagementsystemstudents.Globals.user_pwd;
import static com.example.librarymanagementsystemstudents.Globals.loginresult;



public class MainActivity extends AppCompatActivity {
    Button forgotbtn,loginbtn,registerbtn,visibilitybutton;
    EditText mainenrollment;
    TextInputEditText mainpassword;


    int show=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        profilepicdownloaded=0;
        loadingDialog=new LoadingDialog(MainActivity.this);

        final ExecutorService executorServiceGetCredentials = Executors.newSingleThreadExecutor();
        executorServiceGetCredentials.execute(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();


                Context context=getApplicationContext();
                SharedPreferences sharedPreferences = PreferenceManager
                        .getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                user_id=sharedPreferences.getString("mainenrollment", "null");
                user_pwd=sharedPreferences.getString("mainpassword", "null");



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {




                        if(!user_id.equals("null") && !user_pwd.equals("null"))
                        {  Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>"+ "Auto Login For User Id"+": "+user_id+" In Progress"+  "</b> </font>"), Toast.LENGTH_SHORT);

                            toast.show();
                          AlertDialog percentage1 =  loadingDialog.startLoadingDialog();
                        TextView per1= percentage1.findViewById(R.id.progress_percentage);
                        per1.setText("50.00 %");
                            NetworkUtil.setConnectivityStatus(getApplicationContext());

                           // System.out.println("inside bro");
                            if(status!=0){




                                final ExecutorService executorServiceStoredLogin= Executors.newSingleThreadExecutor();
                                executorServiceStoredLogin.execute(new Runnable() {
                                    @Override
                                    public void run() {
                                        Looper.prepare();

                                       // loadingDialog.startLoadingDialog();


                                        try {
                                            String login_url="https://stalinism-noun.000webhostapp.com/login.php";
                                            //System.out.println("running-----------------------------------------------------------------------");

                                            URL url=new URL(login_url);
                                            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                            httpURLConnection.setRequestMethod("POST");
                                            httpURLConnection.setDoOutput(true);
                                            httpURLConnection.setDoInput(true);
                                            OutputStream outputStream=httpURLConnection.getOutputStream();
                                            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                            String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                                                    URLEncoder.encode("user_pwd","UTF-8")+"="+URLEncoder.encode(user_pwd,"UTF-8");

                                            bufferedWriter.write(post_data);
                                            bufferedWriter.flush();
                                            bufferedWriter.close();
                                            outputStream.close();


                                            InputStream inputStream = httpURLConnection.getInputStream();
                                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                                            String line="";
                                            int i=0;
                                            while((line = bufferedReader.readLine())!= null) {
                                                loginresult[i]="";
                                                loginresult[i] += line;
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
                                        //System.out.println("login result:"+loginresult[0]);

                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                if((loginresult[0]==null) || loginresult[0].equals("null")) {

                                                    CharSequence text = ":( Incorrect UserId/Password ! ): ";
                                                    int duration = Toast.LENGTH_SHORT;
                                                    //System.out.println("result[0]==" + loginresult[0] + "result[1]==" + loginresult[1]);
                                                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                                                    toast.show();

                                                }
                                               else if(loginresult[0].equals("register")) {

                                                    CharSequence text = ":) Please Register ! (: ";
                                                    int duration = Toast.LENGTH_SHORT;
                                                    //System.out.println("result[0]==" + loginresult[0] + "result[1]==" + loginresult[1]);
                                                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                                                    toast.show();

                                                }
                                                else
                                                {   mainenrollment.setText("");
                                                    mainpassword.setText("");
                                                    Intent intent = new Intent(context, InsideActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                                    startActivity(intent);
                                                    finish();
                                                    //System.out.println("result[0]=="+loginresult[0]+"result[1]=="+ loginresult[1]);
                                                    Globals.user_name=loginresult[0];
                                                    Globals.user_course=loginresult[1];
                                                    CharSequence text = loginresult[0];
                                                    int duration = Toast.LENGTH_SHORT;

                                                    Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>"+ ":) Welcome "  + text + " (:  </b> </font>"), duration);

                                                    toast.show();
                                                    loadingDialog.dismissDialog();


                                                }
                                                per1.setText("100.00 %");
                                                loadingDialog.dismissDialog();
                                                //System.out.println("Login Dialog Dismissed!");

                                            }
                                        });

                                    }
                                });


                            }
                            else {
                                toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>"+ ":( " + "Internet Connection Not Found !" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

                                toast.show();


                            }




                        }











                    }
                });
            }




        });



        //buttons
        forgotbtn= findViewById(R.id.forgotbutton);
        loginbtn = findViewById(R.id.loginbutton);
        registerbtn =findViewById(R.id.registerbutton);

        mainenrollment=findViewById(R.id.mainenrollmentnofield);
        mainpassword=findViewById(R.id.mainpasswordfield);

          forgotbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);


                    Context context = getApplicationContext();
                    Intent intent = new Intent(context, ForgotActivity.class);
                    startActivity(intent);

    }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                Context context = getApplicationContext();
                Intent intent = new Intent(context, RegisterActivity.class);
                startActivity(intent);

            }
        });










        loginbtn.setOnClickListener(new View.OnClickListener() {
            Context context=getApplicationContext();


            @Override
            public void onClick(View v) {
                NetworkUtil.setConnectivityStatus(getApplicationContext());
                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);


                // if left blank
                if (mainenrollment.getText().toString().isEmpty() ||  mainpassword.getText().toString().isEmpty()) {



                    Context context = getApplicationContext();
                    CharSequence text = ":( PLease Fill Remaining Fields ! ):";
                    int duration = Toast.LENGTH_SHORT;
                   // System.out.println("Here");
                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();


                }

                else if(mainpassword.getText().toString().length()<8)
                {

                    Context context = getApplicationContext();
                    CharSequence text = ":( Password Length Has To Be Atleast 8 Char ! ):";
                    int duration = Toast.LENGTH_SHORT;

                   Toast  toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();

                }


                else if(status!=0){
                    AlertDialog percentage2 =  loadingDialog.startLoadingDialog();
                    TextView per2= percentage2.findViewById(R.id.progress_percentage);
                    per2.setText("50.00 %");
                    final Context[] context = {getApplicationContext()};
                    user_id = mainenrollment.getText().toString().trim();
                    user_pwd = mainpassword.getText().toString().trim();

                    final ExecutorService executorServiceLogin= Executors.newSingleThreadExecutor();
                    executorServiceLogin.execute(new Runnable() {
                        @Override
                        public void run() {



                            try {
                                String login_url="https://stalinism-noun.000webhostapp.com/login.php";
                                System.out.println("running-----------------------------------------------------------------------");

                                Log.d("Test","running");
                                URL url=new URL(login_url);
                                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                httpURLConnection.setRequestMethod("POST");
                                httpURLConnection.setDoOutput(true);
                                httpURLConnection.setDoInput(true);
                                OutputStream outputStream=httpURLConnection.getOutputStream();
                                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                                        URLEncoder.encode("user_pwd","UTF-8")+"="+URLEncoder.encode(user_pwd,"UTF-8");

                                bufferedWriter.write(post_data);
                                bufferedWriter.flush();
                                bufferedWriter.close();
                                outputStream.close();


                                InputStream inputStream = httpURLConnection.getInputStream();
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                                String line="";
                                int i=0;
                                while((line = bufferedReader.readLine())!= null) {
                                    loginresult[i]="";
                                    loginresult[i] += line;
                                    i++;
                                }
                               // System.out.println("login result:"+loginresult[0]);
                                bufferedReader.close();
                                inputStream.close();
                                httpURLConnection.disconnect();

                            } catch (MalformedURLException e) {
                                e.printStackTrace();

                            } catch (IOException e) {
                                e.printStackTrace();

                            }
                           // System.out.println("login result:"+loginresult[0]);

                       runOnUiThread(new Runnable() {
                           @Override
                           public void run() {

                               if((loginresult[0]==null) || loginresult[0].equals("null")) {

                                   CharSequence text = ":( Incorrect UserId/Password ! ): ";
                                   int duration = Toast.LENGTH_SHORT;
                                  // System.out.println("result[0]==" + loginresult[0] + "result[1]==" + loginresult[1]);
                                   Toast toast = Toast.makeText(context[0], Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                                   toast.show();

                               }
                               else if(loginresult[0].equals("register")) {

                                   CharSequence text = ":) Please Register ! (: ";
                                   int duration = Toast.LENGTH_SHORT;
                                  //System.out.println("result[0]==" + loginresult[0] + "result[1]==" + loginresult[1]);
                                   Toast toast = Toast.makeText(context[0], Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                                   toast.show();

                               }
                               else
                               {
                                   mainenrollment.setText("");
                                   mainpassword.setText("");
                                   Intent intent = new Intent(getApplicationContext(), InsideActivity.class);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                   startActivity(intent);
                                   finish();
                                  // System.out.println("result[0]=="+loginresult[0]+"result[1]=="+ loginresult[1]);
                                   Globals.user_name=loginresult[0];
                                   Globals.user_course=loginresult[1];
                                   CharSequence text = loginresult[0];
                                   int duration = Toast.LENGTH_SHORT;

                                   Toast    toast = Toast.makeText(context[0], Html.fromHtml("<font color='#FF0000' > <b>"+ ":) Welcome "  + text + " (:  </b> </font>"), duration);

                                   toast.show();
                                   user_id=loginresult[2];

                               }
                               per2.setText("100.00 %");
                               loadingDialog.dismissDialog();
                               //System.out.println("Login Dialog Dismissed!");

                           }
                       });

                        }
                    });


                }
                else {
                    Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>"+ ":( " + "Internet Connection Not Found !" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

                    toast.show();


                }



            }
        });



    }
    }







