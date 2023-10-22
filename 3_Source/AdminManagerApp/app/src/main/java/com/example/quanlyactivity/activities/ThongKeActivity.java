package com.example.quanlyactivity.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quanlyactivity.R;
import com.example.quanlyactivity.retrofit.ApiBanHang;
import com.example.quanlyactivity.retrofit.RetrofitClient;
import com.example.quanlyactivity.utils.Utils;
import com.github.mikephil.charting.charts.PieChart;
import com.google.protobuf.Api;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ThongKeActivity extends AppCompatActivity {
    Toolbar toolbar;
    PieChart pieChart;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initview();
        ActionToolBar();
    }

    private void initview()
    {
        toolbar = findViewById(R.id.toolbar);
        pieChart = findViewById(R.id.piechart);

    }
    private void ActionToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){ finish();}
        });

    }
    @Override
    protected  void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }
}
