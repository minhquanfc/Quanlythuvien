package com.poly.bookstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.bookstore.database.DbHelper;
import com.poly.bookstore.model.ThanhVien;

import java.util.ArrayList;
import java.util.List;

public class ThanhVienDAO {
    DbHelper dbHelper;

    public ThanhVienDAO(Context context) {
        dbHelper = new DbHelper(context);

    }
    public long insertThanhvien(ThanhVien thanhVien){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("hoTen",thanhVien.getHoTen());
        values.put("namSinh",thanhVien.getNamSinh());
        long result = database.insert("ThanhVien",null,values);
        return result;
    }
    public long updateThanhvien(ThanhVien thanhVien){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues values =new ContentValues();
        values.put("hoTen",thanhVien.getHoTen());
        values.put("namSinh",thanhVien.getNamSinh());
        long result = database.update("ThanhVien",values,"MaTV=?",new String[]{String.valueOf(thanhVien.getMaTV())});
        return result;
    }
    public long deleteThanhvien(String id){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long result = database.delete("ThanhVien","MaTV=?",new String[]{id});
        return result;
    }
    public List<ThanhVien> getAllDK(String sql,String... a){
        List<ThanhVien> thanhVienArrayList = new ArrayList<>();
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql,a);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int MaTV =cursor.getInt(0);
                String hoTen =cursor.getString(1);
                String namSinh =cursor.getString(2);
                ThanhVien thanhVien = new ThanhVien(MaTV,hoTen, namSinh);
                thanhVienArrayList.add(thanhVien);
                cursor.moveToNext();
            }
            cursor.close();
            database.close();
        }
        return thanhVienArrayList;
    }
    //get theo data
    public List<ThanhVien> getAll(){
        String data="select * from ThanhVien";
        return getAllDK(data);
    }
    //get theo id
    public ThanhVien getId(String id){
        String sql = "select * from ThanhVien where maTV=?";
        List<ThanhVien> list = getAllDK(sql,id);
        return list.get(0);
    }
}
