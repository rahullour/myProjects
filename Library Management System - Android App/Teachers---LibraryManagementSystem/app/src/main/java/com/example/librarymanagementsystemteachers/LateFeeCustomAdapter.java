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






public class LateFeeCustomAdapter extends RecyclerView.Adapter<LateFeeCustomAdapter.ViewHolder> {


    private String[] late_fee_student_name;
    private String[] late_fee_student_id;
    private String[] late_fee_student_course;
    private String[] late_fee_student_late_fee;
    private String[] late_fee_student_book_count;

    private Activity activity;



    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        
        private final TextView late_fee_student_name_field;
        private final TextView late_fee_student_id_field;
        private final TextView late_fee_student_course_field;
        private final TextView late_fee_student_late_fee_field;
        private final TextView late_fee_student_book_count_field;



        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View


            late_fee_student_late_fee_field=view.findViewById(R.id.late_fee_total_fee);
            late_fee_student_name_field=view.findViewById(R.id.late_fee_student_name);
            late_fee_student_id_field=view.findViewById(R.id.late_fee_student_id);
            late_fee_student_course_field=view.findViewById(R.id.late_fee_student_course);
            late_fee_student_book_count_field=view.findViewById(R.id.late_fee_student_book_count);
        }


        public TextView late_fee_student_name_field() {
            return late_fee_student_name_field;
        }
        public TextView late_fee_student_id_field() {
            return late_fee_student_id_field;
        }
        public TextView late_fee_student_course_field() {
            return late_fee_student_course_field;
        }
        public TextView late_fee_student_late_fee_field() {
            return late_fee_student_late_fee_field;
        }
        public TextView late_fee_student_book_count_field() {
            return late_fee_student_book_count_field;
        }



    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param late_fee_student_late_fee,late_fee_student_name,late_fee_student_id,late_fee_student_course containing the data to populate views to be used
     *      * by RecyclerView.
     */
    public LateFeeCustomAdapter(Activity activity,String [] late_fee_student_late_fee ,String [] late_fee_student_name,String [] late_fee_student_id,String [] late_fee_student_course,String[] late_fee_student_book_count) {
        this.activity=activity;
        this.late_fee_student_late_fee=late_fee_student_late_fee;
        this.late_fee_student_name=late_fee_student_name;
        this.late_fee_student_id=late_fee_student_id;
        this.late_fee_student_course=late_fee_student_course;
        this.late_fee_student_book_count=late_fee_student_book_count;
    


    }


    // Create new views (invoked by the layout manager)
    @Override
    public LateFeeCustomAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.late_fee_layout, viewGroup, false);

        return new LateFeeCustomAdapter.ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(LateFeeCustomAdapter.ViewHolder viewHolder, final int position) {
        String Books;
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
       if(late_fee_student_book_count[position].length()>0)
       {
         Books="Books";
       }
      else
       {
           Books="Book";
       }

        viewHolder.late_fee_student_late_fee_field().setText(late_fee_student_late_fee[position]+" Rs.");
        viewHolder.late_fee_student_name_field().setText(late_fee_student_name[position]);
        viewHolder.late_fee_student_id_field().setText(late_fee_student_id[position]);
        viewHolder.late_fee_student_course_field().setText(late_fee_student_course[position]);
        viewHolder.late_fee_student_book_count_field().setText(" "+late_fee_student_book_count[position]+" "+Books+" :");




        //System.out.println("late_fee_book_name_field"+late_fee_book_name[position]);
        //System.out.println("late_fee_book_available_count_field"+late_fee_book_available_count[position]);
        //System.out.println("late_fee_book_borrow_date_field"+late_fee_book_borrow_date[position]);
        //System.out.println("late_fee_book_return_date_field"+late_fee_book_return_date[position]);
        //System.out.println("late_fee_book_late_fee_field"+late_fee_book_late_fee[position] +" Rs");
        //System.out.println("late_fee_book_yop_field"+late_fee_book_yop[position]);
        //System.out.println("late_fee_book_course_field"+late_fee_book_course[position]);
        //System.out.println("late_fee_book_late_fee_field"+late_fee_student_name[position]);
        //System.out.println("late_fee_book_yop_field"+late_fee_student_id[position]);
        //System.out.println("late_fee_book_course_field"+late_fee_student_course[position]);



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        int length=0;
        int i=0;

            while (!(late_fee_student_late_fee[i] == null) && !late_fee_student_late_fee[i].equals("null")) {
                //System.out.println("late_fee_student_late_feein---" + late_fee_student_late_fee[i]);
                length++;
                i++;

        }
        //System.out.println("length"+length);
        return length;
    }
}




