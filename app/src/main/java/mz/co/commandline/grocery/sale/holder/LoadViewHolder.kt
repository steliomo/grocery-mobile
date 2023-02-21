package mz.co.commandline.grocery.sale.holder

import android.view.View
import mz.co.commandline.grocery.databinding.LoadItemBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.sale.dto.SaleItemDTO

class LoadViewHolder(private val binding: LoadItemBinding) : BaseViewHolder<SaleItemDTO>(binding) {

    private var listner: ClickListner<SaleItemDTO>? = null

    private var saleItemDTO: SaleItemDTO? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<SaleItemDTO>
    }

    override fun onClick(view: View?) {
        listner?.onClickListner(saleItemDTO)
    }

    override fun onLongClick(p0: View?): Boolean {
        return false
    }

    override fun bind(saleItemDTO: SaleItemDTO?) {
        this.saleItemDTO = saleItemDTO;
        binding.loadItemName.text = saleItemDTO?.saleableItemDTO?.name
        binding.loadDelivered.text = saleItemDTO?.deliveredQuantity.toString()
        binding.toDelivery.text = saleItemDTO?.toDeliveryQuantity.toString()
        binding.loadSelectedImageView.visibility = saleItemDTO!!.visiblity
    }
}
