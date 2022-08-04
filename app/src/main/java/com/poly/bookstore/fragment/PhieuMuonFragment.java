package com.poly.bookstore.fragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.poly.bookstore.Adapter.PhieuMuonAdapter;
import com.poly.bookstore.DAO.LoaiSachDAO;
import com.poly.bookstore.DAO.PhieuMuonDAO;
import com.poly.bookstore.DAO.SachDAO;
import com.poly.bookstore.DAO.ThanhVienDAO;
import com.poly.bookstore.MainActivity;
import com.poly.bookstore.R;
import com.poly.bookstore.model.LoaiSach;
import com.poly.bookstore.model.OnSearchListener;
import com.poly.bookstore.model.PhieuMuon;
import com.poly.bookstore.model.Sach;
import com.poly.bookstore.model.ThanhVien;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class PhieuMuonFragment extends Fragment implements OnSearchListener {

    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    List<PhieuMuon> phieuMuonList;
    PhieuMuonDAO phieuMuonDAO;
    PhieuMuonAdapter adapter;
    List<ThanhVien> thanhVienList = new ArrayList<>();
    List<Sach> sachList;
    SachDAO sachDAO;
    int masach, tienthue;
    int maThanhVien;
    PhieuMuon phieuMuon;
    ThanhVienDAO thanhVienDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);
        recyclerView = view.findViewById(R.id.rc_phieumuon);
        actionButton = view.findViewById(R.id.fabphieumuon);

        phieuMuonList = new ArrayList<>();
        phieuMuonDAO = new PhieuMuonDAO(getActivity());
        phieuMuonList = phieuMuonDAO.danhsachPhieuMuon();

        adapter = new PhieuMuonAdapter(getActivity(), phieuMuonList);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.phieu_muon_dialog, null);
                builder.setView(view1);
                AlertDialog alertDialog = builder.create();
                //anh xa
                EditText edmaPm = view1.findViewById(R.id.edmaPm);
                EditText ed_tenpm = view1.findViewById(R.id.ed_tenPm);
                EditText ed_note = view1.findViewById(R.id.ed_Note);
                Spinner spTV = view1.findViewById(R.id.spMaTV);
                Spinner spMaSach = view1.findViewById(R.id.spMaSach);
                TextView txtNgaypm = view1.findViewById(R.id.txtNgaypm);
                TextView txtTienthuepm = view1.findViewById(R.id.txtTienthuepm);
                CheckBox chkTraSach = view1.findViewById(R.id.chkTraSach);
                Button btnaddPm = view1.findViewById(R.id.btnaddPm);
                Button btnhuyPm = view1.findViewById(R.id.btnHuyPm);

                //set ngay thue
                txtNgaypm.setText("Ngày :" + sdf.format(new Date()));


                thanhVienDAO = new ThanhVienDAO(getContext());
                thanhVienList = thanhVienDAO.getAll();
                ArrayAdapter adapterspnTV = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, thanhVienList);
                spTV.setAdapter(adapterspnTV);
                spTV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        maThanhVien = thanhVienList.get(position).maTV;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                edmaPm.setEnabled(false);

                sachList = new ArrayList<>();
                sachDAO = new SachDAO(getActivity());
                sachList = sachDAO.danhsachSach();
                ArrayAdapter adapterS = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, sachList);
                spMaSach.setAdapter(adapterS);

                spMaSach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        masach = sachList.get(position).maSach;
                        tienthue = sachList.get(position).giaThue;
                        txtTienthuepm.setText("Tiền thuê : " + tienthue);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                btnaddPm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ed_tenpm.getText().toString().length()<5 || ed_tenpm.getText().toString().length()>15){
                            Toast.makeText(getActivity(), "Tên phiếu có độ dài tối thiểu 5, tối đa 15", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else if (ed_tenpm.getText().toString().equals("")){
                            Toast.makeText(getActivity(), "Không được để trống", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (ed_tenpm.getText().toString().substring(0,1).toLowerCase().equals(ed_tenpm.getText().toString().substring(0,1))){
                            Toast.makeText(getActivity(), "Vui lòng nhập chữ cái đầu viết hoa", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        phieuMuon = new PhieuMuon();
                        phieuMuon.setMaSach(masach);
                        phieuMuon.setTenPM(ed_tenpm.getText().toString());
                        phieuMuon.setNote(ed_note.getText().toString());
                        ThanhVien thanhVien = (ThanhVien) spTV.getSelectedItem();
                        phieuMuon.setMaTV(thanhVien.getMaTV());
                        phieuMuon.setNgay(new Date());
                        phieuMuon.setTienThue(tienthue);
                        if (chkTraSach.isChecked()) {
                            phieuMuon.setTraSach(1);
                        } else {
                            phieuMuon.setTraSach(0);
                        }
                        long kq = phieuMuonDAO.insertPm(phieuMuon);
                        if (kq > 0) {
                            phieuMuonList.clear();
                            phieuMuonList.addAll(phieuMuonDAO.danhsachPhieuMuon());
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Thêm không thành công", Toast.LENGTH_SHORT).show();
                            alertDialog.dismiss();
                        }
                    }
                });
                btnhuyPm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });

        ((MainActivity) getActivity()).setOnSearchListener(this);
        return view;
    }

    @Override
    public void OnSearch(String str) {
        adapter.getFilter().filter(str);
    }
}