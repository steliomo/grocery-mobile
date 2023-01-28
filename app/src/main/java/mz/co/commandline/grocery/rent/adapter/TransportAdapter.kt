package mz.co.commandline.grocery.rent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.TransportItemBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.dto.RentItemDTO
import mz.co.commandline.grocery.rent.holder.TransportViewHolder

class TransportAdapter(private val context: Context?, private val rentItems: List<RentItemDTO>) : BaseAdapter<TransportViewHolder>() {

    private var listner: ClickListner<RentItemDTO>? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<RentItemDTO>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransportViewHolder {
        return TransportViewHolder(TransportItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return rentItems.size
    }

    override fun onBindViewHolder(holder: TransportViewHolder, position: Int) {
        val rentItemDTO = rentItems[position]
        holder.setItemClickListner(listner)
        holder.bind(rentItemDTO)
    }
}