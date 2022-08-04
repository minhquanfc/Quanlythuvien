package com.poly.bookstore.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.poly.bookstore.DAO.ThongKeDAO;
import com.poly.bookstore.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class DoanhThuFragment extends Fragment {

    Button btntungay ,btndenngay,btndoanhthu;
    TextView tvdoanhthu;
    EditText edtungay,eddenngay;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    int mYear,mMonth,mDay;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_doanh_thu, container, false);
        btntungay=view.findViewById(R.id.btnTuNgay);
        btndenngay=view.findViewById(R.id.btnDenNgay);
        btndoanhthu=view.findViewById(R.id.btnDoanhThu);
        tvdoanhthu=view.findViewById(R.id.tvDoanhThu);
        edtungay=view.findViewById(R.id.edTuNgay);
        eddenngay=view.findViewById(R.id.edDenNgay);

        DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth=month;
                mDay=dayOfMonth;
                GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
                edtungay.setText(sdf.format(c.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth=month;
                mDay=dayOfMonth;
                GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
                eddenngay.setText(sdf.format(c.getTime()));
            }
        };
        btntungay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear =calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,mDateTuNgay,mYear,mMonth,mDay);
                d.show();
            }
        });
        btndenngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear =calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,mDateDenNgay,mYear,mMonth,mDay);
                d.show();
            }
        });
        btndoanhthu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tungay = edtungay.getText().toString();
                String denngay = eddenngay.getText().toString();
                ThongKeDAO thongKeDAO = new ThongKeDAO(getContext());
                tvdoanhthu.setText("Doanh thu: "+thongKeDAO.getDoanhThu(tungay,denngay) +" VND");
            }
        });
        return view;
    }
}