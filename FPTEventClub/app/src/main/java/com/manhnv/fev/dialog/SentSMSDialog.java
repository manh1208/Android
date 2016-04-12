package com.manhnv.fev.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.manhnv.fev.R;

/**
 * Created by ManhNV on 12/31/2015.
 */
public class SentSMSDialog extends Dialog {
    private Activity activity;
    private String phoneNumber;
    private Button btn_SentSMS;

    public SentSMSDialog(Activity activity, int style, String phoneNumber) {
        super(activity, style);

        this.activity = activity;
        this.phoneNumber = phoneNumber;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialogsentsms);
        final Intent sendIntent = new Intent(Intent.ACTION_VIEW);
        btn_SentSMS = (Button) findViewById(R.id.btn_sentsms);
        btn_SentSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_Content = (EditText) findViewById(R.id.txt_SentSMS_Content);
                String s = "";
                if (txt_Content.getText() != null) {
                    s = txt_Content.getText().toString();
                    sendIntent.putExtra("sms_body", s);
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    activity.startActivity(sendIntent);
                }else{
                    Toast.makeText(activity, "Nhập nội dung muốn gởi", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    @Override
    public void setTitle(CharSequence title) {

        super.setTitle(title);
    }
}
