package mz.co.commandline.grocery.item.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.holder.BaseViewHolder;
import mz.co.commandline.grocery.item.dto.ItemDTO;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.item.dto.ItemType;

public class ProductViewHolder extends BaseViewHolder<ItemDTO> {

    @BindView(R.id.product_name)
    TextView productName;

    @BindView(R.id.product_icon)
    ImageView itemIcon;

    private ClickListner listner;

    private ItemDTO itemDTO;

    public ProductViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(ItemDTO itemDTO) {
        this.itemDTO = itemDTO;
        productName.setText(itemDTO.getName());

        if (ItemType.SERVICE.equals(itemDTO.getItemType())) {
            itemIcon.setImageResource(R.mipmap.ic_services);
        }
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View v) {
        listner.onClickListner(itemDTO);
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
