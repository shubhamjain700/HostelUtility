package com.appsquadz.hostelutility;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.transition.Fade;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;


public class Dashboard extends BaseActivity<IODashboardView, IODashboardPresenter> implements IODashboardView{

    private DashboardRecyclerAdapter adapter;

    @Override
    public void onDataLoaded(ArrayList<DashBoardBean> list) {
        adapter.update(list);
    }

    @Override
    public void onProfileImageLoaded() {

    }

    public RecyclerView getRecyclerView() {
        RecyclerView recyclerView  = (RecyclerView)getView(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        return recyclerView;
    }

    public DashboardRecyclerAdapter getAdapter() {
        return new DashboardRecyclerAdapter(this,R.layout.new_dashboard_cardui,new ArrayList<DashBoardBean>());
    }

    @Override
    protected IODashboardPresenter createPresenter() {
        return new ODashBoardModel();
    }

    @Override
    protected void initViewsAndEvents() {
        setupToolbar("");
        adapter = getAdapter();
        RecyclerView recyclerView = getRecyclerView();
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                startActivity(position);
            }
        });

        mPresenter.loadData();
        mPresenter.loadProfileImage();

        getView(R.id.iv_display_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent profileActivity = new Intent(mContext, ProfileActivity.class);
                Pair[] pairs = new Pair[2];
                pairs[0] = new Pair<>(getView(R.id.view), "bg_anim");
                pairs[1] = new Pair<>(getView(R.id.iv_display_image), "profile_anim");
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(OfficialDashboard.this, pairs);
                startActivity(profileActivity, optionsCompat.toBundle());
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.new_activity_dashboard;
    }

    private void startActivity(int position){

        switch (position){
            case 0:
                startActivity(new Intent(this, GeneralNotice.class));
                break;
            case 1:
                startActivity(new Intent(this, HostelNoticeActivity.class));
                break;
            case 2:
                startActivity(new Intent(this, SendNoticeActivity.class));
                break;
            case 3:
                startActivity(new Intent(this, StudentComplaintsActivity.class));
                break;
            case 4:
                startActivity(new Intent(this, StudentApplicatoinsActivity.class));
                break;
            case 5:
                startActivity(new Intent(this, StaffsAndFacultyActivity.class));
                break;


        }

    }

    private void openAboutSection() {
        startActivity(new Intent(this, AboutSection.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                openAboutSection();
                break;
            case R.id.logout:
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
