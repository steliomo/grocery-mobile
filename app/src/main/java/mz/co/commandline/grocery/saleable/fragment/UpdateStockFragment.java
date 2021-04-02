package mz.co.commandline.grocery.saleable.fragment;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.saleable.dto.SaleableDTO;
import mz.co.commandline.grocery.saleable.adapter.UpdateStockAdapter;
import mz.co.commandline.grocery.saleable.delegate.SaleableItemActionDelegate;


public class UpdateStockFragment extends BaseFragment {

    @BindView(R.id.fragment_update_stock_recyclerview)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_update_stock_update_btn)
    Button button;

    private SaleableItemActionDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_update_stock;
    }

    @Override
    public void onCreateView() {
        delegate = (SaleableItemActionDelegate) getActivity();
        SaleableDTO saleable = delegate.getSaleable();

        getToolBar().setTitle(delegate.getTiTle());
        button.setText(delegate.getActionName());

        UpdateStockAdapter adapter = new UpdateStockAdapter(getActivity(), saleable.getSaleableItems());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.fragment_update_stock_select_item_btn)
    public void onClickSelectItemBtn() {
        delegate.selectItem();
    }

    @OnClick(R.id.fragment_update_stock_update_btn)
    public void onClickExecuteBtn() {
        delegate.execute();
    }
}
