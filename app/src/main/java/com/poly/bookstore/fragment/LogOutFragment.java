package com.poly.bookstore.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.poly.bookstore.LoginMainActivity;
import com.poly.bookstore.R;


public class LogOutFragment extends Fragment {


    public LogOutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_log_out, container, false);
        Intent intent = new Intent(getContext(), LoginMainActivity.class);
        startActivity(intent);
        return view;
    }
}