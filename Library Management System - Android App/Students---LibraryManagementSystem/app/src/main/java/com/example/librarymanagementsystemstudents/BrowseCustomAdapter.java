package com.example.librarymanagementsystemstudents;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
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
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import static com.example.librarymanagementsystemstudents.Globals.confirmationDialog;
import static com.example.librarymanagementsystemstudents.Globals.loadingDialog;
import static com.example.librarymanagementsystemstudents.Globals.spinner_browse_get_button_book_name;
import static com.example.librarymanagementsystemstudents.Globals.spinner_browse_pending_book_name;
import static com.example.librarymanagementsystemstudents.Globals.status;
import static com.example.librarymanagementsystemstudents.Globals.user_id;
import static com.example.librarymanagementsystemstudents.Globals.get_book;

public class  BrowseCustomAdapter extends RecyclerView.Adapter<BrowseCustomAdapter.ViewHolder> {
    private String[] browse_book_name;
    private String[] browse_author;
    private String[] browse_publisher;
    private String[] browse_course;
    private String[] browse_yop;
    private String[] internal_browse_available_count;
    private String[] browse_image;
    private int pending=0;

   
    private Activity activity;

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView browse_book_name_field;
        private final TextView browse_author_field;
        private final TextView browse_publisher_field;
        private final TextView browse_course_field;
        private final TextView browse_yop_field;
        private final TextView browse_available_count_field;
        private final Button browse_get_button_book_name_button;
        private final ImageView browse_image_field;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View


             browse_book_name_field=view.findViewById(R.id.browse_book_name);
             browse_author_field=view.findViewById(R.id.browse_author);


