package mz.co.commandline.grocery.rent.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.holder.BaseViewHolder;
import mz.co.commandline.grocery.generics.listner.ClickListner;
import mz.co.commandline.grocery.rent.dto.RentItemDTO;
import mz.co.commandline.grocery.util.FormatterUtil;

public class RentItemViewHolder extends BaseViewHolder<RentItemDTO> {

    @BindView(R.id.rent_item_image_view)
    ImageView imageView;

    @BindView(R.id.rent_item_name)
    TextView itemName;

    @BindView(R.id.rent_item_days)
    TextView days;

    @BindView(R.id.rent_item_discount)
    TextView discount;

    @BindView(R.id.rent_item_value)
    TextView value;

    @BindView(R.id.rent_item_quantity)
    TextView quantity;

    public RentItemViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(RentItemDTO rentItemDTO) {
        imageView.setImageResource(rentItemDTO.getSaleableItemDTO().getSalableItemType().getIconId());
        itemName.setText(rentItemDTO.getSaleableItemDTO().getName());
        quantity.setText(String.valueOf(rentItemDTO.getPlannedQuantity()));
        days.setText(String.valueOf(rentItemDTO.getPlannedDays()));
        value.setText(FormatterUtil.mtFormat(rentItemDTO.calculatePlannedValue()));
        discount.setText(FormatterUtil.mtFormat(rentItemDTO.getDiscount()));
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
