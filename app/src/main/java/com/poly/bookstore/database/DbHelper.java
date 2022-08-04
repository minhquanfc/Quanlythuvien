package com.poly.bookstore.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(@Nullable Context context) {
        super(context, "thuvien.db",null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //tao bang thu thu
        String createTableThuThu = "create table ThuThu(maTT text primary key ,hoTen text not null ,matKhau text not null)";
        db.execSQL(createTableThuThu);
        //tao bang thanh vien
        String createTableThanhVien="create table ThanhVien(maTV integer primary key autoincrement ,hoTen text not null,namSinh text not null)";
        db.execSQL(createTableThanhVien);
//        //tao bang loai sach
        String createTableLoaiSach="create table LoaiSach(maLoai integer primary key autoincrement ,tenLoai text not null)";
        db.execSQL(createTableLoaiSach);
        //tao bang sach
        String createTableSach = "create table Sach(maSach integer primary key autoincrement ,tenSach text not null ,giaThue integer not null,soTrang integer, maLoai integer references LoaiSach(maLoai))";
        db.execSQL(createTableSach);
        //tao bang phieu muon
        String createTablePhieuMuon ="create table PhieuMuon(maPM integer primary key autoincrement, maTT text references ThuThu(maTT), maTV integer references ThanhVien(maTV), maSach integer references Sach(maSach), tienThue integer not null, ngay date not null, traSach integer not null,tenPM text,note text)";
        db.execSQL(createTablePhieuMuon);

        db.execSQL(Data.INSERT_THU_THU);
//        db.execSQL(DataSQL.INSERT_THU_THU);
//        db.execSQL(DataSQL.INSERT_ThanhVien);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableThuThu = "drop table if exists ThuThu";
        db.execSQL(dropTableThuThu);
        String dropTableThanhVien = "drop table if exists ThanhVien";
        db.execSQL(dropTableThanhVien);
        String dropTableLoaiSach = "drop table if exists LoaiSach";
        db.execSQL(dropTableLoaiSach);
        String dropTableSach = "drop table if exists Sach";
        db.execSQL(dropTableSach);
        String dropTablePhieuMuon = "drop table if exists PhieuMuon";
        db.execSQL(dropTablePhieuMuon);
    }
}
