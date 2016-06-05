package com.manhnv.fev.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.manhnv.fev.R;
import com.manhnv.fev.dao.FundDAO;
import com.manhnv.fev.dao.MemberDAO;
import com.manhnv.fev.dto.Fund;
import com.manhnv.fev.dto.Member;
import com.manhnv.fev.util.DbUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by ManhNV on 5/26/2016.
 */
public class TransationDialog extends Dialog {
    private Spinner spinnerType;
    private Context mContext;
    private TextView mCreator;
    private DbUtil dbUtil;
    private MemberDAO memberDao;
    private FundDAO fundDAO;
    private Member mCurrentMember;
    private TextView txtCreator;
    private EditText txtResource;
    private EditText txtAmount;
    private EditText txtNote;

    public TransationDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_transaction);
        if (this.dbUtil == null) {
            this.dbUtil = new DbUtil(mContext.getApplicationContext());
        }
        if (this.memberDao == null) {
            this.memberDao = new MemberDAO(dbUtil);
        }
        if (this.fundDAO == null){
            this.fundDAO = new FundDAO(dbUtil);
        }
        init();
        Button submit = (Button) findViewById(R.id.btn_submit_transaction);
         txtCreator = (TextView) findViewById(R.id.txt_creator);
        txtResource = (EditText) findViewById(R.id.txt_resource);
        txtAmount = (EditText) findViewById(R.id.txt_amount);
        txtNote = (EditText) findViewById(R.id.txt_note);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String creator = txtCreator.getText().toString();
                String resource = txtResource.getText().toString();
                String amount = txtAmount.getText().toString();
                String note = txtNote.getText().toString();
                String type = spinnerType.getSelectedItem().toString();
                View focus = null;
                boolean cancel = false;
                if (resource.trim().length() <= 0) {
                    txtResource.setError("Xin hãy nhập nguồn thu/chi");
                    cancel = true;
                    focus = txtResource;
                }
                if (amount.trim().length()<=0){
                    txtAmount.setError("Xin hãy nhập số tiền");
                    cancel = true;
                    focus=txtAmount;
                }
                if (cancel){
                    focus.requestFocus();
                }else{
                    Fund fund = new Fund();
                    fund.setCreator(mCurrentMember.getId());
                    fund.setResource(resource);
                    fund.setAmount(Integer.parseInt(amount));
                    fund.setNote(note);
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
                    Calendar c = Calendar.getInstance();
                    java.util.Date date = c.getTime();
                    fund.setCreateDate(dateFormatter.format(date));
                    fund.setType(type.trim().equals("Thu")?1:-1);
                    boolean result = fundDAO.insert(fund);
                    if (result){
                        dismiss();
                    }
                }
            }
        });
    }

    private void init() {
        SharedPreferences pre=mContext.getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        String currentEmail = pre.getString("email", "");
        boolean isAdmin = pre.getBoolean("isAdmin",false);
         mCurrentMember = memberDao.getBy("Email",currentEmail).get(0);
        mCreator = (TextView) findViewById(R.id.txt_creator);
        mCreator.setText(mCurrentMember.getFullName());
        createspinner();
    }


    private void createspinner() {
        spinnerType = (Spinner) findViewById(R.id.spn_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(mContext,
                R.array.transaction_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapter);
    }
}
