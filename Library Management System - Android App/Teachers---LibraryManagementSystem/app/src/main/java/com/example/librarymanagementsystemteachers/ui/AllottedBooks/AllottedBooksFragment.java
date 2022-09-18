package com.example.librarymanagementsystemteachers.ui.AllottedBooks;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanagementsystemteachers.AllottedBooksCustomAdapter;
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
import static com.example.librarymanagementsystemteachers.Globals.profilepicdownloaded;
import static com.example.librarymanagementsystemteachers.Globals.status;

import static com.example.librarymanagementsystemteachers.Globals.spinner_allotted_book_available_count;
import static com.example.librarymanagementsystemteachers.Globals.spinner_allotted_book_borrow_date;
import static com.example.librarymanagementsystemteachers.Globals.spinner_allotted_book_course;
import static com.example.librarymanagementsystemteachers.Globals.spinner_allotted_book_image;
import static com.example.librarymanagementsystemteachers.Globals.spinner_allotted_book_late_fee;
import static com.example.librarymanagementsystemteachers.Globals.spinner_allotted_book_name;
import static com.example.librarymanagementsystemteachers.Globals.spinner_allotted_book_return_date;
import static com.example.librarymanagementsystemteachers.Globals.spinner_allotted_book_yop;
import static com.example.librarymanagementsystemteachers.Globals.spinner_allotted_student_course;
import static com.example.librarymanagementsystemteachers.Globals.spinner_allotted_student_name;
import static com.example.librarymanagementsystemteachers.Globals.spinner_allotted_student_id;
import static com.example.librarymanagementsystemteachers.Globals.percentage1;
import static com.example.librarymanagementsystemteachers.Globals.per1;




