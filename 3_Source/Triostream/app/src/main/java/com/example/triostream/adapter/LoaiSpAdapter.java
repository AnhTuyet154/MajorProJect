package com.example.triostream.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.triostream.R;
import com.example.triostream.model.LoaiSp;

import java.util.List;

public class LoaiSpAdapter extends BaseAdapter {

    List<LoaiSp> array;
    Context context;

    public LoaiSpAdapter(Context context, List<LoaiSp> array) {
        this.array = array;
        this.context = context;
    }

    @Override
    public int getCount() {
        return array.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    public class ViewHolder{
        TextView texttensp;
        ImageView imghinhanh;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewholder = null;
        if (view == null){
            viewholder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.item_sanpham,null);
            viewholder.texttensp = view.findViewById(R.id.item_tensp);
            viewholder.imghinhanh = view.findViewById(R.id.item_image);
            view.setTag(viewholder);
        }else {
            viewholder = (ViewHolder) view.getTag();
            viewholder.texttensp.setText(array.get(i).getTensanpham());
            Glide.with(context).load(array.get(i).getHinhanh()).into(viewholder.imghinhanh);
        }
    return view;
    }
}
