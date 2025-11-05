package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentPosPayDebtBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.pos.delegate.PosDelegate
import mz.co.commandline.grocery.pos.dto.DebtDTO
import mz.co.commandline.grocery.util.FormatterUtil
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.UnexpectedValuesValidator
import java.math.BigDecimal

class PosPayDebtFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentPosPayDebtBinding? = null
    private val binding get() = _binding!!

    private var delegate: PosDelegate? = null
    private var debt: DebtDTO? = null

    override fun onCreateView() {
        delegate = activity as PosDelegate
        debt = delegate!!.getDebt()

        binding.posPayDeptCustomer.text = debt!!.customer!!.name
        binding.posPayDeptTotalToPay.text = FormatterUtil.mtFormat(debt?.totalToPay)
        binding.posPayDeptTotalPaid.text = FormatterUtil.mtFormat(debt?.totalPaid)
        binding.posPayDeptTotalInDept.text = FormatterUtil.mtFormat(debt?.totalInDebt)

        binding.posPayDeptBtn.setOnClickListener(this)
    }

    override fun getResourceId(): Int {
        return R.layout.fragment_pos_pay_debt
    }

    override fun getTitle(): String {
        return getString(R.string.pay_bill)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPosPayDebtBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(view: View?) {

        val validator = UnexpectedValuesValidator(binding.posPayDeptAmount, debt!!.totalInDebt!!, getString(R.string.payment_value_unexpected))

        if (!validator!!.isValid) {
            return
        }

        val amount = BigDecimal(TextInputLayoutUtil.getInpuText(binding.posPayDeptAmount))
        debt?.amount = amount

        delegate?.payDept(debt!!)
    }
}