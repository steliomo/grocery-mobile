package mz.co.commandline.grocery.rent.holder

import android.view.View
import mz.co.commandline.grocery.databinding.TransportItemBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.dto.RentItemDTO

class TransportViewHolder(private val binding: TransportItemBinding) : BaseViewHolder<RentItemDTO>(binding) {

    private var listner: ClickListner<RentItemDTO>? = null
    private var rentItemDTO: RentItemDTO? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<RentItemDTO>
    }

    override fun onClick(view: View?) {
        listner?.onClickListner(rentItemDTO)
    }

    override fun onLongClick(view: View?): Boolean {
        return false
    }

    override fun bind(rentItemDTO: RentItemDTO?) {
        this.rentItemDTO = rentItemDTO
        binding.transportItemName.text = rentItemDTO?.name
        binding.transportEstimated.text = rentItemDTO?.plannedQuantity.toString()
        binding.transportToLoad.text = rentItemDTO?.quantity.toString()
        binding.transportSelectedImageView.visibility = rentItemDTO!!.visiblity
    }
}