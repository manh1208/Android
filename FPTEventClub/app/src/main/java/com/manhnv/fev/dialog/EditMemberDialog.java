package com.manhnv.fev.dialog;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.manhnv.fev.activity.LoginActivity;
import com.manhnv.fev.dao.MemberDAO;
import com.manhnv.fev.dto.Member;
import com.manhnv.fev.R;
import com.manhnv.fev.util.DbUtil;
import com.manhnv.fev.util.Sync;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ManhNV on 12/22/2015.
 */
public class EditMemberDialog extends Dialog {
    private Activity activity;
    private ArrayAdapter<String> adapter;
    private Spinner role;
    private Button btnSave;
    private EditText txtFirstname;
    private EditText txtMiddlename;
    private EditText txtLastname;
    private EditText txtStudentCode;
    private EditText txtSchoolYear;
    private EditText txtEmail;
    private EditText txtPhone;
    private EditText txtNote;
    private EditText txtBirthDate;
    private EditText txtHometown;
    private EditText txtAddress;
    private Spinner spinnerDepartment;
    private DbUtil dbUtil;
    private MemberDAO memberDao;
    private Member member;
    private SimpleDateFormat dateFormatter;
    private EditText txtDob;
    private DatePickerDialog datePickerDialog;
    private String id;


    public EditMemberDialog(Activity activity, int style) {
        super(activity, style);

        this.activity = activity;
    }

    public EditMemberDialog(Activity activity) {
        super(activity);

        this.activity = activity;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_editinformation);
        if (this.dbUtil == null) {
            this.dbUtil = new DbUtil(activity.getApplicationContext());
        }
        if (this.memberDao == null) {
            this.memberDao = new MemberDAO(dbUtil);
        }

        member = memberDao.getByPrimary(id);
        txtFirstname = (EditText) findViewById(R.id.txt_edit_firstname);
        txtFirstname.setText(member.getFirstName());
        txtMiddlename = (EditText) findViewById(R.id.txt_edit_middlename);
        txtMiddlename.setText(member.getMiddleName());
        txtLastname = (EditText) findViewById(R.id.txt_edit_lastname);
        txtLastname.setText(member.getLastName());
        txtStudentCode = (EditText) findViewById(R.id.txt_edit_student_code);
        txtStudentCode.setText(member.getStudentId());
        txtSchoolYear = (EditText) findViewById(R.id.txt_edit_schoolyear);
        txtSchoolYear.setText(member.getSchoolYear()+"");
        txtPhone = (EditText) findViewById(R.id.txt_edit_phone);
        txtPhone.setText(member.getPhoneNumber());
        txtEmail = (EditText) findViewById(R.id.txt_edit_email);
        txtEmail.setText(member.getEmail());
        txtNote = (EditText) findViewById(R.id.txt_edit_note);
        txtBirthDate = (EditText) findViewById(R.id.txt_edit_birthdate);
        txtBirthDate.setText(member.getBirthdate());
        txtHometown = (EditText) findViewById(R.id.txt_edit_hometown);
        txtHometown.setText(member.getHomeTown());
        txtAddress = (EditText) findViewById(R.id.txt_edit_address);
        txtAddress.setText(member.getCurrAddress());
        createSpinner(member.getDepartment());
//        txtFirstname = (EditText) findViewById(R.id.txt_firstname);
//        txtFirstname.setText(member.getFirstName());
//        txtMiddlename = (EditText) findViewById(R.id.txt_middlename);
//        if (member.getMiddleName()!=null)
//        txtMiddlename.setText(member.getMiddleName());
//        txtLastname = (EditText) findViewById(R.id.txt_lastname);
//        txtLastname.setText(member.getLastName());
//        txtStudentCode = (EditText) findViewById(R.id.txt_student_code);
//        txtStudentCode.setText(member.getStudentId());
//        txtSchoolYear = (EditText) findViewById(R.id.txt_schoolyear);
//        txtSchoolYear.setText(member.getSchoolYear().toString());
//        txtPhone = (EditText) findViewById(R.id.txt_phone);
//        txtPhone.setText(member.getPhoneNumber());
//        txtEmail = (EditText) findViewById(R.id.txt_email);
//        txtEmail.setText(member.getEmail());
//        txtNote = (EditText) findViewById(R.id.txt_note);
//        if (member.getNote()!=null){
//            txtNote.setText(member.getNote());
//        }
//         txtClass = (TextView) findViewById(R.id.txt_class);
//        if (member.getUserClass()!=null)
//            txtClass.setText(member.getUserClass());
//        txtHomeTown = (TextView) findViewById(R.id.txt_HomeTown);
//        if (member.getHomeTown()!=null)
//            txtHomeTown.setText(member.getHomeTown());
//        txtCurrAddress = (TextView) findViewById(R.id.txt_CurrAddress);
//        if (member.getCurrAddress()!=null)
//            txtCurrAddress.setText(member.getCurrAddress());
//        txtLinkFacebook = (TextView) findViewById(R.id.txt_LinkFacebook );
//        if (member.getLinkFacebook()!=null)
//            txtLinkFacebook.setText(member.getLinkFacebook());
//        txtDob = (EditText) findViewById(R.id.txt_birthdate);
//            if (member.getBirthdate()!=null){
//                txtDob.setText(member.getBirthdate());
//            }
        btnSave = (Button) findViewById(R.id.btn_edit_Save);
        setDobField();
        btnSave.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                String firstname = txtFirstname.getText().toString();
                String middlename = txtMiddlename.getText().toString();
                String lastname = txtLastname.getText().toString();
                String studentCode = txtStudentCode.getText().toString();
                String schoolYear = txtSchoolYear.getText().toString();
                String phone = txtPhone.getText().toString();
                String email = txtEmail.getText().toString();
                String note = txtNote.getText().toString();
                String birthDate = txtBirthDate.getText().toString();
                String department = spinnerDepartment.getSelectedItem().toString();
                String hometown = txtHometown.getText().toString();
                String address = txtAddress.getText().toString();
                View focus = null;
                boolean cancel = false;
                if (email.trim().length() <= 0) {
                    txtEmail.setError("Xin hãy nhập email");
                    cancel = true;
                    focus = txtEmail;
                }
                if (phone.trim().length() <= 0) {
                    txtPhone.setError("Xin hãy nhập số điện thoại");
                    cancel = true;
                    focus = txtPhone;
                }
                if (schoolYear.trim().length() <= 0) {
                    txtSchoolYear.setError("Xin hãy nhập khóa nhập học");
                    cancel = true;
                    focus = txtSchoolYear;
                }
                if (studentCode.trim().length() <= 0) {
                    txtStudentCode.setError("Xin hãy nhập mã số sinh viên");
                    cancel = true;
                    focus = txtStudentCode;
                }
                if (firstname.trim().length() <= 0) {
                    txtFirstname.setError("Xin hãy nhập tên");
                    cancel = true;
                    focus = txtFirstname;
                }

