package mz.co.commandline.grocery.saleable.fragment;

import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.saleable.dto.ServiceItemDTO;
import mz.co.commandline.grocery.saleable.delegate.SaleableItemActionDelegate;
import mz.co.commandline.grocery.saleable.dto.ActionType;
import mz.co.commandline.grocery.validator.DefaultValidator;
import mz.co.commandline.grocery.validator.Validator;

public class SaleableServiceFragment extends BaseFragment {

    @BindView(R.id.fragment_saleable_service_price)
    TextInputLayout servicePrice;

    @BindView(R.id.fragment_saleable_service_name)
    TextView serviceName;

    private SaleableItemActionDelegate delegate;

    private Validator validator;

    private ServiceItemDTO serviceItem;

    @Override
    public int getResourceId() {
        return R.layout.fragment_saleable_service;
    }

    @Override
    public void onCreateView() {
        delegate = (SaleableItemActionDelegate) getActivity();

        Toolbar toolBar = getToolBar();
        toolBar.setTitle(delegate.getTiTle());

        serviceItem = (ServiceItemDTO) delegate.getSaleableItem();
        serviceName.setText(serviceItem.getName());

        validator = new DefaultValidator(servicePrice);

        if (ActionType.UPDATE.equals(delegate.getActionType())) {
            servicePrice.getEditText().setText(serviceItem.getSalePrice());
        }
    }

    @OnClick(R.id.fragment_saleable_service_add)
    public void onclickAddService() {

        if (!validator.isValid()) {
            return;
        }

        serviceItem.setSalePrice(servicePrice.getEditText().getText().toString());

        delegate.addSaleableItem(serviceItem);
    }

    @OnClick(R.id.fragment_saleable_service_cancel)
    public void onClickCancelBtn() {
        delegate.cancel();
    }
}
