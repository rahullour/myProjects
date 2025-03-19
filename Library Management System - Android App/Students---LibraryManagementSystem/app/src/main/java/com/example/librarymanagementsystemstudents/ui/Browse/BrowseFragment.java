package com.example.librarymanagementsystemstudents.ui.Browse;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanagementsystemstudents.BrowseCustomAdapter;
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
import static com.example.librarymanagementsystemstudents.Globals.status;
import static com.example.librarymanagementsystemstudents.Globals.user_id;
import static com.example.librarymanagementsystemstudents.Globals.spinner_browse_get_button_book_name;
import static com.example.librarymanagementsystemstudents.Globals.spinner_browse_pending_book_name;
import static com.example.librarymanagementsystemstudents.Globals.browse_available_count;


public class BrowseFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    String[] browse_book_name=new String[100];
    String[] browse_author=new String[100];
    String[] browse_publisher=new String[100];
    String[] browse_course=new String[100];
    String[] browse_yop=new String[100];
    String[] browse_image=new String[100];
    String[] browse_get_button_book_name=new String[100];
    String[] browse_pending_book_name=new String[100];



    String[]  spinner_browse_book_name=new String[100];
    String[]  spinner_browse_author=new String[100];
    String[]  spinner_browse_publisher=new String[100];
    String[]  spinner_browse_course=new String[100];
    String[]  spinner_browse_yop=new String[100];
    String[]  spinner_browse_available_count=new String[100];
    String[]  spinner_browse_image=new String[100];

    int firstRenderComplete =0;

    RecyclerView RecyclerView2;
    Spinner spinner;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_browse_books, container, false);
        RecyclerView2=root.findViewById(R.id.recycleview2);
        spinner =root.findViewById(R.id.course_spinner);



        Button browse_button=root.findViewById(R.id.browse_button);
        EditText browse_book_by_name=root.findViewById(R.id.browse_book_by_name);

        browse_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);

                String SearchNameText = browse_book_by_name.getText().toString().trim().toUpperCase();
                browse_book_by_name.setText("");

                if (SearchNameText.isEmpty()) {
                    spinner.setSelection(0);
                    BrowseCustomAdapter ca = new BrowseCustomAdapter(getActivity(),browse_book_name,browse_author,
                            browse_publisher, browse_course,browse_yop,browse_available_count,browse_image);
                    RecyclerView2.setAdapter(ca);

                } else if (android.text.TextUtils.isDigitsOnly(SearchNameText)) {
                    CharSequence text = ":( Please Enter An Alphabetic Value ! ):";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getContext(), Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();

                } else {
                    final Boolean[] found = {false};
                    final ExecutorService executorServiceSearchButton = Executors.newSingleThreadExecutor();
                    executorServiceSearchButton.execute(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < browse_book_name.length; i++) {
                                spinner_browse_book_name[i] = null;
                                spinner_browse_course[i] =null;
                                spinner_browse_publisher[i] =null;
                                spinner_browse_author[i] = null;
                                spinner_browse_available_count[i] = null;
                                spinner_browse_image[i] = null;
                                spinner_browse_yop[i] = null;


                            }


                            int i = 0;
                            for (int j = 0; browse_book_name[j] != null; j++) {

                                  if (browse_book_name[j].toUpperCase().contains(SearchNameText)) {
                                    spinner_browse_book_name[i] = browse_book_name[j];
                                    spinner_browse_course[i] = browse_course[j];
                                    spinner_browse_publisher[i] = browse_publisher[j];
                                    spinner_browse_author[i] = browse_author[j];
                                    spinner_browse_available_count[i] = browse_available_count[j];
                                    spinner_browse_image[i] = browse_image[j];
                                    spinner_browse_yop[i] = browse_yop[j];
                                    i++;



                                    //System.out.println("Search Button Student Id::" + allotted_student_id[i]);
                                    found[0] =true;
                                }


                            }

                            for (i = 0; i < browse_book_name.length; i++) {
                                //System.out.println("In Allotted Search Button: " + allotted_book_name[i] + allotted_book_name.length);

                            }


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(found[0] ==true)
                                    {

                                        BrowseCustomAdapter ca = new BrowseCustomAdapter(getActivity(),spinner_browse_book_name,spinner_browse_author,
                                                spinner_browse_publisher, spinner_browse_course,spinner_browse_yop,spinner_browse_available_count,spinner_browse_image);
                                        RecyclerView2.setAdapter(ca);
                                    }
                                    else {
                                        CharSequence text = ":( "+SearchNameText+" Book Not Found ! ):";
                                        int duration = Toast.LENGTH_SHORT;

                                        Toast toast = Toast.makeText(getContext(), Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                                        toast.show();

                                    }




                                }
                            });

                        }
                    });


                }
            }

        });
        
        
        
        NetworkUtil.setConnectivityStatus(getContext());
        if (status != 0) {
        loadingDialog=new LoadingDialog(getActivity());
       // //System.out.println("==================="+loadingDialog.isCancelled());
            AlertDialog percentage1 =  loadingDialog.startLoadingDialog();
            TextView per1= percentage1.findViewById(R.id.progress_percentage);


            browse_available_count=new String[100];

        final ExecutorService executorServiceBrowseBookName= Executors.newSingleThreadExecutor();
        executorServiceBrowseBookName.execute(new Runnable() {
            @Override
            public void run() {


                try {
                    String login_url = "https://stalinism-noun.000webhostapp.com/get_browse_book_name.php";
                    //System.out.println("running-----------------------------------------------------------------------");

                    URL url = new URL(login_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                    String line = "";
                    int i = 0;
                    while ((line = bufferedReader.readLine()) != null) {

                        browse_book_name[i] = "";
                        browse_book_name[i] += line;
                        //System.out.println("Browse Book Name : " + i +browse_book_name[i]);
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

                        per1.setText("11.11 %");
                        //System.out.println("Browse Books Name Data Download Complete!");

                        final ExecutorService executorServiceBrowseAuthor= Executors.newSingleThreadExecutor();
                        executorServiceBrowseAuthor.execute(new Runnable() {
                            @Override
                            public void run() {




                                try {
                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_browse_author.php";
                                    //System.out.println("running-----------------------------------------------------------------------");

                                    URL url = new URL(login_url);
                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                    httpURLConnection.setRequestMethod("POST");
                                    httpURLConnection.setDoOutput(true);
                                    httpURLConnection.setDoInput(true);

                                    InputStream inputStream = httpURLConnection.getInputStream();
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                    String line = "";
                                    int i = 0;
                                    while ((line = bufferedReader.readLine()) != null) {
                                        browse_author[i] = "";
                                        browse_author[i] += line;
                                        //  //System.out.println("Browse Book Author : " + i +browse_author[i]);
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

                                        per1.setText("22.22 %");
                                        //System.out.println("Browse Books Author Data Download Complete!");

                                        final ExecutorService executorServiceBrowsePublisher= Executors.newSingleThreadExecutor();
                                        executorServiceBrowsePublisher.execute(new Runnable() {
                                            @Override
                                            public void run() {




                                                try {
                                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_browse_publisher.php";
                                                    //System.out.println("running-----------------------------------------------------------------------");

                                                    URL url = new URL(login_url);
                                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                    httpURLConnection.setRequestMethod("POST");
                                                    httpURLConnection.setDoOutput(true);
                                                    httpURLConnection.setDoInput(true);


                                                    InputStream inputStream = httpURLConnection.getInputStream();
                                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                    String line = "";
                                                    int i = 0;
                                                    while ((line = bufferedReader.readLine()) != null) {
                                                        browse_publisher[i] = "";
                                                        browse_publisher[i] += line;
                                                        //System.out.println("browse_publisher at"+i+" :"+browse_publisher[i]);
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

                                                        per1.setText("33.33 %");
                                                        //System.out.println("Browse Books Publisher Data Download Complete!");


                                                        final ExecutorService executorServiceBrowseCourse= Executors.newSingleThreadExecutor();
                                                        executorServiceBrowseCourse.execute(new Runnable() {
                                                            @Override
                                                            public void run() {


                                                                try {
                                                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_browse_course.php";
                                                                    //System.out.println("running-----------------------------------------------------------------------");

                                                                    URL url = new URL(login_url);
                                                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                    httpURLConnection.setRequestMethod("POST");
                                                                    httpURLConnection.setDoOutput(true);
                                                                    httpURLConnection.setDoInput(true);

                                                                    InputStream inputStream = httpURLConnection.getInputStream();
                                                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                    String line = "";
                                                                    int i = 0;
                                                                    while ((line = bufferedReader.readLine()) != null) {
                                                                        browse_course[i] = "";
                                                                        browse_course[i] += line;
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

                                                                        per1.setText("44.44 %");
                                                                        //System.out.println("Browse Books Course Data Download Complete!");


                                                                        final ExecutorService executorServiceBrowseYop= Executors.newSingleThreadExecutor();
                                                                        executorServiceBrowseYop.execute(new Runnable() {
                                                                            @Override
                                                                            public void run() {


                                                                                try {
                                                                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_browse_yop.php";
                                                                                    //System.out.println("running-----------------------------------------------------------------------");

                                                                                    URL url = new URL(login_url);
                                                                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                    httpURLConnection.setRequestMethod("POST");
                                                                                    httpURLConnection.setDoOutput(true);
                                                                                    httpURLConnection.setDoInput(true);

                                                                                    InputStream inputStream = httpURLConnection.getInputStream();
                                                                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                    String line = "";
                                                                                    int i = 0;
                                                                                    while ((line = bufferedReader.readLine()) != null) {
                                                                                        browse_yop[i] = "";
                                                                                        browse_yop[i] += line;
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

                                                                                        per1.setText("55.55 %");
                                                                                        //System.out.println("Browse Books Yop Data Download Complete!");

                                                                                        final ExecutorService executorServiceBrowsePendingBookName= Executors.newSingleThreadExecutor();
                                                                                        executorServiceBrowsePendingBookName.execute(new Runnable() {
                                                                                            @Override
                                                                                            public void run() {

                                                                                                try {
                                                                                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_pending_book_name.php";
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
                                                                                                        browse_pending_book_name[i] = "";
                                                                                                        browse_pending_book_name[i] += line;
                                                                                                      //  System.out.println("Pending Book :"+i+" "+browse_pending_book_name[i]);
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

                                                                                                        per1.setText("66.66 %");
                                                                                                        //System.out.println("Browse Pending Books Data Download Complete!");

                                                                                                        final ExecutorService executorServiceBrowseAvailableCount= Executors.newSingleThreadExecutor();
                                                                                                        executorServiceBrowseAvailableCount.execute(new Runnable() {
                                                                                                            @Override
                                                                                                            public void run() {


                                                                                                                try {
                                                                                                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_browse_available_count.php";
                                                                                                                    //System.out.println("running-----------------------------------------------------------------------");

                                                                                                                    URL url = new URL(login_url);
                                                                                                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                                                    httpURLConnection.setRequestMethod("POST");
                                                                                                                    httpURLConnection.setDoOutput(true);
                                                                                                                    httpURLConnection.setDoInput(true);

                                                                                                                    InputStream inputStream = httpURLConnection.getInputStream();
                                                                                                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                                                    String line = "";
                                                                                                                    int i = 0;
                                                                                                                    while ((line = bufferedReader.readLine()) != null) {
                                                                                                                        browse_available_count[i] = "";
                                                                                                                        browse_available_count[i] += line;
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

                                                                                                                        per1.setText("77.77 %");
                                                                                                                        //System.out.println("Browse Books Available Count Data Download Complete!");


                                                                                                                        final ExecutorService executorServiceBrowseGetButtonBookName= Executors.newSingleThreadExecutor();
                                                                                                                        executorServiceBrowseGetButtonBookName.execute(new Runnable() {
                                                                                                                            @Override
                                                                                                                            public void run() {


                                                                                                                                try {
                                                                                                                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_browse_get_button_book_name.php";
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
                                                                                                                                        browse_get_button_book_name[i] = "";
                                                                                                                                        browse_get_button_book_name[i] += line;
                                                                                                                                        //System.out.println(i+": browse_get_button_book_name server data:"+browse_get_button_book_name[i]);
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

                                                                                                                                        per1.setText("88.88 %");
                                                                                                                                        //System.out.println("Browse Books Get Button Book Name Data Download Complete!");

                                                                                                                                        final ExecutorService executorServiceBrowseImages= Executors.newSingleThreadExecutor();
                                                                                                                                        executorServiceBrowseImages.execute(new Runnable() {
                                                                                                                                            @Override
                                                                                                                                            public void run() {


                                                                                                                                                try {
                                                                                                                                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_browse_book_images.php";
                                                                                                                                                    //System.out.println("running-----------------------------------------------------------------------");

                                                                                                                                                    URL url = new URL(login_url);
                                                                                                                                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                                                                                    httpURLConnection.setRequestMethod("POST");
                                                                                                                                                    httpURLConnection.setDoOutput(true);
                                                                                                                                                    httpURLConnection.setDoInput(true);

                                                                                                                                                    InputStream inputStream = httpURLConnection.getInputStream();
                                                                                                                                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                                                                                    String line = "";
                                                                                                                                                    int i = 0;
                                                                                                                                                    while ((line = bufferedReader.readLine()) != null) {
                                                                                                                                                        browse_image[i] = "";
                                                                                                                                                        browse_image[i] += line;
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



                                                                                                                                                        int length=0;
                                                                                                                                                        int i=0;

                                                                                                                                                        if(browse_get_button_book_name[0].equals("null"))
                                                                                                                                                        {
                                                                                                                                                          //  System.out.println("length 0 set");
                                                                                                                                                            spinner_browse_get_button_book_name=new String[0];
                                                                                                                                                        }
                                                                                                                                                        else {

                                                                                                                                                            while (!(browse_get_button_book_name[i] == null)) {
                                                                                                                                                                length++;
                                                                                                                                                                i++;
                                                                                                                                                            }
                                                                                                                                                            spinner_browse_get_button_book_name = new String[length];

                                                                                                                                                            for (int p = 0; p < spinner_browse_get_button_book_name.length; p++) {
                                                                                                                                                                spinner_browse_get_button_book_name[p] = browse_get_button_book_name[p];
                                                                                                                                                            }

                                                                                                                                                            for (int p = 0; p < spinner_browse_get_button_book_name.length; p++) {
                                                                                                                                                                //System.out.println("Allocated Book Data In BrowseFragment :"+spinner_browse_get_button_book_name[p]);
                                                                                                                                                                //System.out.println();

                                                                                                                                                            }
                                                                                                                                                        }

                                                                                                                                                         length=0;
                                                                                                                                                         i=0;
                                                                                                                                                         if(browse_pending_book_name[0].equals("null"))
                                                                                                                                                         {
                                                                                                                                                          //   System.out.println("length 0 set");
                                                                                                                                                             spinner_browse_pending_book_name=new String[0];
                                                                                                                                                         }
                                                                                                                                                         else
                                                                                                                                                         {
                                                                                                                                                             while(!(browse_pending_book_name[i] ==null))
                                                                                                                                                             { length++;
                                                                                                                                                                 i++;
                                                                                                                                                             }
                                                                                                                                                             spinner_browse_pending_book_name=new String[length];

                                                                                                                                                             for(int p=0;p<spinner_browse_pending_book_name.length;p++)
                                                                                                                                                             {
                                                                                                                                                                 spinner_browse_pending_book_name[p]=browse_pending_book_name[p];
                                                                                                                                                             }
                                                                                                                                                             for(int p=0;p< spinner_browse_pending_book_name.length;p++)
                                                                                                                                                             {
                                                                                                                                                                 //System.out.println("Pending Book Data :"+spinner_browse_pending_book_name[p]);
                                                                                                                                                                 //System.out.println();

                                                                                                                                                             }
                                                                                                                                                         }



                                                                                                                                                      //  System.out.println("Browse Pending Length:"+spinner_browse_pending_book_name.length);











                                                                                                                                                        if ((browse_image[0] == null) || browse_image[0].equals("null")) {

                                                                                                                                                            //System.out.println(":(Browse Book Images Don't Exist! ):");

                                                                                                                                                        } else {
                                                                                                                                                            //System.out.println("Browse Books Images Data Download Complete!");
                                                                                                                                                            RecyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
                                                                                                                                                            //  RecyclerView2.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                                                                                                                                                            BrowseCustomAdapter ca = new BrowseCustomAdapter(getActivity(),browse_book_name,browse_author,
                                                                                                                                                                    browse_publisher, browse_course,browse_yop,browse_available_count,browse_image);
                                                                                                                                                            RecyclerView2.setAdapter(ca);
                                                                                                                                                            firstRenderComplete=1;

                                                                                                                                                        }
                                                                                                                                                        per1.setText("100.00 %");
                                                                                                                                                        Globals.loadingDialog.dismissDialog();
                                                                                                                                                        //System.out.println("BrowseFragment Dialog Dismissed!");




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



        String[] browse_spinner={"⇓   FIND BY COURSE   ⇓","BCA","BBA","B.COM","B.TECH","LLB","BSC"};

            spinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
// Create an ArrayAdapter using the string array and a default spinner layout

        ArrayAdapter adapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,browse_spinner);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
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

            BrowseCustomAdapter ca = new BrowseCustomAdapter(getActivity(),browse_book_name,browse_author,
                    browse_publisher, browse_course,browse_yop,browse_available_count,browse_image);
            RecyclerView2.setAdapter(ca);


            }

        }
        else if (position==1)
        {
            //System.out.println("Working In BCA");
               //    loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerBCA= Executors.newSingleThreadExecutor();
            executorServiceSpinnerBCA.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<browse_course.length;i++) {
                        spinner_browse_course[i]= null;
                        spinner_browse_author[i]= null;
                        spinner_browse_available_count[i]= null;
                        spinner_browse_book_name[i]= null;
                        spinner_browse_image[i]= null;
                        spinner_browse_publisher[i]= null;
                        spinner_browse_yop[i]= null;
                       // spinner_browse_get_button_book_name[i]=null;

                    }




                    int i=0;
                    for(int j=0;browse_course[j]!=null;j++)
                    {

                        if (browse_course[j].equals("BCA"))
                        {
                            spinner_browse_course[i]=browse_course[j];
                            spinner_browse_author[i]=browse_author[j];
                            spinner_browse_available_count[i]=browse_available_count[j];
                            spinner_browse_book_name[i]=browse_book_name[j];
                            spinner_browse_image[i]=browse_image[j];
                            spinner_browse_publisher[i]=browse_publisher[j];
                            spinner_browse_yop[i]=browse_yop[j];


                            i++;

                           // //System.out.println("BCA BOOK NAME:"+spinner_browse_book_name[i]);

                        }


                    }

//                    for( i=0;i<spinner_browse_course.length;i++)
//                    {
//                        //System.out.println("In Spinner Course: "+spinner_browse_course[i]+spinner_browse_course.length);
//
//                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            BrowseCustomAdapter ca = new BrowseCustomAdapter(getActivity(),spinner_browse_book_name,spinner_browse_author,
                                    spinner_browse_publisher, spinner_browse_course,spinner_browse_yop,spinner_browse_available_count,spinner_browse_image);
                            RecyclerView2.setAdapter(ca);


                          //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BrowseFragment Dialog Dismissed!");




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
                    for(int i=0;i<browse_course.length;i++) {
                        spinner_browse_course[i]= null;
                        spinner_browse_author[i]= null;
                        spinner_browse_available_count[i]= null;
                        spinner_browse_book_name[i]= null;
                        spinner_browse_image[i]= null;
                        spinner_browse_publisher[i]= null;
                        spinner_browse_yop[i]= null;
                       // spinner_browse_get_button_book_name[i]=null;
                    }

                    int i=0;

                    for(int j=0;browse_course[j]!=null;j++)
                    {

                        if (browse_course[j].equals("BBA"))
                        {
                            spinner_browse_course[i]=browse_course[j];
                            spinner_browse_author[i]=browse_author[j];
                            spinner_browse_available_count[i]=browse_available_count[j];
                            spinner_browse_book_name[i]=browse_book_name[j];
                            spinner_browse_image[i]=browse_image[j];
                            spinner_browse_publisher[i]=browse_publisher[j];
                            spinner_browse_yop[i]=browse_yop[j];


                            i++;

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            BrowseCustomAdapter ca = new BrowseCustomAdapter(getActivity(),spinner_browse_book_name,spinner_browse_author,
                                    spinner_browse_publisher, spinner_browse_course,spinner_browse_yop,spinner_browse_available_count,spinner_browse_image);
                            RecyclerView2.setAdapter(ca);


                            //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BrowseFragment Dialog Dismissed!");




                        }
                    });

                }
            });




        }
        else if(position==3)
        {  //System.out.println("Working In B.COM");
            //    loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerBCom= Executors.newSingleThreadExecutor();
            executorServiceSpinnerBCom.execute(new Runnable() {
                @Override
                public void run() {

                    for(int i=0;i<browse_course.length;i++) {
                        spinner_browse_course[i]= null;
                        spinner_browse_author[i]= null;
                        spinner_browse_available_count[i]= null;
                        spinner_browse_book_name[i]= null;
                        spinner_browse_image[i]= null;
                        spinner_browse_publisher[i]= null;
                        spinner_browse_yop[i]= null;

                    }

                    int i=0;

                    for(int j=0;browse_course[j]!=null;j++)
                    {


                        if (browse_course[j].equals("B.COM"))
                        {
                            spinner_browse_course[i]=browse_course[j];
                            spinner_browse_author[i]=browse_author[j];
                            spinner_browse_available_count[i]=browse_available_count[j];
                            spinner_browse_book_name[i]=browse_book_name[j];
                            spinner_browse_image[i]=browse_image[j];
                            spinner_browse_publisher[i]=browse_publisher[j];
                            spinner_browse_yop[i]=browse_yop[j];

                            i++;

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            BrowseCustomAdapter ca = new BrowseCustomAdapter(getActivity(),spinner_browse_book_name,spinner_browse_author,
                                    spinner_browse_publisher, spinner_browse_course,spinner_browse_yop,spinner_browse_available_count,spinner_browse_image);
                            RecyclerView2.setAdapter(ca);


                            //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BrowseFragment Dialog Dismissed!");




                        }
                    });

                }
            });



        }
        else if(position==4)
        {  //System.out.println("Working In B.TECH");
            //    loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerBTech= Executors.newSingleThreadExecutor();
            executorServiceSpinnerBTech.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<browse_course.length;i++) {
                        spinner_browse_course[i]= null;
                        spinner_browse_author[i]= null;
                        spinner_browse_available_count[i]= null;
                        spinner_browse_book_name[i]= null;
                        spinner_browse_image[i]= null;
                        spinner_browse_publisher[i]= null;
                        spinner_browse_yop[i]= null;
                    }



                int i=0;

                    for(int j=0;browse_course[j]!=null;j++)
                    {


                        if (browse_course[j].equals("B.TECH"))
                        {
                            spinner_browse_course[i]=browse_course[j];
                            spinner_browse_author[i]=browse_author[j];
                            spinner_browse_available_count[i]=browse_available_count[j];
                            spinner_browse_book_name[i]=browse_book_name[j];
                            spinner_browse_image[i]=browse_image[j];
                            spinner_browse_publisher[i]=browse_publisher[j];
                            spinner_browse_yop[i]=browse_yop[j];

                            i++;

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            BrowseCustomAdapter ca = new BrowseCustomAdapter(getActivity(),spinner_browse_book_name,spinner_browse_author,
                                    spinner_browse_publisher, spinner_browse_course,spinner_browse_yop,spinner_browse_available_count,spinner_browse_image);
                            RecyclerView2.setAdapter(ca);


                            //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BrowseFragment Dialog Dismissed!");




                        }
                    });

                }
            });



        }
        else if(position==5)
        {  //System.out.println("Working In LLB");
            //    loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerLLB= Executors.newSingleThreadExecutor();
            executorServiceSpinnerLLB.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<browse_course.length;i++) {
                        spinner_browse_course[i]= null;
                        spinner_browse_author[i]= null;
                        spinner_browse_available_count[i]= null;
                        spinner_browse_book_name[i]= null;
                        spinner_browse_image[i]= null;
                        spinner_browse_publisher[i]= null;
                        spinner_browse_yop[i]= null;
                    }


                   int i=0;

                    for(int j=0;browse_course[j]!=null;j++)
                    {

                        if (browse_course[j].equals("LLB"))
                        {
                            spinner_browse_course[i]=browse_course[j];
                            spinner_browse_author[i]=browse_author[j];
                            spinner_browse_available_count[i]=browse_available_count[j];
                            spinner_browse_book_name[i]=browse_book_name[j];
                            spinner_browse_image[i]=browse_image[j];
                            spinner_browse_publisher[i]=browse_publisher[j];
                            spinner_browse_yop[i]=browse_yop[j];
                            i++;
                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            BrowseCustomAdapter ca = new BrowseCustomAdapter(getActivity(),spinner_browse_book_name,spinner_browse_author,
                                    spinner_browse_publisher, spinner_browse_course,spinner_browse_yop,spinner_browse_available_count,spinner_browse_image);
                            RecyclerView2.setAdapter(ca);

                            //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BrowseFragment Dialog Dismissed!");




                        }
                    });

                }
            });
        }

        else if (position==6)
        {
            //System.out.println("Working In BSC");
            //    loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerBSC= Executors.newSingleThreadExecutor();
            executorServiceSpinnerBSC.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<browse_course.length;i++) {
                        spinner_browse_course[i]= null;
                        spinner_browse_author[i]= null;
                        spinner_browse_available_count[i]= null;
                        spinner_browse_book_name[i]= null;
                        spinner_browse_image[i]= null;
                        spinner_browse_publisher[i]= null;
                        spinner_browse_yop[i]= null;
                        // spinner_browse_get_button_book_name[i]=null;

                    }




                    int i=0;
                    for(int j=0;browse_course[j]!=null;j++)
                    {

                        if (browse_course[j].equals("BSC"))
                        {
                            spinner_browse_course[i]=browse_course[j];
                            spinner_browse_author[i]=browse_author[j];
                            spinner_browse_available_count[i]=browse_available_count[j];
                            spinner_browse_book_name[i]=browse_book_name[j];
                            spinner_browse_image[i]=browse_image[j];
                            spinner_browse_publisher[i]=browse_publisher[j];
                            spinner_browse_yop[i]=browse_yop[j];


                            i++;

                            //System.out.println("BSC BOOK NAME:"+spinner_browse_book_name[i]);

                        }


                    }

                    for( i=0;i<spinner_browse_course.length;i++)
                    {
                        //System.out.println("In Spinner Course: "+spinner_browse_course[i]+spinner_browse_course.length);

                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            BrowseCustomAdapter ca = new BrowseCustomAdapter(getActivity(),spinner_browse_book_name,spinner_browse_author,
                                    spinner_browse_publisher, spinner_browse_course,spinner_browse_yop,spinner_browse_available_count,spinner_browse_image);
                            RecyclerView2.setAdapter(ca);


                            //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BrowseFragment Dialog Dismissed!");




                        }
                    });

                }
            });


        }



//        int duration = Toast.LENGTH_SHORT;
//        //System.out.println("Clicked");
//        Toast toast = Toast.makeText(getContext(), Html.fromHtml("<font color='#FF0000' > <b>" + position+"hello" + "</b> </font>"), duration);
//
//        toast.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}