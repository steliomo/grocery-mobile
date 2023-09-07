package mz.co.commandline.grocery.pos.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentPosAddOrderItemBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.pos.delegate.PosDelegate
import mz.co.commandline.grocery.sale.dto.SaleItemDTO
import mz.co.commandline.grocery.saleable.dto.SaleableItemDTO
import mz.co.commandline.grocery.util.TextInputLayoutUtil
import mz.co.commandline.grocery.validator.DefaultValidator
import java.math.BigDecimal


class PosAddOrderItemFragment : BaseFragment(), View.OnClickListener {

    private lateinit var delegate: PosDelegate
    private var _binding: FragmentPosAddOrderItemBinding? = null
    private val binding get() = _binding!!

    private var saleableItem: SaleableItemDTO? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_pos_add_order_item
    }

    override fun onCreateView() {
        delegate = activity as PosDelegate

        saleableItem = delegate.saleableItem
        binding.fragmentAddOrderItemProductName.text = saleableItem!!.name

        binding.fragmentAddOrderItemCancel.setOnClickListener { delegate.cancel() }
        binding.fragmentAddOrderItemAdd.setOnClickListener(this)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPosAddOrderItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getTitle(): String {
        return getString(R.string.order_item)
    }

    override fun onClick(view: View?) {
        val validator = DefaultValidator(binding.fragmentAddOrderItemQuantity)

        if (!validator.isValid)
            return

        val quantity = BigDecimal(TextInputLayoutUtil.getInpuText(binding.fragmentAddOrderItemQuantity))
        val total = quantity.multiply(BigDecimal(saleableItem!!.salePrice))
        val saleItem = SaleItemDTO(saleableItem, quantity, total, BigDecimal.ZERO)

        delegate.addSaleItem(saleItem)
    }
}
