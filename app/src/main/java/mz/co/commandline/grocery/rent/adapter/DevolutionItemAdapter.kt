package mz.co.commandline.grocery.rent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.ReturnItemBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.holder.DevolutionViewHolder
import mz.co.commandline.grocery.rent.dto.RentItemDTO

class DevolutionItemAdapter(private val context: Context?, private val rentItems: List<RentItemDTO>) : BaseAdapter<DevolutionViewHolder>() {

    private var listner: ClickListner<RentItemDTO>? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<RentItemDTO>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevolutionViewHolder {
        return DevolutionViewHolder(ReturnItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return rentItems.size
    }

    override fun onBindViewHolder(holder: DevolutionViewHolder, position: Int) {
        val rentItemDTO = rentItems[position]
        holder.setItemClickListner(listner)
        holder.bind(rentItemDTO)
    }
}