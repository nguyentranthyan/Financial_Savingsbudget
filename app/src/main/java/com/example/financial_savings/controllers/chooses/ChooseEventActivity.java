package com.example.financial_savings.controllers.chooses;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.financial_savings.R;
import com.example.financial_savings.entities.SuKien;
import com.example.financial_savings.helper.DBHelper;
import com.example.financial_savings.interfaces.IMappingView;
import com.example.financial_savings.modules.displays.EventDisplayModule;
import com.example.financial_savings.modules.events.EventApplyModule;
import com.example.financial_savings.modules.refreshs.SwipeRefreshModule;

import java.util.ArrayList;

public class ChooseEventActivity extends AppCompatActivity implements IMappingView {
    private ListView listView;
    private ImageButton buttonReturn;
    private DBHelper dbHelper;
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_home_choose);
        activity = this;
        init();
        loadData();
        eventReturn();
        eventRefresh();
    }

    private void eventRefresh() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        };
        SwipeRefreshModule.eventRefresh(swipeRefresh, runnable);
    }

    private void eventReturn() {
        buttonReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void loadData() {
        ArrayList<SuKien> list = EventApplyModule.getEventApply(dbHelper);
        EventDisplayModule.showListViewChoose_Event(list, getApplicationContext(), listView, dbHelper);
    }

    @Override
    public void init() {
        listView = findViewById(R.id.listView_event_home_choose);
        buttonReturn = findViewById(R.id.buttonReturn_event_home_choose);
        swipeRefresh = findViewById(R.id.swipeRefresh_event_home_choose);
        dbHelper = new DBHelper(this);
        getSupportActionBar().hide();
    }
}
