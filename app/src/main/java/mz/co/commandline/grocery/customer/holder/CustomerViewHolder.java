package mz.co.commandline.grocery.customer.holder;

import android.view.View;
import android.widget.TextView;

import java.math.BigDecimal;

import butterknife.BindView;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.customer.model.CustomerDTO;
import mz.co.commandline.grocery.generics.holder.BaseViewHolder;
import mz.co.commandline.grocery.generics.listner.ClickListner;

public class CustomerViewHolder extends BaseViewHolder<CustomerDTO> {

    @BindView(R.id.customer_icon)
    TextView icon;

    @BindView(R.id.customer_name)
    TextView name;

    @BindView(R.id.customer_phone_number)
    TextView phoneNumber;

    private ClickListner listner;

    private CustomerDTO customer;

    public CustomerViewHolder(View view) {
        super(view);
    }

    @Override
    public void bind(CustomerDTO customerDTO) {
        this.customer = customerDTO;
        icon.setText(String.valueOf(customerDTO.getName().charAt(BigDecimal.ZERO.intValue())));
        name.setText(customerDTO.getName());
        phoneNumber.setText(customerDTO.getContact());
    }

    @Override
    public void setItemClickListner(ClickListner listner) {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClickListner(customer);
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }
}
