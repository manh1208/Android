package com.manhnv.fev.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.manhnv.fev.fevms.R;
import com.manhnv.fev.dao.MemberDAO;
import com.manhnv.fev.dto.Member;
import com.manhnv.fev.fragment.EventFragment;
import com.manhnv.fev.fragment.FundFragment;
import com.manhnv.fev.fragment.HomeFragment;
import com.manhnv.fev.fragment.MemberFragment;
import com.manhnv.fev.listener.ICallFragment;
import com.manhnv.fev.response.SyncResponse;
import com.manhnv.fev.services.RestService;
import com.manhnv.fev.util.DbUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,ICallFragment {
        private  Toolbar toolbar;
    private DbUtil dbUtil;
    private MemberDAO memberDao;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame,new HomeFragment())
                .commit();
        if (this.dbUtil == null) {
            this.dbUtil = new DbUtil(getApplicationContext());
        }
        if (this.memberDao == null) {
            this.memberDao = new MemberDAO(dbUtil);
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.sync) {
            SyncUser();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_homepage) {
            fragment = new HomeFragment();
            toolbar.setTitle("FPT Event Club");
        } else if (id == R.id.nav_member) {
          //  boolean result = Sync.getINSTANT().SyncUser(getApplicationContext());
            fragment = new MemberFragment();
            toolbar.setTitle("Thành viên");
        } else if (id == R.id.nav_fund) {
            fragment = new FundFragment();
            toolbar.setTitle("Tài chính");
        } else if (id == R.id.nav_event) {
            fragment = new EventFragment();
            toolbar.setTitle("Sự kiện");
        } else if (id == R.id.nav_facebook) {
            Uri uri;
            Intent intent;
            uri = Uri.parse("https://www.facebook.com/FPTEventClub");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            this.startActivity(intent);
        } else if (id == R.id.nav_about) {
            Uri uri;
            Intent intent;
            uri = Uri.parse("http://fpteventclub.xyz/");
            intent = new Intent(Intent.ACTION_VIEW, uri);
            this.startActivity(intent);
        }else if (id==R.id.nav_logout){
            SharedPreferences pre=getSharedPreferences("LoginStatus", MODE_PRIVATE);
            SharedPreferences.Editor edit=pre.edit();
            edit.clear();
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
        if (fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frame,fragment)
                    .commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void callFragment(Fragment fragment) {

    }

    @Override
    public void reloadFragment() {
        MenuItem item = navigationView.getMenu().findItem(R.id.nav_member);
        navigationView.setCheckedItem(R.id.nav_member);
        onNavigationItemSelected(item);
    }

    private void SyncUser(){

        if (dbUtil == null) {
            dbUtil = new DbUtil(getApplicationContext());
        }
        if (memberDao == null) {
            memberDao = new MemberDAO(dbUtil);
        }
        RestService restService = new RestService();
        List<Member> list = memberDao.getAll();
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

                    List<Member> members = syncResponse.getMembers();
                    memberDao.deleteAll();
                    boolean flag = true;
                    for (Member member : members
                            ) {
                        member.setChange(false);
                       if ( !memberDao.insert(member)){
                           flag=false;
                       }
                    }
                    if (flag){
                        reloadFragment();
                        Toast.makeText(MainActivity.this, syncResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this, "Lỗi save database local!!", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(MainActivity.this, syncResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences pre=getSharedPreferences("LoginStatus", MODE_PRIVATE);
        SharedPreferences.Editor edit=pre.edit();
        edit.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();

       // SyncUser();
    }
}