public class AllottedBooksFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    
    int firstRenderComplete =0;

    RecyclerView Allotted_RecyclerView;

    String[] allotted_book_name = new String[100];
    String[] allotted_book_borrow_date = new String[100];
    String[] allotted_book_return_date = new String[100];
    String[] allotted_book_late_fee = new String[100];
    String[] allotted_book_yop = new String[100];
    String[]  allotted_book_available_count = new String[100];
    String[] allotted_book_image = new String[100];
    String[] allotted_book_course =new String[100];
    String[] allotted_student_course =new String[100];
    String[] allotted_student_name =new String[100];
    String[] allotted_student_id =new String[100];
    Spinner spinner;






    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_alotted_books, container, false);
        Allotted_RecyclerView = root.findViewById(R.id.allotted_recyclerview);
        spinner  = (Spinner) root.findViewById(R.id.alloted_spinner);

        Button allottedbutton=root.findViewById(R.id.allotted_button);
        EditText allotted_find_by_id=root.findViewById(R.id.allotted_find_by_id);

        allottedbutton.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {

                                                  InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                                  inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);

                                                  String SearchIdText = allotted_find_by_id.getText().toString().trim();
                                                  allotted_find_by_id.setText("");

                                                  if (SearchIdText.isEmpty()) {
                                                      spinner.setSelection(0);
                                                      AllottedBooksCustomAdapter ca = new AllottedBooksCustomAdapter(getActivity(),allotted_book_name, allotted_book_borrow_date,
                                                              allotted_book_return_date, allotted_book_late_fee, allotted_book_yop, allotted_book_available_count, allotted_book_image,allotted_book_course,allotted_student_name,allotted_student_id,allotted_student_course);
                                                      Allotted_RecyclerView.setAdapter(ca);

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
                                                              for (int i = 0; i < allotted_student_id.length; i++) {
                                                                  spinner_allotted_book_name[i] = null;
                                                                  spinner_allotted_book_borrow_date[i] = null;
                                                                  spinner_allotted_book_available_count[i] = null;
                                                                  spinner_allotted_book_name[i] = null;
                                                                  spinner_allotted_book_image[i] = null;
                                                                  spinner_allotted_book_return_date[i] = null;
                                                                  spinner_allotted_book_yop[i] = null;
                                                                  spinner_allotted_book_course[i] = null;


                                                              }


                                                              int i = 0;
                                                              for (int j = 0; allotted_student_id[j] != null; j++) {

                                                                  if (allotted_student_id[j].equals(SearchIdText)) {
                                                                      spinner_allotted_book_name[i] = allotted_book_name[j];
                                                                      spinner_allotted_book_course[i] = allotted_book_course[j];
                                                                      spinner_allotted_book_borrow_date[i] = allotted_book_borrow_date[j];
                                                                      spinner_allotted_book_available_count[i] = allotted_book_available_count[j];
                                                                      spinner_allotted_book_return_date[i] = allotted_book_return_date[j];
                                                                      spinner_allotted_book_image[i] = allotted_book_image[j];
                                                                      spinner_allotted_book_late_fee[i] = allotted_book_late_fee[j];
                                                                      spinner_allotted_book_yop[i] = allotted_book_yop[j];
                                                                      spinner_allotted_student_name[i] = allotted_student_name[j];
                                                                      spinner_allotted_student_id[i] = allotted_student_id[j];
                                                                      spinner_allotted_student_course[i] = allotted_student_course[j];
                                                                      i++;

                                                                      //System.out.println("Search Button Student Id::" + allotted_student_id[i]);
                                                                                   found[0] =true;
                                                                  }


                                                              }

                                                              for (i = 0; i < allotted_book_name.length; i++) {
                                                                  //System.out.println("In Allotted Search Button: " + allotted_book_name[i] + allotted_book_name.length);

                                                              }


                                                              getActivity().runOnUiThread(new Runnable() {
                                                                  @Override
                                                                  public void run() {
                                                                      if(found[0] ==true)
                                                                      {
                                                                          AllottedBooksCustomAdapter ca = new AllottedBooksCustomAdapter(getActivity(), spinner_allotted_book_name, spinner_allotted_book_borrow_date,
                                                                                  spinner_allotted_book_return_date, spinner_allotted_book_late_fee, spinner_allotted_book_yop, spinner_allotted_book_available_count, spinner_allotted_book_image, spinner_allotted_book_course, spinner_allotted_student_name, spinner_allotted_student_id, spinner_allotted_student_course);
                                                                          Allotted_RecyclerView.setAdapter(ca);

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

              spinner_allotted_book_available_count=new String[100];
              spinner_allotted_book_borrow_date=new String[100];
              spinner_allotted_book_course=new String[100];
              spinner_allotted_book_image=new String[100];
              spinner_allotted_book_late_fee=new String[100];
              spinner_allotted_book_name=new String[100];
              spinner_allotted_book_return_date=new String[100];
              spinner_allotted_book_yop=new String[100];
              spinner_allotted_student_course=new String[100];
              spinner_allotted_student_name=new String[100];
              spinner_allotted_student_id=new String[100];




            if (profilepicdownloaded == 1) {

                loadingDialog = new LoadingDialog(getActivity());
                percentage1 =  loadingDialog.startLoadingDialog();
                per1= percentage1.findViewById(R.id.progress_percentage);


            }
            
            
            
            




            final ExecutorService executorServiceallottedStudentCourse = Executors.newSingleThreadExecutor();
            executorServiceallottedStudentCourse.execute(new Runnable() {
                @Override
                public void run() {


                    try {
                        String login_url = "https://stalinism-noun.000webhostapp.com/get_allotted_student_course.php";
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

                            allotted_student_course[i] = "";
                            allotted_student_course[i] += line;
                            //  //System.out.println("allotted Book Name : " + i +allotted_book_name[i]);
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
                                per1.setText("08.33 %");
                            //System.out.println("allotted Books Student Course Data Download Complete!");



                            final ExecutorService executorServiceallottedStudentName = Executors.newSingleThreadExecutor();
                            executorServiceallottedStudentName.execute(new Runnable() {
                                @Override
                                public void run() {


                                    try {
                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_allotted_student_name.php";
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

                                            allotted_student_name[i] = "";
                                            allotted_student_name[i] += line;
                                              //System.out.println("allotted Student Name : " + i +allotted_student_name[i]);
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
                                            per1.setText("16.66 %");

                                            //System.out.println("allotted Books Student Name Data Download Complete!");

                                            final ExecutorService executorServiceallottedStudentId = Executors.newSingleThreadExecutor();
                                            executorServiceallottedStudentId.execute(new Runnable() {
                                                @Override
                                                public void run() {


                                                    try {
                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_allotted_student_id.php";
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

                                                            allotted_student_id[i] = "";
                                                            allotted_student_id[i] += line;
                                                            //  //System.out.println("allotted Book Name : " + i +allotted_book_name[i]);
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
                                                            per1.setText("24.99 %");

                                                            //System.out.println("allotted Books Student Id Data Download Complete!");

                                                            final ExecutorService executorServiceallottedBookName = Executors.newSingleThreadExecutor();
                                                            executorServiceallottedBookName.execute(new Runnable() {
                                                                @Override
                                                                public void run() {


                                                                    try {
                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_allotted_book_name.php";
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

                                                                            allotted_book_name[i] = "";
                                                                            allotted_book_name[i] += line;
                                                                            //  //System.out.println("allotted Book Name : " + i +allotted_book_name[i]);
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

                                                                            //System.out.println("allotted Books Name Data Download Complete!");

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
                                                                                            per1.setText("41.66 %");

                                                                                            //System.out.println("Late Fee Data Update Complete !");




                                                                            final ExecutorService executorServiceallotted_book_late_fee= Executors.newSingleThreadExecutor();
                                                                            executorServiceallotted_book_late_fee.execute(new Runnable() {
                                                                                @Override
                                                                                public void run() {


                                                                                    try {
                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_allotted_book_late_fee.php";
                                                                                        //System.out.println("running-----------------------------------------------------------------------");

                                                                                        URL url=new URL(login_url);
                                                                                        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                                                                        httpURLConnection.setRequestMethod("POST");
                                                                                        httpURLConnection.setDoOutput(false);
                                                                                        httpURLConnection.setDoInput(true);
                                                                                       InputStream inputStream = httpURLConnection.getInputStream();
                                                                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                                                                        String line = "";
                                                                                        int i = 0;
                                                                                        while ((line = bufferedReader.readLine()) != null) {

                                                                                            allotted_book_late_fee[i] = "";
                                                                                            allotted_book_late_fee[i] += line;
                                                                                            //System.out.println("Late Fragment Late Fee : " + i +" "+allotted_book_late_fee[i]);
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

                                                                                            per1.setText("49.99 %");

                                                                                            //System.out.println("Late Fee Data Update Complete!");



                                                                                            final ExecutorService executorServiceallottedBookCourse = Executors.newSingleThreadExecutor();
                                                                                            executorServiceallottedBookCourse.execute(new Runnable() {
                                                                                                @Override
                                                                                                public void run() {


                                                                                                    try {
                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_allotted_course.php";
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

                                                                                                            allotted_book_course[i] = "";
                                                                                                            allotted_book_course[i] += line;
                                                                                                              //System.out.println("allotted Book course : " + i +allotted_book_course[i]);
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

                                                                                                            per1.setText("58.33 %");

                                                                                                            //System.out.println("allotted Books Course Data Download Complete!");



                                                                                                            final ExecutorService executorServiceallottedBookBorrowDate = Executors.newSingleThreadExecutor();
                                                                                                            executorServiceallottedBookBorrowDate.execute(new Runnable() {
                                                                                                                @Override
                                                                                                                public void run() {


                                                                                                                    try {
                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_allotted_book_borrow_date.php";
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
                                                                                                                            allotted_book_borrow_date[i] = "";
                                                                                                                            allotted_book_borrow_date[i] += line;
                                                                                                                            //  //System.out.println("allotted Book Author : " + i +allotted_book_borrow_date[i]);
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

                                                                                                                            //System.out.println("allotted Books Borrow Date Data Download Complete!");

                                                                                                                            final ExecutorService executorServiceallottedReturnDate = Executors.newSingleThreadExecutor();
                                                                                                                            executorServiceallottedReturnDate.execute(new Runnable() {
                                                                                                                                @Override
                                                                                                                                public void run() {


                                                                                                                                    try {
                                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_allotted_book_return_date.php";
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
                                                                                                                                            allotted_book_return_date[i] = "";
                                                                                                                                            allotted_book_return_date[i] += line;
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


                                                                                                                                            //System.out.println("allotted Books Return Date Data Download Complete!");






                                                                                                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                                                                                                        @Override
                                                                                                                                                        public void run() {

                                                                                                                                                            per1.setText("74.99 %");

                                                                                                                                                            //System.out.println("allotted Books Late Fee Data Download Complete!");


                                                                                                                                                            final ExecutorService executorServiceallottedYop = Executors.newSingleThreadExecutor();
                                                                                                                                                            executorServiceallottedYop.execute(new Runnable() {
                                                                                                                                                                @Override
                                                                                                                                                                public void run() {


                                                                                                                                                                    try {
                                                                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_allotted_yop.php";
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
                                                                                                                                                                            allotted_book_yop[i] = "";
                                                                                                                                                                            allotted_book_yop[i] += line;
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

                                                                                                                                                                            per1.setText("83.33 %");

                                                                                                                                                                            //System.out.println("allotted Books Yop Data Download Complete!");

                                                                                                                                                                            final ExecutorService executorServiceallottedAvailableCount = Executors.newSingleThreadExecutor();
                                                                                                                                                                            executorServiceallottedAvailableCount.execute(new Runnable() {
                                                                                                                                                                                @Override
                                                                                                                                                                                public void run() {


                                                                                                                                                                                    try {
                                                                                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_allotted_available_count.php";
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
                                                                                                                                                                                            allotted_book_available_count[i] = "";
                                                                                                                                                                                            allotted_book_available_count[i] += line;
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

                                                                                                                                                                                            per1.setText("91.66 %");

                                                                                                                                                                                            //System.out.println("allotted Books Available Count Data Download Complete!");

                                                                                                                                                                                            final ExecutorService executorServiceallottedImages = Executors.newSingleThreadExecutor();
                                                                                                                                                                                            executorServiceallottedImages.execute(new Runnable() {
                                                                                                                                                                                                @Override
                                                                                                                                                                                                public void run() {


                                                                                                                                                                                                    try {
                                                                                                                                                                                                        String login_url = "https://stalinism-noun.000webhostapp.com/get_allotted_book_images.php";
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
                                                                                                                                                                                                            allotted_book_image[i] = "";
                                                                                                                                                                                                            allotted_book_image[i] += line;
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

//                                                                                                                                                                                                    int length=0;
//                                                                                                                                                                                                    int i=0;
//
//                                                                                                                                                                                                    while(!(allotted_book_name[i] ==null))
//                                                                                                                                                                                                    { length++;
//                                                                                                                                                                                                        i++;
//                                                                                                                                                                                                    }
//                                                                                                                                                                                                    spinner_allotted_book_name=new String[length];

//                                                                                                                                                                                                    for(int p=0;p<spinner_allotted_book_name.length;p++)
//                                                                                                                                                                                                    {
//                                                                                                                                                                                                        spinner_allotted_book_name[p]=allotted_book_name[p];
//                                                                                                                                                                                                    }

//                                                                                                                                                                                                    for(int p=0;p< spinner_allotted_book_name.length;p++)
//                                                                                                                                                                                                    {
//                                                                                                                                                                                                        //System.out.println("Spinner Allocated Book Data In AllottedBooksFragment  :"+spinner_allotted_book_name[p]);
//                                                                                                                                                                                                        //System.out.println();
//
//                                                                                                                                                                                                    }



                                                                                                                                                                                                    getActivity().runOnUiThread(new Runnable() {
                                                                                                                                                                                                        @Override
                                                                                                                                                                                                        public void run() {
                                                                                                                                                                                                            if ((allotted_book_image[0] == null) || allotted_book_image[0].equals("null")) {

                                                                                                                                                                                                                //System.out.println(":(allotted Book Images Don't Exist! ):");

                                                                                                                                                                                                            } else {
                                                                                                                                                                                                                //System.out.println("allotted Books Images Data Download Complete!");
                                                                                                                                                                                                                Allotted_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                                                                                                                                                                                //   Allotted_RecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                                                                                                                                                                                                                AllottedBooksCustomAdapter ca = new AllottedBooksCustomAdapter(getActivity(),allotted_book_name, allotted_book_borrow_date,
                                                                                                                                                                                                                        allotted_book_return_date, allotted_book_late_fee, allotted_book_yop, allotted_book_available_count, allotted_book_image,allotted_book_course,allotted_student_name,allotted_student_id,allotted_student_course);

                                                                                                                                                                                                                for(int i=0;i<allotted_book_course.length;i++) {
                                                                                                                                                                                                                    //System.out.println("allotted_book_course : "+allotted_book_course);
                                                                                                                                                                                                                }

                                                                                                                                                                                                                Allotted_RecyclerView.setAdapter(ca);
                                                                                                                                                                                                                firstRenderComplete=1;


                                                                                                                                                                                                            }
                                                                                                                                                                                                            per1.setText("100.00 %");

                                                                                                                                                                                                            Globals.loadingDialog.dismissDialog();
                                                                                                                                                                                                            //System.out.println("AllottedBooksFragment Dialog Dismissed!");


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
            
            
            
            
            

                    


           

           String[] allotted_spinner = {"⇓   FIND BY BOOK COURSE   ⇓","BCA","BBA","B.COM","B.TECH","LLB","BSC"};

            spinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            //spinner.setBackgroundColor(Color.GRAY);

             Object ob=null; //class level variable





// Create an ArrayAdapter using the string array and a default spinner layout

            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, allotted_spinner);
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
        //System.out.println("pos:"+position);
        if(position==0)
        { if (firstRenderComplete==1)
        {

            AllottedBooksCustomAdapter ca = new AllottedBooksCustomAdapter(getActivity(),allotted_book_name, allotted_book_borrow_date,
                    allotted_book_return_date, allotted_book_late_fee, allotted_book_yop, allotted_book_available_count, allotted_book_image,allotted_book_course,allotted_student_name,allotted_student_id,allotted_student_course);
            Allotted_RecyclerView.setAdapter(ca);



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
                    for(int i=0;i<allotted_book_course.length;i++) {
                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_borrow_date[i]= null;
                        spinner_allotted_book_available_count[i]= null;
                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_image[i]= null;
                        spinner_allotted_book_return_date[i]= null;
                        spinner_allotted_book_yop[i]= null;
                        spinner_allotted_book_course[i]= null;



                    }





                    int i=0;
                    for(int j=0;allotted_book_course[j]!=null;j++)
                    {

                        if (allotted_book_course[j].equals("BCA"))
                        {   spinner_allotted_book_name[i]=allotted_book_name[j];
                            spinner_allotted_book_course[i]=allotted_book_course[j];
                            spinner_allotted_book_borrow_date[i]=allotted_book_borrow_date[j];
                            spinner_allotted_book_available_count[i]=allotted_book_available_count[j];
                            spinner_allotted_book_return_date[i]=allotted_book_return_date[j];
                            spinner_allotted_book_image[i]=allotted_book_image[j];
                            spinner_allotted_book_late_fee[i]=allotted_book_late_fee[j];
                            spinner_allotted_book_yop[i]=allotted_book_yop[j];
                            spinner_allotted_student_name[i]=allotted_student_name[j];
                            spinner_allotted_student_id[i]=allotted_student_id[j];
                            spinner_allotted_student_course[i]=allotted_student_course[j];
                            i++;

                            //System.out.println("BCA BOOK RETURN DATE:"+allotted_book_return_date[i]);

                        }


                    }

                    for( i=0;i<allotted_book_name.length;i++)
                    {
                        //System.out.println("In Spinner Course: "+allotted_book_name[i]+allotted_book_name.length);

                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            AllottedBooksCustomAdapter ca = new AllottedBooksCustomAdapter(getActivity(),spinner_allotted_book_name, spinner_allotted_book_borrow_date,
                                    spinner_allotted_book_return_date, spinner_allotted_book_late_fee, spinner_allotted_book_yop, spinner_allotted_book_available_count, spinner_allotted_book_image,spinner_allotted_book_course,spinner_allotted_student_name,spinner_allotted_student_id,spinner_allotted_student_course);
                            Allotted_RecyclerView.setAdapter(ca);

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
                    for(int i=0;i<allotted_book_course.length;i++) {

                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_borrow_date[i]= null;
                        spinner_allotted_book_available_count[i]= null;
                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_image[i]= null;
                        spinner_allotted_book_return_date[i]= null;
                        spinner_allotted_book_yop[i]= null;
                        spinner_allotted_book_course[i]=null;
                    }
                    int i=0;

                    for(int j=0;allotted_book_course[j]!=null;j++)
                    {

                        if (allotted_book_course[j].equals("BBA"))
                        {  spinner_allotted_book_name[i]=allotted_book_name[j];
                            spinner_allotted_book_course[i]=allotted_book_course[j];
                            spinner_allotted_book_borrow_date[i]=allotted_book_borrow_date[j];
                            spinner_allotted_book_available_count[i]=allotted_book_available_count[j];
                            spinner_allotted_book_return_date[i]=allotted_book_return_date[j];
                            spinner_allotted_book_image[i]=allotted_book_image[j];
                            spinner_allotted_book_late_fee[i]=allotted_book_late_fee[j];
                            spinner_allotted_book_yop[i]=allotted_book_yop[j];
                            spinner_allotted_student_name[i]=allotted_student_name[j];
                            spinner_allotted_student_id[i]=allotted_student_id[j];
                            spinner_allotted_student_course[i]=allotted_student_course[j];
                            i++;

                            //System.out.println("BBA BOOK NAME:"+allotted_book_name[i]);

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            AllottedBooksCustomAdapter ca = new AllottedBooksCustomAdapter(getActivity(),spinner_allotted_book_name, spinner_allotted_book_borrow_date,
                                    spinner_allotted_book_return_date, spinner_allotted_book_late_fee, spinner_allotted_book_yop, spinner_allotted_book_available_count, spinner_allotted_book_image,spinner_allotted_book_course,spinner_allotted_student_name,spinner_allotted_student_id,spinner_allotted_student_course);
                            Allotted_RecyclerView.setAdapter(ca);


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

                    for(int i=0;i<allotted_book_course.length;i++) {
                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_borrow_date[i]= null;
                        spinner_allotted_book_available_count[i]= null;
                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_image[i]= null;
                        spinner_allotted_book_return_date[i]= null;
                        spinner_allotted_book_yop[i]= null;
                        spinner_allotted_book_course[i]=null;
                    }
                    int i=0;

                    for(int j=0;allotted_book_course[j]!=null;j++)
                    {


                        if (allotted_book_course[j].equals("B.COM"))
                        {   spinner_allotted_book_name[i]=allotted_book_name[j];
                            spinner_allotted_book_course[i]=allotted_book_course[j];
                            spinner_allotted_book_borrow_date[i]=allotted_book_borrow_date[j];
                            spinner_allotted_book_available_count[i]=allotted_book_available_count[j];
                            spinner_allotted_book_return_date[i]=allotted_book_return_date[j];
                            spinner_allotted_book_image[i]=allotted_book_image[j];
                            spinner_allotted_book_late_fee[i]=allotted_book_late_fee[j];
                            spinner_allotted_book_yop[i]=allotted_book_yop[j];
                            spinner_allotted_student_name[i]=allotted_student_name[j];
                            spinner_allotted_student_id[i]=allotted_student_id[j];
                            spinner_allotted_student_course[i]=allotted_student_course[j];
                            i++;

                            //System.out.println("B.COM BOOK NAME:"+allotted_book_name[i]);

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            AllottedBooksCustomAdapter ca = new AllottedBooksCustomAdapter(getActivity(),spinner_allotted_book_name, spinner_allotted_book_borrow_date,
                                    spinner_allotted_book_return_date, spinner_allotted_book_late_fee, spinner_allotted_book_yop, spinner_allotted_book_available_count, spinner_allotted_book_image,spinner_allotted_book_course,spinner_allotted_student_name,spinner_allotted_student_id,spinner_allotted_student_course);
                            Allotted_RecyclerView.setAdapter(ca);

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
                    for(int i=0;i<allotted_book_course.length;i++) {
                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_borrow_date[i]= null;
                        spinner_allotted_book_available_count[i]= null;
                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_image[i]= null;
                        spinner_allotted_book_return_date[i]= null;
                        spinner_allotted_book_yop[i]= null;
                        spinner_allotted_book_course[i]=null;
                    }

                    int i=0;

                    for(int j=0;allotted_book_course[j]!=null;j++)
                    {


                        if (allotted_book_course[j].equals("B.TECH"))
                        {  spinner_allotted_book_name[i]=allotted_book_name[j];
                            spinner_allotted_book_course[i]=allotted_book_course[j];
                            spinner_allotted_book_borrow_date[i]=allotted_book_borrow_date[j];
                            spinner_allotted_book_available_count[i]=allotted_book_available_count[j];
                            spinner_allotted_book_return_date[i]=allotted_book_return_date[j];
                            spinner_allotted_book_image[i]=allotted_book_image[j];
                            spinner_allotted_book_late_fee[i]=allotted_book_late_fee[j];
                            spinner_allotted_book_yop[i]=allotted_book_yop[j];
                            spinner_allotted_student_name[i]=allotted_student_name[j];
                            spinner_allotted_student_id[i]=allotted_student_id[j];
                            spinner_allotted_student_course[i]=allotted_student_course[j];
                            i++;

                            //System.out.println("B.TECH BOOK NAME:"+allotted_book_name[i]);


                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            AllottedBooksCustomAdapter ca = new AllottedBooksCustomAdapter(getActivity(),spinner_allotted_book_name, spinner_allotted_book_borrow_date,
                                    spinner_allotted_book_return_date, spinner_allotted_book_late_fee, spinner_allotted_book_yop, spinner_allotted_book_available_count, spinner_allotted_book_image,spinner_allotted_book_course,spinner_allotted_student_name,spinner_allotted_student_id,spinner_allotted_student_course);
                            Allotted_RecyclerView.setAdapter(ca);

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
                    for(int i=0;i<allotted_book_course.length;i++) {
                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_borrow_date[i]= null;
                        spinner_allotted_book_available_count[i]= null;
                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_image[i]= null;
                        spinner_allotted_book_return_date[i]= null;
                        spinner_allotted_book_yop[i]= null;
                        spinner_allotted_book_course[i]=null;
                    }

                    int i=0;

                    for(int j=0;allotted_book_course[j]!=null;j++)
                    {

                        if (allotted_book_course[j].equals("LLB"))
                        {  spinner_allotted_book_name[i]=allotted_book_name[j];
                            spinner_allotted_book_course[i]=allotted_book_course[j];
                            spinner_allotted_book_borrow_date[i]=allotted_book_borrow_date[j];
                            spinner_allotted_book_available_count[i]=allotted_book_available_count[j];
                            spinner_allotted_book_return_date[i]=allotted_book_return_date[j];
                            spinner_allotted_book_image[i]=allotted_book_image[j];
                            spinner_allotted_book_late_fee[i]=allotted_book_late_fee[j];
                            spinner_allotted_book_yop[i]=allotted_book_yop[j];
                            spinner_allotted_student_name[i]=allotted_student_name[j];
                            spinner_allotted_student_id[i]=allotted_student_id[j];
                            spinner_allotted_student_course[i]=allotted_student_course[j];
                            i++;

                            //System.out.println("LLB BOOK NAME:"+allotted_book_name[i]);
                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            AllottedBooksCustomAdapter ca = new AllottedBooksCustomAdapter(getActivity(),spinner_allotted_book_name, spinner_allotted_book_borrow_date,
                                    spinner_allotted_book_return_date, spinner_allotted_book_late_fee, spinner_allotted_book_yop, spinner_allotted_book_available_count, spinner_allotted_book_image,spinner_allotted_book_course,spinner_allotted_student_name,spinner_allotted_student_id,spinner_allotted_student_course);
                            Allotted_RecyclerView.setAdapter(ca);


                        }
                    });

                }
            });





        }

        else if(position==6)
        {  //System.out.println("Working In BSC");
            //    loadingDialog.startLoadingDialog();
            final ExecutorService executorServiceSpinnerBSC= Executors.newSingleThreadExecutor();
            executorServiceSpinnerBSC.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0;i<allotted_book_course.length;i++) {
                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_borrow_date[i]= null;
                        spinner_allotted_book_available_count[i]= null;
                        spinner_allotted_book_name[i]= null;
                        spinner_allotted_book_image[i]= null;
                        spinner_allotted_book_return_date[i]= null;
                        spinner_allotted_book_yop[i]= null;
                        spinner_allotted_book_course[i]=null;
                    }

                    int i=0;

                    for(int j=0;allotted_book_course[j]!=null;j++)
                    {

                        if (allotted_book_course[j].equals("BSC"))
                        {  spinner_allotted_book_name[i]=allotted_book_name[j];
                            spinner_allotted_book_course[i]=allotted_book_course[j];
                            spinner_allotted_book_borrow_date[i]=allotted_book_borrow_date[j];
                            spinner_allotted_book_available_count[i]=allotted_book_available_count[j];
                            spinner_allotted_book_return_date[i]=allotted_book_return_date[j];
                            spinner_allotted_book_image[i]=allotted_book_image[j];
                            spinner_allotted_book_late_fee[i]=allotted_book_late_fee[j];
                            spinner_allotted_book_yop[i]=allotted_book_yop[j];
                            spinner_allotted_student_name[i]=allotted_student_name[j];
                            spinner_allotted_student_id[i]=allotted_student_id[j];
                            spinner_allotted_student_course[i]=allotted_student_course[j];
                            i++;

                            //System.out.println("BSC BOOK NAME:"+allotted_book_name[i]);
                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            AllottedBooksCustomAdapter ca = new AllottedBooksCustomAdapter(getActivity(),spinner_allotted_book_name, spinner_allotted_book_borrow_date,
                                    spinner_allotted_book_return_date, spinner_allotted_book_late_fee, spinner_allotted_book_yop, spinner_allotted_book_available_count, spinner_allotted_book_image,spinner_allotted_book_course,spinner_allotted_student_name,spinner_allotted_student_id,spinner_allotted_student_course);
                            Allotted_RecyclerView.setAdapter(ca);


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

