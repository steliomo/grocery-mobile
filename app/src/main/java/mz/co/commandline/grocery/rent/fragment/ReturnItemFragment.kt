package mz.co.commandline.grocery.rent.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentReturnItemBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.rent.delegate.RentDelegate
import mz.co.commandline.grocery.rent.dto.RentItemDTO
import mz.co.commandline.grocery.rent.dto.ReturnItemDTO
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.DateValidator
import mz.co.commandline.grocery.validator.UnexpectedValuesValidator
import mz.co.commandline.grocery.validator.Validator
import java.math.BigDecimal


class ReturnItemFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentReturnItemBinding? = null
    private val binding get() = _binding!!

    private var _delegate: RentDelegate? = null
    private val delegate get() = _delegate!!

    private var rentItemDTO: RentItemDTO? = null

    private var validators: List<Validator>? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_return_item
    }

    override fun onCreateView() {
        _delegate = activity as RentDelegate
        rentItemDTO = delegate.rentItemDTO

        binding.devolutionItemName.text = rentItemDTO?.name
        binding.devolutionItemRented.text = rentItemDTO?.quantity.toString()
        binding.devolutionItemReturned.text = rentItemDTO?.returned.toString()
        binding.devolutionItemToReturn.text = rentItemDTO?.toReturn.toString()

        validators = listOf(UnexpectedValuesValidator(binding.devolutionItemQuantity, BigDecimal(rentItemDTO?.toReturn.toString()), getString(R.string.return_quantity_unexpected)), DateValidator(activity, binding.devolutionItemDate, true, false))

        binding.addReturnItemBtn.setOnClickListener(this)
    }

    override fun getTitle(): String {
        return getString(R.string.return_item_details)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentReturnItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _delegate = null
    }

    override fun onClick(view: View?) {
        validators?.forEach { validator ->
            if (!validator.isValid) {
                return
            }
        }

        rentItemDTO?.setSelected(true)
        delegate.addReturnItemDTO(ReturnItemDTO(rentItemDTO as RentItemDTO, BigDecimal(TextInputLayoutUtil.getInpuText(binding.devolutionItemQuantity)), TextInputLayoutUtil.getInpuText(binding.devolutionItemDate)))
    }
}
