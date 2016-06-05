package com.manhnv.fev.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.manhnv.fev.dao.FundDAO;
import com.manhnv.fev.dao.MemberDAO;
import com.manhnv.fev.dialog.EditMemberDialog;
import com.manhnv.fev.dto.Fund;
import com.manhnv.fev.dto.Member;
import com.manhnv.fev.R;
import com.manhnv.fev.util.DbUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MemberDetailActivity extends AppCompatActivity {
    private TextView lblEmail;
    private TextView lblPhone;
    private TextView lblBirthDate;

    private TextView lblStudentCode;

    private TextView lblHomeTown;
    private TextView lblCurrAddress;
    private TextView lblSchoolYear;
    private TextView lblDepartment;
    FloatingActionButton btnEdit;
    FloatingActionButton btnFund;
    private TextView lblNote;
    private Activity activity = this;
    private String id;
    public Bundle savedInstanceState;
    private DbUtil dbUtil;
    private MemberDAO memberDao;
    Member member;
    Toolbar toolbar;
    private FundDAO fundDao;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        if (this.dbUtil == null) {
            this.dbUtil = new DbUtil(activity.getApplicationContext());
        }
        if (this.memberDao == null) {
            this.memberDao = new MemberDAO(dbUtil);
        }
        if (this.fundDao ==null){
            this.fundDao=new FundDAO(dbUtil);
        }
        this.savedInstanceState = savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.id = getIntent().getStringExtra("memberId");
        lblEmail = (TextView) findViewById(R.id.lbl_email);
        lblPhone = (TextView) findViewById(R.id.lbl_phone);
        lblBirthDate = (TextView) findViewById(R.id.lbl_dob);
        lblStudentCode = (TextView) findViewById(R.id.lbl_studentId);
        lblSchoolYear = (TextView) findViewById(R.id.lbl_school_year);
        lblHomeTown = (TextView) findViewById(R.id.lbl_home_town);
        lblCurrAddress = (TextView) findViewById(R.id.lbl_current_address);
        lblDepartment = (TextView) findViewById(R.id.lbl_department);

        lblNote = (TextView) findViewById(R.id.lbl_note);


        btnEdit = (FloatingActionButton) findViewById(R.id.btn_edit);
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) btnEdit.getLayoutParams();
        p.setBehavior(null);
        p.setAnchorId(View.NO_ID);
        btnEdit.setLayoutParams(p);
        btnEdit.setVisibility(View.GONE);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditMemberDialog editMemberDialog = new EditMemberDialog(activity, R.style.cust_add_member_dialog);
                editMemberDialog.setTitle("Sửa thông tin:" + id);
                editMemberDialog.show();
            }
        });

        btnFund = (FloatingActionButton) findViewById(R.id.btn_fund);
        CoordinatorLayout.LayoutParams p2 = (CoordinatorLayout.LayoutParams) btnFund.getLayoutParams();
        p2.setBehavior(null);
        p2.setAnchorId(View.NO_ID);
        btnFund.setLayoutParams(p2);
        btnFund.setVisibility(View.GONE);
        btnFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EditMemberDialog editMemberDialog = new EditMemberDialog(activity, R.style.cust_add_member_dialog);
