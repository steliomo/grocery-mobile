package mz.co.commandline.grocery.sale.holder

import android.view.View
import mz.co.commandline.grocery.databinding.SaleBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.sale.dto.SaleDTO
import mz.co.commandline.grocery.util.FormatterUtil

class SaleViewHolder(private val binding: SaleBinding) : BaseViewHolder<SaleDTO>(binding) {

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

        binding.saleDate.text = saleDTO?.saleDate
        binding.saleTotal.text = FormatterUtil.mtFormat(saleDTO?.total)
        binding.saleStatus.text = saleDTO?.saleStatus?.label
        binding.saleDeliveryStatus.text = saleDTO?.deliveryStatus?.label
    }
}