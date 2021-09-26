package mz.co.commandline.grocery.rent.fragment;

import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.rent.adapter.RentItemAdapter;
import mz.co.commandline.grocery.rent.delegate.RentDelegate;
import mz.co.commandline.grocery.rent.model.RentDTO;
import mz.co.commandline.grocery.util.FormatterUtil;


public class RentItemsFragment extends BaseFragment {

    @BindView(R.id.fragment_rent_items_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.fragment_rent_items_total_value)
    TextView totalValue;

    private RentDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_rent_items;
    }

    @Override
    public void onCreateView() {
        delegate = (RentDelegate) getActivity();

        RentDTO rent = delegate.getRent();
        totalValue.setText(FormatterUtil.mtFormat(rent.getTotal()));

        RentItemAdapter adapter = new RentItemAdapter(getActivity(), rent.getRentItemsDTO());
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public String getTitle() {
        return getString(R.string.rent);
    }

    @OnClick(R.id.fragment_rent_items_select_item)
    public void onClickSelectItemBtn() {
        delegate.selectItem();
    }

    @OnClick(R.id.fragment_rent_items_rent)
    public void onClickRentBtn() {
        delegate.nextStep();
    }
}