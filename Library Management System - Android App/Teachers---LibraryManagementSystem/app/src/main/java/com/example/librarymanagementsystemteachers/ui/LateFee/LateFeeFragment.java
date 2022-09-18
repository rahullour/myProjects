package com.example.librarymanagementsystemteachers.ui.LateFee;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanagementsystemteachers.LateFeeCustomAdapter;
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
import static com.example.librarymanagementsystemteachers.Globals.status;





public class LateFeeFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    int firstRenderComplete =0;

    RecyclerView LateFee_RecyclerView;

    String[] late_fee_student_late_fee =new String[100];
    String[] late_fee_student_course =new String[100];
    String[] late_fee_student_name =new String[100];
    String[] late_fee_student_id =new String[100];
    String[] late_fee_student_book_count =new String[100];
    Spinner spinner;



    String[] spinner_late_fee_student_late_fee =new String[100];
    String[] spinner_late_fee_student_course =new String[100];
    String[] spinner_late_fee_student_name =new String[100];
    String[] spinner_late_fee_student_id =new String[100];
    String[] spinner_late_fee_student_book_count =new String[100];




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_late_fee, container, false);
        LateFee_RecyclerView = root.findViewById(R.id.late_fee_recyclerview);
        spinner = (Spinner) root.findViewById(R.id.late_fee_spinner);

        Button late_fee_button=root.findViewById(R.id.late_fee_button);
        EditText late_fee_find_by_id=root.findViewById(R.id.late_fee_find_by_id);

        late_fee_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager inputMethodManager = (InputMethodManager)getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);

                String SearchIdText = late_fee_find_by_id.getText().toString().trim();
                late_fee_find_by_id.setText("");

                if (SearchIdText.isEmpty()) {
                    spinner.setSelection(0);
                    LateFeeCustomAdapter ca = new LateFeeCustomAdapter(getActivity(),late_fee_student_late_fee ,late_fee_student_name,late_fee_student_id,late_fee_student_course,late_fee_student_book_count );
                    LateFee_RecyclerView.setAdapter(ca);

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
                            for (int i = 0; i < late_fee_student_id.length; i++) {

                                spinner_late_fee_student_name[i] = null;
                                spinner_late_fee_student_id[i] = null;
                                spinner_late_fee_student_course[i] = null;
                                spinner_late_fee_student_late_fee[i] = null;
                                spinner_late_fee_student_book_count[i]=null;
                                


                            }


                            int i = 0;
                            for (int j = 0; late_fee_student_id[j] != null; j++) {

                                if (late_fee_student_id[j].equals(SearchIdText)) {

                                    spinner_late_fee_student_name[i]=late_fee_student_name[j];
                                    spinner_late_fee_student_id[i]=late_fee_student_id[j];
                                    spinner_late_fee_student_course[i]=late_fee_student_course[j];
                                    spinner_late_fee_student_late_fee[i]=late_fee_student_late_fee[j];
                                    spinner_late_fee_student_book_count[i]=late_fee_student_book_count[j];

                                    i++;

                                    //System.out.println("Search Button Student Id::" + late_fee_student_id[i]);
                                    found[0] =true;
                                }


                            }

                            for (i = 0; i < late_fee_student_name.length; i++) {
                                //System.out.println("In LateFee Search Button: " + late_fee_student_name[i] + late_fee_student_name.length);

                            }


                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(found[0] ==true)
                                    {
                                        LateFeeCustomAdapter ca = new LateFeeCustomAdapter(getActivity(),late_fee_student_late_fee ,late_fee_student_name,late_fee_student_id,late_fee_student_course,late_fee_student_book_count );
                                        LateFee_RecyclerView.setAdapter(ca);

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




            loadingDialog = new LoadingDialog(getActivity());

            AlertDialog percentage1 =  loadingDialog.startLoadingDialog();
            TextView per1= percentage1.findViewById(R.id.progress_percentage);



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


                        //System.out.println("Late Fee Data Update Complete !");

                        per1.setText("16.66 %");


                        final ExecutorService executorServiceStudentLateFee = Executors.newSingleThreadExecutor();
        executorServiceStudentLateFee.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String login_url = "https://stalinism-noun.000webhostapp.com/get_late_fee_student_late_fee.php";
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

                        late_fee_student_late_fee[i] = "";
                        late_fee_student_late_fee[i] += line;
                        //System.out.println("late_fee_student_late_fee : " + i +late_fee_student_late_fee[i]);
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
                        //System.out.println("late_fee_student_late_fee Data Download Complete!");
                        per1.setText("33.33 %");
                        final ExecutorService executorServiceStudentId = Executors.newSingleThreadExecutor();
                        executorServiceStudentId.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_late_fee_student_id.php";
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

                                        late_fee_student_id[i] = "";
                                        late_fee_student_id[i] += line;
                                        //System.out.println("late_fee_student_id : " + i +late_fee_student_id[i]);
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
                                        //System.out.println("late_fee_student_id Data Download Complete!");
                                        per1.setText("49.99 %");
                                        final ExecutorService executorServiceStudentCourse = Executors.newSingleThreadExecutor();
                                        executorServiceStudentCourse.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                try {
                                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_late_fee_student_course.php";
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

                                                        late_fee_student_course[i] = "";
                                                        late_fee_student_course[i] += line;
                                                        //System.out.println("late_fee_student_course : " + i +late_fee_student_course[i]);
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
                                                        //System.out.println("late_fee_student_course Data Download Complete!");
                                                        per1.setText("66.66 %");
                                                        final ExecutorService executorServiceStudentName = Executors.newSingleThreadExecutor();
                                                        executorServiceStudentName.execute(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                try {
                                                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_late_fee_student_name.php";
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

                                                                        late_fee_student_name[i] = "";
                                                                        late_fee_student_name[i] += line;
                                                                        //System.out.println("late_fee Student Name : " + i +late_fee_student_name[i]);
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
                                                                        //System.out.println("late_fee_student_name Data Download Complete!");
                                                                        per1.setText("83.33 %");

                                                                        final ExecutorService executorServiceStudentBookCount = Executors.newSingleThreadExecutor();
                                                                        executorServiceStudentBookCount.execute(new Runnable() {
                                                                            @Override
                                                                            public void run() {
                                                                                try {
                                                                                    String login_url = "https://stalinism-noun.000webhostapp.com/get_late_fee_student_book_count.php";
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

                                                                                        late_fee_student_book_count[i] = "";
                                                                                        late_fee_student_book_count[i] += line;
                                                                                        //System.out.println("late_fee_student_book_count : " + i +late_fee_student_book_count[i]);
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
                                                                                        //System.out.println("late_fee_student_book_count Data Download Complete!");
                                                                                        per1.setText("100.00 %");
                                                                                        loadingDialog.dismissDialog();


                                                                                        LateFee_RecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                                                                        LateFeeCustomAdapter ca = new LateFeeCustomAdapter(getActivity(),late_fee_student_late_fee ,late_fee_student_name,late_fee_student_id,late_fee_student_course,late_fee_student_book_count );
                                                                                        LateFee_RecyclerView.setAdapter(ca);

                                                                                        firstRenderComplete=1;


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




            String[] late_fee_spinner = {"⇓   FIND BY STUDENT COURSE   ⇓","BCA","BBA","B.COM","B.TECH","LLB","BSC"};

            spinner.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            //spinner.setBackgroundColor(Color.GRAY);

            Object ob=null; //class level variable





// Create an ArrayAdapter using the string array and a default spinner layout

            ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, late_fee_spinner);
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

            LateFeeCustomAdapter ca = new LateFeeCustomAdapter(getActivity(),late_fee_student_late_fee ,late_fee_student_name,late_fee_student_id,late_fee_student_course,late_fee_student_book_count );
            LateFee_RecyclerView.setAdapter(ca);


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
                    for(int i=0;i<late_fee_student_course.length;i++) {
                        spinner_late_fee_student_name[i] = null;
                                spinner_late_fee_student_id[i] = null;
                                spinner_late_fee_student_course[i] = null;
                                spinner_late_fee_student_late_fee[i] = null;
                                spinner_late_fee_student_book_count[i]=null;




                    }





                    int i=0;
                    for(int j=0;late_fee_student_course[j]!=null;j++)
                    {

                        if (late_fee_student_course[j].equals("BCA"))
                        {
                            spinner_late_fee_student_name[i]=late_fee_student_name[j];
                            spinner_late_fee_student_id[i]=late_fee_student_id[j];
                            spinner_late_fee_student_course[i]=late_fee_student_course[j];
                            spinner_late_fee_student_late_fee[i]=late_fee_student_late_fee[j];
                            spinner_late_fee_student_book_count[i]=late_fee_student_book_count[j];
                            i++;

                        

                        }


                    }

                    for( i=0;i<late_fee_student_name.length;i++)
                    {
                        //System.out.println("In Spinner Course: "+late_fee_student_name[i]+late_fee_student_name.length);

                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LateFeeCustomAdapter ca = new LateFeeCustomAdapter(getActivity(),spinner_late_fee_student_late_fee ,spinner_late_fee_student_name,spinner_late_fee_student_id,spinner_late_fee_student_course,spinner_late_fee_student_book_count );
                            LateFee_RecyclerView.setAdapter(ca);

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
                    for(int i=0;i<late_fee_student_course.length;i++) {

                        spinner_late_fee_student_name[i] = null;
                                spinner_late_fee_student_id[i] = null;
                                spinner_late_fee_student_course[i] = null;
                                spinner_late_fee_student_late_fee[i] = null;
                                spinner_late_fee_student_book_count[i]=null;
                    }
                    int i=0;

                    for(int j=0;late_fee_student_course[j]!=null;j++)
                    {

                        if (late_fee_student_course[j].equals("BBA"))
                        {    spinner_late_fee_student_name[i]=late_fee_student_name[j];
                            spinner_late_fee_student_id[i]=late_fee_student_id[j];
                            spinner_late_fee_student_course[i]=late_fee_student_course[j];
                            spinner_late_fee_student_late_fee[i]=late_fee_student_late_fee[j];
                            spinner_late_fee_student_book_count[i]=late_fee_student_book_count[j];
                            i++;

                            //System.out.println("BBA student NAME:"+late_fee_student_name[i]);

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LateFeeCustomAdapter ca = new LateFeeCustomAdapter(getActivity(),spinner_late_fee_student_late_fee ,spinner_late_fee_student_name,spinner_late_fee_student_id,spinner_late_fee_student_course,spinner_late_fee_student_book_count );
                            LateFee_RecyclerView.setAdapter(ca);


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

                    for(int i=0;i<late_fee_student_course.length;i++) {
                        spinner_late_fee_student_name[i] = null;
                                spinner_late_fee_student_id[i] = null;
                                spinner_late_fee_student_course[i] = null;
                                spinner_late_fee_student_late_fee[i] = null;
                                spinner_late_fee_student_book_count[i]=null;
                    }
                    int i=0;

                    for(int j=0;late_fee_student_course[j]!=null;j++)
                    {


                        if (late_fee_student_course[j].equals("B.COM"))
                        {     spinner_late_fee_student_name[i]=late_fee_student_name[j];
                            spinner_late_fee_student_id[i]=late_fee_student_id[j];
                            spinner_late_fee_student_course[i]=late_fee_student_course[j];
                            spinner_late_fee_student_late_fee[i]=late_fee_student_late_fee[j];
                            spinner_late_fee_student_book_count[i]=late_fee_student_book_count[j];
                            i++;

                            //System.out.println("B.COM student NAME:"+late_fee_student_name[i]);

                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LateFeeCustomAdapter ca = new LateFeeCustomAdapter(getActivity(),spinner_late_fee_student_late_fee ,spinner_late_fee_student_name,spinner_late_fee_student_id,spinner_late_fee_student_course,spinner_late_fee_student_book_count );
                            LateFee_RecyclerView.setAdapter(ca);


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
                    for(int i=0;i<late_fee_student_course.length;i++) {
                        spinner_late_fee_student_name[i] = null;
                                spinner_late_fee_student_id[i] = null;
                                spinner_late_fee_student_course[i] = null;
                                spinner_late_fee_student_late_fee[i] = null;
                                spinner_late_fee_student_book_count[i]=null;
                    }

                    int i=0;

                    for(int j=0;late_fee_student_course[j]!=null;j++)
                    {


                        if (late_fee_student_course[j].equals("B.TECH"))
                        {    spinner_late_fee_student_name[i]=late_fee_student_name[j];
                            spinner_late_fee_student_id[i]=late_fee_student_id[j];
                            spinner_late_fee_student_course[i]=late_fee_student_course[j];
                            spinner_late_fee_student_late_fee[i]=late_fee_student_late_fee[j];
                            spinner_late_fee_student_book_count[i]=late_fee_student_book_count[j];
                            i++;

                            //System.out.println("B.TECH student NAME:"+late_fee_student_name[i]);


                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LateFeeCustomAdapter ca = new LateFeeCustomAdapter(getActivity(),spinner_late_fee_student_late_fee ,spinner_late_fee_student_name,spinner_late_fee_student_id,spinner_late_fee_student_course,spinner_late_fee_student_book_count );
                            LateFee_RecyclerView.setAdapter(ca);

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
                    for(int i=0;i<late_fee_student_course.length;i++) {
                        spinner_late_fee_student_name[i] = null;
                                spinner_late_fee_student_id[i] = null;
                                spinner_late_fee_student_course[i] = null;
                                spinner_late_fee_student_late_fee[i] = null;
                                spinner_late_fee_student_book_count[i]=null;
                    }

                    int i=0;

                    for(int j=0;late_fee_student_course[j]!=null;j++)
                    {

                        if (late_fee_student_course[j].equals("LLB"))
                        {   spinner_late_fee_student_name[i]=late_fee_student_name[j];
                            spinner_late_fee_student_id[i]=late_fee_student_id[j];
                            spinner_late_fee_student_course[i]=late_fee_student_course[j];
                            spinner_late_fee_student_late_fee[i]=late_fee_student_late_fee[j];
                            spinner_late_fee_student_book_count[i]=late_fee_student_book_count[j];
                            i++;

                            //System.out.println("LLB student NAME:"+late_fee_student_name[i]);
                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LateFeeCustomAdapter ca = new LateFeeCustomAdapter(getActivity(),spinner_late_fee_student_late_fee ,spinner_late_fee_student_name,spinner_late_fee_student_id,spinner_late_fee_student_course,spinner_late_fee_student_book_count );
                            LateFee_RecyclerView.setAdapter(ca);


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
                    for(int i=0;i<late_fee_student_course.length;i++) {
                        spinner_late_fee_student_name[i] = null;
                                spinner_late_fee_student_id[i] = null;
                                spinner_late_fee_student_course[i] = null;
                                spinner_late_fee_student_late_fee[i] = null;
                                spinner_late_fee_student_book_count[i]=null;
                    }

                    int i=0;

                    for(int j=0;late_fee_student_course[j]!=null;j++)
                    {

                        if (late_fee_student_course[j].equals("BSC"))
                        {   spinner_late_fee_student_name[i]=late_fee_student_name[j];
                            spinner_late_fee_student_id[i]=late_fee_student_id[j];
                            spinner_late_fee_student_course[i]=late_fee_student_course[j];
                            spinner_late_fee_student_late_fee[i]=late_fee_student_late_fee[j];
                            spinner_late_fee_student_book_count[i]=late_fee_student_book_count[j];
                            i++;

                            //System.out.println("BSC student NAME:"+late_fee_student_name[i]);
                        }


                    }



                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            LateFeeCustomAdapter ca = new LateFeeCustomAdapter(getActivity(),spinner_late_fee_student_late_fee ,spinner_late_fee_student_name,spinner_late_fee_student_id,spinner_late_fee_student_course,spinner_late_fee_student_book_count );
                            LateFee_RecyclerView.setAdapter(ca);


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

