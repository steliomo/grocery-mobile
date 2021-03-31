package mz.co.commandline.grocery.item.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.adapter.BaseAdapter;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.item.holder.ProductViewHolder;

public class ProductAdapter extends BaseAdapter<ProductViewHolder> implements Filterable {

    private ClickListner listner;

    private Context context;

    private List<ItemDTO> productDTOs;

    private Filter filter;

    public ProductAdapter(Context context, final List<ItemDTO> itemsDTO) {
        this.context = context;
        this.productDTOs = itemsDTO;
        final List<ItemDTO> mainItemstDTO = new ArrayList<>(itemsDTO);

        filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<ItemDTO> filteredItems = new ArrayList<>();
                FilterResults results = new FilterResults();

                if (charSequence.length() != 0) {

                    for (ItemDTO itemDTO : mainItemstDTO) {
                        if (itemDTO.getName().toLowerCase().contains(charSequence.toString().toLowerCase().trim())) {
                            filteredItems.add(itemDTO);
                        }
                    }

                    results.values = filteredItems;
                    return results;
                }

                results.values = mainItemstDTO;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemsDTO.clear();
                itemsDTO.addAll((List) filterResults.values);
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
        ItemDTO itemDTO = productDTOs.get(position);
        holder.setItemClickListner(listner);
        holder.bind(itemDTO);
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
