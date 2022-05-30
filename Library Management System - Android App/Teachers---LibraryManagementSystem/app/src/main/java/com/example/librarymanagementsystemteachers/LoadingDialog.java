package com.example.librarymanagementsystemteachers;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoadingDialog {
    private Activity activity;
     private AlertDialog dialog;
    public LoadingDialog(Activity myActivity)
    {
this.activity=myActivity;
    }
public AlertDialog startLoadingDialog()
{
    AlertDialog.Builder builder=new AlertDialog.Builder(activity);
    LayoutInflater inflater=activity.getLayoutInflater();
    builder.setView(inflater.inflate(R.layout.progress_layout,null));
    builder.setCancelable(false);
    dialog= builder.create();
    dialog.show();
return dialog;

}

public void dismissDialog()
{
    dialog.dismiss();
}
}
