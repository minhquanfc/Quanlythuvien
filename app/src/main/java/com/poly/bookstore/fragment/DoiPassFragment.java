package com.poly.bookstore.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.poly.bookstore.DAO.ThuThuDAO;
import com.poly.bookstore.R;
import com.poly.bookstore.model.ThuThu;


public class DoiPassFragment extends Fragment {


    TextInputEditText edpass1, edpass2, edpass3;
    Button btndoipass;
    ThuThuDAO dao;
    public static DoiPassFragment newInstance(String param1, String param2) {
        DoiPassFragment fragment = new DoiPassFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_pass, container, false);
        edpass1 = view.findViewById(R.id.edPassCu);
        edpass2 = view.findViewById(R.id.edPassMoi);
        edpass3 = view.findViewById(R.id.edPassMoi2);
        btndoipass = view.findViewById(R.id.btnpass);
        dao = new ThuThuDAO(getActivity());
        btndoipass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
                String user = preferences.getString("USERNAME", "");
                if (check() > 0) {
                    ThuThu thuThu = dao.getId(user);
                    thuThu.setMatKhau(edpass2.getText().toString());
                    if (dao.update(thuThu) > 0) {
                        Toast.makeText(getContext(), "Thay mk thanh cong", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Thay mk ko thanh cong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    public int check() {
        int check = 1;
        if (edpass1.getText().length() == 0 || edpass2.getText().length() == 0 || edpass3.getText().length() == 0) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            //doc user ,pass trong share
            SharedPreferences preferences = getActivity().getSharedPreferences("USER_FILE", Context.MODE_PRIVATE);
            String passold = preferences.getString("PASSWORD", "");
            String pass = edpass2.getText().toString();
            String pass2 = edpass3.getText().toString();
            if (!passold.equals(edpass1.getText().toString())) {
                Toast.makeText(getContext(), "Mật khẩu cũ ", Toast.LENGTH_SHORT).show();
                check = -1;
            }
            if (!pass.equals(pass2)) {
                Toast.makeText(getContext(), "Mật khẩu không trùng", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}