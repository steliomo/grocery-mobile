package mz.co.commandline.grocery.sale.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentPaymentDetailsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.sale.delegate.SaleDelegate
import mz.co.commandline.grocery.sale.dto.SaleDTO
import mz.co.commandline.grocery.util.FormatterUtil
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.DateValidator
import mz.co.commandline.grocery.validator.UnexpectedValuesValidator
import mz.co.commandline.grocery.validator.Validator
import java.math.BigDecimal

class PaymentDetailsFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentPaymentDetailsBinding? = null
    private val binding get() = _binding!!

    private var delegate: SaleDelegate? = null
    private var validators: List<Validator>? = null
    private var sale: SaleDTO? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_payment_details
    }

    override fun onCreateView() {
        delegate = activity as SaleDelegate

        sale = delegate?.sale
        binding.salePaymentDetailsTotalAmountValue.text = FormatterUtil.mtFormat(sale?.totalSale)
        binding.salePaymentDetailsCustomerName.text = sale?.customerDTO?.name

        validators = listOf(DateValidator(activity, binding.salePaymentDetailsDueDate, false, true))

        binding.salePaymentDetailsPayBtn.setOnClickListener(this)

    }

    override fun getTitle(): String {
        return getString(R.string.payment_details)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPaymentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(v: View?) {
        validators?.forEach { validator ->
            if (!validator.isValid) {
                return
            }
        }

        sale?.dueDate = TextInputLayoutUtil.getInpuText(binding.salePaymentDetailsDueDate)

        delegate?.registInstallmentSale(sale)
    }
}