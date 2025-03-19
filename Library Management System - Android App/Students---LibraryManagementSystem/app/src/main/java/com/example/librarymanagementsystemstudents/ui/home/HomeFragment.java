package com.example.librarymanagementsystemstudents.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanagementsystemstudents.HomeCustomAdapter;
import com.example.librarymanagementsystemstudents.Globals;
import com.example.librarymanagementsystemstudents.LoadingDialog;
import com.example.librarymanagementsystemstudents.NetworkUtil;
import com.example.librarymanagementsystemstudents.R;

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
import static com.example.librarymanagementsystemstudents.Globals.spinner_browse_pending_book_name;
import static com.example.librarymanagementsystemstudents.Globals.status;
import static com.example.librarymanagementsystemstudents.Globals.user_id;
import static com.example.librarymanagementsystemstudents.Globals.spinner_my_book_name;
import static com.example.librarymanagementsystemstudents.Globals.spinner_my_book_borrow_date;
import static com.example.librarymanagementsystemstudents.Globals.spinner_my_book_return_date;
import static com.example.librarymanagementsystemstudents.Globals.spinner_my_book_late_fee;
import static com.example.librarymanagementsystemstudents.Globals.spinner_my_book_yop;
import static com.example.librarymanagementsystemstudents.Globals.spinner_my_book_available_count;
import static com.example.librarymanagementsystemstudents.Globals.spinner_my_book_image;
import static com.example.librarymanagementsystemstudents.Globals.spinner_my_book_course;
import static com.example.librarymanagementsystemstudents.Globals.spinner_home_pending_book_name;
import static com.example.librarymanagementsystemstudents.Globals.per1;
import static com.example.librarymanagementsystemstudents.Globals.percentage1;


public class HomeFragment  extends Fragment implements AdapterView.OnItemSelectedListener {


    String[] spinner_my_return_button_book_name;
    int firstRenderComplete =0;

    RecyclerView RecyclerView1;

