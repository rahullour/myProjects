package com.example.librarymanagementsystemteachers;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.librarymanagementsystemteachers.Globals.return_book;
import static com.example.librarymanagementsystemteachers.Globals.loadingDialog;
import static com.example.librarymanagementsystemteachers.Globals.confirmationDialog;
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



public class AllottedBooksCustomAdapter extends RecyclerView.Adapter<AllottedBooksCustomAdapter.ViewHolder> {

    private String[] allotted_book_name;
    private String[] allotted_book_available_count;
    private String[] allotted_book_borrow_date;
    private String[] allotted_book_return_date;
    private String[] allotted_book_late_fee;
    private String[] allotted_book_yop;
    private String[] allotted_book_image;
    private String[] allotted_student_name;
    private String[] allotted_student_id;
    private String[] allotted_student_course;    
    private String[] allotted_book_course;
    private Activity activity;



    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView allotted_book_name_field;
        private final TextView allotted_book_available_count_field;
        private final TextView allotted_book_borrow_date_field;
        private final TextView allotted_book_return_date_field;
        private final TextView allotted_book_late_fee_field;
        private final TextView allotted_book_yop_field;
        private final TextView allotted_book_course_field;
        private final TextView allotted_student_name_field;
        private final TextView allotted_student_id_field;
        private final TextView allotted_student_course_field;

        private final ImageView allotted_book_image_field;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View



            allotted_book_name_field=view.findViewById(R.id.allotted_book_name);
            allotted_book_available_count_field=view.findViewById(R.id.allotted_book_available_count);
            allotted_book_borrow_date_field=view.findViewById(R.id.allotted_book_borrow_date);
            allotted_book_return_date_field=view.findViewById(R.id.allotted_book_return_date);
            allotted_book_late_fee_field=view.findViewById(R.id.allotted_book_late_fee);
            allotted_book_yop_field=view.findViewById(R.id.allotted_book_yop);
            allotted_book_image_field=view.findViewById(R.id.allotted_book_image);
            allotted_book_course_field=view.findViewById(R.id.allotted_book_course);

