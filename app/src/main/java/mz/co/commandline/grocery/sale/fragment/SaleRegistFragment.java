package mz.co.commandline.grocery.sale.fragment;


import android.app.ProgressDialog;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.sale.delegate.SaleDelegate;
import mz.co.commandline.grocery.fragment.BaseFragment;

public class SaleRegistFragment extends BaseFragment {

    @BindView(R.id.fragment_sale_regist_total_sale_value)
    TextView totalSale;

    @BindView(R.id.fragment_sale_regist_regist_sale)
    Button registSale;

    private SaleDelegate delegate;

    private ProgressDialog progressBar;

    @Override
    public int getResourceId() {
        return R.layout.fragment_sale_regist;
    }

    @Override
    public void onCreateView() {
        delegate = (SaleDelegate) getActivity();
    }

    @OnClick(R.id.fragment_sale_regist_add_item)
    public void onClickAddItem() {
        delegate.addItem();
    }
}
