package com.example.quanlyactivity.model;

import java.io.Serializable;

public class SanPhamMoi implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getLoai() {
        return loai;
    }

    public void setLoai(int loai) {
        this.loai = loai;
    }

    int id;
    String tensp;
    String giasp;
    String hinhanh;
    String mota;
    int loai;
    int slsp;

    public int getSlsp() {
        return slsp;
    }

    public void setSlsp(int slsp) {
        this.slsp = slsp;
    }
}
