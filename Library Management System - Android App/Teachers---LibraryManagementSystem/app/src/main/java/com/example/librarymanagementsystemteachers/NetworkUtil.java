package com.example.librarymanagementsystemteachers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static com.example.librarymanagementsystemteachers.Globals.status;

public class NetworkUtil {
    public static void setConnectivityStatus(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                status = 2;

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                status = 1;

            }
        } else {
            status = 0;

        }

    }
}