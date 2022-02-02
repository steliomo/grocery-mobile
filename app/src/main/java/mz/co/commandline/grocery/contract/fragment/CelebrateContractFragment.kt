package mz.co.commandline.grocery.contract.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.contract.delegate.ContractDelegate
import mz.co.commandline.grocery.contract.dto.ContractDTO
import mz.co.commandline.grocery.databinding.FragmentCelebrateContractBinding
import mz.co.commandline.grocery.generics.dto.EnumDTO
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.DateValidator
import mz.co.commandline.grocery.validator.DefaultValidator
import mz.co.commandline.grocery.validator.Validator
import java.math.BigDecimal

class CelebrateContractFragment : BaseFragment(), View.OnClickListener {

    private lateinit var binding: FragmentCelebrateContractBinding

    private lateinit var validators: List<Validator>

    private lateinit var delegate: ContractDelegate

    private lateinit var contract: ContractDTO

    override fun getResourceId(): Int {
        return R.layout.fragment_celebrate_contract
    }

    override fun onCreateView() {

        delegate = activity as ContractDelegate
        contract = delegate.getContract()

        validators = listOf(DefaultValidator(binding.celebrateContractDescription), DateValidator(activity, binding.celebrateContractStartDate, true, false), DateValidator(activity, binding.celebrateContractEndDate, false, true), DefaultValidator(binding.celebrateContractMonthyValue))

        val adapter: ArrayAdapter<EnumDTO> = ArrayAdapter(activity!!, android.R.layout.simple_list_item_1, delegate.getContractTypes())
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        binding.celebrateContractTypeSpinner.adapter = adapter
        binding.celebrateContractTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(adapterView: AdapterView<*>?) {
            }

            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                contract.contractType = delegate.getContractTypes()[position]
            }
        }

        binding.celebrateContractBtn.setOnClickListener(this)
    }

    override fun getTitle(): String {
        return getString(R.string.celebrate_contract)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = FragmentCelebrateContractBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onClick(view: View?) {

        if (contract.contractType == null) {
            val textView = binding.celebrateContractTypeSpinner.selectedView as TextView
            textView.error = getString(R.string.required_field)
            return
        }

        validators.forEach { validator ->
            if (!validator.isValid) {
                return
            }
        }

        contract.description = TextInputLayoutUtil.getInpuText(binding.celebrateContractDescription)
        contract.startDate = TextInputLayoutUtil.getInpuText(binding.celebrateContractStartDate)
        contract.endDate = TextInputLayoutUtil.getInpuText(binding.celebrateContractEndDate)
        contract.monthlyPayment = BigDecimal(TextInputLayoutUtil.getInpuText(binding.celebrateContractMonthyValue))

        delegate.celebrateContract();
    }
}