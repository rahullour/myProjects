package com.example.librarymanagementsystemstudents;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;

import com.example.librarymanagementsystemstudents.ui.Browse.BrowseFragment;
import com.example.librarymanagementsystemstudents.ui.home.HomeFragment;
import com.example.librarymanagementsystemstudents.ui.latefee.LateFeeFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.librarymanagementsystemstudents.Globals.loadingDialog;
import static com.example.librarymanagementsystemstudents.Globals.downloadimageresult;

import static com.example.librarymanagementsystemstudents.Globals.status;
import static com.example.librarymanagementsystemstudents.Globals.user_course;
import static com.example.librarymanagementsystemstudents.Globals.user_id;
import static com.example.librarymanagementsystemstudents.Globals.user_name;
import static com.example.librarymanagementsystemstudents.Globals.profilepicdownloaded;
import static com.example.librarymanagementsystemstudents.Globals.percentage1;
import static com.example.librarymanagementsystemstudents.Globals.per1;
import static com.example.librarymanagementsystemstudents.Globals.uploadimageresult;
import static com.example.librarymanagementsystemstudents.Globals.user_pwd;


public class InsideActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    TextView tvname, tvid, tvcourse;
    LinearLayout inside_back;
    DrawerLayout drawer;
    RecyclerView RecyclerView1,RecyclerView2;
//    String[] list = {"adsasd", "awewaeqw", "werty", "asdqwqe", "123qweqwe", "Qweq2131", "Qqeqwe2", "aseasea", "qweqeqe2", "Qweqwe212"};

    private static int[] backimages = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
    private static Random random = new Random();
    private static int imageno = random.nextInt(backimages.length);
    private int PICK_IMAGE_REQUEST = 1;
    private AppBarConfiguration mAppBarConfiguration;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inside);

        final ExecutorService executorServiceSaveCredentials = Executors.newSingleThreadExecutor();
        executorServiceSaveCredentials.execute(new Runnable() {
            @Override
            public void run() {



                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Context context=getApplicationContext();
                        SharedPreferences sharedPreferences = PreferenceManager
                                .getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mainenrollment", user_id);
                        editor.putString("mainpassword", user_pwd);
                        editor.apply();

                    }
                });

            }


        });





        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        Context context = getApplicationContext();

         Globals.loadingDialog=new LoadingDialog(InsideActivity.this);



        fab.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                    NetworkUtil.setConnectivityStatus(context);
                if(status!=0) {

                    //System.out.println("sending mail");

                    String[] addresses = {"rahulbidawas@gmail.com"};

                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                    intent.putExtra(Intent.EXTRA_EMAIL, addresses);
                    intent.putExtra(Intent.EXTRA_SUBJECT, Globals.user_id.toString() + "'s Query ! <-  (Please don't change this.)");
                    startActivity(intent);
                }
                else {
                    Toast    toast = Toast.makeText(context, Html.fromHtml("<font color='#FF0000' > <b>"+ ":( " + "Internet Connection Not Found !" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

                    toast.show();


                }

            }


        });


         drawer = findViewById(R.id.drawer_layout);
       navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_mybooks, R.id.nav_browse, R.id.nav_late_fee)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.getCheckedItem().setTitle(Html.fromHtml("<font color='#ff3824'>MY BOOKS</font>"));



     navigationView.setNavigationItemSelectedListener(this);


//        if (savedInstanceState == null) {
//            HomeFragment hf = new HomeFragment();
//            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//            transaction.addToBackStack(null);
//            transaction.replace(R.id.nav_host_fragment, hf);
//            transaction.commit();

    //    navigationView.setCheckedItem(R.id.nav_mybooks);





        //}

    }

