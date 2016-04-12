package com.manhnv.fev.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.manhnv.fev.dao.MemberDAO;
import com.manhnv.fev.dto.Member;
import com.manhnv.fev.R;
import com.manhnv.fev.util.DbUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity  {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    Button mbtnLogin;
    Button mbtnRegister;
    EditText mtxtEmail;
    EditText mtxtPassword;
    private DbUtil dbUtil;
    private MemberDAO memberDao;
    private Member member;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (this.dbUtil == null) {
            this.dbUtil = new DbUtil(getApplicationContext());
        }
        if (this.memberDao == null) {
            this.memberDao = new MemberDAO(dbUtil);
        }

        mbtnLogin = (Button) findViewById(R.id.btn_login);
        mbtnRegister = (Button) findViewById(R.id.btn_register);
        mtxtEmail = (EditText) findViewById(R.id.txt_login_email);
        mtxtPassword = (EditText) findViewById(R.id.txt_login_password);

        mbtnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                 String email = mtxtEmail.getText().toString();
                 String password = mtxtPassword.getText().toString();
                if (email.length()<=0){
                    mtxtEmail.setError("Hãy nhập Email");
                    mtxtEmail.setFocusable(true);
                }
                if (password.length()<=0){
                    mtxtPassword.setError("Hãy nhập Password");
                    mtxtPassword.setFocusable(true);
                }
                if (!isValidEmail(email)){
                    mtxtEmail.setError("Email is invalid");
                    mtxtEmail.setFocusable(true);
                }else
                if (isValidLogin(email,password)){
                    SharedPreferences pre=getSharedPreferences("LoginStatus", MODE_PRIVATE);
                    SharedPreferences.Editor edit=pre.edit();
                    edit.putString("email",email);
                    edit.commit();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    mtxtEmail.setError("Invalid Email hoac Password");
                    mtxtPassword.setError("Invalid Email hoac Password");
                    mtxtEmail.setFocusable(true);
                }
            }
        });
        mbtnRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    private boolean isValidLogin(String email,String password){
        Member member = memberDao.checkLogin(email,password);
        if (member!=null){
            return true;
        }

        return false;
    }
}

