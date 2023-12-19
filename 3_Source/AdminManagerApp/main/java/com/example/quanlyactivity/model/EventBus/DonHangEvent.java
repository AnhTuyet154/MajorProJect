package com.example.quanlyactivity.model.EventBus;

import com.example.quanlyactivity.model.DonHang;

//import com.example.quanlyactivity.model.DonHang;
//bai46
public class DonHangEvent {
    DonHang donHang;
    public  DonHangEvent(DonHang donHang){
        this.donHang = donHang;
    }
    public  DonHang getDonHang(){
        return donHang;
    }

    public void setDonHang(DonHang donHang){
        this.donHang = donHang;
    }
}
