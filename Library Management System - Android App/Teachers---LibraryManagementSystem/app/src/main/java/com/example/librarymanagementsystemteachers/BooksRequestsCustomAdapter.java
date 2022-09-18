package com.example.librarymanagementsystemteachers;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

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


import static com.example.librarymanagementsystemteachers.Globals.confirmationDialog;
import static com.example.librarymanagementsystemteachers.Globals.loadingDialog;
import static com.example.librarymanagementsystemteachers.Globals.spinner_requested_book_available_count;
import static com.example.librarymanagementsystemteachers.Globals.spinner_requested_book_name;
import static com.example.librarymanagementsystemteachers.Globals.spinner_requested_book_author;
import static com.example.librarymanagementsystemteachers.Globals.spinner_requested_book_publisher;
import static com.example.librarymanagementsystemteachers.Globals.spinner_requested_book_course;
import static com.example.librarymanagementsystemteachers.Globals.spinner_requested_book_yop;
import static com.example.librarymanagementsystemteachers.Globals.spinner_requested_book_image;
import static com.example.librarymanagementsystemteachers.Globals.spinner_requested_student_id;
import static com.example.librarymanagementsystemteachers.Globals.spinner_requested_student_course;
import static com.example.librarymanagementsystemteachers.Globals.spinner_requested_student_name;
import static com.example.librarymanagementsystemteachers.Globals.status;



public class BooksRequestsCustomAdapter extends RecyclerView.Adapter<BooksRequestsCustomAdapter.ViewHolder> {
    private String[] requested_book_name;
    private String[] requested_book_author;
    private String[] requested_book_publisher;
    private String[] requested_book_course;
    private String[] requested_book_yop;
    private String[] requested_book_available_count;
    private String[] requested_book_image;
    private String[] requested_book_student_name;
    private String[] requested_book_student_id;
    private String[] requested_book_student_course;
    private String[] grant_book=new String[5];
    private String[] deny_book=new String[5];








    private Activity activity;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView requested_book_name_field;
        private final TextView requested_book_author_field;
        private final TextView requested_book_publisher_field;
        private final TextView requested_book_course_field;
        private final TextView requested_book_yop_field;
        private final TextView requested_book_available_count_field;
        private final TextView requested_book_student_name_field;
        private final TextView requested_book_student_id_field;
        private final TextView requested_book_student_course_field;
        
        private final ImageView requested_book_image_field;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View


            requested_book_name_field=view.findViewById(R.id.requested_book_name);
            requested_book_author_field=view.findViewById(R.id.requested_author);
            requested_book_publisher_field=view.findViewById(R.id.requested_publisher);
            requested_book_course_field=view.findViewById(R.id.requested_course);
            requested_book_yop_field=view.findViewById(R.id.requested_yop);
            requested_book_available_count_field=view.findViewById(R.id.requested_available_count);
            requested_book_student_name_field=view.findViewById(R.id.requested_student_name);
            requested_book_student_id_field=view.findViewById(R.id.requested_student_id);
            requested_book_student_course_field=view.findViewById(R.id.requested_student_course);




