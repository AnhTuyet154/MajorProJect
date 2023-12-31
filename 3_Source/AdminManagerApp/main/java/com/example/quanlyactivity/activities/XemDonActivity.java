package com.example.quanlyactivity.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyactivity.R;
import com.example.quanlyactivity.interfaces.ItemClickDeleteListener;
import com.example.quanlyactivity.interfaces.adapter.DonHangAdapter;
import com.example.quanlyactivity.model.DonHang;
import com.example.quanlyactivity.model.EventBus.DonHangEvent;
import com.example.quanlyactivity.model.NotiSendData;
import com.example.quanlyactivity.retrofit.ApiBanHang;
import com.example.quanlyactivity.retrofit.ApiPushNofication;
import com.example.quanlyactivity.retrofit.RetrofitClient;
import com.example.quanlyactivity.retrofit.RetrofitClientNoti;
import com.example.quanlyactivity.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
//bai 46 full

public class XemDonActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView redonhang;
    Toolbar toolbar;
    DonHang donHang;
    int tinhtrang;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xem_don);
        initView();
        initToolbar();
        getOrder();
        

    }

    private void getOrder() {
        compositeDisposable.add(apiBanHang.xemDonHang(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        donHangModel -> {
                            DonHangAdapter adapter = new DonHangAdapter(getApplicationContext(), donHangModel.getResult(), new ItemClickDeleteListener() {
                                @Override
                                public void onClickDelete(int iddonhang) {
                                    showDeleteOrder(iddonhang);
                                }
                            });
                            redonhang.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        },
                        throwable -> {

                        }
                ));
    }

    private void showDeleteOrder(int iddonhang) {
        PopupMenu popupMenu = new PopupMenu(this,redonhang.findViewById(R.id.trangthaidon));
        popupMenu.inflate(R.menu.menu_delete);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.deleteOrder)
                    deleteOrder(iddonhang);
                return false;
            }
        });
        popupMenu.show();
    }

    private void deleteOrder(int iddonhang) {
        compositeDisposable.add(apiBanHang.deleteOrder(iddonhang)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess()){
                                getOrder();
                            }
                        },throwable -> {
                            Log.d("Loggg", throwable.getMessage());
                        }
                ));
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){ finish();}
        });
    }

    private void initView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        redonhang = findViewById(R.id.recycleview_donhang);
        toolbar = findViewById(R.id.toolbar);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        redonhang.setLayoutManager(layoutManager);
    }
    @Override
    protected void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }

    //bai46
    private void showCustumDialog(){
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_donhang,null);
        Spinner spinner = view.findViewById(R.id.spinner_dialog);
        AppCompatButton btndongy = view.findViewById(R.id.dongy_dialog);
        List<String> list = new ArrayList<>();
        list.add("Đơn hàng đang được xử lí");
        list.add("Đơn hàng đã chấp nhận");
        list.add("Đơn hàng đã giao cho");
        list.add("Thành công");
        list.add("Đơn hàng đã hủy");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        spinner.setSelection(donHang.getTrangthai());
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                tinhtrang = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btndongy.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                capNhapDonHang();
            }
        });
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }
    private void capNhapDonHang(){
        compositeDisposable.add(apiBanHang.updateOrder(donHang.getId(),tinhtrang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            getOrder();
                            dialog.dismiss();
                        },
                        throwable -> {

                        }
                ));
    }
//bai47
    private void pushNotiToUser(){
        //gettoken
        compositeDisposable.add(apiBanHang.gettoken(0,donHang.getIduser())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                for(int i=0;i<userModel.getResult().size();i++){
                                    Map<String, String > data = new HashMap<>();
                                    data.put("title", "thong bao");
                                    data.put("body",trangThaiDon(tinhtrang));
                                    NotiSendData notiSendData = new NotiSendData(userModel.getResult().get(i).getToken(), data);
                                    ApiPushNofication apiPushNofication = RetrofitClientNoti.getInstance().create(ApiPushNofication.class);
                                    compositeDisposable.add(apiPushNofication.sendNofitication(notiSendData)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(
                                                    notiResponse -> {

                                                    },
                                                    throwable -> {
                                                        Log.d("logg",throwable.getMessage());
                                                    }
                                            ));
                                }
                            }
                        }
                ));
    }
    //bai47
    private String trangThaiDon(int status){
        String result = "";
        switch (status){
            case 0:
                result = "Đơn hàng đang được xử lí";
                break;
            case 1:
                result = "Đơn hàng đã chấp nhận";
                break;
            case 2:
                result = "Đơn hàng đã giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Thành công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
        }
        return result;
    }
    //bai46
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void evenDonHang(DonHangEvent event){
        if(event!=null){
            donHang = event.getDonHang();
            showCustumDialog();
        }
    }
    //bai46
    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }
    @Override
    protected void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