            allotted_student_name_field=view.findViewById(R.id.allotted_student_name);
            allotted_student_id_field=view.findViewById(R.id.allotted_student_id);
            allotted_student_course_field=view.findViewById(R.id.allotted_student_course);

        }

        public TextView allotted_book_name_field() {
            return allotted_book_name_field;
        }
        public TextView allotted_book_available_count_field() { return allotted_book_available_count_field;}
        public TextView allotted_book_borrow_date_field() {
            return allotted_book_borrow_date_field;
        }
        public TextView allotted_book_return_date_field() {
            return allotted_book_return_date_field;
        }
        public TextView allotted_book_late_fee_field() {
            return allotted_book_late_fee_field;
        }
        public TextView allotted_book_yop_field() {
            return allotted_book_yop_field;
        }
        public TextView allotted_book_course_field() {
            return allotted_book_course_field;
        }
        public TextView allotted_student_name_field() {
            return allotted_student_name_field;
        }
        public TextView allotted_student_id_field() {
            return allotted_student_id_field;
        }
        public TextView allotted_student_course_field() {
            return allotted_student_course_field;
        }
        public ImageView allotted_book_image_field() {
            return allotted_book_image_field;
        }



    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param allotted_book_name,allotted_author,allotted_publisher,allotted_course,allotted_yop,allotted_available_count,allotted_image,allotted_course,allotted_student_name,allotted_student_id,allotted_student_course containing the data to populate views to be used
     *      * by RecyclerView.
     */
    public AllottedBooksCustomAdapter(Activity activity, String[] allotted_book_name, String[] allotted_book_borrow_date, String[] allotted_book_return_date, String[] allotted_book_late_fee, String[] allotted_yop, String[] allotted_available_count, String[] allotted_book_image, String[] allotted_book_course,String[] allotted_student_name,String[] allotted_student_id,String[] allotted_student_course) {
        this.activity=activity;
        this.allotted_book_name = allotted_book_name;
        this.allotted_book_borrow_date = allotted_book_borrow_date;
        this.allotted_book_return_date = allotted_book_return_date;
        this.allotted_book_late_fee = allotted_book_late_fee;
        this.allotted_book_yop = allotted_yop;
        this.allotted_book_available_count = allotted_available_count;
        this.allotted_book_course=allotted_book_course;
        this.allotted_student_name=allotted_student_name;
        this.allotted_student_id=allotted_student_id;
        this.allotted_student_course=allotted_student_course;

        this.allotted_book_image=allotted_book_image;


    }


    // Create new views (invoked by the layout manager)
    @Override
    public AllottedBooksCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.alloted_books_layout, viewGroup, false);

        return new AllottedBooksCustomAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(AllottedBooksCustomAdapter.ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.allotted_book_name_field().setText(allotted_book_name[position]);
        viewHolder.allotted_book_available_count_field().setText(allotted_book_available_count[position]);
        viewHolder.allotted_book_borrow_date_field().setText(allotted_book_borrow_date[position]);
        viewHolder.allotted_book_return_date_field().setText(allotted_book_return_date[position]);
        viewHolder.allotted_book_late_fee_field().setText(allotted_book_late_fee[position] +" Rs.");
        viewHolder.allotted_book_yop_field().setText(allotted_book_yop[position]);
        viewHolder.allotted_book_course_field().setText(allotted_book_course[position]);
        viewHolder.allotted_student_name_field().setText(allotted_student_name[position]);
        viewHolder.allotted_student_id_field().setText(allotted_student_id[position]);
        viewHolder.allotted_student_course_field().setText(allotted_student_course[position]);



        byte[] decodedImage = Base64.decode(allotted_book_image[position].getBytes(), Base64.DEFAULT);
        Bitmap bmp = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
//        Bitmap resizedBitmap = Bitmap.createScaledBitmap(
//                bmp, 800, 1450, false);
        // 800/1450=0.5517241379310345;
        viewHolder.allotted_book_image_field.setImageBitmap(bmp);

//        //System.out.println("allotted_book_name_field"+allotted_book_name[position]);
//        //System.out.println("allotted_book_available_count_field"+allotted_book_available_count[position]);
//        //System.out.println("allotted_book_borrow_date_field"+allotted_book_borrow_date[position]);
//        //System.out.println("allotted_book_return_date_field"+allotted_book_return_date[position]);
//        //System.out.println("allotted_book_late_fee_field"+allotted_book_late_fee[position] +" Rs");
//        //System.out.println("allotted_book_yop_field"+allotted_book_yop[position]);
//        //System.out.println("allotted_book_course_field"+allotted_book_course[position]);
//        //System.out.println("allotted_book_late_fee_field"+allotted_student_name[position]);
//        //System.out.println("allotted_book_yop_field"+allotted_student_id[position]);
//        //System.out.println("allotted_book_course_field"+allotted_student_course[position]);





        viewHolder.itemView.findViewById(R.id.retain_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtil.setConnectivityStatus(activity.getApplicationContext());


                if (status != 0) {



                    confirmationDialog  = new ConfirmationDialog(activity);
                    AlertDialog dialog=confirmationDialog.startConfirmationDialog();

                    TextView  confirm_heading_text= dialog.findViewById(R.id.confirm_heading_text);
                    confirm_heading_text.setText("Are You Sure You Want To Retain : "+allotted_book_name[position]+" From : "+allotted_student_name[position]+" ?");

                    dialog.findViewById(R.id.yes_button).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            //System.out.println("Retain Yes Working!!");
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
                                        String login_url = "https://stalinism-noun.000webhostapp.com/allotted_return_book.php";
                                        //System.out.println("running-----------------------------------------------------------------------");

                                        URL url=new URL(login_url);
                                        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                                        httpURLConnection.setRequestMethod("POST");
                                        httpURLConnection.setDoOutput(true);
                                        httpURLConnection.setDoInput(true);
                                        OutputStream outputStream=httpURLConnection.getOutputStream();
                                        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                                        String post_data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(allotted_student_id[position],"UTF-8")+"&"+
                                                URLEncoder.encode("book_name","UTF-8")+"="+URLEncoder.encode(allotted_book_name[position],"UTF-8");

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
                                            //System.out.println("Allotted Return Book Name : " + "["+i+"]" +return_book[i]);
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
                                                //System.out.println("Return book doesn't exist!");
                                                int duration = Toast.LENGTH_SHORT;
                                                //System.out.println("return_book[0]==" + return_book[0] + "return_book[1]==" + return_book[1]);
                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" + allotted_book_name[position] +"'s Return Doesn't Exist , Report To Developer !" + " </b> </font>"), duration);
                                                loadingDialog.dismissDialog();
                                                notifyDataSetChanged();
                                                toast.show();

                                            }
                                            else
                                            {
                                                int duration = Toast.LENGTH_SHORT;
                                                //System.out.println("return_book[0]==" + return_book[0] + "return_book[1]==" + return_book[1]);
                                                Toast toast = Toast.makeText(activity.getApplicationContext(), Html.fromHtml("<font color='#FF0000' > <b>" + allotted_book_name[position] +"'s Retain "+"From : "+allotted_student_name[position]+" Complete !" + " </b> </font>"), duration);
                                                viewHolder.allotted_book_available_count_field().setText(return_book[0]);
                                                toast.show();

                                                final ExecutorService executorServiceShift = Executors.newSingleThreadExecutor();
                                                executorServiceShift.execute(new Runnable() {
                                                    @Override
                                                    public void run() {

                                                        //System.out.println("Before Shifting");
                                                        for (int i = 0; i < allotted_book_name.length; i++) {
                                                            //System.out.println("position :"+i +"value :"+allotted_book_name[i]);


                                                        }
                                                        String retainbuttonbookname = allotted_book_name[position];
                                                        //System.out.println("retainbuttonbookname"+retainbuttonbookname);

                                                        for (int i = position; i < allotted_book_name.length - 1; i++) {
                                                            allotted_book_available_count[i] = allotted_book_available_count[i + 1];
                                                            allotted_book_borrow_date[i] = allotted_book_borrow_date[i + 1];
                                                            allotted_book_course[i] = allotted_book_course[i + 1];
                                                            allotted_book_image[i] = allotted_book_image[i + 1];
                                                            allotted_book_late_fee[i] = allotted_book_late_fee[i + 1];
                                                            allotted_book_name[i] = allotted_book_name[i + 1];
                                                            allotted_book_return_date[i] = allotted_book_return_date[i + 1];
                                                            allotted_book_yop[i] = allotted_book_yop[i + 1];
                                                            allotted_student_course[i] = allotted_student_course[i + 1];
                                                            allotted_student_name[i] = allotted_student_name[i + 1];
                                                            allotted_student_id[i] = allotted_student_id[i + 1];


                                                        }

                                                        for (int i = 0; i < allotted_book_name.length; i++) {
                                                            if (allotted_book_name[i] != null && allotted_book_name[i].equals(retainbuttonbookname)) {
                                                                allotted_book_available_count[i] = return_book[0];
                                                                //System.out.println("allotted_book_available_count" + allotted_book_available_count[i]);
                                                            }
                                                        }


                                                        spinner_allotted_book_available_count = new String[allotted_book_available_count.length];
                                                        spinner_allotted_book_borrow_date = new String[allotted_book_borrow_date.length];
                                                        spinner_allotted_book_course = new String[allotted_book_course.length];
                                                        spinner_allotted_book_image = new String[allotted_book_image.length];
                                                        spinner_allotted_book_late_fee = new String[allotted_book_late_fee.length];
                                                        spinner_allotted_book_name = new String[allotted_book_name.length];
                                                        spinner_allotted_book_return_date = new String[allotted_book_return_date.length];
                                                        spinner_allotted_book_yop = new String[allotted_book_yop.length];
                                                        spinner_allotted_student_course = new String[allotted_student_course.length];
                                                        spinner_allotted_student_name = new String[allotted_student_name.length];
                                                        spinner_allotted_student_id = new String[allotted_student_id.length];


                                                        for (int i = 0; i < allotted_book_name.length; i++) {


                                                            spinner_allotted_book_name[i] = allotted_book_name[i];
                                                            spinner_allotted_book_borrow_date[i] = allotted_book_borrow_date[i];
                                                            spinner_allotted_book_return_date[i] = allotted_book_return_date[i];
                                                            spinner_allotted_book_late_fee[i] = allotted_book_late_fee[i];
                                                            spinner_allotted_book_yop[i] = allotted_book_yop[i];
                                                            spinner_allotted_book_available_count[i] = allotted_book_available_count[i];
                                                            spinner_allotted_book_image[i] = allotted_book_image[i];
                                                            spinner_allotted_book_course[i] = allotted_book_course[i];
                                                            spinner_allotted_student_course[i] = allotted_student_course[i];
                                                            spinner_allotted_student_name[i] = allotted_student_name[i];
                                                            spinner_allotted_student_id[i] = allotted_student_id[i];


                                                        }


                                                        //System.out.println("After Shifting");
                                                        for (int i = 0; i < allotted_book_name.length; i++) {
                                                            //System.out.println("position :"+i +"value :"+allotted_book_name[i]);


                                                        }
                                                        activity.runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {

                                                                loadingDialog.dismissDialog();
                                                                notifyDataSetChanged();
                                                                //System.out.println("Return Book Complete!");

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
                            //System.out.println("Retain No Working!!");
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
        while(!(allotted_book_name[i] ==null))
        { length++;
            i++;
        }
        //System.out.println("length"+length);
        return length;
    }
}




