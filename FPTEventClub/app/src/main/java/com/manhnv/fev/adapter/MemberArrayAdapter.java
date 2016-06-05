package com.manhnv.fev.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.manhnv.fev.dto.Member;
import com.manhnv.fev.R;

import java.util.List;

/**
 * Created by ManhNV on 12/27/2015.
 */
public class MemberArrayAdapter extends ArrayAdapter<Member> {
    private Context Context;
    private int resource;
    private List<Member> members;

    public MemberArrayAdapter(Context context, int resource, List<Member> members) {
        super(context, resource, members);
        this.Context = context;
        this.resource = resource;
        this.members = members;
    }

    @Override
    public int getCount() {
        return members.size();
    }

    @Override
    public Member getItem(int position) {
        return members.get(position);
    }

    @Override
    public long getItemId(int position) {
        return members.get(position).getId().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view_member_item,parent,false);
        }
        Member member = members.get(position);
        TextView lblFullname = (TextView) convertView.findViewById(R.id.lbl_itemfullname);
        TextView lblPhone = (TextView) convertView.findViewById(R.id.lbl_itemphone);
        String fullname =member.getLastName()+" "+(member.getMiddleName()!=null?member.getMiddleName()+" ":"") + member.getFirstName();
        lblFullname.setText(fullname);
        lblPhone.setText(member.getPhoneNumber());
        return convertView;
    }
    public void update(List<Member> mMembers) {
        this.members = mMembers;
        notifyDataSetChanged();
    }
}
