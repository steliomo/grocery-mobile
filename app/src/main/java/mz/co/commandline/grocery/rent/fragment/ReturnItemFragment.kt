package mz.co.commandline.grocery.rent.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.activities.RentActivity
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

    private var validator: Validator? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_return_item
    }

    override fun onCreateView() {
        _delegate = activity as RentDelegate
        rentItemDTO = delegate.rentItemDTO

        binding.returnItemName.text = rentItemDTO?.name
        binding.returnItemEstimatedQuantity.text = rentItemDTO?.plannedQuantity.toString()
        binding.returnItemEstimatedDays.text = rentItemDTO?.plannedDays.toString()
        binding.returnItemLoaded.text = rentItemDTO?.loadedQuantity.toString()
        binding.returnedItems.text = rentItemDTO?.returnedQuantity.toString()
        binding.returnItemToReturn.text = rentItemDTO?.quantityToReturn.toString()
        binding.returnItemLastReturnDate.text = rentItemDTO?.returnDate

        validator = UnexpectedValuesValidator(binding.returnItemQuantity, rentItemDTO?.quantityToReturn!!, getString(R.string.return_quantity_unexpected))

        binding.returnItemAddBtn.setOnClickListener(this)
    }

    override fun getTitle(): String {
        return getString(R.string.item_details)
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
        if (!validator!!.isValid) {
            return
        }

        var quantity = BigDecimal(TextInputLayoutUtil.getInpuText(binding.returnItemQuantity))
        val rentActivity = activity as RentActivity

        if (quantity.compareTo(BigDecimal.ZERO) == 0) {
            rentItemDTO?.isSelected = false
            rentItemDTO?.quantity = quantity

            rentActivity.popBackStack()
            return
        }

        rentItemDTO?.isSelected = true
        rentItemDTO?.quantity = quantity
        rentActivity.popBackStack()
    }
}
