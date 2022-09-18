package com.example.librarymanagementsystemstudents;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.librarymanagementsystemstudents.Globals.get_book;
import static com.example.librarymanagementsystemstudents.Globals.return_book;
import static com.example.librarymanagementsystemstudents.Globals.loadingDialog;
import static com.example.librarymanagementsystemstudents.Globals.confirmationDialog;
import static com.example.librarymanagementsystemstudents.Globals.spinner_browse_pending_book_name;
import static com.example.librarymanagementsystemstudents.Globals.spinner_home_pending_book_name;
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


public class HomeCustomAdapter extends RecyclerView.Adapter<HomeCustomAdapter.ViewHolder> {

    private String[] my_book_name;
    private String[] my_book_available_count;
    private String[] my_book_borrow_date;
    private String[] my_book_return_date;
    private String[] my_book_late_fee;
    private String[] my_book_yop;
    private String[] my_book_image;
    private String[] my_book_course;
    private Activity activity;



    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView home_book_name_field;
        private final TextView home_book_available_count_field;
        private final TextView home_book_borrow_date_field;
        private final TextView home_book_return_date_field;
        private final TextView home_book_late_fee_field;
        private final TextView home_book_yop_field;
        private final TextView home_book_course_field;
        private final ImageView home_book_image_field;
        private final Button home_get_button_book_name_button;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View



