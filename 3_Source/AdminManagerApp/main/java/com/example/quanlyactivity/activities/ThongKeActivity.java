package com.example.quanlyactivity.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quanlyactivity.R;
import com.example.quanlyactivity.retrofit.ApiBanHang;
import com.example.quanlyactivity.retrofit.RetrofitClient;
import com.example.quanlyactivity.utils.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.protobuf.Api;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThongKeActivity extends AppCompatActivity {
    Toolbar toolbar;
    PieChart pieChart;
    BarChart barChart;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        initview();
        ActionToolBar();
        getdataChart();
        settingBarchart();
    }

    private void settingBarchart() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);
        YAxis yAxisright = barChart.getAxisRight();
        yAxisright.setAxisMinimum(0);
        YAxis yAxisleft = barChart.getAxisLeft();
        yAxisleft.setAxisMinimum(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_thongke, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.tkthang) {
            getTKThang();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void getTKThang() {
        barChart.setVisibility(View.VISIBLE);
        pieChart.setVisibility(View.GONE);
        compositeDisposable.add(apiBanHang.getthongkeThang()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModel -> {
                            if (thongKeModel.isSuccess()){
                                List<BarEntry> listdata = new ArrayList<>();
                                for (int i =0; i<thongKeModel.getResult().size();i++){
                                    String tongtien = thongKeModel.getResult().get(i).getTongtienthang();
                                    String thang = thongKeModel.getResult().get(i).getThang();
                                    listdata.add(new BarEntry(Integer.parseInt(thang), Float.parseFloat(tongtien)));
                                }
                                BarDataSet barDataSet = new BarDataSet(listdata, "Thông kê");
                                barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                                barDataSet.setValueTextSize(14f);
                                barDataSet.setValueTextColor(Color.RED);

                                BarData data = new BarData(barDataSet);
                                barChart.animateXY(2000,2000);
                                barChart.setData(data);
                                barChart.invalidate();

                            }
                        },throwable -> {
                            Log.d("Log", throwable.getMessage());
                        }
                ));
    }

    private void getdataChart() {
        List<PieEntry> listdata =  new ArrayList<>();
        compositeDisposable.add(apiBanHang.getthongke()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        thongKeModel -> {
                            if (thongKeModel.isSuccess()){
                                for (int i=0;i<thongKeModel.getResult().size();i++){
                                    String tensp = thongKeModel.getResult().get(i).getTensp();
                                    int tong = thongKeModel.getResult().get(i).getTong();
                                    listdata.add(new PieEntry(tong, tensp));
                                }
                                PieDataSet pieDataSet = new PieDataSet(listdata, "Thống kê");
                                PieData data = new PieData();
                                data.setDataSet(pieDataSet);
                                data.setValueTextSize(12f);
                                data.setValueFormatter(new PercentFormatter(pieChart));
                                pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

                                pieChart.setData(data);
                                pieChart.animateXY(2000,2000);
                                pieChart.setUsePercentValues(true);
                                pieChart.getDescription().setEnabled(false);
                                pieChart.invalidate();
                            }
                        },throwable -> {
                            Log.d("Log", throwable.getMessage());
                        }
                ));
    }

    private void initview()
    {
        toolbar = findViewById(R.id.toolbar);
        pieChart = findViewById(R.id.piechart);
        barChart = findViewById(R.id.barChart);

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
