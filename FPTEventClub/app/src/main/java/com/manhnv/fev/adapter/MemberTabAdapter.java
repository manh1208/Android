package com.manhnv.fev.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.manhnv.fev.fragment.MemberAllFragment;
import com.manhnv.fev.fragment.MemberChuNhiemFragment;
import com.manhnv.fev.fragment.MemberKeHoachFragment;
import com.manhnv.fev.fragment.MemberThucHienFragment;
import com.manhnv.fev.fragment.MemberTruyenThongFragment;

/**
 * Created by ManhNV on 12/22/2015.
 */
public class MemberTabAdapter extends FragmentPagerAdapter {

    public MemberTabAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new MemberAllFragment();
            case 1 : return new MemberChuNhiemFragment();
            case 2 : return new MemberKeHoachFragment();
            case 3 : return new MemberThucHienFragment();
            case 4 : return new MemberTruyenThongFragment();

        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "Tất cả";
            case 1:
                return "Chủ nhiệm";
            case 2 :
                return "Kế Hoạch";
            case 3 :
                return "Thực Hiện";
            case 4:
                return "Truyền Thông";

        }
        return null;
    }
}
