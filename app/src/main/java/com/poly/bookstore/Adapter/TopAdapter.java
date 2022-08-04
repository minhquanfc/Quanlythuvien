package com.poly.bookstore.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.poly.bookstore.R;
import com.poly.bookstore.fragment.TopFragment;
import com.poly.bookstore.model.Top;

import java.util.ArrayList;

public class TopAdapter extends ArrayAdapter<Top> {
    Context context;
    TopFragment fragment;
    ArrayList<Top> list;
    TextView tvsach,tvsoLuong;
    ImageView imgdelete;

    public TopAdapter(@NonNull Context context, TopFragment fragment, ArrayList<Top> list) {
        super(context, 0,list);
        this.context = context;
        this.fragment = fragment;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=inflater.inflate(R.layout.top_item,null);
        }
        final Top item = list.get(position);
        if (item !=null){
            tvsach = v.findViewById(R.id.tvSach);
            tvsach.setText("Sách : "+item.tenSach);
            tvsoLuong = v.findViewById(R.id.tvSL);
            tvsoLuong.setText("Số lượng : "+item.soLuong);
        }
        return v;
    }
}
