package com.manhnv.fev.util;

import android.content.Context;
import android.widget.Toast;

import com.manhnv.fev.dao.MemberDAO;
import com.manhnv.fev.dto.Member;
import com.manhnv.fev.response.SyncResponse;
import com.manhnv.fev.services.RestService;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by ManhNV on 12/27/2015.
 */
public class Sync {
        private static Sync INSTANT=null;
    private static DbUtil dbUtil=null;
    private static MemberDAO memberDao=null;
        private Sync(){

        }
    private static Context mContext;
    public static Sync getINSTANT(){
        if (INSTANT ==null){
            INSTANT = new Sync();
        }
        return INSTANT;
    }


    public static boolean SyncUser( final Context context, final int flags){
        mContext=context;
        if (dbUtil == null) {
            dbUtil = new DbUtil(context);
        }
        if (memberDao == null) {
            memberDao = new MemberDAO(dbUtil);
        }
        RestService restService = new RestService();
        List<Member> list = memberDao.getAll();

        final boolean[] flag = {true};
        List<Member> listChange = new ArrayList<Member>();
        for (Member m:list
             ) {
            if (m.isChange()){
                listChange.add(m);
            }
        }
        restService.getUserService().syncData(listChange, new Callback<SyncResponse>() {
            @Override
            public void success(SyncResponse syncResponse, Response response) {
                if (syncResponse.isStatus()) {
                    Toast.makeText(mContext, syncResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    List<Member> members = syncResponse.getMembers();
                    memberDao.deleteAll();
                    for (Member member : members
                            ) {
                        member.setChange(false);
                        if (!memberDao.insert(member))
                            flag[0] = false;
                    }
                    if (flag[0] && flags==2){
//                        ICallFragment call = (MainActivity)context;
//                        call.reloadFragment();
                    }
                }else{
                    Toast.makeText(mContext, syncResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                flag[0] = false;
            }
        });
        return flag[0];
    }
}
