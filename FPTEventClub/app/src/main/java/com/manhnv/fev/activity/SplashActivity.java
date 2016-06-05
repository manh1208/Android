package com.manhnv.fev.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.manhnv.fev.GCM.QuickstartPreferences;
import com.manhnv.fev.GCM.RegistrationIntentService;
import com.manhnv.fev.R;
import com.manhnv.fev.util.Sync;
import com.onesignal.OneSignal;


/**
 * Created by manhn on 11/30/2015.
 */
public class SplashActivity extends Activity {

    private static final long SPLASH_TIME_OUT = 3000;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String TAG = "Splash Activity";
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private boolean isReceiverRegistered;
    private Context context;
    ConnectivityManager connManager;
    NetworkInfo mWifi;
    NetworkInfo mMobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

         connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
         mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
         mMobile = connManager .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        context = this.getApplicationContext();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(context);
                boolean sentToken = sharedPreferences
                        .getBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false);
                if (sentToken) {
                    OneSignal.startInit(context).init();
                    //Toast.makeText(SpashActivity.this, "Token ok", Toast.LENGTH_SHORT).show();
                    //  mInformationTextView.setText("Token ok");
                } else {
                    //   Toast.makeText(SpashActivity.this, "Lỗi rồi", Toast.LENGTH_SHORT).show();
                    // mInformationTextView.setText("Bị sida rồi");
                }
            }
        };
        registerReceiver();

        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            startService(intent);
        }
        
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
//                    SyncData();
//                    loadData();
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
//        Sync.getINSTANT().SyncUser(getApplicationContext(),1);
    }

    private void loadData(){

    }
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        isReceiverRegistered = false;
        super.onPause();
    }

    private void registerReceiver(){
        if(!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                    new IntentFilter(QuickstartPreferences.REGISTRATION_COMPLETE));
            isReceiverRegistered = true;
        }
    }
    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
