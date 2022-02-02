package mz.co.commandline.grocery.rent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.RentBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.holder.RentViewHolder
import mz.co.commandline.grocery.rent.dto.RentDTO

class RentAdapter(private val context: Context?, private val rents: List<RentDTO>) : BaseAdapter<RentViewHolder>() {

    private var listner: ClickListner<RentDTO>? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<RentDTO>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RentViewHolder {
        return RentViewHolder(RentBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return rents.size
    }

    override fun onBindViewHolder(holder: RentViewHolder, position: Int) {
        val rentDTO: RentDTO = rents[position]
        holder.setItemClickListner(listner)
        holder.bind(rentDTO)
    }
}