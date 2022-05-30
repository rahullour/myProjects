package com.example.librarymanagementsystemstudents;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class Globals {
    public static int profilepicdownloaded=0;
    public static int status=0;


    public static String user_id;
    public static String user_course;
    public static String user_name;
    public static String user_pwd;
    public static ImageView user_image;
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
    public static String[] get_book=new String[5];
    public static String[] return_book=new String[5];

    public static AlertDialog percentage1 ;
    public static TextView per1 =null;


    public static  String[]  spinner_my_book_name=new String[100];
    public static  String[]  spinner_my_book_borrow_date=new String[100];
    public static  String[]  spinner_my_book_return_date=new String[100];
    public static  String[]  spinner_my_book_late_fee=new String[100];
    public static  String[]  spinner_my_book_yop=new String[100];
    public static  String[]  spinner_my_book_available_count=new String[100];
    public static  String[]  spinner_my_book_image=new String[100];
    public static  String[] spinner_my_book_course =new String[100];


    public static String[] spinner_browse_get_button_book_name;
    public static String[] spinner_browse_pending_book_name;

    public static String[] spinner_home_pending_book_name;










}
