package mz.co.commandline.grocery.product.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import mz.co.commandline.grocery.Listner.ClickListner;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.product.holder.ProductViewHolder;
import mz.co.commandline.grocery.product.model.Product;

public class ProductAdapter extends BaseAdapter<ProductViewHolder> {

    private ClickListner listner;

    private Context context;

    private List<Product> products;

    public ProductAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
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
        Product product = products.get(position);
        holder.setItemClickListner(listner);
        holder.bind(product);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
