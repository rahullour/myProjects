package com.example.librarymanagementsystemteachers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import static com.example.librarymanagementsystemteachers.Globals.status;
import static com.example.librarymanagementsystemteachers.Globals.user_id;
import static com.example.librarymanagementsystemteachers.Globals.user_pwd;
import static com.example.librarymanagementsystemteachers.Globals.loginresult;



public class MainActivity extends AppCompatActivity {
    Button forgotbtn,loginbtn;
   // Button registerbtn;
    EditText mainenrollment,mainpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] demo={"asdas","Asdsa"};
        loadingDialog=new LoadingDialog(MainActivity.this);

        //buttons
        forgotbtn= findViewById(R.id.forgotbutton);
        loginbtn = findViewById(R.id.loginbutton);
       // registerbtn =findViewById(R.id.registerbutton);

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
                    CharSequence text = ":( PLease Fill Remaining Fields! ):";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();


                }

                else if(mainpassword.getText().toString().length()<8)
                {

                    Context context = getApplicationContext();
                    CharSequence text = ":( Password Length Has To Be Atleast 8 Char! ):";
                    int duration = Toast.LENGTH_SHORT;

                    Toast  toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();

                }


                else if(status!=0){
                    loadingDialog.startLoadingDialog();
                    final Context[] context = {getApplicationContext()};
                    user_id = mainenrollment.getText().toString().trim();
                    user_pwd = mainpassword.getText().toString().trim();

                    final ExecutorService executorServiceLogin= Executors.newSingleThreadExecutor();
                    executorServiceLogin.execute(new Runnable() {
                        @Override
                        public void run() {



                            try {
                                String login_url="https://rahullour.thats.im/admin_login.php";
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

                                        CharSequence text = ":( Incorrect UserId/Password! ): ";
                                        int duration = Toast.LENGTH_SHORT;
                                        //System.out.println("result[0]==" + loginresult[0] + "result[1]==" + loginresult[1]);
                                        Toast toast = Toast.makeText(context[0], Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                                        toast.show();

                                    }
                                    else
                                    {

                                        Intent intent = new Intent(context[0], InsideActivity.class);
                                        startActivity(intent);
                                        //System.out.println("result[0]=="+loginresult[0]+"result[1]=="+ loginresult[1]);
                                        Globals.user_name=loginresult[0];
                                        Globals.user_course=loginresult[1];
                                        CharSequence text = loginresult[0];
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast    toast = Toast.makeText(context[0], Html.fromHtml("<font color='#FF0000' > <b>"+ ":) Welcome "  + text + " (:  </b> </font>"), duration);

                                        toast.show();

                                    }
                                    loadingDialog.dismissDialog();
                                    //System.out.println("Login Dialog Dismissed!");

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



//        registerbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
//                Context context = getApplicationContext();
//                Intent intent = new Intent(context,RegisterActivity.class);
//                startActivity(intent);
//
//            }
//        });
    }
}







