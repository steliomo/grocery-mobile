package mz.co.commandline.grocery.payment.fragment;

import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.payment.delegate.PaymentDelegate;
import mz.co.commandline.grocery.payment.dto.PaymentDTO;
import mz.co.commandline.grocery.user.dto.UnitDetail;
import mz.co.commandline.grocery.util.DateUtil;
import mz.co.commandline.grocery.util.FormatterUtil;


public class PaymentConfirmationFragment extends BaseFragment {

    @BindView(R.id.fragment_payment_confirmation_manager_name)
    TextView managenName;

    @BindView(R.id.fragment_payment_confirmation_number_of_users)
    TextView numberOfUsers;

    @BindView(R.id.fragment_payment_confirmation_subscription)
    TextView subscription;

    @BindView(R.id.fragment_payment_confirmation_mpesa_number)
    TextView mpesaNumber;

    @BindView(R.id.fragment_payment_confirmation_voucher)
    TextView voucher;

    @BindView(R.id.fragment_payment_confirmation_discount)
    TextView paymentDiscount;

    @BindView(R.id.fragment_payment_confirmation_total)
    TextView paymentTotal;

    private PaymentDelegate delegate;

    private PaymentDTO payment;

    @Override
    public int getResourceId() {
        return R.layout.fragment_payment_confirmation;
    }

    @Override
    public void onCreateView() {
        delegate = (PaymentDelegate) getActivity();

        UnitDetail unitDetail = delegate.getUnitDetail();
        managenName.setText(unitDetail.getManagerName());
        numberOfUsers.setText(String.valueOf(unitDetail.getUsers()));
        subscription.setText(DateUtil.format(unitDetail.getSubscriptionEndDate()));

        payment = delegate.getPayment();
        mpesaNumber.setText(payment.getWalletNumber());
        voucher.setText(payment.getVoucherLabel());
        paymentDiscount.setText(FormatterUtil.mtFormat(payment.getDiscountValue()));
        paymentTotal.setText(FormatterUtil.mtFormat(payment.getTotal()));
    }

    @OnClick({R.id.openTableDetailsOpenBtn})
    public void onClickConfirmBtn() {
        delegate.paymentExecution(payment);
    }

    @Override
    public String getTitle() {
        return getString(R.string.payment_confirmation);
    }
}