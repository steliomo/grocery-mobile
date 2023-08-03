package mz.co.commandline.grocery.quotation.holder

import android.view.View
import mz.co.commandline.grocery.databinding.QuotationBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.quotation.dto.QuotationDTO
import mz.co.commandline.grocery.util.FormatterUtil

class QuotationViewHolder(private val binding: QuotationBinding) : BaseViewHolder<QuotationDTO>(binding) {

    private var listner: ClickListner<QuotationDTO>? = null
    private var quotation: QuotationDTO? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<QuotationDTO>
    }

    override fun onClick(view: View?) {
        listner?.onClickListner(quotation)
    }

    override fun onLongClick(p0: View?): Boolean {
        return false
    }

    override fun bind(quotation: QuotationDTO?) {
        this.quotation = quotation
        binding.quotationDate.text = quotation!!.issueDate
        binding.quotationCode.text = quotation.code
        binding.quotationDiscount.text = FormatterUtil.mtFormat(quotation.discount)
        binding.quotationTotalValue.text = FormatterUtil.mtFormat(quotation.totalValue)
    }
}