                if (lastname.trim().length() <= 0) {
                    txtLastname.setError("Xin hãy nhập họ");
                    cancel = true;
                    focus = txtLastname;
                }
                if (birthDate.trim().length()<=0){
                    txtBirthDate.setError("Xin hãy nhập ngày sinh");
                    cancel = true;
                    focus = txtBirthDate;
                }

                if (hometown.trim().length()<=0){
                    txtHometown.setError("Xin hãy nhập quê quán");
                    cancel=true;
                    focus = txtHometown;
                }

                if (cancel) {
                    focus.requestFocus();

                } else {

                    Member m = memberDao.checkEmailEdit(email,member.getId());
                    if (m!=null){
                        txtEmail.setError("Email đã tồn tại");
                        focus=txtEmail;
                    }else {
                        //member.setId(System.currentTimeMillis() % 100000 + "");
                        member.setFirstName(firstname);
                        member.setMiddleName(middlename);
                        member.setLastName(lastname);
                        member.setStudentId(studentCode);
                        member.setSchoolYear(Integer.parseInt(schoolYear));
                        member.setPhoneNumber(phone);
                        member.setEmail(email);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                        Calendar c = Calendar.getInstance();
                        java.util.Date date = c.getTime();
                        member.setBirthdate(birthDate);
                        member.setDepartment(department);
                        member.setNote(note);
                        member.setHomeTown(hometown);
                        member.setCurrAddress(address);
                        member.setChange(true);

                        boolean result = memberDao.update(member);
                        if (result){
                            SharedPreferences pre=activity.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
                            Sync.getINSTANT().SyncUser(activity, 1);
                            String currentEmail = pre.getString("email","");
                            if (currentEmail.trim().equals(member.getEmail().trim())) {
                                activity.recreate();
                            }else{
                                SharedPreferences.Editor edit=pre.edit();
                                edit.clear();
                                Intent intent = new Intent(activity,LoginActivity.class);
                                activity.startActivity(intent);
                                activity.finish();
                            }
                            dismiss();
                            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();

                        }else{
                            Toast.makeText(getContext(), "Lỗi rồi nhé", Toast.LENGTH_SHORT).show();
                        }

//                    Intent intent = new Intent(activity,MemberDetailActivity.class);
//                    Bundle bundle = new Bundle();
//                    bundle.putString("memberId", member.getId());
//                    intent.putExtras(bundle);

                    }
                }

            }
        });


    }

    private void setDobField() {
        dateFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        txtBirthDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    datePickerDialog.show();
            }
        });
        txtBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        String[] s = {};
        if (member.getBirthdate()!=null && member.getBirthdate().length()>0){
            s=member.getBirthdate().split("/");
        }

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                txtBirthDate.setText(dateFormatter.format(newDate.getTime()));
                //date = new Date(newDate.getTime().getTime());
            }

        }, (s.length>0? Integer.parseInt(s[2]): newCalendar.get(Calendar.YEAR)), (s.length>0? Integer.parseInt(s[0])-1: newCalendar.get(Calendar.MONTH)),  (s.length>0? Integer.parseInt(s[1]): newCalendar.get(Calendar.DATE)));
    }


    @Override
    public void setTitle(CharSequence title) {
        String[] s= title.toString().split(":");
        id = s[1];
        super.setTitle(s[0]);
    }

    private void createSpinner(String s){
        spinnerDepartment = (Spinner) findViewById(R.id.spinner_edit_department);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.department_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapter);
        String[] array = activity.getResources().getStringArray(R.array.department_array);
        for (int i=0;i<array.length;i++){
            if (array[i].trim().equals(s.trim())){
                spinnerDepartment.setSelection(i);
                break;
            }
        }

    }


}
