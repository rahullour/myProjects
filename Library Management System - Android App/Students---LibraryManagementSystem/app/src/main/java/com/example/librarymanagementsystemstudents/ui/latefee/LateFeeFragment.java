package com.example.librarymanagementsystemstudents.ui.latefee;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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

import static com.example.librarymanagementsystemstudents.Globals.latefee;
import static com.example.librarymanagementsystemstudents.Globals.loadingDialog;
import static com.example.librarymanagementsystemstudents.Globals.status;
import static com.example.librarymanagementsystemstudents.Globals.user_id;

public class LateFeeFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_late_fee, container, false);

        NetworkUtil.setConnectivityStatus(getContext());
        if (status != 0) {


           Globals.loadingDialog=new LoadingDialog(getActivity());
            AlertDialog percentage1 =  Globals.loadingDialog.startLoadingDialog();
            TextView per1= percentage1.findViewById(R.id.progress_percentage);
            per1.setText("50.00 %");

        final ExecutorService executorServiceLateFee= Executors.newSingleThreadExecutor();
        executorServiceLateFee.execute(new Runnable() {
            @Override
            public void run() {


                try {
                    String login_url = "https://stalinism-noun.000webhostapp.com/get_late_fee.php";
                    //System.out.println("running-----------------------------------------------------------------------");

                    URL url=new URL(login_url);
                    HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
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

                        latefee[i] = "";
                        latefee[i] += line;
                          //System.out.println("Late Fragment Late Fee : " + i +latefee[i]);
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
                        per1.setText("100.00 %");
                          loadingDialog.dismissDialog();
                        //System.out.println("Late Fee Data Download Complete!");
                      TextView late_fee= root.findViewById(R.id.late_fee);

                      if(latefee[0]==null ||latefee[0].equals("null"))
                      {
                          late_fee.setText("Your Payable Amount Is : 0 Rs.");
                      }
                      else
                      {
                          late_fee.setText("Your Payable Amount Is : "+latefee[0]+" Rs.");
                      }

                    }
                });

            }
        });
        }
        else {
            Toast toast = Toast.makeText(getContext(), Html.fromHtml("<font color='#FF0000' > <b>" + ":( " + "Internet Connection Not Found !" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

            toast.show();
        }
        return root;

    }



    }
