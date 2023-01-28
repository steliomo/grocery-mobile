package mz.co.commandline.grocery.rent.holder

import android.view.View
import mz.co.commandline.grocery.databinding.RentBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.dto.RentDTO
import mz.co.commandline.grocery.util.FormatterUtil

class RentViewHolder(private val binding: RentBinding) : BaseViewHolder<RentDTO>(binding) {

    private var listner: ClickListner<RentDTO>? = null

    private var rentDTO: RentDTO? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<RentDTO>
    }

    override fun onClick(view: View?) {
        listner?.onClickListner(rentDTO)
    }

    override fun onLongClick(view: View?): Boolean {
        return false
    }

    override fun bind(rentDTO: RentDTO) {
        this.rentDTO = rentDTO
        binding.rentDate.text = rentDTO.rentDate
        binding.rentTotalPaid.text = FormatterUtil.mtFormat(rentDTO.totalPaid)
        binding.rentTotaToPay.text = FormatterUtil.mtFormat(rentDTO.totalToPay)
        binding.rentPaymentStatus.text = rentDTO.paymentStatus.label
    }
}