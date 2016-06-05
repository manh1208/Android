package com.manhnv.fev.dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.manhnv.fev.activity.LoginActivity;
import com.manhnv.fev.activity.RegisterActivity;
import com.manhnv.fev.dao.MemberDAO;
import com.manhnv.fev.dto.Member;
import com.manhnv.fev.R;
import com.manhnv.fev.fragment.MemberFragment;
import com.manhnv.fev.listener.ICallFragment;
import com.manhnv.fev.util.DbUtil;
import com.manhnv.fev.util.Sync;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by ManhNV on 12/22/2015.
 */
public class AddMemberDialog extends Dialog {
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
    private DbUtil dbUtil;
    private MemberDAO memberDao;
    private Member member;
    private int ban;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog datePickerDialog;
    private Spinner spinnerDepartment;
    private EditText txtHometown;
    private EditText txtAddress;

    public AddMemberDialog(Activity activity, int style, int ban) {
        super(activity, style);

        this.activity = activity;
        this.ban = ban;
    }

    public AddMemberDialog(Activity activity, int ban) {
        super(activity);

        this.activity = activity;
        this.ban = ban;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_addmember);
        if (this.dbUtil == null) {
            this.dbUtil = new DbUtil(this.activity.getApplicationContext());
        }
        if (this.memberDao == null) {
            this.memberDao = new MemberDAO(dbUtil);
        }

        txtFirstname = (EditText) findViewById(R.id.txt_firstname);
        txtMiddlename = (EditText) findViewById(R.id.txt_middlename);
        txtLastname = (EditText) findViewById(R.id.txt_lastname);
        txtStudentCode = (EditText) findViewById(R.id.txt_student_code);
        txtSchoolYear = (EditText) findViewById(R.id.txt_schoolyear);
        txtPhone = (EditText) findViewById(R.id.txt_phone);
        txtEmail = (EditText) findViewById(R.id.txt_email);
        txtNote = (EditText) findViewById(R.id.txt_note);
        txtBirthDate = (EditText) findViewById(R.id.txt_birthdate);
        txtHometown = (EditText) findViewById(R.id.txt_hometown);
        txtAddress = (EditText) findViewById(R.id.txt_address);
        setDobField();
        createSpinner();
        btnSave = (Button) findViewById(R.id.btn_Save);
        btnSave.setOnClickListener(new View.OnClickListener() {
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
                String password = "fev123";
                String confirmpassword = "fev123";
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
                    List<Member> m = memberDao.getBy("Email",email);
                    if (m.size()>0){
                        txtEmail.setError("Email đã tồn tại");
                        focus=txtEmail;
                    }else {
                        member = new Member();
                        member.setId(System.currentTimeMillis() % 100000 + "");
                        member.setFirstName(firstname);
                        member.setMiddleName(middlename);
                        member.setLastName(lastname);
                        member.setStudentId(studentCode);
                        member.setSchoolYear(Integer.parseInt(schoolYear));
                        member.setPhoneNumber(phone);
                        member.setEmail(email);
                        member.setIsActive(true);
                        member.setPassword(password);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
                        Calendar c = Calendar.getInstance();
                        java.util.Date date = c.getTime();
                        member.setChange(true);
                        member.setBirthdate(birthDate);
                        member.setDepartment(department);
                        member.setNote(note);
                        member.setHomeTown(hometown);
                        member.setCurrAddress(address);
                        member.setLastMonth(dateFormatter.format(date));
                        boolean result = memberDao.insert(member);
                        if (result){
                            dismiss();
                        }
//                        if (result){
//                            Sync.getINSTANT().SyncUser(activity.getApplicationContext(),1);
//                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                            startActivity(intent);
//                        }else{
//                            Toast.makeText(RegisterActivity.this, "Lỗi rồi nhé", Toast.LENGTH_SHORT).show();
//                        }
//                    Toast.makeText(activity, "Saved", Toast.LENGTH_SHORT).show();
//                    ICallFragment liICallFragment = (ICallFragment) activity;
//                    liICallFragment.callFragment(new MemberFragment());
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
//        String[] s = {};
//        if (member.getBirthdate()!=null && member.getBirthdate().length()>0){
//            s=member.getBirthdate().split("/");
//        }

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                txtBirthDate.setText(dateFormatter.format(newDate.getTime()));
                //date = new Date(newDate.getTime().getTime());
            }

        }, ( newCalendar.get(Calendar.YEAR)), ( newCalendar.get(Calendar.MONTH)),  ( newCalendar.get(Calendar.DATE)));
    }


    @Override
    public void setTitle(CharSequence title) {

        super.setTitle(title);
    }

    private void createSpinner(){
        spinnerDepartment = (Spinner) findViewById(R.id.spinner_department);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                R.array.department_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDepartment.setAdapter(adapter);
    }

}
