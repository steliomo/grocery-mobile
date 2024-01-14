package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentPosPaymentBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.pos.delegate.PosDelegate
import mz.co.commandline.grocery.sale.dto.SalePaymentDTO
import mz.co.commandline.grocery.util.FormatterUtil
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.UnexpectedValuesValidator
import java.math.BigDecimal


class PosPaymentFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentPosPaymentBinding? = null
    private val binding get() = _binding!!

    private var delegate: PosDelegate? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_pos_payment
    }

    override fun onCreateView() {
        delegate = activity as PosDelegate
        val table = delegate!!.getTable()

        binding.posPaymentCode.text = "#" + table.tableNumber.toString().padStart(2, '0')
        binding.posPaymentOwner.text = table.customerDTO.name
        binding.posPaymentTotal.text = FormatterUtil.mtFormat(table.total)
        binding.posPaymentTotalPaid.text = FormatterUtil.mtFormat(table.totalPaid)
        binding.posPaymentTotalToPay.text = FormatterUtil.mtFormat(table.totalToPay())

        binding.posPaymentPayBtn.setOnClickListener(this)
    }

    override fun getTitle(): String {
        return getString(R.string.payment_regist)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPosPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(p0: View?) {
        val table = delegate!!.getTable()
        val validator = UnexpectedValuesValidator(binding.posPaymentValue, table.totalToPay(), getString(R.string.payment_value_unexpected))

        if (!validator!!.isValid) {
            return
        }

        val payment = SalePaymentDTO(table.uuid, BigDecimal(TextInputLayoutUtil.getInpuText(binding.posPaymentValue)), table.saleDate)

        delegate!!.registPayment(payment)
    }
}