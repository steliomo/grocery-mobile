package mz.co.commandline.grocery.rent.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.activities.RentActivity
import mz.co.commandline.grocery.databinding.FragmentTransportItemBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.rent.delegate.RentDelegate
import mz.co.commandline.grocery.rent.dto.RentItemDTO
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.UnexpectedValuesValidator
import mz.co.commandline.grocery.validator.Validator
import java.math.BigDecimal

class TransportItemFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentTransportItemBinding? = null
    private val binding get() = _binding!!

    private var delegate: RentDelegate? = null

    private var validator: Validator? = null
    private var rentItemDTO: RentItemDTO? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_transport_item
    }

    override fun onCreateView() {
        delegate = activity as RentDelegate

        rentItemDTO = delegate?.rentItemDTO
        binding.transportItemName.text = rentItemDTO?.name
        binding.transportItemEstimatedQuantity.text = rentItemDTO?.plannedQuantity.toString()
        binding.transportItemEstimatedDays.text = rentItemDTO?.plannedDays.toString()
        binding.transportItemLoaded.text = rentItemDTO?.loadedQuantity.toString()
        binding.transportItemToLoad.text = rentItemDTO?.quantityToLoad.toString()
        binding.transportItemLastLoadDate.text = rentItemDTO?.loadingDate

        validator = UnexpectedValuesValidator(binding.transportItemQuantity, rentItemDTO?.quantityToLoad!!, getString(R.string.return_quantity_unexpected))

        binding.transportItemAddBtn.setOnClickListener(this)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentTransportItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(view: View?) {
        if (!(validator!!.isValid)) {
            return
        }

        var quantity = BigDecimal(TextInputLayoutUtil.getInpuText(binding.transportItemQuantity))
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

    override fun getTitle(): String {
        return getString(R.string.item_details)
    }
}