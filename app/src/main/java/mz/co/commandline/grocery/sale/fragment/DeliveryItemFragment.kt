package mz.co.commandline.grocery.sale.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.activities.SaleActivity
import mz.co.commandline.grocery.databinding.FragmentDeliveryItemBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.sale.delegate.SaleDelegate
import mz.co.commandline.grocery.sale.dto.SaleItemDTO
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.UnexpectedValuesValidator
import mz.co.commandline.grocery.validator.Validator
import java.math.BigDecimal

class DeliveryItemFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentDeliveryItemBinding? = null
    private val binding get() = _binding!!
    private var delegate: SaleDelegate? = null
    private var validator: Validator? = null
    private var saleItemDTO: SaleItemDTO? = null


    override fun getResourceId(): Int {
        return R.layout.fragment_delivery_item
    }

    override fun onCreateView() {
        delegate = activity as SaleDelegate
        saleItemDTO = delegate?.saleItemDTO

        binding.deliveryItemName.text = saleItemDTO?.saleableItemDTO?.name
        binding.deliveryItemRequestedQuantity.text = saleItemDTO?.quantity.toString()
        binding.deliveryItemDeliveredQuantity.text = saleItemDTO?.deliveredQuantity.toString()
        binding.deliveryItemToDelivery.text = saleItemDTO?.toDeliveryQuantity.toString()

        validator = UnexpectedValuesValidator(binding.deliveryItemQuantity, saleItemDTO!!.toDeliveryQuantity, getString(R.string.return_quantity_unexpected))

        binding.deliveryItemAddBtn.setOnClickListener(this)
    }

    override fun getTitle(): String {
        return getString(R.string.item_details)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentDeliveryItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(view: View?) {
        if (!validator!!.isValid) {
            return
        }

        val saleActivity = activity as SaleActivity
        var quantity = BigDecimal(TextInputLayoutUtil.getInpuText(binding.deliveryItemQuantity))

        if (quantity.compareTo(BigDecimal.ZERO) == 0) {
            saleItemDTO?.setSelected(false)
            saleItemDTO?.sendQuantity = quantity

            saleActivity.popBackStack()
            return
        }

        saleItemDTO?.setSelected(true)
        saleItemDTO?.sendQuantity = quantity

        saleActivity.popBackStack()
    }
}