package mz.co.commandline.grocery.rent.holder

import android.view.View
import mz.co.commandline.grocery.databinding.ReturnItemBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.dto.RentItemDTO

class DevolutionViewHolder(private val binding: ReturnItemBinding) : BaseViewHolder<RentItemDTO>(binding) {

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

    override fun bind(rentItemDTO: RentItemDTO) {
        this.rentItemDTO = rentItemDTO
        binding.retunrItemName.text = rentItemDTO.name
        binding.returnLoaded.text = rentItemDTO.loadedQuantity.toString()
        binding.toReturn.text = rentItemDTO.quantity.toString()
        binding.returnSelectedImageView.visibility = rentItemDTO.visiblity
    }
}