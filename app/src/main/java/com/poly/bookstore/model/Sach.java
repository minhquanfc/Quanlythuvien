package com.poly.bookstore.model;

public class Sach {
    public int maSach;
    public String tenSach;
    public int giaThue;
    public int soTrang;
    public int maLoai;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, int giaThue, int soTrang, int maLoai) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.soTrang = soTrang;
        this.maLoai = maLoai;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getSoTrang() {
        return soTrang;
    }

    public void setSoTrang(int soTrang) {
        this.soTrang = soTrang;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    @Override
    public String toString() {
        return maSach+" : "+tenSach;
    }
}