//


    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        Context context=getApplicationContext();
        ActionBar actionBar = getSupportActionBar();
        switch (item.getItemId()) {
            case R.id.nav_mybooks:
                //System.out.println("hello mybooks");
                item.setTitle(Html.fromHtml("<font color='#ff0000'>MY BOOKS</font>"));
                navigationView.getMenu().getItem(1).setTitle(Html.fromHtml("<font color='#000000'>BROWSE BOOKS</font>"));
                navigationView.getMenu().getItem(2).setTitle(Html.fromHtml("<font color='#000000'>PAYABLE AMOUNT</font>"));
                navigationView.getMenu().getItem(3).setTitle(Html.fromHtml("<font color='#000000'>SIGN OUT</font>"));

                actionBar.setTitle("MY BOOKS");
                drawer.closeDrawer(GravityCompat.START);
                HomeFragment hf=new HomeFragment();
                FragmentTransaction  transaction=getSupportFragmentManager().beginTransaction();

                transaction.addToBackStack(null);
                transaction.replace(R.id.nav_host_fragment,hf);

                transaction.commit();





                break;

                case R.id.nav_browse:
                //System.out.println("hello browse");
                    item.setTitle(Html.fromHtml("<font color='#ff0000'>BROWSE BOOKS</font>"));
                    navigationView.getMenu().getItem(3).setTitle(Html.fromHtml("<font color='#000000'>SIGN OUT</font>"));
                    navigationView.getMenu().getItem(2).setTitle(Html.fromHtml("<font color='#000000'>PAYABLE AMOUNT</font>"));
                    navigationView.getMenu().getItem(0).setTitle(Html.fromHtml("<font color='#000000'>MY BOOKS</font>"));

                    actionBar.setTitle("BROWSE BOOKS");
                    drawer.closeDrawer(GravityCompat.START);


                    BrowseFragment bf=new BrowseFragment();
                    transaction=getSupportFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.nav_host_fragment,bf);
                    transaction.commit();




                    break;

            case R.id.nav_late_fee:
               //System.out.println("hello latefee");
               item.setTitle(Html.fromHtml("<font color='#ff0000'>PAYABLE AMOUNT</font>"));
                navigationView.getMenu().getItem(1).setTitle(Html.fromHtml("<font color='#000000'>BROWSE BOOKS</font>"));
                navigationView.getMenu().getItem(3).setTitle(Html.fromHtml("<font color='#000000'>SIGN OUT</font>"));
                navigationView.getMenu().getItem(0).setTitle(Html.fromHtml("<font color='#000000'>MY BOOKS</font>"));

                actionBar.setTitle("PAYABLE AMOUNT");

                drawer.closeDrawer(GravityCompat.START);

                LateFeeFragment lf=new LateFeeFragment();
                transaction=getSupportFragmentManager().beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.nav_host_fragment,lf);
                transaction.commit();

                break;

            case R.id.nav_sign_out:
                item.setTitle(Html.fromHtml("<font color='#ff0000'>SIGN OUT</font>"));
                navigationView.getMenu().getItem(1).setTitle(Html.fromHtml("<font color='#000000'>BROWSE BOOKS</font>"));
                navigationView.getMenu().getItem(2).setTitle(Html.fromHtml("<font color='#000000'>PAYABLE AMOUNT</font>"));
                navigationView.getMenu().getItem(0).setTitle(Html.fromHtml("<font color='#000000'>MY BOOKS</font>"));


                ConfirmationDialog confirmationDialog  = new ConfirmationDialog(this);
                AlertDialog dialog=confirmationDialog.startConfirmationDialog();

                TextView  confirm_heading_text= dialog.findViewById(R.id.confirm_heading_text);
                confirm_heading_text.setText("Are You Sure You Want To Sign Out ?");

                dialog.findViewById(R.id.yes_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //System.out.println("Grant Yes Working!!");
                        confirmationDialog.dismissConfirmationDialog();
                        Context context=getApplicationContext();
                        SharedPreferences sharedPreferences = PreferenceManager
                                .getDefaultSharedPreferences(context);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("mainenrollment", "null");
                        editor.putString("mainpassword", "null");
                        editor.apply();



                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                       System.gc();

                        finish();
                        startActivity(intent);



                    }
                });

                dialog.findViewById(R.id.no_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmationDialog.dismissConfirmationDialog();

                    }
                });


                break;



        }


        return true;
    }


    public static void triggerRebirth(Context context) {

        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
        ComponentName componentName = intent.getComponent();
        Intent mainIntent = Intent.makeRestartActivityTask(componentName);
        context.startActivity(mainIntent);
        Runtime.getRuntime().exit(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        Globals.user_image = findViewById(R.id.home_user_image);
        final Context[] context = {getApplicationContext()};
        tvname = findViewById(R.id.home_username);
        tvid = findViewById(R.id.home_userid);
        getMenuInflater().inflate(R.menu.activity_inside, menu);


        tvname.setText(user_name+" - "+user_course);

        //   tvname.setText(downloadimageresult[0]);


        //System.out.println("----------------" + user_name);
        tvid.setText(Globals.user_id);


        int tempimgno = imageno;
        imageno = random.nextInt(backimages.length);
        while (tempimgno == imageno) {
            imageno = random.nextInt(backimages.length);
        }
        inside_back = findViewById(R.id.back_image);
        switch (imageno) {
            case 0:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i1));
                break;
            case 1:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i2));
                break;
            case 2:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i3));
                break;
            case 3:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i4));
                break;
            case 4:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i5));
                break;
            case 5:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i6));
                break;
            case 6:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i7));
                break;
            case 7:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i8));
                break;
            case 8:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i9));
                break;
            case 9:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i10));
                break;
            case 10:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i11));
                break;
            case 11:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i12));
                break;
            case 12:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i13));
                break;
            case 13:
                inside_back.setBackground(ContextCompat.getDrawable(context[0], R.drawable.i14));
                break;


        }


        Globals.user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetworkUtil.setConnectivityStatus(context[0]);
                if (status != 0) {
                    Toast t = new Toast(context[0]);
                    t.setText("clicked");
                    t.setDuration(Toast.LENGTH_SHORT);
                  //  t.show();

                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
                } else {
                    Toast toast = Toast.makeText(context[0], Html.fromHtml("<font color='#FF0000' > <b>" + ":( " + "Internet Connection Not Found !" + " ):  </b> </font>"), Toast.LENGTH_SHORT);

                    toast.show();
                }


                drawer.closeDrawer(GravityCompat.START);

            }


        });

        NetworkUtil.setConnectivityStatus(context[0]);
        if (status != 0) {
             percentage1 =  loadingDialog.startLoadingDialog();
             per1= percentage1.findViewById(R.id.progress_percentage);


            final ExecutorService executorServiceDownloadImage = Executors.newSingleThreadExecutor();
            executorServiceDownloadImage.execute(new Runnable() {
                @Override
                public void run() {


                    try {

                        String login_url = "https://stalinism-noun.000webhostapp.com/downloadimage.php";
                        //System.out.println("running-----------------------------------------------------------------------");
                        URL url = new URL(login_url);
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                        httpURLConnection.setRequestMethod("POST");
                        httpURLConnection.setDoOutput(true);
                        httpURLConnection.setDoInput(true);
                        OutputStream outputStream = httpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                        String post_data = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");


                        bufferedWriter.write(post_data);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();

                        InputStream inputStream = httpURLConnection.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));


                        String line = "";
                        int i = 0;
                        while ((line = bufferedReader.readLine()) != null) {
                            downloadimageresult[i] = "";
                            downloadimageresult[i] += line;
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


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if ((downloadimageresult[0] == null) || downloadimageresult[0].equals("null")) {
                                //System.out.println(":( Image Doesn't Exist! ):");
                                profilepicdownloaded=1;
                                per1.setText("100.00 %");
                            } else {

                                byte[] decodedImage = Base64.decode(downloadimageresult[0].getBytes(), Base64.DEFAULT);
                                Bitmap bmp = BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.length);
                                Globals.user_image.setImageBitmap(bmp);
                                Globals.profilepicdownloaded = 1;
                                //System.out.println("Image Downloaded");
                                profilepicdownloaded=1;
                                per1.setText("100.00 %");
                            }

                            //System.out.println("Download Image Dialog Dismissed!");

                        }
                    });

                }


            });
        }
        else {
            Toast toast = Toast.makeText(context[0], Html.fromHtml("<font color='#FF0000' > <b>" + ":( " + "Internet Connection Not Found ! " + " ):  </b> </font>"), Toast.LENGTH_SHORT);

            toast.show();
        }


        return true;
        }


    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish();// finish activity
            System.exit(0);
        } else {
            Toast.makeText(this, "Press Back Again To Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                    System.gc();
//
                }
            }, 5 * 1000);

        }

    }




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Bitmap bitmap;
            Uri filePath;

            try {
                filePath = data.getData();
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                        bitmap, 500, 500, false);


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                Globals.user_image.setImageBitmap(resizedBitmap);
                byte[] imageBytes = baos.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                //Background Work
                Context context = getApplicationContext();

               AlertDialog  percentage2 =  loadingDialog.startLoadingDialog();
               TextView  per2= percentage2.findViewById(R.id.progress_percentage);
                per2.setText("50.00 %");

                final ExecutorService executorServiceUploadImage= Executors.newSingleThreadExecutor();
                executorServiceUploadImage.execute(new Runnable() {
                    @Override
                    public void run() {

                        try {


                            String login_url="https://stalinism-noun.000webhostapp.com/uploadimage.php";
                            //System.out.println("running-----------------------------------------------------------------------");
                            URL url=new URL(login_url);
                            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                            httpURLConnection.setRequestMethod("POST");
                            httpURLConnection.setDoOutput(true);
                            httpURLConnection.setDoInput(true);
                            OutputStream outputStream=httpURLConnection.getOutputStream();
                            BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                            String post_data=
                                    URLEncoder.encode("image","UTF-8")+"="+URLEncoder.encode(encodedImage,"UTF-8")+"&"+
                                            URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8");


                            bufferedWriter.write(post_data);
                            bufferedWriter.flush();
                            bufferedWriter.close();
                            outputStream.close();


                            InputStream inputStream = httpURLConnection.getInputStream();
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));

                            String line="";
                            int i=0;
                            while((line = bufferedReader.readLine())!= null) {
                                uploadimageresult[i]="";
                                uploadimageresult[i] += line;
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




                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                 per2.setText("100.00 %");
                                    //System.out.println("Image Uploaded!");
                                    Globals.loadingDialog.dismissDialog();
                                //System.out.println("Upload Image Dialog Dismissed!");





                            }
                        });

                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}
