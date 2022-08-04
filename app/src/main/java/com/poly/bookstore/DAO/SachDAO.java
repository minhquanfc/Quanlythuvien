package com.poly.bookstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.bookstore.database.DbHelper;
import com.poly.bookstore.model.Sach;
import com.poly.bookstore.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class SachDAO {
    private SQLiteDatabase database;

    public SachDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public long insertSach(Sach sach){
        ContentValues values =new ContentValues();
        values.put("tenSach",sach.getTenSach());
        values.put("giaThue",sach.getGiaThue());
        values.put("soTrang",sach.getSoTrang());
        values.put("maLoai",sach.getMaLoai());
        long result = database.insert("Sach",null,values);
        return result;
    }
    public long updateSach(Sach sach){
        ContentValues values =new ContentValues();
        values.put("tenSach",sach.getTenSach());
        values.put("giaThue",sach.getGiaThue());
        values.put("soTrang",sach.getSoTrang());
        values.put("maLoai",sach.getMaLoai());
        long result = database.update("Sach",values,"maSach=?",new String[]{String.valueOf(sach.getMaSach())});
        return result;
    }
    public long deleteSach(String id){
        long result = database.delete("Sach","maSach=?",new String[]{id});
        return result;
    }
    public List<Sach> getData(String sql ,String...selectionArgs){
        List<Sach> sachList = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql, selectionArgs);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                int maSach =cursor.getInt(0);
                String tenSach =cursor.getString(1);
                int giaThue = cursor.getInt(2);
                int soTrang = cursor.getInt(3);
                int maLoai = cursor.getInt(4);
                Sach sach = new Sach(maSach,tenSach, giaThue,soTrang, maLoai);
                sachList.add(sach);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return sachList;
    }
    //get all
    public List<Sach> danhsachSach(){
        String bang = "select *from Sach";
        return getData(bang);
    }

    //get theo id
    public Sach getId(String id){
        String sql = "select * from Sach where maSach=?";
        List<Sach> list = getData(sql,id);
        return list.get(0);
    }
}
