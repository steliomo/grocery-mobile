package mz.co.commandline.grocery.contract.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.contract.delegate.ContractDelegate
import mz.co.commandline.grocery.databinding.FragmentCelebrateContractConfirmationBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.util.DateUtil
import mz.co.commandline.grocery.util.FormatterUtil


class CelebrateContractConfirmationFragment : BaseFragment() {

    private lateinit var binding: FragmentCelebrateContractConfirmationBinding

    private lateinit var contractDelegate: ContractDelegate

    override fun getResourceId(): Int {
        return R.layout.fragment_celebrate_contract_confirmation
    }

    override fun onCreateView() {
        contractDelegate = activity as ContractDelegate
        val contract = contractDelegate.getContract();

        binding.contractConfirmationType.text = contract.contractType?.label
        binding.contractConfirmationDescription.text = contract.description
        binding.contractConfirmationDuration.text = DateUtil.daysBetween(contract.startDate, contract.endDate).toString()
        binding.contractConfirmationValue.text = FormatterUtil.mtFormat(contract.monthlyPayment)
        binding.contractConfirmationCustomerName.text = contract.customerDTO?.name
        binding.contractConfirmationCustomerAddress.text = contract.customerDTO?.address
        binding.contractConfirmationCustomerPhone.text = contract.customerDTO?.contact

        binding.contractConfirmationBtn.setOnClickListener { contractDelegate.registContract() }
    }

    override fun getTitle(): String {
        return getString(R.string.contract_confirmation)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentCelebrateContractConfirmationBinding.inflate(inflater, container, false)
        return binding.root
    }
}