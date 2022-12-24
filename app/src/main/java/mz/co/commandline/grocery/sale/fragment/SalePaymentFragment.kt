package mz.co.commandline.grocery.sale.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentSalePaymentBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.sale.delegate.SaleDelegate
import mz.co.commandline.grocery.sale.dto.SalePaymentDTO
import mz.co.commandline.grocery.util.DateUtil
import mz.co.commandline.grocery.util.FormatterUtil
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.DateValidator
import mz.co.commandline.grocery.validator.UnexpectedValuesValidator
import mz.co.commandline.grocery.validator.Validator
import java.math.BigDecimal


class SalePaymentFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentSalePaymentBinding? = null
    private val binding get() = _binding!!

    private var delegate: SaleDelegate? = null

    private var validators: List<Validator>? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_sale_payment
    }

    override fun onCreateView() {
        delegate = activity as SaleDelegate
        val sale = delegate?.sale

        binding.salePaymentFragmentSaleDateValue.text = sale?.saleDate
        binding.salePaymentFragmentTotalSaleValue.text = FormatterUtil.mtFormat(sale?.total)
        binding.salePaymentFragmentTotalPaidValue.text = FormatterUtil.mtFormat(sale?.totalPaid)
        binding.salePaymentFragmentTotalDeptValue.text = FormatterUtil.mtFormat(sale?.total?.subtract(sale?.totalPaid))
        binding.salePaymentFragmentCustomerName.text = sale?.customerDTO?.name

        validators = listOf(UnexpectedValuesValidator(binding.salePaymentDetailsInstallmentValue, sale!!.total.subtract(sale!!.totalPaid), getString(R.string.installment_value_not_allowed)),
                DateValidator(activity, binding.salePaymentInstallmentPaymentDate, true, false))


        binding.salePaymentDetailsPayBtn.setOnClickListener(this);
    }

    override fun getTitle(): String {
        return getString(R.string.installment_payment)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentSalePaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding == null
    }

    override fun onClick(view: View?) {
        validators?.forEach { validator ->
            if (!validator.isValid) {
                return
            }
        }

        var salePayment = SalePaymentDTO(delegate!!.sale.uuid, BigDecimal(TextInputLayoutUtil.getInpuText(binding.salePaymentDetailsInstallmentValue)),
                TextInputLayoutUtil.getInpuText(binding.salePaymentInstallmentPaymentDate))

        delegate?.payInstallment(salePayment)
    }
}