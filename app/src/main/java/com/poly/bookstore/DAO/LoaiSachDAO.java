package com.poly.bookstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.bookstore.database.DbHelper;
import com.poly.bookstore.model.LoaiSach;
import com.poly.bookstore.model.Sach;
import com.poly.bookstore.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class LoaiSachDAO {
    private SQLiteDatabase database;

    public LoaiSachDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public long insertLoaiSach(LoaiSach loaiSach){
        ContentValues values =new ContentValues();
//        values.put("maLoai", loaiSach.getMaLoai());
        values.put("tenLoai", loaiSach.getTenLoai());
        long result = database.insert("LoaiSach",null,values);
        return result;
    }
    public long updateLoaiSach(LoaiSach loaiSach){
        ContentValues values =new ContentValues();
        values.put("tenLoai", loaiSach.getTenLoai());
        long result = database.update("LoaiSach",values,"maLoai=?",new String[]{String.valueOf(loaiSach.getMaLoai())});
        return result;
    }
    public long deleteLoaiSach(String id){
        long result = database.delete("LoaiSach","maLoai=?",new String[]{id});
        return result;
    }
    public List<LoaiSach> getData(String sql ,String...selectionArgs){
        List<LoaiSach> sachArrayList = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int maLoai =cursor.getInt(0);
                String hoTen =cursor.getString(1);
                LoaiSach sach = new LoaiSach(maLoai,hoTen);
                sachArrayList.add(sach);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return sachArrayList;
    }
    //get theo data
    public List<LoaiSach> danhsachLoaiSach(){
        String bang = "select * from LoaiSach";
        return getData(bang);
    }
    public List<LoaiSach> getTenLoai(String tenLoai){
        List<LoaiSach> sachArrayList = new ArrayList<>();
        String sql = "select * from LoaiSach where tenLoai=?";
        sachArrayList = getData(sql,tenLoai);
        return sachArrayList;
    }

    public LoaiSach getId(String id){
        String sql = "select * from LoaiSach where maLoai=?";
        List<LoaiSach> list = getData(sql,id);
        return list.get(0);
    }
}
