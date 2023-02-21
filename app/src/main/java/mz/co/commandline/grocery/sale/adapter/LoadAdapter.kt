package mz.co.commandline.grocery.sale.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.LoadItemBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.sale.dto.SaleDTO
import mz.co.commandline.grocery.sale.dto.SaleItemDTO
import mz.co.commandline.grocery.sale.holder.LoadViewHolder

class LoadAdapter(private val context: Context?, private val sales: List<SaleItemDTO>) : BaseAdapter<LoadViewHolder>() {

    private var listner: ClickListner<SaleDTO>? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<SaleDTO>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadViewHolder {
        return LoadViewHolder(LoadItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return sales.size
    }

    override fun onBindViewHolder(holder: LoadViewHolder, position: Int) {
        val saleItemDTO = sales[position];
        holder.setItemClickListner(listner)
        holder.bind(saleItemDTO)
    }
}
