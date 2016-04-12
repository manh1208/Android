package com.manhnv.fev.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.manhnv.fev.fevms.R;
import com.manhnv.fev.util.Sync;
import com.onesignal.OneSignal;

/**
 * Created by manhn on 11/30/2015.
 */
public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_TIME_OUT = 3000;
    ConnectivityManager connManager;
    NetworkInfo mWifi;
    NetworkInfo mMobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        OneSignal.startInit(this).init();
         connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
         mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
         mMobile = connManager .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        
        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if (mWifi.isConnected() || mMobile.isConnected()){
                    SyncData();
                    loadData();
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(i);
                    finish();

                }else{
                    Toast.makeText(SplashActivity.this, "Không có kết nối internet. Sử dụng ứng dụng offline?", Toast.LENGTH_LONG).show();
                    AlertDialog myAlertDialog = taoMotAlertDialog();
                    myAlertDialog.show();
                }

            }
        }, SPLASH_TIME_OUT);
    }

    private AlertDialog taoMotAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Thiết lập tiêu đề hiển thị
        builder.setTitle("No Internet");
        //Thiết lập thông báo hiển thị
        builder.setMessage("Bạn muốn sử dụng ứng dụng offline không?");
        builder.setCancelable(false);
        //Tạo nút Cài Đặt
        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
        //Tạo nút Thoát
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        finish();
                    }
                });
        AlertDialog dialog = builder.create();
        return dialog;
    }

    private void SyncData(){
        Sync.getINSTANT().SyncUser(getApplicationContext(),1);
    }

    private void loadData(){

    }
}
