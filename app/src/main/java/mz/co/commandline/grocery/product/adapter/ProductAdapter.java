package mz.co.commandline.grocery.product.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.listner.ClickListner;
import mz.co.commandline.grocery.product.dto.ProductDTO;
import mz.co.commandline.grocery.product.holder.ProductViewHolder;

public class ProductAdapter extends BaseAdapter<ProductViewHolder> implements Filterable {

    private ClickListner listner;

    private Context context;

    private List<ProductDTO> productDTOs;

    private Filter filter;

    public ProductAdapter(Context context, final List<ProductDTO> productDTOs) {
        this.context = context;
        this.productDTOs = productDTOs;
        final List<ProductDTO> mainProductDTOS = new ArrayList<>(productDTOs);

        filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<ProductDTO> filteredProducts = new ArrayList<>();
                FilterResults results = new FilterResults();

                if (charSequence.length() != 0) {

                    for (ProductDTO product : mainProductDTOS) {
                        if (product.getName().toLowerCase().contains(charSequence.toString().toLowerCase().trim())) {
                            filteredProducts.add(product);
                        }
                    }

                    results.values = filteredProducts;
                    return results;
                }

                results.values = mainProductDTOS;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                productDTOs.clear();
                productDTOs.addAll((List) filterResults.values);
                notifyDataSetChanged();
            }
        };
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

    @Override
    public Filter getFilter() {
        return filter;
    }
}
