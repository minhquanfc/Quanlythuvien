package com.poly.bookstore.DAO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.bookstore.database.DbHelper;
import com.poly.bookstore.model.Sach;
import com.poly.bookstore.model.Top;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {
    private SQLiteDatabase database;
    private Context context;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public ThongKeDAO( Context context) {
        this.context = context;
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    //thong ke top 10
    public List<Top> getTop(){
        List<Top> list = new ArrayList<>();
        String sqlTop ="select maSach, count(maSach) as soLuong from PhieuMuon group by maSach order by soLuong desc limit 10";
        SachDAO  sachDAO = new SachDAO(context);
        Cursor cursor = database.rawQuery(sqlTop,null);
        while (cursor.moveToNext()){
            Top top = new Top();
            Sach sach = sachDAO.danhsachSach().get(cursor.getColumnIndex("maSach"));
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
            list.add(top);
        }
        return list;
    }
    public int getDoanhThu(String tuNgay,String denNgay){
        String sqlDoanhthu ="select Sum(tienThue) as doanhThu From PhieuMuon where ngay between ? and ?";
        List<Integer> list = new ArrayList<>();
        Cursor cursor = database.rawQuery(sqlDoanhthu,new String[]{tuNgay,denNgay});
        while (cursor.moveToNext()){
            try {
                list.add(Integer.parseInt(cursor.getString(cursor.getColumnIndex("doanhThu"))));
            }catch (Exception e){
                list.add(0);
            }
        }
        return list.get(0);
    }
}
