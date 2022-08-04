package com.poly.bookstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.bookstore.database.DbHelper;
import com.poly.bookstore.model.ThuThu;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDAO {
    private SQLiteDatabase database;

    public ThuThuDAO(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public long insert(ThuThu thuThu) {
        ContentValues values = new ContentValues();
        values.put("maTT", thuThu.getMaTT());
        values.put("hoTen", thuThu.getHoTen());
        values.put("matKhau", thuThu.getMatKhau());
        return database.insert("ThuThu", null, values);
    }

    public long update(ThuThu obj) {
        ContentValues values = new ContentValues();
        values.put("hoTen", obj.hoTen);
        values.put("matKhau", obj.matKhau);
        return database.update("ThuThu", values, "maTT=?", new String[]{obj.getMaTT()});
    }

    public long delete(String id) {
        return database.delete("ThuThu", "maTT=?", new String[]{id});
    }
    public List<ThuThu> getData(String sql ,String...selectionArgs){
        List<ThuThu> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sql,selectionArgs);
        while (cursor.moveToNext()){
            ThuThu thuThu = new ThuThu();
            thuThu.setMaTT(cursor.getString(cursor.getColumnIndex("maTT")));
            thuThu.setHoTen(cursor.getString(cursor.getColumnIndex("hoTen")));
            thuThu.setMatKhau(cursor.getString(cursor.getColumnIndex("matKhau")));
            list.add(thuThu);
        }
        return list;
    }

    //get theo data
    public List<ThuThu> getAll(){
        String data="select * from ThuThu";
        return getData(data);
    }
    //get theo id
    public ThuThu getId(String id){
        String sql = "select * from ThuThu where maTT=?";
        List<ThuThu> list = getData(sql,id);
        return list.get(0);
    }
    //check login
    public int checkLogin(String id,String pass){
        String sql = "select * from ThuThu where maTT=? and matKhau=?";
        List<ThuThu> list = getData(sql,id,pass);
        if (list.size()==0){
            return -1;
        }else {
            return 1;
        }
    }
}
