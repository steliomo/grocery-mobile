package mz.co.commandline.grocery.quotation.holder

import android.view.View
import mz.co.commandline.grocery.databinding.QuotationSaleItemBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.quotation.dto.QuotationItemDTO
import mz.co.commandline.grocery.util.FormatterUtil

class QuotationSaleItemViewHolder(private val binding: QuotationSaleItemBinding) : BaseViewHolder<QuotationItemDTO>(binding) {
    override fun setItemClickListner(listner: ClickListner<*>?) {
    }

    override fun onClick(view: View?) {
    }

    override fun onLongClick(view: View?): Boolean {
        return false
    }

    override fun bind(quotationItem: QuotationItemDTO?) {
        binding.quotationSaleItemImageView.setImageResource(quotationItem!!.icon)
        binding.quotationSaleItemName.text = quotationItem.getName()
        binding.quotationSaleItemQuantity.text = quotationItem.quantity.toString()
        binding.quotationSaleItemValue.text = FormatterUtil.mtFormat(quotationItem.getValue())
    }
}
