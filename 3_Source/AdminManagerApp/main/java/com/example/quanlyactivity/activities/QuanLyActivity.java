package com.example.quanlyactivity.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.quanlyactivity.R;
import com.example.quanlyactivity.interfaces.adapter.SanPhamMoiAdapter;
import com.example.quanlyactivity.model.EventBus.SuaXoaEvent;
import com.example.quanlyactivity.model.SanPhamMoi;
import com.example.quanlyactivity.model.User;
import com.example.quanlyactivity.retrofit.ApiBanHang;
import com.example.quanlyactivity.retrofit.RetrofitClient;
import com.example.quanlyactivity.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.messaging.FirebaseMessaging;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class QuanLyActivity extends AppCompatActivity {
    ImageView img_them;
    RecyclerView recyclerView;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    List<SanPhamMoi> list;
    SanPhamMoiAdapter adapter;
    SanPhamMoi sanPhamSuaXoa;
    ImageView imageMess;
    //bai21
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly);
        //de o main
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

        initView();
        initControl();
        getSpMoi();
        //De o main
        Paper.init(this);
        if (Paper.book().read("user")!=null){
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }
        getToken();
        if(isConnected(this)){
            Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(getApplicationContext(), "khong co Internet", Toast.LENGTH_LONG).show();

    }

    private void initControl() {
        img_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Add_Product_Activity.class);
                startActivity(intent);
            }
        });
        imageMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThongKeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getSpMoi(){
        compositeDisposable.add(apiBanHang.getSpMoi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamMoiModel -> {
                            if (sanPhamMoiModel.isSuccess()) {
                                list = sanPhamMoiModel.getResult();
                                adapter = new SanPhamMoiAdapter(getApplicationContext(), list);
                                recyclerView.setAdapter(adapter);
                            }
                        },
                        throwable -> {
                            Log.e("Lấy sản phẩm", "Không kết nối được với server: " + throwable.getMessage()); // Log lỗi không xác định
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                ));

    }


    private void initView(){
        img_them = findViewById(R.id.img_them);
        imageMess = findViewById(R.id.image_mess);
        recyclerView = findViewById(R.id.recycleview_ql);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Sửa")){
            suaSanPham();
        }else if (item.getTitle().equals("Xóa")){
            xoaSanPham();
        }
        
        return super.onContextItemSelected(item);
    }

    private void xoaSanPham() {
        compositeDisposable.add(apiBanHang.xoaSanPham(sanPhamSuaXoa.getId())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(
                messageModel -> {
                    if (messageModel.isSuccess()){
                        Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                        getSpMoi();
                    }else {
                        Toast.makeText(getApplicationContext(), messageModel.getMessage(), Toast.LENGTH_LONG).show();
                    }
                },
                throwable -> {
                    Log.d("Log", throwable.getMessage());
                }
                ));
    }

    private void suaSanPham() {
        Intent intent = new Intent(getApplicationContext(), Add_Product_Activity.class);
        intent.putExtra("sua", sanPhamSuaXoa);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventSuaXoa(SuaXoaEvent event){
        if(event != null){
            sanPhamSuaXoa = event.getSanPhamMoi();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
//Main

    private void Anhxa(){
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper=findViewById(R.id.viewlipper);
        recyclerViewManHinhChinh = findViewById(R.id.recycleview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        RecyclerViewManHinhChinh.setLayoutManager(layoutManager);
        recyclerViewManHinhChinh = findViewById(R.id.listviewmanhinhchinh);
        navigationView = findViewById(R.id.navigationview);
        drawerLayout = findViewById(R.drawerlayout);
        badge = findViewById(R.id.menu_sl);
        frameLayout = findViewById(R.id.framegiohang);
        //khoitaolist
        mangloaisp = new ArrayList<>();
        mangSpMoi = new ArrayList<>();
        if(Utils.manggiohang == null){
            Utils.manggiohang = new ArrayList<>();
        }else {
            int totalItem = 0;
            for(int i = 0;i<Utils.manggiohang.size();i++){
                totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
frameLayout.setOnClickListener(new View.OnClickListener()
{
    @Override
    public void onClick(View view){
        Intent giohang = new Intent(getApplicationContext(), GioHangActivity.class);
        startActivity(giohang);
    }

});
    }
    @Override
    protected void onResume(){
        super.onResume();
        int totalItem = 0;
        for(int i = 0;i<Utils.manggiohang.size();i++){
            totalItem = totalItem + Utils.manggiohang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));
    }
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI));
            } else {
                android.net.NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                return activeNetworkInfo != null && activeNetworkInfo.isConnected();
            }
        }
        return false;
    }
    private void getToken(){
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if(!TextUtils.isEmpty(s)){
                            compositeDisposable.add(apiBanHang.updateToken(Utils.user_current.getId(),s)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            messageModel -> {

                                            },
                                            throwable -> {
                                                Log.d("Log", throwable.getMessage());
                                            }
                                    ));
                        }
                    }
                });
        compositeDisposable.add(apiBanHang.getToken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if(userModel.isSuccess()){
                                Utils.ID_RECEIVED = String.valueOf(userModel.getResult().get(0).getId());                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                ));
    }
}

