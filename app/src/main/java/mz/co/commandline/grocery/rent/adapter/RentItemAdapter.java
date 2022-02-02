package mz.co.commandline.grocery.rent.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.util.List;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.rent.holder.RentItemViewHolder;
import mz.co.commandline.grocery.rent.dto.RentItemDTO;

public class RentItemAdapter extends BaseAdapter<RentItemViewHolder> {

    private Context context;

    private List<RentItemDTO> rentItems;

    @Override
    public void setItemClickListner(ClickListner listner) {

    }

    public RentItemAdapter(Context context, List<RentItemDTO> rentItems) {
        this.context = context;
        this.rentItems = rentItems;
    }

    @NonNull
    @Override
    public RentItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rent_item, parent, false);
        RentItemViewHolder holder = new RentItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RentItemViewHolder holder, int position) {
        RentItemDTO rentItemDTO = rentItems.get(position);
        holder.bind(rentItemDTO);
    }

    @Override
    public int getItemCount() {
        return rentItems.size();
    }
}