            requested_book_image_field=view.findViewById(R.id.requested_book_image);
        



        }

        public TextView requested_book_name_field() {
            return requested_book_name_field;
        }
        public TextView requested_book_available_count_field() {
            return requested_book_available_count_field;
        }
        public TextView requested_book_author_field() {
            return requested_book_author_field;
        }
        public TextView requested_book_publisher_field() {
            return requested_book_publisher_field;
        }
        public TextView requested_book_course_field() {
            return requested_book_course_field;
        }
        public TextView requested_book_yop_field() {
            return requested_book_yop_field;
        }
       
        public TextView requested_book_student_name_field() {
            return requested_book_student_name_field;
        }
        public TextView requested_book_student_id_field() {
            return requested_book_student_id_field;
        }
        public TextView requested_book_student_course_field() { return requested_book_student_course_field; }

     





    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param requested_book_name,requested_book_author,requested_book_publisher,requested_book_course,requested_book_yop,requested_available_count,requested_book_image,requested_book_student_name,requested_book_student_id,requested_book_student_course containing the data to populate views to be used
     * by RecyclerView.
     */
    public BooksRequestsCustomAdapter(Activity activity, String[] requested_book_name, String[] requested_book_author, String[] requested_book_publisher, String[] requested_book_course, String[] requested_book_yop, String[] requested_available_count, String[] requested_book_image, String[] requested_book_student_name, String[] requested_book_student_id, String[] requested_book_student_course) {
        this.activity=activity;
        this.requested_book_name = requested_book_name;
        this.requested_book_author = requested_book_author;
        this.requested_book_publisher = requested_book_publisher;
        this.requested_book_course = requested_book_course;
        this.requested_book_yop = requested_book_yop;
        this.requested_book_available_count = requested_available_count;
        this.requested_book_image=requested_book_image;
        this.requested_book_student_name = requested_book_student_name;
        this.requested_book_student_id = requested_book_student_id;
        this.requested_book_student_course=requested_book_student_course;


    }

    // Create new views (invoked by the layout manager)
    @Override
    public BooksRequestsCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.books_requests_layout, viewGroup, false);



        return new BooksRequestsCustomAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(BooksRequestsCustomAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.requested_book_name_field().setText(requested_book_name[position]);
        viewHolder.requested_book_author_field().setText(requested_book_author[position]);
        viewHolder.requested_book_publisher_field().setText(requested_book_publisher[position]);
        viewHolder.requested_book_course_field().setText(requested_book_course[position]);
        viewHolder.requested_book_yop_field().setText(requested_book_yop[position]);
        viewHolder.requested_book_available_count_field().setText(requested_book_available_count[position]);
        viewHolder.requested_book_student_name_field().setText(requested_book_student_name[position]);
        viewHolder.requested_book_student_id_field().setText(requested_book_student_id[position]);
        viewHolder.requested_book_student_course_field().setText(requested_book_student_course[position]);


        byte[] decodedImage = Base64.decode(requested_book_image[position].getBytes(), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
//        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
//                bmp, 775, 1460, false);
        // 775/1460=0.5308219178082192;
        viewHolder.requested_book_image_field.setImageBitmap(bmp);










        viewHolder.itemView.findViewById(R.id.grant_retain_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                NetworkUtil.setConnectivityStatus(activity.getApplicationContext());
                if (status != 0) {



                    confirmationDialog  = new ConfirmationDialog(activity);
                    AlertDialog dialog=confirmationDialog.startConfirmationDialog();

                    TextView  confirm_heading_text= dialog.findViewById(R.id.confirm_heading_text);
                    confirm_heading_text.setText("Are You Sure You Want To Grant : "+requested_book_name[position]+" To : "+requested_book_student_name[position]+" ?");

                    dialog.findViewById(R.id.yes_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //System.out.println("Grant Yes Working!!");
                            confirmationDialog.dismissConfirmationDialog();





                            loadingDialog = new LoadingDialog(activity);
                            // //System.out.println("==================="+loadingDialog.isCancelled());
                            AlertDialog percentage1 =  loadingDialog.startLoadingDialog();
                            TextView per1= percentage1.findViewById(R.id.progress_percentage);
                            per1.setText("50.00 %");

                            final ExecutorService executorServicerequestedGrantBook = Executors.newSingleThreadExecutor();
                            executorServicerequestedGrantBook.execute(new Runnable() {
                                @Override
                                public void run() {


                                    try {
                                        String login_url = "https://stalinism-noun.000webhostapp.com/grant_requested_book.php";
                                        //System.out.println("running-----------------------------------------------------------------------");

                                        URL url=new URL(login_url);
                                        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoOutput(true);
                                        httpURLConnection.setDoInput(true);
                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(requested_book_student_id[position],"UTF-8")+"&"+
                                                URLEncoder.encode("book_name","UTF-8")+"="+URLEncoder.encode(requested_book_name[position],"UTF-8");

                                        bufferedWriter.write(post_data);
                                        bufferedWriter.flush();
                                        bufferedWriter.close();
                                        outputStream.close();

                                        InputStream inputStream = httpURLConnection.getInputStream();
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                        String line = "";
                                        int i = 0;
                                        while ((line = bufferedReader.readLine()) != null) {

                                            grant_book[i] = "";
                                            grant_book[i] += line;
                                            //System.out.println("grant Book Name : " + "["+i+"]" +grant_book[i]);
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


                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(grant_book[0]==null ||grant_book[0].equals("null"))
                                            {
                                                int duration = Toast.LENGTH_SHORT;

                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" + "Either Their Books Limit Reached Max Or Book Not Available , Report To Developer !"  + " </b> </font>"), duration);
                                                loadingDialog.dismissDialog();
                                                notifyDataSetChanged();
                                                toast.show();

                                            }


                                            else
                                            {

                                                int duration = Toast.LENGTH_SHORT;
                                                //
                                                // //System.out.println("grant_book[0]==" + grant_book[0] + "grant_book[1]==" + grant_book[1]);
                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" +" "+ requested_book_name[position] +"'s Allocation To : "+requested_book_student_name[position]+" Successful !" + " </b> </font>"), duration);
                                                viewHolder.requested_book_available_count_field().setText(grant_book[0]);

                                                toast.show();

                                                final ExecutorService executorServiceShift1 = Executors.newSingleThreadExecutor();
                                                executorServiceShift1.execute(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                //System.out.println("Before Shifting");
                                                for(int i=0;i< requested_book_name.length;i++)
                                                {
                                                    //System.out.println("position :"+i +"value :"+requested_book_name[i]);


                                                }

                                                String grantbuttonbookname=requested_book_name[position];
                                                //System.out.println("requested_book_available_count"+grant_book[0]);
                                                for(int i=position;i< requested_book_name.length-1;i++)
                                                {

                                                    requested_book_available_count[i]=requested_book_available_count[i+1];
                                                    requested_book_name[i]=requested_book_name[i+1];
                                                    requested_book_author[i]=requested_book_author[i+1];
                                                    requested_book_publisher[i]=requested_book_publisher[i+1];
                                                    requested_book_course[i]=requested_book_course[i+1];
                                                    requested_book_image[i]=requested_book_image[i+1];
                                                    requested_book_yop[i]=requested_book_yop[i+1];
                                                    requested_book_student_name[i]=requested_book_student_name[i+1];
                                                    requested_book_student_id[i]=requested_book_student_id[i+1];
                                                    requested_book_student_course[i]=requested_book_student_course[i+1];


                                                }





                                                for(int i=0;i< requested_book_name.length;i++) {
                                                    if (requested_book_name[i]!=null && requested_book_name[i].equals(grantbuttonbookname)) {
                                                        requested_book_available_count[i] = grant_book[0];
                                                        //System.out.println("requested_book_available_count" + requested_book_available_count[i]);
                                                    }
                                                }









                                                spinner_requested_book_name=new String[requested_book_name.length];
                                                spinner_requested_book_author=new String[requested_book_author.length];
                                                spinner_requested_book_publisher=new String[requested_book_publisher.length];
                                                spinner_requested_book_course=new String[requested_book_course.length];
                                                spinner_requested_book_yop=new String[requested_book_yop.length];
                                                spinner_requested_book_available_count=new String[requested_book_available_count.length];
                                                spinner_requested_student_name=new String[requested_book_student_name.length];
                                                spinner_requested_student_id=new String[requested_book_student_id.length];
                                                spinner_requested_student_course=new String[requested_book_student_course.length];




                                                for( int i=0;i< requested_book_name.length;i++)
                                                {


                                                    spinner_requested_book_name[i]=requested_book_name[i];
                                                    spinner_requested_book_author[i]=requested_book_author[i];
                                                    spinner_requested_book_publisher[i]=requested_book_publisher[i];
                                                    spinner_requested_book_course[i]=requested_book_course[i];
                                                    spinner_requested_book_yop[i]=requested_book_yop[i];
                                                    spinner_requested_book_available_count[i]=requested_book_available_count[i];
                                                    spinner_requested_book_image[i]=requested_book_image[i];
                                                    spinner_requested_book_course[i]=requested_book_course[i];
                                                    spinner_requested_student_name[i]=requested_book_student_name[i];
                                                    spinner_requested_student_id[i]=requested_book_student_id[i];
                                                    spinner_requested_student_course[i]=requested_book_student_course[i];




                                                }





                                                //System.out.println("After Shifting");
                                                for( int i=0;i< requested_book_name.length;i++)
                                                {
                                                    //System.out.println("position :"+i +"value :"+requested_book_name[i]);


                                                }

                                                        activity.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                loadingDialog.dismissDialog();
                                                                notifyDataSetChanged();
                                                                //System.out.println("Grant Book Complete!");

                                                            }
                                                        });
                                                    }
                                                });


                                            }



                                        }
                                    });

                                }
                            });



                        }
                    });

                    dialog.findViewById(R.id.no_button).setOnClickListener(new View.OnClickListener() {
                                                                                @Override
                                                                                public void onClick(View v) {
                                                                                    //System.out.println("Grant No Working!!");
                                                                                    confirmationDialog.dismissConfirmationDialog();

                                                                                }
                                                                            });