    public static String[] home_my_book_name = new String[100];
    public static String[] home_my_book_borrow_date = new String[100];
    public static String[] home_my_book_return_date = new String[100];
    public static String[] home_my_book_late_fee = new String[100];
    public static String[] home_my_book_yop = new String[100];
    public static String[] home_my_book_available_count = new String[100];
    public static String[] home_my_book_image = new String[100];
    public static String[] home_my_book_course =new String[100];
    public static String[] home_my_pending_book_name =new String[100];




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_books, container, false);
        RecyclerView1 = root.findViewById(R.id.recycleview1);




        NetworkUtil.setConnectivityStatus(getContext());

        if (status != 0) {


            if (profilepicdownloaded == 1) {
                loadingDialog = new LoadingDialog(getActivity());
                 percentage1 =  loadingDialog.startLoadingDialog();
                 per1= percentage1.findViewById(R.id.progress_percentage);


            }


           





            final ExecutorService executorServicehomeBookName = Executors.newSingleThreadExecutor();
            
            executorServicehomeBookName.execute(new Runnable() {
                @Override
                public void run() {

                    try {
                        String login_url = "https://stalinism-noun.000webhostapp.com/get_home_book_name.php";
                       // System.out.println("running-----------------------------------------------------------------------");

                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream=httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                       System.out.println("checking:"+user_id);
                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");


                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();



                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                        String line = "";
                        int i = 0;

                        while ((line = bufferedReader.readLine()) != null) {

                            home_my_book_name[i] = "";
                            home_my_book_name[i] += line;
                          //  System.out.println("home Book Name : " + i +home_my_book_name[i]);
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


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    per1.setText("10.00 %");
                            //System.out.println("Home Books Name Data Download Complete!");


                            final ExecutorService executorServiceBrowsePendingBookName= Executors.newSingleThreadExecutor();
                            executorServiceBrowsePendingBookName.execute(new Runnable() {
                                @Override
                                public void run() {
                                    home_my_pending_book_name=new String[100];
                                    try {
                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_pending_return_book_name.php";
                                        //System.out.println("running-----------------------------------------------------------------------");

                                        URL url = new URL(login_url);
                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoOutput(true);
                                        httpURLConnection.setDoInput(true);
                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");


                                        bufferedWriter.write(post_data);
                                        bufferedWriter.flush();
                                        bufferedWriter.close();
                                        outputStream.close();

                                        InputStream inputStream = httpURLConnection.getInputStream();
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                        String line = "";
                                        int i = 0;
                                        while ((line = bufferedReader.readLine()) != null) {
                                            home_my_pending_book_name[i] = "";
                                            home_my_pending_book_name[i] += line;
                                            //System.out.println("Pending Book :"+i+" "+home_my_pending_book_name[i]);
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

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            per1.setText("20.00 %");
                                            //System.out.println("Pending Return Data Update Complete!");



                                            final ExecutorService executorServicehomeBookCourse = Executors.newSingleThreadExecutor();
                                            executorServicehomeBookCourse.execute(new Runnable() {
                                                @Override
                                                public void run() {


                                                    try {
                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_home_course.php";
                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                        URL url = new URL(login_url);
                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                        httpURLConnection.setRequestMethod("POST");
                                                        httpURLConnection.setDoOutput(true);
                                                        httpURLConnection.setDoInput(true);
                                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");
                                                        bufferedWriter.write(post_data);
                                                        bufferedWriter.flush();
                                                        bufferedWriter.close();
                                                        outputStream.close();

                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                        String line = "";
                                                        int i = 0;
                                                        while ((line = bufferedReader.readLine()) != null) {

                                                            home_my_book_course[i] = "";
                                                            home_my_book_course[i] += line;
                                                            //System.out.println("home Book Course : " + i +home_my_book_name[i]);
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


                                                    getActivity().runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            per1.setText("30.00 %");
                                                            //System.out.println("Home Books Course Data Download Complete!");



                                                            final ExecutorService executorServicehomeBookBorrowDate = Executors.newSingleThreadExecutor();
                                                            executorServicehomeBookBorrowDate.execute(new Runnable() {
                                                                @Override
                                                                public void run() {


                                                                    try {
                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_home_book_borrow_date.php";
                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                        URL url = new URL(login_url);
                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                        httpURLConnection.setRequestMethod("POST");
                                                                        httpURLConnection.setDoOutput(true);
                                                                        httpURLConnection.setDoInput(true);
                                                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");
                                                                        bufferedWriter.write(post_data);
                                                                        bufferedWriter.flush();
                                                                        bufferedWriter.close();
                                                                        outputStream.close();

                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                        String line = "";
                                                                        int i = 0;
                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                            home_my_book_borrow_date[i] = "";
                                                                            home_my_book_borrow_date[i] += line;
                                                                              //System.out.println("home Book Author : " + i +home_my_book_borrow_date[i]);
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


                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                        @Override
                                                                        public void run() {

                                                                            per1.setText("40.00 %");
                                                                            //System.out.println("Home Books Borrow Date Data Download Complete!");

                                                                            final ExecutorService executorServicehomeReturnDate = Executors.newSingleThreadExecutor();
                                                                            executorServicehomeReturnDate.execute(new Runnable() {
                                                                                @Override
                                                                                public void run() {


                                                                                    try {
                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_home_book_return_date.php";
                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                        URL url = new URL(login_url);
                                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                        httpURLConnection.setDoOutput(true);
                                                                                        httpURLConnection.setDoInput(true);
                                                                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                                                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                                                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");
                                                                                        bufferedWriter.write(post_data);
                                                                                        bufferedWriter.flush();
                                                                                        bufferedWriter.close();
                                                                                        outputStream.close();


                                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                        String line = "";
                                                                                        int i = 0;
                                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                                            home_my_book_return_date[i] = "";
                                                                                            home_my_book_return_date[i] += line;
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


                                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                                        @Override
                                                                                        public void run() {

                                                                                            per1.setText("50.00 %");
                                                                                            //System.out.println("Home Books Return Date Data Download Complete!");


                                                                                            final ExecutorService executorServiceUpdateLateFee = Executors.newSingleThreadExecutor();
                                                                                            executorServiceUpdateLateFee.execute(new Runnable() {
                                                                                                @Override
                                                                                                public void run() {


                                                                                                    try {
                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/update_late_fee.php";
                                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                                        URL url=new URL(login_url);
                                                                                                        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                                        httpURLConnection.setDoOutput(false);
                                                                                                        httpURLConnection.setDoInput(false);


                                                                                                        httpURLConnection.disconnect();

                                                                                                    } catch (MalformedURLException e) {
                                                                                                        e.printStackTrace();

                                                                                                    } catch (IOException e) {
                                                                                                        e.printStackTrace();

                                                                                                    }



                                                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                                                        @Override
                                                                                                        public void run() {
                                                                                                            per1.setText("60.00 %");
                                                                                                            //System.out.println("Late Fee Data Update Complete !");


                                                                                                            final ExecutorService executorHomeBookLateFee = Executors.newSingleThreadExecutor();
                                                                                                            executorHomeBookLateFee.execute(new Runnable() {
                                                                                                                @Override
                                                                                                                public void run() {

                                                                                                        try {
                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_home_book_late_fee.php";
                                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                                        URL url = new URL(login_url);
                                                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                                        httpURLConnection.setDoOutput(true);
                                                                                                        httpURLConnection.setDoInput(true);
                                                                                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                                                                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                                                                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");
                                                                                                        bufferedWriter.write(post_data);
                                                                                                        bufferedWriter.flush();
                                                                                                        bufferedWriter.close();
                                                                                                        outputStream.close();

                                                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                                        String line = "";
                                                                                                        int i = 0;
                                                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                                                            home_my_book_late_fee[i] = "";
                                                                                                            home_my_book_late_fee[i] += line;
                                                                                                            i++;
                                                                                                            //System.out.println("Home Book Late Fee: "+i+":"+home_my_book_late_fee[i]);
                                                                                                        }
                                                                                                        bufferedReader.close();
                                                                                                        inputStream.close();
                                                                                                        httpURLConnection.disconnect();

                                                                                                    } catch (MalformedURLException e) {
                                                                                                        e.printStackTrace();

                                                                                                    } catch (IOException e) {
                                                                                                        e.printStackTrace();

                                                                                                    }


                                                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                                                        @Override
                                                                                                        public void run() {

                                                                                                            per1.setText("70.00 %");
                                                                                                            //System.out.println("Home Books Late Fee Data Download Complete!");


                                                                                                            final ExecutorService executorServicehomeYop = Executors.newSingleThreadExecutor();
                                                                                                            executorServicehomeYop.execute(new Runnable() {
                                                                                                                @Override
                                                                                                                public void run() {


                                                                                                                    try {
                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_home_yop.php";
                                                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                                                        URL url = new URL(login_url);
                                                                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                                                        httpURLConnection.setDoOutput(true);
                                                                                                                        httpURLConnection.setDoInput(true);
                                                                                                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                                                                                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                                                                                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");
                                                                                                                        bufferedWriter.write(post_data);
                                                                                                                        bufferedWriter.flush();
                                                                                                                        bufferedWriter.close();
                                                                                                                        outputStream.close();

                                                                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                                                        String line = "";
                                                                                                                        int i = 0;
                                                                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                                                                            home_my_book_yop[i] = "";
                                                                                                                            home_my_book_yop[i] += line;
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


                                                                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                                                                        @Override
                                                                                                                        public void run() {

                                                                                                                            per1.setText("80.00 %");
                                                                                                                            //System.out.println("Home Books Yop Data Download Complete!");

                                                                                                                            final ExecutorService executorServicehomeAvailableCount = Executors.newSingleThreadExecutor();
                                                                                                                            executorServicehomeAvailableCount.execute(new Runnable() {
                                                                                                                                @Override
                                                                                                                                public void run() {


                                                                                                                                    try {
                                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_home_available_count.php";
                                                                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                                                                        URL url = new URL(login_url);
                                                                                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                                                                        httpURLConnection.setDoOutput(true);
                                                                                                                                        httpURLConnection.setDoInput(true);
                                                                                                                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                                                                                                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                                                                                                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");
                                                                                                                                        bufferedWriter.write(post_data);
                                                                                                                                        bufferedWriter.flush();
                                                                                                                                        bufferedWriter.close();
                                                                                                                                        outputStream.close();

                                                                                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                                                                        String line = "";
                                                                                                                                        int i = 0;
                                                                                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                                                                                            home_my_book_available_count[i] = "";
                                                                                                                                            home_my_book_available_count[i] += line;
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


                                                                                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                                                                                        @Override
                                                                                                                                        public void run() {

                                                                                                                                            per1.setText("90.00 %");
                                                                                                                                            //System.out.println("Home Books Available Count Data Download Complete!");

                                                                                                                                            final ExecutorService executorServicehomeImages = Executors.newSingleThreadExecutor();
                                                                                                                                            executorServicehomeImages.execute(new Runnable() {
                                                                                                                                                @Override
                                                                                                                                                public void run() {


                                                                                                                                                    try {
                                                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_home_book_images.php";
                                                                                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                                                                                        URL url = new URL(login_url);
                                                                                                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                                                                                        httpURLConnection.setDoOutput(true);
                                                                                                                                                        httpURLConnection.setDoInput(true);
                                                                                                                                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                                                                                                                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                                                                                                                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");
                                                                                                                                                        bufferedWriter.write(post_data);
                                                                                                                                                        bufferedWriter.flush();
                                                                                                                                                        bufferedWriter.close();
                                                                                                                                                        outputStream.close();



                                                                                                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                                                                                        String line = "";
                                                                                                                                                        int i = 0;
                                                                                                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                                                                                                            home_my_book_image[i] = "";
                                                                                                                                                            home_my_book_image[i] += line;
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




                                                                                                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                                                                                                        @Override
                                                                                                                                                        public void run() {


                                                                                                                                                            int length = 0;
                                                                                                                                                            int i = 0;


                                                                                                                                                            if (home_my_book_name[0].equals("null")) {
                                                                                                                                                             //   System.out.println("length 0 set");
                                                                                                                                                                spinner_my_return_button_book_name = new String[0];
                                                                                                                                                            } else {
                                                                                                                                                                while (!(home_my_book_name[i] == null)) {
                                                                                                                                                                    length++;
                                                                                                                                                                    i++;
                                                                                                                                                                }
                                                                                                                                                                spinner_my_return_button_book_name = new String[length];

                                                                                                                                                                for (int p = 0; p < spinner_my_return_button_book_name.length; p++) {
                                                                                                                                                                    spinner_my_return_button_book_name[p] = home_my_book_name[p];
                                                                                                                                                                }

                                                                                                                                                                for (int p = 0; p < spinner_my_return_button_book_name.length; p++) {
                                                                                                                                                                    //System.out.println("Allocated Book Data In HomeFragment  :"+spinner_my_return_button_book_name[p]);
                                                                                                                                                                    //System.out.println();

                                                                                                                                                                }
                                                                                                                                                            }


                                                                                                                                                            length = 0;
                                                                                                                                                            i = 0;

                                                                                                                                                            if (home_my_pending_book_name[0].equals("null")) {
                                                                                                                                                            //    System.out.println("length 0 set");
                                                                                                                                                                spinner_home_pending_book_name = new String[0];
                                                                                                                                                            }
                                                                                                                                                            else {
                                                                                                                                                            while (!(home_my_pending_book_name[i] == null)) {
                                                                                                                                                                length++;
                                                                                                                                                                i++;
                                                                                                                                                            }
                                                                                                                                                            spinner_home_pending_book_name = new String[length];

                                                                                                                                                            for (int p = 0; p < spinner_home_pending_book_name.length; p++) {
                                                                                                                                                                spinner_home_pending_book_name[p] = home_my_pending_book_name[p];
                                                                                                                                                            }

                                                                                                                                                            for (int p = 0; p < spinner_home_pending_book_name.length; p++) {
                                                                                                                                                                //System.out.println("Home Pending Book Data :"+spinner_home_pending_book_name[p]);
                                                                                                                                                                //System.out.println();

                                                                                                                                                            }
                                                                                                                                                        }




                                                                                                                                                            if ((home_my_book_image[0] == null) || home_my_book_image[0].equals("null")) {

                                                                                                                                                                //System.out.println(":(Home Book Images Don't Exist! ):");

                                                                                                                                                            } else {
                                                                                                                                                                //System.out.println("Home Books Images Data Download Complete!");
                                                                                                                                                                RecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
                                                                                                                                                                   RecyclerView1.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                                                                                                                                                                HomeCustomAdapter ca = new HomeCustomAdapter(getActivity(),home_my_book_name, home_my_book_borrow_date,
                                                                                                                                                                        home_my_book_return_date, home_my_book_late_fee, home_my_book_yop, home_my_book_available_count, home_my_book_image,home_my_book_course);
                                                                                                                                                                RecyclerView1.setAdapter(ca);
                                                                                                                                                                firstRenderComplete=1;


                                                                                                                                                            }
                                                                                                                                                            per1.setText("100.00 %");
                                                                                                                                                            Globals.loadingDialog.dismissDialog();
                                                                                                                                                            //System.out.println("HomeFragment Dialog Dismissed!");


                                                                                                                                                        }
                                                                                                                                                    });

                                                                                                                                                }
                                                                                                                                            });



                                                                                                                                                }
                                                                                                                                            });

                                                                                                                                                }
                                                                                                                                            });



                                                                                                                                        }
                                                                                                                                    });

                                                                                                                                }
                                                                                                                            });


                                                                                                                        }
                                                                                                                    });

                                                                                                                }
                                                                                                            });


                                                                                                        }
                                                                                                    });

                                                                                                }
                                                                                            });



                                                                                        }
                                                                                    });

                                                                                }
                                                                            });






                                                                        }
                                                                    });

                                                                }
                                                            });



                                                        }
                                                    });




                                                }
                                            });








                                        }
                                    });

                                }
                            });




                        }
                    });

                }
            });



            String[] home_spinner = {"⇓   FIND BY COURSE   ⇓","BCA","BBA","B.COM","B.TECH","LLB","BSC"};
            Spinner spinner = root.findViewById(R.id.my_spinner);
            spinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
 //Create an ArrayAdapter using the string array and a default spinner layout

            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, home_spinner);
 //Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
 //Apply the adapter to the spinner
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);


        }
        else {
            Toast toast = Toast.makeText(getContext(), Html.fromHtml("<font color='#FF0000' > <b>" + ":( " + "Internet Connection Not Found !" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

            toast.show();
        }
        return root;

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(position==0)
        { if (firstRenderComplete==1)
        {

            HomeCustomAdapter ca = new HomeCustomAdapter(getActivity(),home_my_book_name, home_my_book_borrow_date,
                    home_my_book_return_date, home_my_book_late_fee, home_my_book_yop, home_my_book_available_count, home_my_book_image,home_my_book_course);
            RecyclerView1.setAdapter(ca);



        }

        }
        else if (position==1)
        {
            //System.out.println("Working In BCA");
               // loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerBCA= Executors.newSingleThreadExecutor();
            executorServiceSpinnerBCA.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<home_my_book_course.length;i++) {
                        spinner_my_book_name[i]= null;
                        spinner_my_book_borrow_date[i]= null;
                        spinner_my_book_available_count[i]= null;
                        spinner_my_book_late_fee[i]= null;
                        spinner_my_book_image[i]= null;
                        spinner_my_book_return_date[i]= null;
                        spinner_my_book_yop[i]= null;
                        spinner_my_book_course[i]= null;



                    }





                    int i=0;
                    for(int j=0;home_my_book_course[j]!=null;j++)
                    {

                        if (home_my_book_course[j].equals("BCA"))
                        {   spinner_my_book_name[i]=home_my_book_name[j];
                            spinner_my_book_course[i]=home_my_book_course[j];
                            spinner_my_book_borrow_date[i]=home_my_book_borrow_date[j];
                            spinner_my_book_available_count[i]=home_my_book_available_count[j];
                            spinner_my_book_return_date[i]=home_my_book_return_date[j];
                            spinner_my_book_image[i]=home_my_book_image[j];
                            spinner_my_book_late_fee[i]=home_my_book_late_fee[j];
                            spinner_my_book_yop[i]=home_my_book_yop[j];
                            i++;

                            //System.out.println("BCA BOOK RETURN DATE:"+spinner_my_book_return_date[i]);

                        }


                    }

                    for( i=0;i<spinner_my_book_name.length;i++)
                    {
                        //System.out.println("In Spinner Course: "+spinner_my_book_name[i]+spinner_my_book_name.length);

                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            HomeCustomAdapter ca = new HomeCustomAdapter(getActivity(),spinner_my_book_name, spinner_my_book_borrow_date,
                                    spinner_my_book_return_date, spinner_my_book_late_fee, spinner_my_book_yop, spinner_my_book_available_count, spinner_my_book_image,spinner_my_book_course);
                            RecyclerView1.setAdapter(ca);

                        }
                    });

                }
            });


        }

        else if(position==2)
        {  //System.out.println("Working In BBA");
            //    loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerBBA= Executors.newSingleThreadExecutor();
            executorServiceSpinnerBBA.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<home_my_book_course.length;i++) {

                        spinner_my_book_name[i]= null;
                        spinner_my_book_borrow_date[i]= null;
                        spinner_my_book_available_count[i]= null;
                        spinner_my_book_late_fee[i]= null;
                        spinner_my_book_image[i]= null;
                        spinner_my_book_return_date[i]= null;
                        spinner_my_book_yop[i]= null;
                        spinner_my_book_course[i]=null;
                    }
                    int i=0;

                    for(int j=0;home_my_book_course[j]!=null;j++)
                    {

                        if (home_my_book_course[j].equals("BBA"))
                        {   spinner_my_book_name[i]=home_my_book_name[j];
                            spinner_my_book_course[i]=home_my_book_course[j];
                            spinner_my_book_borrow_date[i]=home_my_book_borrow_date[j];
                            spinner_my_book_available_count[i]=home_my_book_available_count[j];
                            spinner_my_book_return_date[i]=home_my_book_return_date[j];
                            spinner_my_book_image[i]=home_my_book_image[j];
                            spinner_my_book_late_fee[i]=home_my_book_late_fee[j];
                            spinner_my_book_yop[i]=home_my_book_yop[j];
                            i++;

                            //System.out.println("BBA BOOK NAME:"+spinner_my_book_name[i]);

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            HomeCustomAdapter ca = new HomeCustomAdapter(getActivity(),spinner_my_book_name, spinner_my_book_borrow_date,
                                    spinner_my_book_return_date, spinner_my_book_late_fee, spinner_my_book_yop, spinner_my_book_available_count, spinner_my_book_image,spinner_my_book_course);
                            RecyclerView1.setAdapter(ca);


                        }
                    });

                }
            });




        }
        else if(position==3)
        {  //System.out.println("Working In B.COM");
             //   loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerBCom= Executors.newSingleThreadExecutor();
            executorServiceSpinnerBCom.execute(new Runnable() {
                @Override
                public void run() {

                    for(int i=0;i<home_my_book_course.length;i++) {
                        spinner_my_book_name[i]= null;
                        spinner_my_book_borrow_date[i]= null;
                        spinner_my_book_available_count[i]= null;
                        spinner_my_book_late_fee[i]= null;
                        spinner_my_book_image[i]= null;
                        spinner_my_book_return_date[i]= null;
                        spinner_my_book_yop[i]= null;
                        spinner_my_book_course[i]=null;
                    }
                    int i=0;

                    for(int j=0;home_my_book_course[j]!=null;j++)
                    {


                        if (home_my_book_course[j].equals("B.COM"))
                        {   spinner_my_book_name[i]=home_my_book_name[j];
                            spinner_my_book_course[i]=home_my_book_course[j];
                            spinner_my_book_borrow_date[i]=home_my_book_borrow_date[j];
                            spinner_my_book_available_count[i]=home_my_book_available_count[j];
                            spinner_my_book_return_date[i]=home_my_book_return_date[j];
                            spinner_my_book_image[i]=home_my_book_image[j];
                            spinner_my_book_late_fee[i]=home_my_book_late_fee[j];
                            spinner_my_book_yop[i]=home_my_book_yop[j];
                            i++;

                            //System.out.println("B.COM BOOK NAME:"+spinner_my_book_name[i]);

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            HomeCustomAdapter ca = new HomeCustomAdapter(getActivity(),spinner_my_book_name, spinner_my_book_borrow_date,
                                    spinner_my_book_return_date, spinner_my_book_late_fee, spinner_my_book_yop, spinner_my_book_available_count, spinner_my_book_image,spinner_my_book_course);
                            RecyclerView1.setAdapter(ca);

                        }
                    });

                }
            });



        }
        else if(position==4)
        {  //System.out.println("Working In B.TECH");
              //  loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerBTech= Executors.newSingleThreadExecutor();
            executorServiceSpinnerBTech.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<home_my_book_course.length;i++) {
                        spinner_my_book_name[i]= null;
                        spinner_my_book_borrow_date[i]= null;
                        spinner_my_book_available_count[i]= null;
                        spinner_my_book_late_fee[i]= null;
                        spinner_my_book_image[i]= null;
                        spinner_my_book_return_date[i]= null;
                        spinner_my_book_yop[i]= null;
                        spinner_my_book_course[i]=null;
                    }

                    int i=0;

                    for(int j=0;home_my_book_course[j]!=null;j++)
                    {


                        if (home_my_book_course[j].equals("B.TECH"))
                        {   spinner_my_book_name[i]=home_my_book_name[j];
                            spinner_my_book_course[i]=home_my_book_course[j];
                            spinner_my_book_borrow_date[i]=home_my_book_borrow_date[j];
                            spinner_my_book_available_count[i]=home_my_book_available_count[j];
                            spinner_my_book_return_date[i]=home_my_book_return_date[j];
                            spinner_my_book_image[i]=home_my_book_image[j];
                            spinner_my_book_late_fee[i]=home_my_book_late_fee[j];
                            spinner_my_book_yop[i]=home_my_book_yop[j];
                            i++;

                            //System.out.println("B.TECH BOOK NAME:"+spinner_my_book_name[i]);


                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            HomeCustomAdapter ca = new HomeCustomAdapter(getActivity(),spinner_my_book_name, spinner_my_book_borrow_date,
                                    spinner_my_book_return_date, spinner_my_book_late_fee, spinner_my_book_yop, spinner_my_book_available_count, spinner_my_book_image,spinner_my_book_course);
                            RecyclerView1.setAdapter(ca);

                        }
                    });

                }
            });



        }
        else if(position==5)
        {  //System.out.println("Working In LLB");
              //  loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerLLB= Executors.newSingleThreadExecutor();
            executorServiceSpinnerLLB.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<home_my_book_course.length;i++) {
                        spinner_my_book_name[i]= null;
                        spinner_my_book_borrow_date[i]= null;
                        spinner_my_book_available_count[i]= null;
                        spinner_my_book_late_fee[i]= null;
                        spinner_my_book_image[i]= null;
                        spinner_my_book_return_date[i]= null;
                        spinner_my_book_yop[i]= null;
                        spinner_my_book_course[i]=null;
                    }

                    int i=0;

                    for(int j=0;home_my_book_course[j]!=null;j++)
                    {

                        if (home_my_book_course[j].equals("LLB"))
                        {   spinner_my_book_name[i]=home_my_book_name[j];
                            spinner_my_book_course[i]=home_my_book_course[j];
                            spinner_my_book_borrow_date[i]=home_my_book_borrow_date[j];
                            spinner_my_book_available_count[i]=home_my_book_available_count[j];
                            spinner_my_book_return_date[i]=home_my_book_return_date[j];
                            spinner_my_book_image[i]=home_my_book_image[j];
                            spinner_my_book_late_fee[i]=home_my_book_late_fee[j];
                            spinner_my_book_yop[i]=home_my_book_yop[j];
                            i++;

                            //System.out.println("LLB BOOK NAME:"+spinner_my_book_name[i]);
                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            HomeCustomAdapter ca = new HomeCustomAdapter(getActivity(),spinner_my_book_name, spinner_my_book_borrow_date,
                                    spinner_my_book_return_date, spinner_my_book_late_fee, spinner_my_book_yop, spinner_my_book_available_count, spinner_my_book_image,spinner_my_book_course);
                            RecyclerView1.setAdapter(ca);


                        }
                    });

                }
            });





        }

        else if(position==6)
        {  //System.out.println("Working In BSC");
              //  loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerBSC= Executors.newSingleThreadExecutor();
            executorServiceSpinnerBSC.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<home_my_book_course.length;i++) {
                        spinner_my_book_name[i]= null;
                        spinner_my_book_borrow_date[i]= null;
                        spinner_my_book_available_count[i]= null;
                        spinner_my_book_late_fee[i]= null;
                        spinner_my_book_image[i]= null;
                        spinner_my_book_return_date[i]= null;
                        spinner_my_book_yop[i]= null;
                        spinner_my_book_course[i]=null;
                    }

                    int i=0;

                    for(int j=0;home_my_book_course[j]!=null;j++)
                    {

                        if (home_my_book_course[j].equals("BSC"))
                        {   spinner_my_book_name[i]=home_my_book_name[j];
                            spinner_my_book_course[i]=home_my_book_course[j];
                            spinner_my_book_borrow_date[i]=home_my_book_borrow_date[j];
                            spinner_my_book_available_count[i]=home_my_book_available_count[j];
                            spinner_my_book_return_date[i]=home_my_book_return_date[j];
                            spinner_my_book_image[i]=home_my_book_image[j];
                            spinner_my_book_late_fee[i]=home_my_book_late_fee[j];
                            spinner_my_book_yop[i]=home_my_book_yop[j];
                            i++;

                            //System.out.println("BSC BOOK NAME:"+spinner_my_book_name[i]);
                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            HomeCustomAdapter ca = new HomeCustomAdapter(getActivity(),spinner_my_book_name, spinner_my_book_borrow_date,
                                    spinner_my_book_return_date, spinner_my_book_late_fee, spinner_my_book_yop, spinner_my_book_available_count, spinner_my_book_image,spinner_my_book_course);
                            RecyclerView1.setAdapter(ca);


                        }
                    });

                }
            });





        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}

