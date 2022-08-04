package com.poly.bookstore.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.poly.bookstore.database.DbHelper;
import com.poly.bookstore.model.PhieuMuon;
import com.poly.bookstore.model.Sach;
import com.poly.bookstore.model.ThanhVien;
import com.poly.bookstore.model.Top;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.SimpleFormatter;

public class PhieuMuonDAO {
    private SQLiteDatabase database;
    private Context context;
    SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");

    public PhieuMuonDAO(Context context) {
        this.context =context;
        DbHelper dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    public long insertPm(PhieuMuon phieuMuon){
        ContentValues values =new ContentValues();
//        values.put("maPM",phieuMuon.getMaPM());
//        values.put("maTT",phieuMuon.getMaTT());
        values.put("maTV",phieuMuon.getMaTV());
        values.put("tenPM",phieuMuon.getTenPM());
        values.put("maSach",phieuMuon.getMaSach());
        values.put("ngay",sdf.format(phieuMuon.getNgay()));
        values.put("traSach",phieuMuon.getTraSach());
        values.put("tienThue",phieuMuon.getTienThue());
        values.put("note",phieuMuon.getNote());
        long result = database.insert("PhieuMuon",null,values);
        return result;
    }
    public long updatePm(PhieuMuon phieuMuon){
        ContentValues values =new ContentValues();
//        values.put("maPM",phieuMuon.getMaPM());
//        values.put("maTT",phieuMuon.getMaTT());
        values.put("maTV",phieuMuon.getMaTV());
        values.put("maSach",phieuMuon.getMaSach());
        values.put("ngay",sdf.format(phieuMuon.getNgay()));
        values.put("traSach",phieuMuon.getTraSach());
        values.put("tienThue",phieuMuon.getTienThue());
        values.put("note",phieuMuon.getNote());
        long result = database.update("PhieuMuon",values,"maPM=?",new String[]{String.valueOf(phieuMuon.getMaPM())});
        return result;
    }
    public long deletePm(String id){
        long result = database.delete("PhieuMuon","maPM=?",new String[]{id});
        return result;
    }
    public List<PhieuMuon> danhsachPhieuMuon(){
        List<PhieuMuon> phieuMuons = new ArrayList<>();
        String bang = "select *from phieumuon";
        Cursor cursor = database.rawQuery(bang, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                PhieuMuon phieuMuon = new PhieuMuon();
                phieuMuon.setMaPM(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maPM"))));
                phieuMuon.setTenPM(cursor.getString(cursor.getColumnIndex("tenPM")));
                phieuMuon.setMaTT(cursor.getString(cursor.getColumnIndex("maTT")));
                phieuMuon.setMaTV(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maTV"))));
                phieuMuon.setMaSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("maSach"))));
                phieuMuon.setTienThue(Integer.parseInt(cursor.getString(cursor.getColumnIndex("tienThue"))));
                phieuMuon.setNote(cursor.getString(cursor.getColumnIndex("note")));
                try {
                    phieuMuon.setNgay(sdf.parse(cursor.getString(cursor.getColumnIndex("ngay"))));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                phieuMuon.setTraSach(Integer.parseInt(cursor.getString(cursor.getColumnIndex("traSach"))));
                phieuMuons.add(phieuMuon);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return phieuMuons;
    }
    //top doanh thu
    public List<Top> getTop(){
        List<Top> list = new ArrayList<>();
        String sqlTop ="select maSach, count(maSach) as soLuong from PhieuMuon group by maSach order by soLuong desc limit 10";
        SachDAO  sachDAO = new SachDAO(context);
        Cursor cursor = database.rawQuery(sqlTop,null);
        while (cursor.moveToNext()){
            Top top = new Top();
            Sach sach = sachDAO.getId(cursor.getString(cursor.getColumnIndex("maSach")));
            top.setTenSach(sach.getTenSach());
            top.setSoLuong(Integer.parseInt(cursor.getString(cursor.getColumnIndex("soLuong"))));
            list.add(top);
        }
        return list;
    }
}
