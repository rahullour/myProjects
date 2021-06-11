package com.example.librarymanagementsystemstudents;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class ConfirmationDialog {
    private Activity activity;
    private AlertDialog dialog;
    public ConfirmationDialog(Activity myActivity)
    {
        this.activity=myActivity;

    }
    public AlertDialog startConfirmationDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(activity);
        LayoutInflater inflater=activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.confirmation_layout,null));
        builder.setCancelable(false);
        dialog= builder.create();
        dialog.show();




     return dialog;
    }

    public void dismissConfirmationDialog()
    {
        dialog.dismiss();
    }



}






