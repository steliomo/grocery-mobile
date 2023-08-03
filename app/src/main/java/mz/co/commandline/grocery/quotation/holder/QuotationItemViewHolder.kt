package mz.co.commandline.grocery.quotation.holder

import android.view.View
import mz.co.commandline.grocery.databinding.RentItemBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.quotation.dto.QuotationItemDTO
import mz.co.commandline.grocery.util.FormatterUtil

class QuotationItemViewHolder(private val binding: RentItemBinding) : BaseViewHolder<QuotationItemDTO>(binding) {

    override fun setItemClickListner(listner: ClickListner<*>?) {
    }

    override fun onClick(p0: View?) {
    }

    override fun onLongClick(p0: View?): Boolean {
        return false
    }

    override fun bind(quotationItemDTO: QuotationItemDTO?) {
        binding.rentItemImageView.setImageResource(quotationItemDTO!!.icon)
        binding.rentItemName.text = quotationItemDTO?.getName()
        binding.rentItemQuantity.text = quotationItemDTO?.quantity.toString()
        binding.rentItemDays.text = quotationItemDTO?.days.toString()
        binding.rentItemValue.text = FormatterUtil.mtFormat(quotationItemDTO?.getValue())
    }
}