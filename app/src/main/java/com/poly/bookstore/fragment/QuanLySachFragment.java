package com.poly.bookstore.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.poly.bookstore.Adapter.SachAdapter;
import com.poly.bookstore.DAO.LoaiSachDAO;
import com.poly.bookstore.DAO.SachDAO;
import com.poly.bookstore.MainActivity;
import com.poly.bookstore.R;
import com.poly.bookstore.model.LoaiSach;
import com.poly.bookstore.model.OnSearchListener;
import com.poly.bookstore.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class QuanLySachFragment extends Fragment implements OnSearchListener {

    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    List<Sach> sachList;
    SachDAO dao;
    SachAdapter adapter;
    List<LoaiSach> loaiSachList;
    int position, maloaisach;

    EditText ed_maSach;
    EditText ed_tenSach;
    EditText ed_giaThue;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quan_ly_sach, container, false);
        recyclerView = view.findViewById(R.id.rc_quanlysach);
        actionButton = view.findViewById(R.id.fabqlsach);

        sachList = new ArrayList<>();
        dao = new SachDAO(getActivity());
        sachList = dao.danhsachSach();

        adapter = new SachAdapter(getActivity(), sachList);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.quan_ly_sach_dialog, null);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();

                //anh xa
//                ed_maSach = view1.findViewById(R.id.ed_maSach);
                ed_tenSach = view1.findViewById(R.id.ed_tenSach);
                ed_giaThue = view1.findViewById(R.id.ed_giaThue);

                EditText ed_soTrang = view1.findViewById(R.id.ed_soTrang);

                Spinner spinner = view1.findViewById(R.id.spinner);
                Button btnthemsach = view1.findViewById(R.id.btnthemsach);
                Button btnhuysach = view1.findViewById(R.id.btnhuySach);

                loaiSachList = new ArrayList<>();
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getActivity());
                loaiSachList = loaiSachDAO.danhsachLoaiSach();
                ArrayAdapter arrayAdapter = new ArrayAdapter<LoaiSach>(getActivity(), android.R.layout.simple_list_item_1, loaiSachList);
                spinner.setAdapter(arrayAdapter);


                btnthemsach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            check();
                            Sach sach = new Sach();
                            sach.setTenSach(ed_tenSach.getText().toString());
                            sach.setGiaThue(Integer.valueOf(ed_giaThue.getText().toString()));

                            sach.setSoTrang(Integer.valueOf(ed_soTrang.getText().toString()));
                            
                            for (int i = 0; i < loaiSachList.size(); i++)
                                if (sach.getMaLoai() == (loaiSachList.get(i).getMaLoai())) {
                                    position = i;
                                }
                            LoaiSach loaiSach = (LoaiSach) spinner.getSelectedItem();
                            sach.setMaLoai(loaiSach.getMaLoai());
                            long kq = dao.insertSach(sach);
                            if (kq > 0) {
                                sachList.clear();
                                sachList.addAll(dao.danhsachSach());
                                adapter.notifyDataSetChanged();
                                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                            } else {
                                Toast.makeText(getActivity(), "Thêm không thành công", Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                btnhuysach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }

            public void check() {
                if (ed_tenSach.getText().length()==0) {
                    Toast.makeText(getActivity(), "Vui lòng nhập tên sách", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (ed_giaThue.getText().length()==0) {
                    Toast.makeText(getActivity(), "Vui lòng nhập tiền thuê", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        ((MainActivity)getActivity()).setOnSearchListener(this);
        return view;
    }

    @Override
    public void OnSearch(String str) {
        adapter.getFilter().filter(str);
    }
}