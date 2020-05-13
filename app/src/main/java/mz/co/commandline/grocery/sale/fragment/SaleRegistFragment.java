package mz.co.commandline.grocery.sale.fragment;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.product.delegate.ProductDelegate;
import mz.co.commandline.grocery.sale.adapter.SaleItemAdapter;
import mz.co.commandline.grocery.sale.delegate.SaleDelegate;
import mz.co.commandline.grocery.fragment.BaseFragment;
import mz.co.commandline.grocery.sale.dto.SaleDTO;
import mz.co.commandline.grocery.util.FormatterUtil;

public class SaleRegistFragment extends BaseFragment {

    @BindView(R.id.fragment_sale_regist_total_sale_value)
    TextView totalSale;

    @BindView(R.id.fragment_sale_regist_regist_sale)
    Button registSale;

    @BindView(R.id.fragment_sale_regist_recycleview)
    RecyclerView recyclerView;

    private SaleDelegate saleDelegate;

    private ProductDelegate productDelegate;


    @Override
    public int getResourceId() {
        return R.layout.fragment_sale_regist;
    }

    @Override
    public void onCreateView() {
        saleDelegate = (SaleDelegate) getActivity();
        productDelegate = (ProductDelegate) getActivity();

        SaleDTO sale = saleDelegate.getSale();

        totalSale.setText(FormatterUtil.mtFormat(sale.getTotalSale()));
        SaleItemAdapter adapter = new SaleItemAdapter(getActivity(), sale.getItems());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fragment_sale_regist_add_item)
    public void onClickAddItem() {
        productDelegate.selectProduct();
    }

    @OnClick(R.id.fragment_sale_regist_regist_sale)
    public void onClickRegistSale() {
        saleDelegate.registSale();
    }

    @Override
    public String getTitle() {
        return getString(R.string.regist_sales);
    }
}
