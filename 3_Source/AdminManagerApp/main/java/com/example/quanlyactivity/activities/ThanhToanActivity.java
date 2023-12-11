package com.example.quanlyactivity.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.quanlyactivity.R;
import com.example.quanlyactivity.model.NotiSendData;
import com.example.quanlyactivity.retrofit.ApiBanHang;
import com.example.quanlyactivity.retrofit.ApiPushNofication;
import com.example.quanlyactivity.retrofit.RetrofitClient;
import com.example.quanlyactivity.retrofit.RetrofitClientNoti;
import com.example.quanlyactivity.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    EditText edtAmount;
    Button btnPayment;

    String  clientid="";

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.thanhtoan);
    }
























    /*
    Toolbar toolbar;
    TextView txttongtien, txtsodt, txtemail;
    EditText edtdiachi;
    AppCompatButton btndathang, btnmomo;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long tongtien;
    int totalItem;

    //bai 49
    private String amount = "10000";
    private String fee = "0";
    int environment = 0;//developer default
    private String merchantName = "Thanh toán đơn hàng";
    private String merchantCode = "SCB01";
    private String merchantNameLable = "HoangNgoc";
    private String description = "mua hàng Online";


    @Override
    protected  void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        AppMoMoLib.getInstance().setEnvironment(AppMoMoLib.ENVIRONMENT.DEVELOPMENT);// Bai 49 // chua them iden..
        initView();
        initControl();

    }
    //code cua ba`i nao do
    private void countItem(){
        totalItem = 0;
        for(int i=0;i< Utils.mangmuahang.size(); i++){
            totalItem = totalItem + Utils.mangmuahang.get(i).getSoluong();
        }
    }
    //bai49
    //Get token through MoMo app
    private void requestPayment() {
        AppMoMoLib.getInstance().setAction(AppMoMoLib.ACTION.PAYMENT);
        AppMoMoLib.getInstance().setActionType(AppMoMoLib.ACTION_TYPE.GET_TOKEN);


        Map<String, Object> eventValue = new HashMap<>();
        //client Required
        eventValue.put("merchantname", merchantName); //Tên đối tác. được đăng ký tại https://business.momo.vn. VD: Google, Apple, Tiki , CGV Cinemas
        eventValue.put("merchantcode", merchantCode); //Mã đối tác, được cung cấp bởi MoMo tại https://business.momo.vn
        eventValue.put("amount", total_amount); //Kiểu integer
        eventValue.put("orderId", "orderId123456789"); //uniqueue id cho Bill order, giá trị duy nhất cho mỗi đơn hàng
        eventValue.put("orderLabel", "Mã đơn hàng"); //gán nhãn

        //client Optional - bill info
        eventValue.put("merchantnamelabel", "Dịch vụ");//gán nhãn
        eventValue.put("fee", total_fee); //Kiểu integer
        eventValue.put("description", description); //mô tả đơn hàng - short description

        //client extra data
        eventValue.put("requestId",  merchantCode+"merchant_billId_"+System.currentTimeMillis());
        eventValue.put("partnerCode", merchantCode);
        //Example extra data
        JSONObject objExtraData = new JSONObject();
        try {
            objExtraData.put("site_code", "008");
            objExtraData.put("site_name", "CGV Cresent Mall");
            objExtraData.put("screen_code", 0);
            objExtraData.put("screen_name", "Special");
            objExtraData.put("movie_name", "Kẻ Trộm Mặt Trăng 3");
            objExtraData.put("movie_format", "2D");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        eventValue.put("extraData", objExtraData.toString());

        eventValue.put("extra", "");
        AppMoMoLib.getInstance().requestMoMoCallBack(this, eventValue);


    }
    //Get token callback from MoMo app an submit to server side
    void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppMoMoLib.getInstance().REQUEST_CODE_MOMO && resultCode == -1) {
            if(data != null) {
                if(data.getIntExtra("status", -1) == 0) {
                    //TOKEN IS AVAILABLE
                    tvMessage.setText("message: " + "Get token " + data.getStringExtra("message"));
                    String token = data.getStringExtra("data"); //Token response
                    String phoneNumber = data.getStringExtra("phonenumber");
                    String env = data.getStringExtra("env");
                    if(env == null){
                        env = "app";
                    }

                    if(token != null && !token.equals("")) {
                        // TODO: send phoneNumber & token to your server side to process payment with MoMo server
                        // IF Momo topup success, continue to process your order
                    } else {
                        tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                    }
                } else if(data.getIntExtra("status", -1) == 1) {
                    //TOKEN FAIL
                    String message = data.getStringExtra("message") != null?data.getStringExtra("message"):"Thất bại";
                    tvMessage.setText("message: " + message);
                } else if(data.getIntExtra("status", -1) == 2) {
                    //TOKEN FAIL
                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                } else {
                    //TOKEN FAIL
                    tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
                }
            } else {
                tvMessage.setText("message: " + this.getString(R.string.not_receive_info));
            }
        } else {
            tvMessage.setText("message: " + this.getString(R.string.not_receive_info_err));
        }
    }
    private void initControl(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });
    }

    private void pushNotiToUser(){
        String token ="dfcUnnJ5QHqPqitVOz1lOr:APA91bHRlXn8eQTvoRco08e8j97qhuQmbrpxQzVSIeWXS16i7Kg_bvtuoWf3-qhozO7ftjAWcVR8cCnp4yGLzNc0FA0qdpjYFWR2I86taSLnOQxnPaeSCa4-nxSQ1fCFH3AFnMtDrLR-";
        Map<String, String> data = new HashMap<>();
        data.put("title","thong bao");
        data.put("body", "ban co don hang moi");
        NotiSendData notiSendData = new NotiSendData(token, data);
        ApiPushNofication apiPushNofication = RetrofitClientNoti.getInstance().create(ApiPushNofication.class);
        compositeDisposable.add(apiPushNofication.sendNofitication(notiSendData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        notiResponse -> {

                        },throwable -> {
                            Log.d("Logg", throwable.getMessage());
                        }
                ));
    }
    private void initView(){
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class); // them tu bai 49
        toolbar = findViewById(R.id.toolbar);
        txttongtien = findViewById(R.id.username)
        txtsodt=findViewById(R.id.txtsodienthoai);
        txtemail=findViewById(R.id.txtemail);
        edtdiachi = findViewById(R.id.edtdiachi);
        btnmomo = findViewById(R.id.btnmomo);// bai 49

    }
    // them tu ba`i 49, ko biet bai goc cua bai nao
    @Override
    protected  void onDestroy(){
        compositeDisposable.clear();
        super.onDestroy();
    }*/
}
