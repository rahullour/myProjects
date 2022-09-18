package com.example.librarymanagementsystemteachers.ui.ReturnBooksRequests;

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

import com.example.librarymanagementsystemteachers.ReturnBooksRequestsCustomAdapter;
import com.example.librarymanagementsystemteachers.Globals;
import com.example.librarymanagementsystemteachers.LoadingDialog;
import com.example.librarymanagementsystemteachers.NetworkUtil;
import com.example.librarymanagementsystemteachers.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.librarymanagementsystemteachers.Globals.loadingDialog;
import static com.example.librarymanagementsystemteachers.Globals.spinner_return_requested_book_author;
import static com.example.librarymanagementsystemteachers.Globals.spinner_return_requested_book_available_count;
import static com.example.librarymanagementsystemteachers.Globals.spinner_return_requested_book_course;
import static com.example.librarymanagementsystemteachers.Globals.spinner_return_requested_book_image;
import static com.example.librarymanagementsystemteachers.Globals.spinner_return_requested_book_name;
import static com.example.librarymanagementsystemteachers.Globals.spinner_return_requested_book_publisher;
import static com.example.librarymanagementsystemteachers.Globals.spinner_return_requested_book_yop;
import static com.example.librarymanagementsystemteachers.Globals.spinner_return_requested_student_course;
import static com.example.librarymanagementsystemteachers.Globals.spinner_return_requested_student_id;
import static com.example.librarymanagementsystemteachers.Globals.spinner_return_requested_student_name;
import static com.example.librarymanagementsystemteachers.Globals.status;