            home_book_name_field=view.findViewById(R.id.my_book_name);
            home_book_available_count_field=view.findViewById(R.id.my_book_available_count);
            home_book_borrow_date_field=view.findViewById(R.id.my_book_borrow_date);
            home_book_return_date_field=view.findViewById(R.id.my_book_return_date);
            home_book_late_fee_field=view.findViewById(R.id.my_book_late_fee);
            home_book_yop_field=view.findViewById(R.id.my_book_yop);
            home_book_image_field=view.findViewById(R.id.my_book_image);
            home_book_course_field=view.findViewById(R.id.my_book_course);
            home_get_button_book_name_button=view.findViewById(R.id.return_button);

        }

        public TextView home_book_name_field() {
            return home_book_name_field;
        }
        public TextView home_book_available_count_field() { return home_book_available_count_field;}
        public TextView home_book_borrow_date_field() {
            return home_book_borrow_date_field;
        }
        public TextView home_book_return_date_field() {
            return home_book_return_date_field;
        }
        public TextView home_book_late_fee_field() {
            return home_book_late_fee_field;
        }
        public TextView home_book_yop_field() {
            return home_book_yop_field;
        }
        public TextView home_book_course_field() {
            return home_book_course_field;
        }
        public Button home_get_button_book_name_button() {
            return home_get_button_book_name_button;
        }
        public ImageView home_book_image_field() {
            return home_book_image_field;
        }

    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param home_book_name,home_author,home_publisher,home_course,home_yop,home_available_count,home_image,home_course containing the data to populate views to be used
     *      * by RecyclerView.
     */
    public HomeCustomAdapter(Activity activity,String[] home_book_name,String[] home_book_borrow_date,String[] home_book_return_date,String[] home_book_late_fee,String[] home_yop,String[] home_available_count,String[] home_book_image,String[] home_book_course) {
            this.activity=activity;
            this.my_book_name = home_book_name;
            this.my_book_borrow_date = home_book_borrow_date;
            this.my_book_return_date = home_book_return_date;
            this.my_book_late_fee = home_book_late_fee;
            this.my_book_yop = home_yop;
            this.my_book_available_count = home_available_count;
            this.my_book_image=home_book_image;
            this.my_book_course=home_book_course;

        }


        // Create new views (invoked by the layout manager)
        @Override
        public HomeCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.my_books_layout, viewGroup, false);

            return new HomeCustomAdapter.ViewHolder(view);
        }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(HomeCustomAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        
        viewHolder.home_book_name_field().setText(my_book_name[position]);
        viewHolder.home_book_available_count_field().setText(my_book_available_count[position]);
        viewHolder.home_book_borrow_date_field().setText(my_book_borrow_date[position]);
        viewHolder.home_book_return_date_field().setText(my_book_return_date[position]);
        viewHolder.home_book_late_fee_field().setText(my_book_late_fee[position] +" Rs");
        viewHolder.home_book_yop_field().setText(my_book_yop[position]);
        viewHolder.home_book_course_field().setText(my_book_course[position]);
        byte[] decodedImage = Base64.decode(my_book_image[position].getBytes(), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
//        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
//                bmp, 2450, 3080, false);
        //  1225/1540=0.7954545454545455;
        viewHolder.home_book_image_field.setImageBitmap(bmp);


        if(Arrays.asList(spinner_home_pending_book_name).contains(my_book_name[position]))
        {
            //System.out.println("spinner_home_pending_book_name :"+my_book_name[position]);
            viewHolder.home_get_button_book_name_button().setTextColor(Color.parseColor("#000000"));
            viewHolder.home_get_button_book_name_button().setText("RETURN PENDING");
            viewHolder.home_get_button_book_name_button().setBackground(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.pending_book_ripple_effect));

        }


        viewHolder.itemView.findViewById(R.id.return_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtil.setConnectivityStatus(activity.getApplicationContext());

                if (status != 0) {



                    confirmationDialog  = new ConfirmationDialog(activity);
                    AlertDialog dialog=confirmationDialog.startConfirmationDialog();

                    TextView  confirm_heading_text= dialog.findViewById(R.id.confirm_heading_text);
                    confirm_heading_text.setText("Are You Sure You Want To Return : "+my_book_name[position]+" ?");

                    dialog.findViewById(R.id.yes_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //System.out.println("Yes Working!!");
                            confirmationDialog.dismissConfirmationDialog();


                            loadingDialog = new LoadingDialog(activity);
                            // //System.out.println("==================="+loadingDialog.isCancelled());
                            AlertDialog percentage1 =  loadingDialog.startLoadingDialog();
                            TextView per1= percentage1.findViewById(R.id.progress_percentage);
                            per1.setText("50.00 %");


                            final ExecutorService executorServiceHomeReturnBook = Executors.newSingleThreadExecutor();
                            executorServiceHomeReturnBook.execute(new Runnable() {
                                @Override
                                public void run() {


                                    try {
                                        String login_url = "https://stalinism-noun.000webhostapp.com/request_return_book.php";
                                        //System.out.println("running-----------------------------------------------------------------------");

                                        URL url=new URL(login_url);
                                        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoOutput(true);
                                        httpURLConnection.setDoInput(true);
                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"+
                                                URLEncoder.encode("book_name","UTF-8")+"="+URLEncoder.encode(my_book_name[position],"UTF-8");

                                        bufferedWriter.write(post_data);
                                        bufferedWriter.flush();
                                        bufferedWriter.close();
                                        outputStream.close();

                                        InputStream inputStream = httpURLConnection.getInputStream();
                                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                                        String line = "";
                                        int i = 0;
                                        while ((line = bufferedReader.readLine()) != null) {

                                            return_book[i] = "";
                                            return_book[i] += line;
                                            //System.out.println("Return Book Name : " + "["+i+"]" +return_book[i]);
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
                                            if(return_book[0]==null ||return_book[0].equals("null"))
                                            {
                                                int duration = Toast.LENGTH_SHORT;

                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" +my_book_name[position]+ "'s Return Is Pending !"  + " </b> </font>"), duration);
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
                                                 //System.out.println("get_book[0]==" + get_book[0] + "get_book[1]==" + get_book[1]);
                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" + my_book_name[position] +"'s Return Request Sent !" + " </b> </font>"), duration);
                                               
                                                viewHolder.home_get_button_book_name_button().setText("RETURN PENDING");
                                                viewHolder.home_get_button_book_name_button().setBackground(ContextCompat.getDrawable(activity.getApplicationContext(), R.drawable.pending_book_ripple_effect));
                                                viewHolder.home_get_button_book_name_button().setTextColor(Color.parseColor("#000000"));

                                                
                                                toast.show();



                                                        //System.out.println("HOME BUTTON BOOK LENGTH:"+spinner_home_pending_book_name.length);

                                                        int length=spinner_home_pending_book_name.length;


                                                        String[] temp=new String[length+1];

                                                        for(int p=0;p<spinner_home_pending_book_name.length;p++)
                                                        {
                                                            temp[p]=spinner_home_pending_book_name[p];
                                                        }

                                                        temp[temp.length-1]=my_book_name[position];
                                                        spinner_home_pending_book_name=temp;
                                                        spinner_home_pending_book_name=temp;
                                                        toast.show();

                                                        per1.setText("100.00 %");
                                                            loadingDialog.dismissDialog();
                                                               // notifyDataSetChanged();
                                                                //System.out.println("Request Book Complete!");





                                            }


                                            //System.out.println("Return Book Complete!");


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
        while(!(my_book_name[i] ==null))
        { length++;
            i++;
        }
        //System.out.println("length"+length);
        return length;
    }
}
