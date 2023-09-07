package mz.co.commandline.grocery.pos.holder

import android.view.View
import mz.co.commandline.grocery.databinding.OrderBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.generics.listner.LongClickListner
import mz.co.commandline.grocery.sale.dto.SaleItemDTO
import mz.co.commandline.grocery.util.FormatterUtil

class PosSaleItemViewHolder(private val binding: OrderBinding) : BaseViewHolder<SaleItemDTO>(binding) {

    private var clickListner: ClickListner<SaleItemDTO>? = null

    private var longClickListner: LongClickListner<SaleItemDTO>? = null

    private var saleItem: SaleItemDTO? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.clickListner = listner as ClickListner<SaleItemDTO>?
    }

    override fun setItemLongClickListner(listner: LongClickListner<*>?) {
        this.longClickListner = listner as LongClickListner<SaleItemDTO>?
    }

    override fun onClick(view: View?) {
        clickListner?.onClickListner(saleItem)
    }

    override fun onLongClick(view: View?): Boolean {
        longClickListner?.onLongClickListner(saleItem)
        return true
    }

    override fun bind(saleItemDTO: SaleItemDTO?) {
        this.saleItem = saleItemDTO
        binding.orderSelectedImageView.visibility = saleItem!!.visiblity
        binding.orderName.text = saleItem!!.saleableItemDTO.name
        binding.orderQuantity.text = saleItem!!.quantity.toString()
        binding.orderTotal.text = FormatterUtil.mtFormat(saleItem!!.total)
    }
}