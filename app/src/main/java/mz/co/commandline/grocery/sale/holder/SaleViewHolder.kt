package mz.co.commandline.grocery.sale.holder

import android.view.View
import mz.co.commandline.grocery.databinding.SalePaymentBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.sale.dto.SaleDTO
import mz.co.commandline.grocery.util.DateUtil
import mz.co.commandline.grocery.util.FormatterUtil

class SaleViewHolder(private val binding: SalePaymentBinding) : BaseViewHolder<SaleDTO>(binding) {

    private var listner: ClickListner<SaleDTO>? = null

    private var saleDTO: SaleDTO? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<SaleDTO>
    }

    override fun onClick(view: View?) {
        listner?.onClickListner(saleDTO)
    }

    override fun onLongClick(view: View?): Boolean {
        return false
    }

    override fun bind(saleDTO: SaleDTO?) {
        this.saleDTO = saleDTO
        binding.salePaymentTotalSaleValue.text = FormatterUtil.mtFormat(saleDTO?.total)
        binding.salePaymentDateValue.text = saleDTO?.saleDate
        binding.salePaymentStatusValue.text = saleDTO?.saleStatus?.label
    }
}