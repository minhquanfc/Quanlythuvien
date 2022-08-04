package com.poly.bookstore.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.poly.bookstore.Adapter.TopAdapter;
import com.poly.bookstore.DAO.PhieuMuonDAO;
import com.poly.bookstore.R;
import com.poly.bookstore.model.PhieuMuon;
import com.poly.bookstore.model.Top;

import java.util.ArrayList;
import java.util.List;

public class TopFragment extends Fragment {

    ListView listView;
    TopAdapter adapter;
    ArrayList<Top> list;
    PhieuMuonDAO phieuMuonDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_top, container, false);
        listView=view.findViewById(R.id.lv_top);
        list= new ArrayList<>();
        phieuMuonDAO= new PhieuMuonDAO(getContext());
        list = (ArrayList<Top>) phieuMuonDAO.getTop();
        adapter = new TopAdapter(getContext(),this,list);
        listView.setAdapter(adapter);
        return view;
    }
}