package mz.co.commandline.grocery.payment.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.OnClick;
import mz.co.commandline.grocery.R;
import mz.co.commandline.grocery.generics.dto.EnumDTO;
import mz.co.commandline.grocery.generics.fragment.BaseFragment;
import mz.co.commandline.grocery.payment.delegate.PaymentDelegate;
import mz.co.commandline.grocery.payment.dto.PaymentDTO;
import mz.co.commandline.grocery.user.dto.UnitDetail;
import mz.co.commandline.grocery.util.FormatterUtil;
import mz.co.commandline.grocery.util.TextInputLayoutUtil;
import mz.co.commandline.grocery.validator.PhoneNumberValidator;
import mz.co.commandline.grocery.validator.Validator;


public class MpesaPaymentFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.fragment_mpesa_payment_manager_name)
    TextView managerName;

    @BindView(R.id.fragment_mpesa_payment_number_of_users)
    TextView numberOfUsers;

    @BindView(R.id.fragment_mpesa_payment_balance)
    TextView balance;

    @BindView(R.id.fragment_mpesa_voucher_spinner)
    Spinner vouchers;

    @BindView(R.id.fragment_mpesa_payment_number)
    TextInputLayout mpesaNumber;

    private PaymentDelegate delegate;

    private PaymentDTO payment;

    @Override
    public int getResourceId() {
        return R.layout.fragment_mpesa_payment;
    }

    @Override
    public void onCreateView() {
        delegate = (PaymentDelegate) getActivity();
        UnitDetail unitDetail = delegate.getUnitDetail();
        managerName.setText(unitDetail.getManagerName());
        numberOfUsers.setText(String.valueOf(unitDetail.getUsers()));
        balance.setText(FormatterUtil.mtFormat(unitDetail.getBalance()));

        ArrayAdapter<EnumDTO> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, delegate.getVouchers());
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);

        vouchers.setAdapter(adapter);
        vouchers.setOnItemSelectedListener(this);

        payment = delegate.getPayment();
    }

    @OnClick(R.id.fragment_payment_confirmation_mpesa_payment_next_btn)
    public void onClickProceed() {
        Validator validator = new PhoneNumberValidator(mpesaNumber);

        if (!validator.isValid()) {
            return;
        }

        if (payment.getVoucher() == null) {
            ((TextView) vouchers.getSelectedView()).setError(getString(R.string.required_field));
            return;
        }

        payment.setMpesaNumber(TextInputLayoutUtil.getInpuText(mpesaNumber));
        delegate.proceed();
    }

    @Override
    public String getTitle() {
        return getString(R.string.mpesa_payment);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        payment.setVoucher(delegate.getVouchers().get(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}