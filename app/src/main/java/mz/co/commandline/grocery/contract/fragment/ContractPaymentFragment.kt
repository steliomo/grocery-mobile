package mz.co.commandline.grocery.contract.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.contract.delegate.ContractDelegate
import mz.co.commandline.grocery.contract.dto.ContractPaymentDTO
import mz.co.commandline.grocery.databinding.FragmentContractPaymentBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.util.DateUtil
import mz.co.commandline.grocery.util.FormatterUtil
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.DateValidator

class ContractPaymentFragment : BaseFragment() {

    private lateinit var binding: FragmentContractPaymentBinding

    override fun getResourceId(): Int {
        return R.layout.fragment_contract_payment
    }

    override fun onCreateView() {
        val delegate = activity as ContractDelegate
        val contract = delegate.getContract();

        binding.contractPaymentType.text = contract.contractType?.label
        binding.contractPaymentStartDate.text = contract.startDate
        binding.contractPaymentEndDate.text = contract.endDate
        binding.contractPaymentMonthly.text = FormatterUtil.mtFormat(contract.monthlyPayment)
        binding.contractPaymentTotalPaid.text = FormatterUtil.mtFormat(contract.totalPaid)
        binding.contractPaymentReferenceMonth.editText?.setText(contract.referencePaymentDate)

        val validator = DateValidator(activity, binding.contractPaymentDate, true, false)

        binding.contractPaymentRegistBtn.setOnClickListener {
            if (validator.isValid) {
                val contractPaymentDTO = ContractPaymentDTO(contract, TextInputLayoutUtil.getInpuText(binding.contractPaymentDate))
                delegate.registContractPayment(contractPaymentDTO)
            }
        }
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentContractPaymentBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.payment_regist)
    }
}