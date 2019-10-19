package mz.co.commandline.grocery.product.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.product.dto.ProductDTO;
import mz.co.commandline.grocery.product.holder.ProductViewHolder;

public class ProductAdapter extends BaseAdapter<ProductViewHolder> {

    private ClickListner listner;

    private Context context;

    private List<ProductDTO> productDTOs;

    public ProductAdapter(Context context, List<ProductDTO> productDTOs) {
        this.context = context;
        this.productDTOs = productDTOs;
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product, parent, false);
        ProductViewHolder holder = new ProductViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        ProductDTO productDTO = productDTOs.get(position);
        holder.setItemClickListner(listner);
        holder.bind(productDTO);
    }

    @Override
    public int getItemCount() {
        return productDTOs.size();
    }
}
