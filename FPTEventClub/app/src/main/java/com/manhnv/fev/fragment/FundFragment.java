package com.manhnv.fev.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.manhnv.fev.R;
import com.manhnv.fev.adapter.FundAdapter;
import com.manhnv.fev.dao.FundDAO;
import com.manhnv.fev.dao.MemberDAO;
import com.manhnv.fev.dialog.TransationDialog;
import com.manhnv.fev.dto.Fund;
import com.manhnv.fev.util.DbUtil;

import java.util.List;

/**
 * Created by ManhNV on 12/22/2015.
 */
public class FundFragment extends Fragment {
    private DbUtil dbUtil;
    private FundDAO fundDao;
    private FloatingActionButton btnIncome;
    private FloatingActionButton btnOutcome;
    private TextView mIncome;
    private TextView mOutcome;
    private TextView mSum;
    private FundAdapter adapter;
    private ListView mFundsView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_fund,container,false);
        if (this.dbUtil == null) {
            this.dbUtil = new DbUtil(getActivity().getApplicationContext());
        }
        if (this.fundDao == null) {
            this.fundDao = new FundDAO(dbUtil);
        }



         mIncome = (TextView) v.findViewById(R.id.lbl_income_number);

         mOutcome = (TextView) v.findViewById(R.id.lbl_outcome_number);
        mSum = (TextView) v.findViewById(R.id.txt_sum_fund);

        SharedPreferences pre=getActivity().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        String currentEmail = pre.getString("email", "");
        boolean isAdmin = pre.getBoolean("isAdmin",false);
        btnIncome = (FloatingActionButton) v.findViewById(R.id.fab_addincome);
        btnOutcome= (FloatingActionButton) v.findViewById(R.id.fab_addexpenditure);
        if (!isAdmin){
            btnIncome.setVisibility(View.INVISIBLE);
            btnOutcome.setVisibility(View.INVISIBLE);
        }

        btnIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransationDialog transationDialog = new TransationDialog(getActivity(),R.style.cust_add_member_dialog);
                transationDialog.show();
                transationDialog.setTitle("Tạo giao dịch");
                transationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        onResume();
                    }
                });
            }
        });

        btnOutcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransationDialog transationDialog = new TransationDialog(getActivity(),R.style.cust_add_member_dialog);
                transationDialog.show();
                transationDialog.setTitle("Tạo giao dịch");
                transationDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        onResume();
                    }
                });
            }
        });
        List<Fund> fundList = fundDao.getFive();

        mFundsView = (ListView) v.findViewById(R.id.listViewFund);
        adapter = new FundAdapter(getActivity(),R.layout.view_fund_item,fundList);
        mFundsView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onResume() {
        List<Fund> fundList = fundDao.getAll();
        int income = getIncome(fundList);
        int outcome = getOutcome(fundList);

        mIncome.setText(income+"K");

        mOutcome.setText(outcome+"K");
        mSum.setText("Tổng: "+(income-outcome)+"K");
        adapter.setmFunds(fundDao.getFive());
        super.onResume();
    }

    public int getIncome(List<Fund> funds){
        int sum = 0;
        for (Fund item :funds
             ) {
            if (item.getType()>0){
                sum+=item.getAmount();
            }
        }
        return sum;
    }
    public int getOutcome(List<Fund> funds){
        int sum = 0;
        for (Fund item :funds
                ) {
            if (item.getType()<0){
                sum+=item.getAmount();
            }
        }
        return sum;
    }
}