//                    //System.out.println("button " + position + " pressed");
//                    //System.out.println(requested_book_name[position]);

                }
                else
                {

                    Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" + ":( " + "Internet Connection Not Found !" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

                    toast.show();

                }
            }
        });

        viewHolder.itemView.findViewById(R.id.deny_retain_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                NetworkUtil.setConnectivityStatus(activity.getApplicationContext());
                if (status != 0) {


                    confirmationDialog = new ConfirmationDialog(activity);
                    AlertDialog dialog = confirmationDialog.startConfirmationDialog();
                    TextView  confirm_heading_text= dialog.findViewById(R.id.confirm_heading_text);
                    confirm_heading_text.setText("Are You Sure You Want To Deny : "+requested_book_name[position]+" To : "+requested_book_student_name[position]+" ?");

                    dialog.findViewById(R.id.yes_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            confirmationDialog.dismissConfirmationDialog();

                            //System.out.println("Deny Yes Working!!");
                            confirmationDialog.dismissConfirmationDialog();





                            loadingDialog = new LoadingDialog(activity);
                            // //System.out.println("==================="+loadingDialog.isCancelled());
                            AlertDialog percentage2 =  loadingDialog.startLoadingDialog();
                            TextView per2= percentage2.findViewById(R.id.progress_percentage);
                            per2.setText("50.00 %");


                            final ExecutorService executorServicerequestedDenyBook = Executors.newSingleThreadExecutor();
                            executorServicerequestedDenyBook.execute(new Runnable() {
                                @Override
                                public void run() {


                                    try {
                                        String login_url = "https://stalinism-noun.000webhostapp.com/deny_requested_book.php";
                                        //System.out.println("running-----------------------------------------------------------------------");

                                        URL url=new URL(login_url);
                                        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoOutput(true);
                                        httpURLConnection.setDoInput(true);
                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(requested_book_student_id[position],"UTF-8")+"&"+
                                                URLEncoder.encode("book_name","UTF-8")+"="+URLEncoder.encode(requested_book_name[position],"UTF-8");

                                        bufferedWriter.write(post_data);
                                        bufferedWriter.flush();
                                        bufferedWriter.close();
                                        outputStream.close();

                                        InputStream inputStream = httpURLConnection.getInputStream();
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                        String line = "";
                                        int i = 0;
                                        while ((line = bufferedReader.readLine()) != null) {

                                            deny_book[i] = "";
                                            deny_book[i] += line;
                                            //System.out.println("Deny Book Name : " + "["+i+"]" +deny_book[i]);
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


                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(deny_book[0]==null ||deny_book[0].equals("null"))
                                            {
                                                int duration = Toast.LENGTH_SHORT;
                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" +":( "+requested_book_name[position] +"'s Request Not Found ! ):"  + " </b> </font>"), duration);

                                                per2.setText("100.00 %");
                                                loadingDialog.dismissDialog();
                                                notifyDataSetChanged();
                                                toast.show();
                                            }

                                            else
                                            {
                                                //System.out.println("inside else");
                                                int duration = Toast.LENGTH_SHORT;
                                                //
                                                // //System.out.println("deny_book[0]==" + deny_book[0] + "deny_book[1]==" + deny_book[1]);
                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" + requested_book_name[position] +"'s Allocation To : " +requested_book_student_name[position]+" Is Denied !" + " </b> </font>"), duration);
                                                toast.show();

                                                final ExecutorService executorServiceShift2 = Executors.newSingleThreadExecutor();
                                                executorServiceShift2.execute(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                //System.out.println("Before Shifting");
                                                for(int i=0;i< requested_book_name.length;i++)
                                                {
                                                    //System.out.println("position :"+i +"value :"+requested_book_name[i]);


                                                }


                                                for(int i=position;i< requested_book_name.length-1;i++)
                                                {

                                                    requested_book_available_count[i]=requested_book_available_count[i+1];
                                                    requested_book_name[i]=requested_book_name[i+1];
                                                    requested_book_author[i]=requested_book_author[i+1];
                                                    requested_book_publisher[i]=requested_book_publisher[i+1];
                                                    requested_book_course[i]=requested_book_course[i+1];
                                                    requested_book_yop[i]=requested_book_yop[i+1];
                                                    requested_book_student_name[i]=requested_book_student_name[i+1];
                                                    requested_book_student_id[i]=requested_book_student_id[i+1];
                                                    requested_book_student_course[i]=requested_book_student_course[i+1];


                                                }


                                                spinner_requested_book_name=new String[requested_book_name.length];
                                                spinner_requested_book_author=new String[requested_book_author.length];
                                                spinner_requested_book_publisher=new String[requested_book_publisher.length];
                                                spinner_requested_book_course=new String[requested_book_course.length];
                                                spinner_requested_book_yop=new String[requested_book_yop.length];
                                                spinner_requested_book_available_count=new String[requested_book_available_count.length];
                                                spinner_requested_student_name=new String[requested_book_student_name.length];
                                                spinner_requested_student_id=new String[requested_book_student_id.length];
                                                spinner_requested_student_course=new String[requested_book_student_course.length];




                                                for(int i=0;i< requested_book_name.length;i++)
                                                {


                                                    spinner_requested_book_name[i]=requested_book_name[i];
                                                    spinner_requested_book_author[i]=requested_book_author[i];
                                                    spinner_requested_book_publisher[i]=requested_book_publisher[i];
                                                    spinner_requested_book_course[i]=requested_book_course[i];
                                                    spinner_requested_book_yop[i]=requested_book_yop[i];
                                                    spinner_requested_book_available_count[i]=requested_book_available_count[i];
                                                    spinner_requested_book_image[i]=requested_book_image[i];
                                                    spinner_requested_book_course[i]=requested_book_course[i];
                                                    spinner_requested_student_name[i]=requested_book_student_name[i];
                                                    spinner_requested_student_id[i]=requested_book_student_id[i];
                                                    spinner_requested_student_course[i]=requested_book_student_course[i];




                                                }





                                                //System.out.println("After Shifting");
                                                for(int i=0;i< requested_book_name.length;i++)
                                                {
                                                    //System.out.println("position :"+i +"value :"+requested_book_name[i]);


                                                }

                                                        activity.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                per2.setText("100.00 %");
                                                                loadingDialog.dismissDialog();
                                                                notifyDataSetChanged();
                                                                //System.out.println("Deny Book Complete!");

                                                            }
                                                        });
                                                    }
                                                });


                                            }



                                        }
                                    });

                                }
                            });








                        }
                    });

                    dialog.findViewById(R.id.no_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //System.out.println("Deny No Working!!");
                            confirmationDialog.dismissConfirmationDialog();

                        }
                    });





                }
                else
                {

                    Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" + ":( " + "Internet Connection Not Found !" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

                    toast.show();

                }

                }
        });


    }


    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {



        int length=0;
        int i=0;

        while(!(requested_book_name[i] ==null))
        { length++;
            i++;
        }
        //System.out.println("length:"+length);
        return length;
    }
}