             browse_publisher_field=view.findViewById(R.id.browse_publisher);
             browse_course_field=view.findViewById(R.id.browse_course);
             browse_yop_field=view.findViewById(R.id.browse_yop);
             browse_available_count_field=view.findViewById(R.id.browse_available_count);
             browse_image_field=view.findViewById(R.id.browse_book_image);
             browse_get_button_book_name_button=view.findViewById(R.id.get_button);
        }

        public TextView browse_book_name_field() {
            return browse_book_name_field;
        }
        public TextView browse_author_field() {
            return browse_author_field;
        }
        public TextView browse_publisher_field() {
            return browse_publisher_field;
        }
        public TextView browse_course_field() {
            return browse_course_field;
        }
        public TextView browse_yop_field() {
            return browse_yop_field;
        }
        public TextView browse_available_count_field() { return browse_available_count_field; }
        public Button browse_get_button_book_name_button() { return browse_get_button_book_name_button; }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param browse_book_name,browse_author,browse_publisher,browse_course,browse_yop,browse_available_count,browse_image,browse_get_button containing the data to populate views to be used
     * by RecyclerView.
     */
    public BrowseCustomAdapter(Activity activity, String[] browse_book_name, String[] browse_author, String[] browse_publisher, String[] browse_course, String[] browse_yop, String[] browse_available_count, String[] browse_image) {
        this.activity=activity;
        this.browse_book_name = browse_book_name;
        this.browse_author = browse_author;
        this.browse_publisher = browse_publisher;
        this.browse_course = browse_course;
        this.browse_yop = browse_yop;
        this.internal_browse_available_count = browse_available_count;
        this.browse_image=browse_image;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public BrowseCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.browse_books_layout, viewGroup, false);



        return new BrowseCustomAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(BrowseCustomAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.browse_book_name_field().setText(browse_book_name[position]);
        viewHolder.browse_author_field().setText(browse_author[position]);
        viewHolder.browse_publisher_field().setText(browse_publisher[position]);
        viewHolder.browse_course_field().setText(browse_course[position]);
        viewHolder.browse_yop_field().setText(browse_yop[position]);
        viewHolder.browse_available_count_field().setText(internal_browse_available_count[position]);

       // System.out.println("Error In Book Image :"+browse_book_name[position] + " browse_image:"+browse_image[position]);
        byte[] decodedImage = Base64.decode(browse_image[position].getBytes(), Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
//        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
//                bmp, 1225, 1875, false);
        // 1225/1875=0.6533333333333333;
              viewHolder.browse_image_field.setImageBitmap(bmp);


                    if(Arrays.asList(spinner_browse_get_button_book_name).contains(browse_book_name[position]))
                    {
                        //System.out.println("browse_get_button_book_name_button :"+browse_book_name[position]);
                        viewHolder.browse_get_button_book_name_button().setTextColor(Color.parseColor("#FFFFFF"));
                        viewHolder.browse_get_button_book_name_button().setText("ACQUIRED");
                        viewHolder.browse_get_button_book_name_button().setBackground(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.acquired_book_ripple_effect));

                    }
                    else
                    {
                        if(Arrays.asList(spinner_browse_pending_book_name).contains(browse_book_name[position]))
                        {
                            //System.out.println("spinner_browse_pending_book_name :"+browse_book_name[position]);
                            viewHolder.browse_get_button_book_name_button().setTextColor(Color.parseColor("#000000"));
                            viewHolder.browse_get_button_book_name_button().setText("ALLOCATION PENDING");
                            viewHolder.browse_get_button_book_name_button().setBackground(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.pending_book_ripple_effect));


                        }

                        else
                        {  viewHolder.browse_get_button_book_name_button().setTextColor(Color.parseColor("#FFFFFF"));
                            viewHolder.browse_get_button_book_name_button().setText("GET BOOK");
                            viewHolder.browse_get_button_book_name_button().setBackground(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.get_book_ripple_effect));

                        }

                    }










        viewHolder.itemView.findViewById(R.id.get_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                NetworkUtil.setConnectivityStatus(activity.getApplicationContext());
                if (status != 0) {



                    confirmationDialog  = new ConfirmationDialog(activity);
                    AlertDialog dialog=confirmationDialog.startConfirmationDialog();

                    TextView  confirm_heading_text= dialog.findViewById(R.id.confirm_heading_text);
                    confirm_heading_text.setText("Are You Sure You Want To Acquire : "+browse_book_name[position]+" ?");

                    dialog.findViewById(R.id.yes_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //System.out.println("Yes Working!!");
                            confirmationDialog.dismissConfirmationDialog();





                            loadingDialog = new LoadingDialog(activity);
                            //System.out.println("==================="+loadingDialog.isCancelled());
                            AlertDialog percentage1 =  loadingDialog.startLoadingDialog();
                            TextView per1= percentage1.findViewById(R.id.progress_percentage);
                            per1.setText("50.00 %");


                            final ExecutorService executorServiceBrowseGetBook = Executors.newSingleThreadExecutor();
                            executorServiceBrowseGetBook.execute(new Runnable() {
                                @Override
                                public void run() {


                                    try {
                                        String login_url = "https://stalinism-noun.000webhostapp.com/request_book.php";
                                        //System.out.println("running-----------------------------------------------------------------------");

                                        URL url=new URL(login_url);
                                        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoOutput(true);
                                        httpURLConnection.setDoInput(true);
                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                                                URLEncoder.encode("book_name","UTF-8")+"="+URLEncoder.encode(browse_book_name[position],"UTF-8");

                                        bufferedWriter.write(post_data);
                                        bufferedWriter.flush();
                                        bufferedWriter.close();
                                        outputStream.close();

                                        InputStream inputStream = httpURLConnection.getInputStream();
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                        String line = "";
                                        int i = 0;
                                        while ((line = bufferedReader.readLine()) != null) {

                                            get_book[i] = "";
                                            get_book[i] += line;
                                            //System.out.println("Get Book Name : " + "["+i+"]" +get_book[i]);
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
                                            if(get_book[0]==null ||get_book[0].equals("null"))
                                            {
                                                int duration = Toast.LENGTH_SHORT;

                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" + "Your Books Limit Has Maxed Out !"  + " </b> </font>"), duration);
                                                per1.setText("100.00 %");
                                                loadingDialog.dismissDialog();
                                                notifyDataSetChanged();
                                                toast.show();
                                            }
                                            else if(get_book[0].equals("zero"))
                                            {
                                                int duration = Toast.LENGTH_SHORT;

                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" +browse_book_name[position] +" Is Not Available !"  + " </b> </font>"), duration);
                                                per1.setText("100.00 %");
                                                loadingDialog.dismissDialog();
                                                notifyDataSetChanged();
                                                toast.show();
                                            }
                                            else if(get_book[0].equals("exists"))
                                            {
                                                int duration = Toast.LENGTH_SHORT;

                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" +browse_book_name[position]+ "'s Allocation Is Pending !"  + " </b> </font>"), duration);
                                                per1.setText("100.00 %");
                                                loadingDialog.dismissDialog();
                                                notifyDataSetChanged();
                                                toast.show();

                                            }
                                            else if(get_book[0].equals("acquired"))
                                            {
                                                int duration = Toast.LENGTH_SHORT;

                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" +browse_book_name[position]+ " Is Already Acquired !"  + " </b> </font>"), duration);
                                                per1.setText("100.00 %");
                                                loadingDialog.dismissDialog();
                                                notifyDataSetChanged();
                                                toast.show();

                                            }
                                            else
                                            {
                                                //System.out.println("inside else");
                                                int duration = Toast.LENGTH_SHORT;
                                                //
                                                // //System.out.println("get_book[0]==" + get_book[0] + "get_book[1]==" + get_book[1]);
                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" + browse_book_name[position] +"'s Allocation Request Sent !" + " </b> </font>"), duration);
                                                viewHolder.browse_get_button_book_name_button().setText("PENDING");
                                                viewHolder.browse_get_button_book_name_button().setBackground(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.pending_book_ripple_effect));
                                                viewHolder.browse_get_button_book_name_button().setTextColor(Color.parseColor("#0000FF"));

                                                final ExecutorService executorServiceShift1 = Executors.newSingleThreadExecutor();
                                                executorServiceShift1.execute(new Runnable() {
                                                    @Override
                                                    public void run() {


                                               // System.out.println("BROWSE BUTTON BOOK LENGTH:"+spinner_browse_pending_book_name.length);

                                                int length=spinner_browse_pending_book_name .length;


                                                String[] temp=new String[length+1];

                                                for(int p=0;p<spinner_browse_pending_book_name.length;p++)
                                                {
                                                    temp[p]=spinner_browse_pending_book_name[p];
                                                }

                                                temp[temp.length-1]=browse_book_name[position];
                                                spinner_browse_pending_book_name=temp;
                                                spinner_browse_pending_book_name=temp;




                                                toast.show();

                                                        activity.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                per1.setText("100.00 %");
                                                                loadingDialog.dismissDialog();
                                                                notifyDataSetChanged();
                                                                //System.out.println("Request Book Complete!");

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

                            confirmationDialog.dismissConfirmationDialog();
                        }
                    });




//                    //System.out.println("button " + position + " pressed");
//                    //System.out.println(browse_book_name[position]);

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

      while(!(browse_book_name[i] ==null))
      { length++;
        i++;
      }
      //System.out.println("length:"+length);
        return length;
    }
}