//                editMemberDialog.setTitle("Sửa thông tin:" + id);
//                editMemberDialog.show();

                SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
                Calendar c = Calendar.getInstance();
                Date result = c.getTime();
                int month = 0;
                try {
                    result =  dateFormatter.parse(member.getLastMonth());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(result);
                    cal.add(Calendar.MONTH,1);
                    result = cal.getTime();
                    member.setLastMonth(dateFormatter.format(result));
                    boolean res =  memberDao.update(member);
                    Fund fund = new Fund();
                    fund.setResource(member.getFullName());
                    fund.setType(1);
                    fund.setAmount(20);
                    fund.setCreateDate(dateFormatter.format(c.getTime()));
                    int i = (int) (System.currentTimeMillis() % 100000);
                    fund.setId(i);
                    month=cal.get(Calendar.MONTH)-                                                                                                                                                                                                                                                                                                                                                                                                                                                             1;
                    fund.setNote("Đóng Quỹ Tháng "+month);
                    fund.setCreator("Admin");
                    boolean res2=fundDao.insert(fund);
                    if (res && res2){
                        Toast.makeText(MemberDetailActivity.this, "Đã đóng quỹ thành công", Toast.LENGTH_SHORT).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            recreate();
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        if (ActivityCompat.checkSelfPermission(MemberDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            String[] perms = {"android.permission.CALL_PHONE"};
            int permsRequestCode = 200;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(perms, permsRequestCode);
            }
        }

        FloatingActionButton btnCall = (FloatingActionButton) findViewById(R.id.btn_call);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + member.getPhoneNumber()));

                if (ActivityCompat.checkSelfPermission(MemberDetailActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


                    startActivity(callIntent);

                } else {

                    startActivity(callIntent);
                }

            }
        });
        FloatingActionButton btnEmail = (FloatingActionButton) findViewById(R.id.btn_email);
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                String aEmailList[] = {member.getEmail()};
                intent.putExtra(Intent.EXTRA_EMAIL, aEmailList);
                intent.putExtra(Intent.EXTRA_SUBJECT, "[FPT EVENT CLUB]");

                intent.setType("plain/text");

                startActivity(Intent.createChooser(intent, "Send email in:"));
            }
        });

        FloatingActionButton btnSms = (FloatingActionButton) findViewById(R.id.btn_sms);
        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("address",member.getPhoneNumber().trim());
                sendIntent.setType("vnd.android-dir/mms-sms");
                activity.startActivity(sendIntent);
            }
        });
        setSupportActionBar(toolbar);


    }

    @Override
    protected void onResume() {
        super.onResume();
        member = memberDao.getByPrimary(id);
        SharedPreferences pre=getSharedPreferences("LoginStatus", MODE_PRIVATE);
        String currentEmail = pre.getString("email", "");
        boolean isAdmin = pre.getBoolean("isAdmin",false);
        if (currentEmail.trim().equals(member.getEmail().trim()) || isAdmin ){
                CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) btnEdit.getLayoutParams();
                p.setBehavior(new FloatingActionButton.Behavior());
                p.setAnchorId(R.id.app_bar);
                btnEdit.setLayoutParams(p);

        }
        if (isAdmin){
            CoordinatorLayout.LayoutParams p2 = (CoordinatorLayout.LayoutParams) btnFund.getLayoutParams();
            p2.setBehavior(new FloatingActionButton.Behavior());
            p2.setAnchorId(R.id.app_bar);
            btnFund.setLayoutParams(p2);
        }
        String[] s = member.getMiddleName().split(" ");
        String middleName = "";
        for (int i=0;i<s.length;i++){
            middleName+=s[i].substring(0,1)+". ";
        }
        String fullname = member.getLastName().substring(0,1) + ". " + middleName + member.getFirstName();
        toolbar.setTitle(fullname);

        id=member.getId();

        lblEmail.setText(member.getEmail());

        lblPhone.setText(member.getPhoneNumber());

        if (member.getBirthdate()!=null)
            lblBirthDate.setText(member.getBirthdate().toString());



        lblStudentCode.setText(member.getStudentId());


        lblSchoolYear.setText(member.getSchoolYear().toString());

        if (member.getHomeTown()!=null){
            lblHomeTown.setText(member.getHomeTown());
        }


        if (member.getCurrAddress()!=null){
            lblCurrAddress.setText(member.getCurrAddress());
        }

        if (member.getDepartment()!=null){
            lblDepartment.setText(member.getDepartment());
        }


//        if (member.getDepartment()!=null){
//            String department = "";
//            switch ( member.getDepartment()){
//                case 1: department="Kế hoạch"; break;
//                case 2: department="Thực hiện"; break;
//                case 3: department="Truyền Thông"; break;
//            }
//            lblDepartment.setText(department);
//        }

//        if (member.getRole()!=null){
//            String role=  "";
//            switch (member.getRole()){
//                case "CN": role="Chủ Nhiệm";break;
//                case "PCN": role="Phó Chủ Nhiệm";break;
//                case "TQ": role="Thủ Quỹ";break;
//                case "HR": role="Quản lý nhân sự";break;
//                case "TBKH": role="Trưởng Ban Kế hoạch";break;
//                case "TBTH": role="Trưởng Ban Thực Hiện";break;
//                case "TBTT": role="Trưởng Ban Truyền Thông";break;
//                case "Member": role="Thành viên";break;
//                default:role = "Chưa phân ban";
//            }
//            lblRole.setText(role);
//        }

//        if (member.getLinkFacebook()!=null){
//            lblLinkFacebook.setText(member.getLinkFacebook());
//        }

        if (member.getNote()!=null){
            lblNote.setText(member.getNote());
        }
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        Calendar c = Calendar.getInstance();
        Date result = c.getTime();
        int month = 0;
        try {
             result =  dateFormatter.parse(member.getLastMonth());
            Calendar cal = Calendar.getInstance();
            cal.setTime(result);
             month = cal.get(Calendar.MONTH) +1;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        lblNote.setText("Lần cuối đóng tiền là: "+ (new SimpleDateFormat("MM/yyyy",Locale.US)).format(result));
    }


}
