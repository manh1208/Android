package com.manhnv.fev.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.manhnv.fev.activity.MemberDetailActivity;
import com.manhnv.fev.adapter.MemberArrayAdapter;
import com.manhnv.fev.dao.MemberDAO;
import com.manhnv.fev.dialog.AddMemberDialog;
import com.manhnv.fev.dto.Member;
import com.manhnv.fev.R;
import com.manhnv.fev.util.DbUtil;

import java.util.List;

/**
 * Created by ManhNV on 12/22/2015.
 */
public class MemberThucHienFragment extends Fragment {
    private DbUtil dbUtil;
    private MemberDAO memberDao;
    private ListView listViewMembers;
    private MemberArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_member_thuchien,container,false);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab_addmember);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddMemberDialog addMemberDialog = new AddMemberDialog(getActivity(),R.style.cust_add_member_dialog,3);
                addMemberDialog.setTitle("Thêm thành viên");
                addMemberDialog.show();
                addMemberDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        adapter.update(getList());
                    }
                });
            }
        });
        SharedPreferences pre=getActivity().getSharedPreferences("LoginStatus", Context.MODE_PRIVATE);
        boolean isAdmin = pre.getBoolean("isAdmin", false);
        if (isAdmin) {
            fab.setVisibility(View.VISIBLE);
        }else{
            fab.setVisibility(View.INVISIBLE);
        }
        if (this.dbUtil == null) {
            this.dbUtil = new DbUtil(getActivity().getApplicationContext());
        }
        if (this.memberDao == null) {
            this.memberDao = new MemberDAO(dbUtil);
        }
        listViewMembers = (ListView) v.findViewById(R.id.lv_members);
         adapter = new MemberArrayAdapter(getActivity(),R.layout.view_member_item,getList());
        listViewMembers.setAdapter(adapter);
        listViewMembers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Member member = adapter.getItem(position);
                Intent intent = new Intent(getActivity(), MemberDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("memberId", member.getId());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        listViewMembers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Member member = adapter.getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("Cảnh Báo");
                builder.setMessage("Bạn muốn xóa thành viên " + member.getFirstName() + "?");

                builder
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                memberDao.delete(member);
                                adapter.update(getList());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                builder.create();
                builder.show();


                return false;
            }
        });

        return v;
    }
    private List<Member> getList(){
        List<Member> list = memberDao.getBy("Department", "Thực hiện");
        return list;
    }

    @Override
    public void onResume() {
        adapter.update(getList());
        super.onResume();
    }
}
