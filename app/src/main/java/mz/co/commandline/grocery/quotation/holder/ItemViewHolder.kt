package mz.co.commandline.grocery.quotation.holder

import android.view.View
import mz.co.commandline.grocery.databinding.QuotationItemBinding
import mz.co.commandline.grocery.databinding.RentItemBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.quotation.dto.QuotationItemDTO
import mz.co.commandline.grocery.util.FormatterUtil

class ItemViewHolder(private val binding: QuotationItemBinding) : BaseViewHolder<QuotationItemDTO>(binding) {

    override fun setItemClickListner(listner: ClickListner<*>?) {
    }

    override fun onClick(p0: View?) {
    }

    override fun onLongClick(p0: View?): Boolean {
        return false
    }

    override fun bind(quotationItemDTO: QuotationItemDTO?) {
        binding.quotationItemName.text = quotationItemDTO?.getName()
        binding.quotationItemQuantity.text = quotationItemDTO?.quantity.toString()
    }
}