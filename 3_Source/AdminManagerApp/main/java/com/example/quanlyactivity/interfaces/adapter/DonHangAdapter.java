package com.example.quanlyactivity.interfaces.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyactivity.R;
import com.example.quanlyactivity.interfaces.ItemClickDeleteListener;
import com.example.quanlyactivity.interfaces.ItemClickListener;
import com.example.quanlyactivity.model.DonHang;
import com.example.quanlyactivity.model.EventBus.DonHangEvent;
import com.example.quanlyactivity.utils.Utils;

import org.checkerframework.checker.units.qual.C;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {

    List<DonHang> listdonhang;
    Context context;
    ItemClickDeleteListener deleteListener;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public DonHangAdapter(List<DonHang> listdonhang, Context context, ItemClickDeleteListener deleteListener) {
        this.listdonhang = listdonhang;
        this.context = context;
        this.deleteListener = deleteListener;
    }

    public DonHangAdapter(Context applicationContext, List<DonHang> result, ItemClickDeleteListener deleteListener) {
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = listdonhang.get(position);
        holder.txtdonhang.setText("Đơn hàng: "+ donHang.getId());
        holder.diachi.setText("Địa chỉ: " +donHang.getDiachi());
        holder.txttrangthai.setText(Utils.statusOrder(donHang.getTrangthai()));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                deleteListener.onClickDelete(donHang.getId());
                return false;
            }
        });
        holder.username.setText("Người đặt: "+donHang.getUsername());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.reChitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        //adapter chitiet
        ChiTietAdapter chiTietAdapter = new ChiTietAdapter(context,donHang.getItem());
        holder.reChitiet.setLayoutManager(layoutManager);
        holder.reChitiet.setAdapter(chiTietAdapter);
        holder.reChitiet.setRecycledViewPool(viewPool);
        //bai46
        holder.setListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
            if(isLongClick){
                EventBus.getDefault().postSticky(new DonHangEvent(donHang));
            }
            }
        });
    }
//bai46
    private String trangThaiDon(int status){
        String result = "";
        switch (status){
            case 0:
                result = "Đơn hàng đang được xử lí";
                break;
            case 1:
                result = "Đơn hàng đã chấp nhận";
                break;
            case 2:
                result = "Đơn hàng đã giao cho đơn vị vận chuyển";
                break;
            case 3:
                result = "Thành công";
                break;
            case 4:
                result = "Đơn hàng đã hủy";
                break;
        }
        return result;
    }
    @Override
    public int getItemCount() {
        return listdonhang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView txtdonhang,username, txttrangthai, diachi;
        RecyclerView reChitiet;
        ItemClickListener listener;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang =  itemView.findViewById(R.id.iddonhang);
            txttrangthai = itemView.findViewById(R.id.trangthaidon);
            diachi = itemView.findViewById(R.id.diachi_donhang);
            reChitiet = itemView.findViewById(R.id.recycleview_chitiet);
            username =  itemView.findViewById(R.id.user_donhang);
            itemView.setOnLongClickListener(this);
        }
        //bai46
        public void setListener(ItemClickListener listener) {
            this.listener = listener;
        }
        //bai46
        @Override
        public boolean onLongClick(View view) {
            listener.onClick(view,getAdapterPosition(),true);
            return false;
        }
    }
}
