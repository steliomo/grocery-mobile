package mz.co.commandline.grocery.rent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.RentPaymentBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.dto.RentPaymentDTO
import mz.co.commandline.grocery.rent.holder.RentPaymentViewHolder

class RentPaymentAdapter(private val context: Context?, private val rentPaymentsDTO: List<RentPaymentDTO>) : BaseAdapter<RentPaymentViewHolder>() {

    override fun setItemClickListner(listner: ClickListner<*>?) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentPaymentViewHolder {
        return RentPaymentViewHolder(RentPaymentBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return rentPaymentsDTO.size
    }

    override fun onBindViewHolder(holder: RentPaymentViewHolder, position: Int) {
        val rentPaymentDTO = rentPaymentsDTO[position]
        holder.bind(rentPaymentDTO)
    }
}
