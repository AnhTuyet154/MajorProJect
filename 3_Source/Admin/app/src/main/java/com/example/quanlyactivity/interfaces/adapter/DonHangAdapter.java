package com.example.quanlyactivity.interfaces.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlyactivity.interfaces.ItemClickListener;
import com.example.quanlyactivity.model.EventBus.DonHangEvent;

import org.greenrobot.eventbus.EventBus;

import io.paperdb.Paper;

public class DonHangAdapter {
    /*@Override
    public void onBindViewHOlder(@NonNull MyViewHolder holder, int position){
        DonHang donHang = listdonhang.get(position);
        holder.txtdonhang.setText("Đơn hàng: "+ donHang.getId());
        holder.trangthai,setText(trangThaiDon(donHang.getTrangthai()));
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.reChitiet.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(donHang.getItem().size());
        //adapter chitiet
        ChitietAdapter chitietAdapter = new ChitietAdapter(context, donHang.getItem());
        holder.reChitiet.setLayoutManager(layoutManager);
        holder.reChitiet.setAdapter(chitietAdapter);
        holder.reChitiet.setRecycledViewPool(viewPool);
        holder.setListener(new ItemClickListener(){
            @Override
            public void onClick(View view, int pos, boolean isLongClick){
                if(isLongClick) {
                    EventBus.getDefault().postSticky(new DonHangEvent(donHang));
                }
            }
        })
    }
    private String trangThaiDon(int status){
        String result = "";
        switch(status){
            case 0:
                result = "Đơn hàng đang được xử lí";
                break;
            case 1:
                result = "Đơn hàng đã được chấp nhận";
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
    public int getItemCount()
    {
        return listdonhang.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{}
    TextView txtdonhang, trangthai;
    RecyclerView reChitiet;
    ItemClickListener listener;

    public MyViewHolder(@NonNull View itemView){
        super(itemView);
        txtdonhang = itemView.findViewById(R.id.iddonhang);
        trangthai = itemView.findViewById(R.id.tinhtrang);
        reChitiet = itemView.findViewById(R.recycleview_chitiet);
        itemView.setOnLongClickListener(this);
    }
    public void setListener(ItemClickListener listener){
        this.listener = listener;
    }

*/
}
