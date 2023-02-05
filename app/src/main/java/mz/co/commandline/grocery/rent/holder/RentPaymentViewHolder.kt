package mz.co.commandline.grocery.rent.holder

import android.view.View
import mz.co.commandline.grocery.databinding.RentPaymentBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.dto.RentPaymentDTO
import mz.co.commandline.grocery.util.FormatterUtil

class RentPaymentViewHolder(private val binding: RentPaymentBinding) : BaseViewHolder<RentPaymentDTO>(binding) {

    override fun setItemClickListner(listner: ClickListner<*>?) {
    }

    override fun onClick(p0: View?) {
    }

    override fun onLongClick(p0: View?): Boolean {
        return false
    }

    override fun bind(rentPaymentDTO: RentPaymentDTO?) {
        binding.rentPaymentsDate.text = rentPaymentDTO?.paymentDate
        binding.rentPaymentsPaid.text = FormatterUtil.mtFormat(rentPaymentDTO?.paymentValue)
    }
}
