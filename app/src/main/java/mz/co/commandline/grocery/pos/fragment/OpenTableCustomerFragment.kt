package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.customer.delegate.CustomerDelegate
import mz.co.commandline.grocery.customer.model.CustomerDTO
import mz.co.commandline.grocery.databinding.FragmentCustomerOpenTableBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.pos.delegate.PosDelegate
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.DefaultValidator
import mz.co.commandline.grocery.validator.PhoneNumberValidator
import mz.co.commandline.grocery.validator.Validator


class OpenTableCustomerFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentCustomerOpenTableBinding? = null
    private val binding get() = _binding!!

    private var validators: List<Validator>? = null

    private var _delegate: CustomerDelegate? = null
    private val delegate get() = _delegate!!

    override fun getResourceId(): Int {
        return R.layout.fragment_customer_open_table
    }

    override fun onCreateView() {
        _delegate = activity as CustomerDelegate
        validators = listOf(DefaultValidator(binding.fragmentOpenTableCustomer), PhoneNumberValidator(binding.fragmentOpenTableContact))

        binding.fragmentOpenTableOpenBtn.setOnClickListener(this)
    }

    override fun getTitle(): String {
        return getString(R.string.customer)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentCustomerOpenTableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(view: View?) {
        validators!!.forEach { validator ->
            if (!validator.isValid) {
                return
            }
        }

        var customer = CustomerDTO()
        customer.name = TextInputLayoutUtil.getInpuText(binding.fragmentOpenTableCustomer)
        customer.contact = TextInputLayoutUtil.getInpuText(binding.fragmentOpenTableContact)
        customer.email = TextInputLayoutUtil.getInpuText(binding.fragmentOpenTableEmail)

        delegate.registCustomer(customer)
    }
}