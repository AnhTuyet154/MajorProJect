package com.example.quanlyactivity.utils;

import com.example.quanlyactivity.model.GioHang;
import com.example.quanlyactivity.model.User;

import java.util.List;

public class Utils {
    public static final String BASE_URL = "http://192.168.1.18/banhang/";
    public static List<GioHang> manggiohang;
    public static User user_current = new User();

    public static String ID_RECEIVED;
    public static final String SENDID = "idsend";
    public static final String RECEIVEDID = "idreceived";
    public static final String MESS = "message";
    public static final String DATETIME = "datetime";
    public static final String PATH_CHAT = "chat";
    public static String statusOrder(int status){
        String result="";
        switch (status){
            case 0:
                result = "Đơn hàng đang được xử lí";
                break;
            case 1:
                result = "Được tiếp nhận";
                break;
            case 2:
                result = "Được giao cho vận chuyển";
                break;
            case 3:
                result = "Giao thành công";
                break;
            case 4:
                result = "Hủy";
                break;
            default:
                result="...";
        }
        return result;
    }
}