public class ReturnBooksRequestsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    String[] return_requested_book_name=new String[100];
    String[] return_requested_book_author=new String[100];
    String[] return_requested_book_publisher=new String[100];
    String[] return_requested_book_course=new String[100];
    String[] return_requested_book_yop=new String[100];
    String[] return_requested_book_image=new String[100];
    String[] return_requested_book_available_count=new String[100];
    String[] return_requested_student_name=new String[100];
    String[] return_requested_student_id=new String[100];
    String[] return_requested_student_course=new String[100];



    int firstRenderComplete =0;

    RecyclerView RequestedRecyclerView;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_return_books_requests, container, false);
        RequestedRecyclerView=root.findViewById(R.id.return_requested_recyclerview);
        Spinner spinner = (Spinner) root.findViewById(R.id.return_requested_spinner);


        Button requesteddbutton=root.findViewById(R.id.return_requested_button);
        EditText allotted_find_by_id=root.findViewById(R.id.return_requested_find_by_id);

        requesteddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);

                String SearchIdText = allotted_find_by_id.getText().toString().trim();
                allotted_find_by_id.setText("");

                if (SearchIdText.isEmpty()) {
                    spinner.setSelection(0);
                    ReturnBooksRequestsCustomAdapter ca = new ReturnBooksRequestsCustomAdapter(getActivity(),return_requested_book_name,return_requested_book_author,
                            return_requested_book_publisher, return_requested_book_course,return_requested_book_yop,return_requested_book_available_count,return_requested_book_image,return_requested_student_name,return_requested_student_id,return_requested_student_course);
                    RequestedRecyclerView.setAdapter(ca);


                } else if (!android.text.TextUtils.isDigitsOnly(SearchIdText)) {
                    CharSequence text = ":( Please Enter A Numeric Value ! ):";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(getContext(), Html.fromHtml("<font color='#FF0000' > <b>" + text + "</b> </font>"), duration);

                    toast.show();

                } else {
                    final Boolean[] found = {false};
                    final ExecutorService executorServiceSearchButton = Executors.newSingleThreadExecutor();
                    executorServiceSearchButton.execute(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < return_requested_student_id.length; i++) {
                                spinner_return_requested_book_course[i]= null;
                                spinner_return_requested_book_author[i]= null;
                                spinner_return_requested_book_available_count[i]= null;
                                spinner_return_requested_book_name[i]= null;
                                spinner_return_requested_book_image[i]= null;
                                spinner_return_requested_book_publisher[i]= null;
                                spinner_return_requested_book_yop[i]= null;
                                spinner_return_requested_student_name[i]= null;
                                spinner_return_requested_student_id[i]= null;
                                spinner_return_requested_student_course[i]= null;


                            }


                            int i = 0;
                            for (int j = 0; return_requested_student_id[j] != null; j++) {

                                if (return_requested_student_id[j].equals(SearchIdText)) {
                                    spinner_return_requested_book_course[i]=return_requested_book_course[j];
                                    spinner_return_requested_book_author[i]=return_requested_book_author[j];
                                    spinner_return_requested_book_available_count[i]=return_requested_book_available_count[j];
                                    spinner_return_requested_book_name[i]=return_requested_book_name[j];
                                    spinner_return_requested_book_image[i]=return_requested_book_image[j];
                                    spinner_return_requested_book_publisher[i]=return_requested_book_publisher[j];
                                    spinner_return_requested_book_yop[i]=return_requested_book_yop[j];
                                    spinner_return_requested_student_name[i]=return_requested_student_name[j];
                                    spinner_return_requested_student_id[i]=return_requested_student_id[j];
                                    spinner_return_requested_student_course[i]=return_requested_student_course[j];
                                    i++;

                                    //System.out.println("Search Button Student Id::" + return_requested_student_id[i]);
                                    found[0] =true;
                                }


                            }

                            for (i = 0; i < return_requested_book_name.length; i++) {
                                //System.out.println("In BooksRequests Search Button: " + return_requested_book_name[i] + return_requested_book_name.length);

                            }


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(found[0] ==true)
                                    {
                                        ReturnBooksRequestsCustomAdapter ca = new ReturnBooksRequestsCustomAdapter(getActivity(),spinner_return_requested_book_name,spinner_return_requested_book_author,
                                                spinner_return_requested_book_publisher, spinner_return_requested_book_course,spinner_return_requested_book_yop,spinner_return_requested_book_available_count,spinner_return_requested_book_image,spinner_return_requested_student_name,spinner_return_requested_student_id,spinner_return_requested_student_course);
                                        RequestedRecyclerView.setAdapter(ca);

                                    }
                                    else {
                                        CharSequence text = ":( Student Id : "+SearchIdText+" Not Found ! ):";
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
            spinner_return_requested_book_name=new String[100];
            spinner_return_requested_book_author=new String[100];
            spinner_return_requested_book_publisher=new String[100];
            spinner_return_requested_book_course=new String[100];
            spinner_return_requested_book_yop=new String[100];
            spinner_return_requested_book_available_count=new String[100];
            spinner_return_requested_book_image=new String[100];
            spinner_return_requested_student_name=new String[100];
            spinner_return_requested_student_id=new String[100];
            spinner_return_requested_student_course=new String[100];


            loadingDialog=new LoadingDialog(getActivity());
            // //System.out.println("==================="+loadingDialog.isCancelled());
            AlertDialog percentage1 =  loadingDialog.startLoadingDialog();
            TextView per1= percentage1.findViewById(R.id.progress_percentage);

            final ExecutorService executorServiceRequestedStudentName= Executors.newSingleThreadExecutor();
            executorServiceRequestedStudentName.execute(new Runnable() {
                @Override
                public void run() {


                    try {
                        String login_url = "https://stalinism-noun.000webhostapp.com/get_return_requested_student_name.php";
                        //System.out.println("running-----------------------------------------------------------------------");

                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(false);
                        httpURLConnection.setDoInput(true);

                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                        String line = "";
                        int i = 0;
                        while ((line = bufferedReader.readLine()) != null) {

                            return_requested_student_name[i] = "";
                            return_requested_student_name[i] += line;
                            //  //System.out.println("Requested Book Name : " + i +return_requested_book_name[i]);
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
                            //System.out.println("Books Requests Student Name Data Download Complete!");
                            final ExecutorService executorServiceRequestedStudentId= Executors.newSingleThreadExecutor();
                            executorServiceRequestedStudentId.execute(new Runnable() {
                                @Override
                                public void run() {


                                    try {
                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_return_requested_student_id.php";
                                        //System.out.println("running-----------------------------------------------------------------------");

                                        URL url = new URL(login_url);
                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoOutput(false);
                                        httpURLConnection.setDoInput(true);

                                        InputStream inputStream = httpURLConnection.getInputStream();
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                        String line = "";
                                        int i = 0;
                                        while ((line = bufferedReader.readLine()) != null) {

                                            return_requested_student_id[i] = "";
                                            return_requested_student_id[i] += line;
                                            //  //System.out.println("Requested Book Name : " + i +return_requested_book_name[i]);
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
                                            //System.out.println("Books Requests Student Id Data Download Complete!");
                                            final ExecutorService executorServiceRequestedStudentCourse= Executors.newSingleThreadExecutor();
                                            executorServiceRequestedStudentCourse.execute(new Runnable() {
                                                @Override
                                                public void run() {


                                                    try {
                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_return_requested_student_course.php";
                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                        URL url = new URL(login_url);
                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                        httpURLConnection.setRequestMethod("POST");
                                                        httpURLConnection.setDoOutput(false);
                                                        httpURLConnection.setDoInput(true);

                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                        String line = "";
                                                        int i = 0;
                                                        while ((line = bufferedReader.readLine()) != null) {

                                                            return_requested_student_course[i] = "";
                                                            return_requested_student_course[i] += line;
                                                            //  //System.out.println("Requested Book Name : " + i +return_requested_book_name[i]);
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
                                                            //System.out.println("Books Requests Student Course Data Download Complete!");
                                                            final ExecutorService executorServiceRequestedBookName= Executors.newSingleThreadExecutor();
                                                            executorServiceRequestedBookName.execute(new Runnable() {
                                                                @Override
                                                                public void run() {


                                                                    try {
                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_return_requested_book_name.php";
                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                        URL url = new URL(login_url);
                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                        httpURLConnection.setRequestMethod("POST");
                                                                        httpURLConnection.setDoOutput(false);
                                                                        httpURLConnection.setDoInput(true);

                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                        String line = "";
                                                                        int i = 0;
                                                                        while ((line = bufferedReader.readLine()) != null) {

                                                                            return_requested_book_name[i] = "";
                                                                            return_requested_book_name[i] += line;
                                                                            //  //System.out.println("Requested Book Name : " + i +return_requested_book_name[i]);
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
                                                                            //System.out.println("Books Requests Books Name Data Download Complete!");

                                                                            final ExecutorService executorServiceRequestedAuthor= Executors.newSingleThreadExecutor();
                                                                            executorServiceRequestedAuthor.execute(new Runnable() {
                                                                                @Override
                                                                                public void run() {




                                                                                    try {
                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_return_requested_book_author.php";
                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                        URL url = new URL(login_url);
                                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                        httpURLConnection.setDoOutput(false);
                                                                                        httpURLConnection.setDoInput(true);

                                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                        String line = "";
                                                                                        int i = 0;
                                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                                            return_requested_book_author[i] = "";
                                                                                            return_requested_book_author[i] += line;
                                                                                            //  //System.out.println("Requested Book Author : " + i +return_requested_book_author[i]);
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
                                                                                            //System.out.println("Books Requests Author Data Download Complete!");

                                                                                            final ExecutorService executorServiceRequestedPublisher= Executors.newSingleThreadExecutor();
                                                                                            executorServiceRequestedPublisher.execute(new Runnable() {
                                                                                                @Override
                                                                                                public void run() {




                                                                                                    try {
                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_return_requested_book_publisher.php";
                                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                                        URL url = new URL(login_url);
                                                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                                        httpURLConnection.setDoOutput(false);
                                                                                                        httpURLConnection.setDoInput(true);


                                                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                                        String line = "";
                                                                                                        int i = 0;
                                                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                                                            return_requested_book_publisher[i] = "";
                                                                                                            return_requested_book_publisher[i] += line;
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

                                                                                                            per1.setText("60.00 %");
                                                                                                            //System.out.println("Books Requests Publisher Data Download Complete!");


                                                                                                            final ExecutorService executorServiceRequestedCourse= Executors.newSingleThreadExecutor();
                                                                                                            executorServiceRequestedCourse.execute(new Runnable() {
                                                                                                                @Override
                                                                                                                public void run() {


                                                                                                                    try {
                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_return_requested_book_course.php";
                                                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                                                        URL url = new URL(login_url);
                                                                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                                                        httpURLConnection.setDoOutput(false);
                                                                                                                        httpURLConnection.setDoInput(true);

                                                                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                                                        String line = "";
                                                                                                                        int i = 0;
                                                                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                                                                            return_requested_book_course[i] = "";
                                                                                                                            return_requested_book_course[i] += line;
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

                                                                                                                            per1.setText("70.00 %");
                                                                                                                            //System.out.println("Books Requests Course Data Download Complete!");


                                                                                                                            final ExecutorService executorServiceRequestedYop= Executors.newSingleThreadExecutor();
                                                                                                                            executorServiceRequestedYop.execute(new Runnable() {
                                                                                                                                @Override
                                                                                                                                public void run() {


                                                                                                                                    try {
                                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_return_requested_book_yop.php";
                                                                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                                                                        URL url = new URL(login_url);
                                                                                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                                                                        httpURLConnection.setDoOutput(false);
                                                                                                                                        httpURLConnection.setDoInput(true);

                                                                                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                                                                        String line = "";
                                                                                                                                        int i = 0;
                                                                                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                                                                                            return_requested_book_yop[i] = "";
                                                                                                                                            return_requested_book_yop[i] += line;
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
                                                                                                                                            //System.out.println("Books Requests Yop Data Download Complete!");


                                                                                                                                            final ExecutorService executorServiceRequestedAvailableCount= Executors.newSingleThreadExecutor();
                                                                                                                                            executorServiceRequestedAvailableCount.execute(new Runnable() {
                                                                                                                                                @Override
                                                                                                                                                public void run() {


                                                                                                                                                    try {
                                                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_return_requested_book_available_count.php";
                                                                                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                                                                                        URL url = new URL(login_url);
                                                                                                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                                                                                        httpURLConnection.setDoOutput(false);
                                                                                                                                                        httpURLConnection.setDoInput(true);

                                                                                                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                                                                                        String line = "";
                                                                                                                                                        int i = 0;
                                                                                                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                                                                                                            return_requested_book_available_count[i] = "";
                                                                                                                                                            return_requested_book_available_count[i] += line;
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
                                                                                                                                                            //System.out.println("Books Requests Available Count Data Download Complete!");


                                                                                                                                                            final ExecutorService executorServiceRequestedImages= Executors.newSingleThreadExecutor();
                                                                                                                                                            executorServiceRequestedImages.execute(new Runnable() {
                                                                                                                                                                @Override
                                                                                                                                                                public void run() {


                                                                                                                                                                    try {
                                                                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_return_requested_book_images.php";
                                                                                                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                                                                                                        URL url = new URL(login_url);
                                                                                                                                                                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                                                                                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                                                                                                        httpURLConnection.setDoOutput(false);
                                                                                                                                                                        httpURLConnection.setDoInput(true);

                                                                                                                                                                        InputStream inputStream = httpURLConnection.getInputStream();
                                                                                                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                                                                                                        String line = "";
                                                                                                                                                                        int i = 0;
                                                                                                                                                                        while ((line = bufferedReader.readLine()) != null) {
                                                                                                                                                                            return_requested_book_image[i] = "";
                                                                                                                                                                            return_requested_book_image[i] += line;
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

                                                                                                                                                                            if ((return_requested_book_image[0] == null) || return_requested_book_image[0].equals("null")) {

                                                                                                                                                                                //System.out.println(":(Requested Book Images Don't Exist! ):");

                                                                                                                                                                            } else {
                                                                                                                                                                                //System.out.println("Requested Books Images Data Download Complete!");
                                                                                                                                                                                RequestedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                                                                                                                                                //  RequestedRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
                                                                                                                                                                                ReturnBooksRequestsCustomAdapter ca = new ReturnBooksRequestsCustomAdapter(getActivity(),return_requested_book_name,return_requested_book_author,
                                                                                                                                                                                        return_requested_book_publisher, return_requested_book_course,return_requested_book_yop,return_requested_book_available_count,return_requested_book_image,return_requested_student_name,return_requested_student_id,return_requested_student_course);
                                                                                                                                                                                RequestedRecyclerView.setAdapter(ca);
                                                                                                                                                                                firstRenderComplete=1;

                                                                                                                                                                            }
                                                                                                                                                                            per1.setText("100.00 %");
                                                                                                                                                                            Globals.loadingDialog.dismissDialog();
                                                                                                                                                                            //System.out.println("BooksRequests Dialog Dismissed!");

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









            String[] return_requested_spinner={"⇓   FIND BY BOOK COURSE   ⇓","BCA","BBA","B.COM","B.TECH","LLB","BSC"};
            spinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
// Create an ArrayAdapter using the string array and a default spinner layout

            ArrayAdapter adapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,return_requested_spinner);
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
        { if (firstRenderComplete==1) {

            ReturnBooksRequestsCustomAdapter ca = new ReturnBooksRequestsCustomAdapter(getActivity(), return_requested_book_name, return_requested_book_author,
                    return_requested_book_publisher, return_requested_book_course, return_requested_book_yop, return_requested_book_available_count, return_requested_book_image, return_requested_student_name, return_requested_student_id, return_requested_student_course);
            RequestedRecyclerView.setAdapter(ca);

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
                    for(int i=0;i<return_requested_book_course.length;i++) {
                        spinner_return_requested_book_course[i]= null;
                        spinner_return_requested_book_author[i]= null;
                        spinner_return_requested_book_available_count[i]= null;
                        spinner_return_requested_book_name[i]= null;
                        spinner_return_requested_book_image[i]= null;
                        spinner_return_requested_book_publisher[i]= null;
                        spinner_return_requested_book_yop[i]= null;
                        spinner_return_requested_student_name[i]= null;
                        spinner_return_requested_student_id[i]= null;
                        spinner_return_requested_student_course[i]= null;


                    }




                    int i=0;
                    for(int j=0;return_requested_book_course[j]!=null;j++)
                    {

                        if (return_requested_book_course[j].equals("BCA"))
                        {
                            spinner_return_requested_book_course[i]=return_requested_book_course[j];
                            spinner_return_requested_book_author[i]=return_requested_book_author[j];
                            spinner_return_requested_book_available_count[i]=return_requested_book_available_count[j];
                            spinner_return_requested_book_name[i]=return_requested_book_name[j];
                            spinner_return_requested_book_image[i]=return_requested_book_image[j];
                            spinner_return_requested_book_publisher[i]=return_requested_book_publisher[j];
                            spinner_return_requested_book_yop[i]=return_requested_book_yop[j];
                            spinner_return_requested_student_name[i]=return_requested_student_name[j];
                            spinner_return_requested_student_id[i]=return_requested_student_id[j];
                            spinner_return_requested_student_course[i]=return_requested_student_course[j];


                            i++;

                            // //System.out.println("BCA BOOK NAME:"+spinner_return_requested_book_name[i]);

                        }


                    }

//                    for( i=0;i<spinner_return_requested_book_course.length;i++)
//                    {
//                        //System.out.println("In Spinner Course: "+spinner_return_requested_book_course[i]+spinner_return_requested_book_course.length);
//
//                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            ReturnBooksRequestsCustomAdapter ca = new ReturnBooksRequestsCustomAdapter(getActivity(),spinner_return_requested_book_name,spinner_return_requested_book_author,
                                    spinner_return_requested_book_publisher, spinner_return_requested_book_course,spinner_return_requested_book_yop,spinner_return_requested_book_available_count,spinner_return_requested_book_image,spinner_return_requested_student_name,spinner_return_requested_student_id,spinner_return_requested_student_course);
                            RequestedRecyclerView.setAdapter(ca);


                            //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BooksRequestsBooksFragment Dialog Dismissed!");




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
                    for(int i=0;i<return_requested_book_course.length;i++) {
                        spinner_return_requested_book_course[i]= null;
                        spinner_return_requested_book_author[i]= null;
                        spinner_return_requested_book_available_count[i]= null;
                        spinner_return_requested_book_name[i]= null;
                        spinner_return_requested_book_image[i]= null;
                        spinner_return_requested_book_publisher[i]= null;
                        spinner_return_requested_book_yop[i]= null;
                        spinner_return_requested_student_name[i]= null;
                        spinner_return_requested_student_id[i]= null;
                        spinner_return_requested_student_course[i]= null;

                    }

                    int i=0;

                    for(int j=0;return_requested_book_course[j]!=null;j++)
                    {

                        if (return_requested_book_course[j].equals("BBA"))
                        {
                            spinner_return_requested_book_course[i]=return_requested_book_course[j];
                            spinner_return_requested_book_author[i]=return_requested_book_author[j];
                            spinner_return_requested_book_available_count[i]=return_requested_book_available_count[j];
                            spinner_return_requested_book_name[i]=return_requested_book_name[j];
                            spinner_return_requested_book_image[i]=return_requested_book_image[j];
                            spinner_return_requested_book_publisher[i]=return_requested_book_publisher[j];
                            spinner_return_requested_book_yop[i]=return_requested_book_yop[j];
                            spinner_return_requested_student_name[i]=return_requested_student_name[j];
                            spinner_return_requested_student_id[i]=return_requested_student_id[j];
                            spinner_return_requested_student_course[i]=return_requested_student_course[j];


                            i++;

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ReturnBooksRequestsCustomAdapter ca = new ReturnBooksRequestsCustomAdapter(getActivity(),spinner_return_requested_book_name,spinner_return_requested_book_author,
                                    spinner_return_requested_book_publisher, spinner_return_requested_book_course,spinner_return_requested_book_yop,spinner_return_requested_book_available_count,spinner_return_requested_book_image,spinner_return_requested_student_name,spinner_return_requested_student_id,spinner_return_requested_student_course);
                            RequestedRecyclerView.setAdapter(ca);


                            //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BooksRequestsBooksFragment Dialog Dismissed!");




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

                    for(int i=0;i<return_requested_book_course.length;i++) {
                        spinner_return_requested_book_course[i]= null;
                        spinner_return_requested_book_author[i]= null;
                        spinner_return_requested_book_available_count[i]= null;
                        spinner_return_requested_book_name[i]= null;
                        spinner_return_requested_book_image[i]= null;
                        spinner_return_requested_book_publisher[i]= null;
                        spinner_return_requested_book_yop[i]= null;
                        spinner_return_requested_student_name[i]= null;
                        spinner_return_requested_student_id[i]= null;
                        spinner_return_requested_student_course[i]= null;

                    }

                    int i=0;

                    for(int j=0;return_requested_book_course[j]!=null;j++)
                    {


                        if (return_requested_book_course[j].equals("B.COM"))
                        {
                            spinner_return_requested_book_course[i]=return_requested_book_course[j];
                            spinner_return_requested_book_author[i]=return_requested_book_author[j];
                            spinner_return_requested_book_available_count[i]=return_requested_book_available_count[j];
                            spinner_return_requested_book_name[i]=return_requested_book_name[j];
                            spinner_return_requested_book_image[i]=return_requested_book_image[j];
                            spinner_return_requested_book_publisher[i]=return_requested_book_publisher[j];
                            spinner_return_requested_book_yop[i]=return_requested_book_yop[j];
                            spinner_return_requested_student_name[i]=return_requested_student_name[j];
                            spinner_return_requested_student_id[i]=return_requested_student_id[j];
                            spinner_return_requested_student_course[i]=return_requested_student_course[j];

                            i++;

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ReturnBooksRequestsCustomAdapter ca = new ReturnBooksRequestsCustomAdapter(getActivity(),spinner_return_requested_book_name,spinner_return_requested_book_author,
                                    spinner_return_requested_book_publisher, spinner_return_requested_book_course,spinner_return_requested_book_yop,spinner_return_requested_book_available_count,spinner_return_requested_book_image,spinner_return_requested_student_name,spinner_return_requested_student_id,spinner_return_requested_student_course);
                            RequestedRecyclerView.setAdapter(ca);


                            //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BooksRequestsBooksFragment Dialog Dismissed!");




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
                    for(int i=0;i<return_requested_book_course.length;i++) {
                        spinner_return_requested_book_course[i]= null;
                        spinner_return_requested_book_author[i]= null;
                        spinner_return_requested_book_available_count[i]= null;
                        spinner_return_requested_book_name[i]= null;
                        spinner_return_requested_book_image[i]= null;
                        spinner_return_requested_book_publisher[i]= null;
                        spinner_return_requested_book_yop[i]= null;
                        spinner_return_requested_student_name[i]= null;
                        spinner_return_requested_student_id[i]= null;
                        spinner_return_requested_student_course[i]= null;

                    }



                    int i=0;

                    for(int j=0;return_requested_book_course[j]!=null;j++)
                    {


                        if (return_requested_book_course[j].equals("B.TECH"))
                        {
                            spinner_return_requested_book_course[i]=return_requested_book_course[j];
                            spinner_return_requested_book_author[i]=return_requested_book_author[j];
                            spinner_return_requested_book_available_count[i]=return_requested_book_available_count[j];
                            spinner_return_requested_book_name[i]=return_requested_book_name[j];
                            spinner_return_requested_book_image[i]=return_requested_book_image[j];
                            spinner_return_requested_book_publisher[i]=return_requested_book_publisher[j];
                            spinner_return_requested_book_yop[i]=return_requested_book_yop[j];
                            spinner_return_requested_student_name[i]=return_requested_student_name[j];
                            spinner_return_requested_student_id[i]=return_requested_student_id[j];
                            spinner_return_requested_student_course[i]=return_requested_student_course[j];

                            i++;

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ReturnBooksRequestsCustomAdapter ca = new ReturnBooksRequestsCustomAdapter(getActivity(),spinner_return_requested_book_name,spinner_return_requested_book_author,
                                    spinner_return_requested_book_publisher, spinner_return_requested_book_course,spinner_return_requested_book_yop,spinner_return_requested_book_available_count,spinner_return_requested_book_image,spinner_return_requested_student_name,spinner_return_requested_student_id,spinner_return_requested_student_course);
                            RequestedRecyclerView.setAdapter(ca);


                            //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BooksRequestsBooksFragment Dialog Dismissed!");




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
                    for(int i=0;i<return_requested_book_course.length;i++) {
                        spinner_return_requested_book_course[i]= null;
                        spinner_return_requested_book_author[i]= null;
                        spinner_return_requested_book_available_count[i]= null;
                        spinner_return_requested_book_name[i]= null;
                        spinner_return_requested_book_image[i]= null;
                        spinner_return_requested_book_publisher[i]= null;
                        spinner_return_requested_book_yop[i]= null;
                        spinner_return_requested_student_name[i]= null;
                        spinner_return_requested_student_id[i]= null;
                        spinner_return_requested_student_course[i]= null;

                    }


                    int i=0;

                    for(int j=0;return_requested_book_course[j]!=null;j++)
                    {

                        if (return_requested_book_course[j].equals("LLB"))
                        {
                            spinner_return_requested_book_course[i]=return_requested_book_course[j];
                            spinner_return_requested_book_author[i]=return_requested_book_author[j];
                            spinner_return_requested_book_available_count[i]=return_requested_book_available_count[j];
                            spinner_return_requested_book_name[i]=return_requested_book_name[j];
                            spinner_return_requested_book_image[i]=return_requested_book_image[j];
                            spinner_return_requested_book_publisher[i]=return_requested_book_publisher[j];
                            spinner_return_requested_book_yop[i]=return_requested_book_yop[j];
                            spinner_return_requested_student_name[i]=return_requested_student_name[j];
                            spinner_return_requested_student_id[i]=return_requested_student_id[j];
                            spinner_return_requested_student_course[i]=return_requested_student_course[j];
                            i++;
                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ReturnBooksRequestsCustomAdapter ca = new ReturnBooksRequestsCustomAdapter(getActivity(),spinner_return_requested_book_name,spinner_return_requested_book_author,
                                    spinner_return_requested_book_publisher, spinner_return_requested_book_course,spinner_return_requested_book_yop,spinner_return_requested_book_available_count,spinner_return_requested_book_image,spinner_return_requested_student_name,spinner_return_requested_student_id,spinner_return_requested_student_course);
                            RequestedRecyclerView.setAdapter(ca);

                            //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BooksRequestsBooksFragment Dialog Dismissed!");




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
                    for(int i=0;i<return_requested_book_course.length;i++) {
                        spinner_return_requested_book_course[i]= null;
                        spinner_return_requested_book_author[i]= null;
                        spinner_return_requested_book_available_count[i]= null;
                        spinner_return_requested_book_name[i]= null;
                        spinner_return_requested_book_image[i]= null;
                        spinner_return_requested_book_publisher[i]= null;
                        spinner_return_requested_book_yop[i]= null;
                        spinner_return_requested_student_name[i]= null;
                        spinner_return_requested_student_id[i]= null;
                        spinner_return_requested_student_course[i]= null;

                    }




                    int i=0;
                    for(int j=0;return_requested_book_course[j]!=null;j++)
                    {

                        if (return_requested_book_course[j].equals("BSC"))
                        {
                            spinner_return_requested_book_course[i]=return_requested_book_course[j];
                            spinner_return_requested_book_author[i]=return_requested_book_author[j];
                            spinner_return_requested_book_available_count[i]=return_requested_book_available_count[j];
                            spinner_return_requested_book_name[i]=return_requested_book_name[j];
                            spinner_return_requested_book_image[i]=return_requested_book_image[j];
                            spinner_return_requested_book_publisher[i]=return_requested_book_publisher[j];
                            spinner_return_requested_book_yop[i]=return_requested_book_yop[j];
                            spinner_return_requested_student_name[i]=return_requested_student_name[j];
                            spinner_return_requested_student_id[i]=return_requested_student_id[j];
                            spinner_return_requested_student_course[i]=return_requested_student_course[j];


                            i++;

                            //System.out.println("BSC BOOK NAME:"+spinner_return_requested_book_name[i]);

                        }


                    }

                    for( i=0;i<spinner_return_requested_book_course.length;i++)
                    {
                        //System.out.println("In Spinner Course: "+spinner_return_requested_book_course[i]+spinner_return_requested_book_course.length);

                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            ReturnBooksRequestsCustomAdapter ca = new ReturnBooksRequestsCustomAdapter(getActivity(),spinner_return_requested_book_name,spinner_return_requested_book_author,
                                    spinner_return_requested_book_publisher, spinner_return_requested_book_course,spinner_return_requested_book_yop,spinner_return_requested_book_available_count,spinner_return_requested_book_image,spinner_return_requested_student_name,spinner_return_requested_student_id,spinner_return_requested_student_course);
                            RequestedRecyclerView.setAdapter(ca);


                            //  Globals.loadingDialog.dismissDialog();
                            // //System.out.println("BooksRequestsBooksFragment Dialog Dismissed!");




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