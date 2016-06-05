package com.manhnv.fev.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.manhnv.fev.R;
import com.manhnv.fev.dto.Fund;

import java.util.List;

/**
 * Created by ManhNV on 5/28/2016.
 */
public class FundAdapter extends ArrayAdapter<Fund> {
    private Context mContext;
    private List<Fund> mFunds;

    public FundAdapter(Context context, int resource, List<Fund> funds) {
        super(context, resource, funds);
        this.mContext = context;
        this.mFunds = funds;
    }

    @Override
    public Fund getItem(int position) {
        return mFunds.get(position);
    }

    @Override
    public int getCount() {
        return mFunds.size();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_fund_item,parent,false);
        }
        TextView txtResource = (TextView) convertView.findViewById(R.id.txt_fund_resource);
        txtResource.setTypeface(Typeface.DEFAULT);
        TextView txtAmount = (TextView) convertView.findViewById(R.id.txt_fund_amount);
        txtAmount.setTypeface(Typeface.DEFAULT);
        TextView txtType = (TextView) convertView.findViewById(R.id.txt_fund_type);
        txtType.setTypeface(Typeface.DEFAULT);
        Fund fund = getItem(position);

        txtResource.setText(fund.getResource());
        txtAmount.setText(fund.getAmount()+"");
        txtType.setText(fund.getType()>0?"Thu":"Chi");
        return convertView;
    }

    public void setmFunds(List<Fund> mFunds) {
        this.mFunds = mFunds;
        notifyDataSetChanged();
    }
}
