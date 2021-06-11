package com.example.librarymanagementsystemteachers;

import android.app.AlertDialog;
import android.widget.ImageView;
import android.widget.TextView;

public class Globals {
    public static int profilepicdownloaded=0;
    public static int status=0;


    public static String user_id;
    public static String user_name;
    public static String user_pwd;
    public static String user_course;
    public static ImageView userimage;
    public static String user_email;
    public static LoadingDialog loadingDialog;
    public static ConfirmationDialog confirmationDialog;
    public static String[] browse_available_count;
    public static String[] loginresult=new String[5];
    public static String[] signupresult=new String[5];
    public static String[] downloadimageresult=new String[5];
    public static String[] uploadimageresult=new String[5];
    public static String[] sendlinkresult=new String[5];
    public static String[] resetpassresult=new String[5];
    public static String[] latefee=new String[5];
    public static String[] return_book=new String[5];


    public static AlertDialog percentage1 ;
    public static TextView per1 =null;


    public static String[]  spinner_allotted_book_available_count;
    public static String[]  spinner_allotted_book_borrow_date;
    public static String[]  spinner_allotted_book_course;
    public static String[]  spinner_allotted_book_image;
    public static String[]  spinner_allotted_book_late_fee;
    public static String[]  spinner_allotted_book_name;
    public static String[]  spinner_allotted_book_return_date;
    public static String[]  spinner_allotted_book_yop;
    public static String[]  spinner_allotted_student_course;
    public static String[]  spinner_allotted_student_name;
    public static String[]  spinner_allotted_student_id;


    public static String[]    spinner_requested_book_name=new String[100];
    public static String[]    spinner_requested_book_author=new String[100];
    public static String[]    spinner_requested_book_publisher=new String[100];
    public static String[]    spinner_requested_book_course=new String[100];
    public static String[]    spinner_requested_book_yop=new String[100];
    public static String[]    spinner_requested_book_available_count=new String[100];
    public static String[]    spinner_requested_book_image=new String[100];
    public static String[]    spinner_requested_student_name=new String[100];
    public static String[]    spinner_requested_student_id=new String[100];
    public static String[]    spinner_requested_student_course=new String[100];

    public static String[]    spinner_return_requested_book_name=new String[100];
    public static String[]    spinner_return_requested_book_author=new String[100];
    public static String[]    spinner_return_requested_book_publisher=new String[100];
    public static String[]    spinner_return_requested_book_course=new String[100];
    public static String[]    spinner_return_requested_book_yop=new String[100];
    public static String[]    spinner_return_requested_book_available_count=new String[100];
    public static String[]    spinner_return_requested_book_image=new String[100];
    public static String[]    spinner_return_requested_student_name=new String[100];
    public static String[]    spinner_return_requested_student_id=new String[100];
    public static String[]    spinner_return_requested_student_course=new String[100];














}
