package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentPosPrintDebtBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.pos.delegate.PosDelegate
import mz.co.commandline.grocery.util.FormatterUtil

class PosPrintDebtFragment : BaseFragment() {

    private var _binding: FragmentPosPrintDebtBinding? = null
    private val binding get() = _binding!!

    private var delegate: PosDelegate? = null

    override fun onCreateView() {
        delegate = activity as PosDelegate
        val debt = delegate!!.getDebt()

        binding.posPrintDeptCustomer.text = debt!!.customer!!.name
        binding.posPrintDeptTotalToPay.text = FormatterUtil.mtFormat(debt?.totalToPay)
        binding.posPrintDeptTotalPaid.text = FormatterUtil.mtFormat(debt?.totalPaid)
        binding.posPrintDeptTotalInDept.text = FormatterUtil.mtFormat(debt?.totalInDebt)

        binding.posPrintDeptPrintImageView.setOnClickListener{delegate?.print()}
        binding.posPrintDeptWhatsAppImageView.setOnClickListener{delegate?.sendToWhatsApp()}
    }

    override fun getResourceId(): Int {
       return R.layout.fragment_pos_print_debt
    }

    override fun getTitle(): String {
        return getString(R.string.print_bill)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPosPrintDebtBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}