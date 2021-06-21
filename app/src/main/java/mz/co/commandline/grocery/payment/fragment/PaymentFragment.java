package mz.co.commandline.grocery.payment.fragment;

import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.payment.delegate.PaymentDelegate;
import mz.co.commandline.grocery.user.dto.UnitDetail;
import mz.co.commandline.grocery.user.dto.UserDTO;
import mz.co.commandline.grocery.util.FormatterUtil;


public class PaymentFragment extends BaseFragment {

    @BindView(R.id.fragment_payment_manager_name)
    TextView managerName;

    @BindView(R.id.fragment_payment_number_of_users)
    TextView numberOfUsers;

    @BindView(R.id.fragment_payment_balance)
    TextView balance;

    private PaymentDelegate delegate;

    @Override
    public int getResourceId() {
        return R.layout.fragment_payment;
    }

    @Override
    public void onCreateView() {
        delegate = (PaymentDelegate) getActivity();
        UnitDetail unitDetail = delegate.getUnitDetail();
        managerName.setText(unitDetail.getManagerName());
        numberOfUsers.setText(String.valueOf(unitDetail.getUsers()));
        balance.setText(FormatterUtil.mtFormat(unitDetail.getBalance()));
    }

    @OnClick({R.id.fragment_payment_mpesa_view, R.id.fragment_payment_mpesa_label})
    public void onClickMpesaMethos() {
        delegate.mpesaMethod();
    }

    @Override
    public String getTitle() {
        return getString(R.string.payments);
    }
}