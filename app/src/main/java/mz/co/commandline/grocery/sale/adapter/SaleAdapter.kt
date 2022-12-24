package mz.co.commandline.grocery.sale.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.SalePaymentBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.sale.dto.SaleDTO
import mz.co.commandline.grocery.sale.holder.SaleViewHolder

class SaleAdapter(private val context: Context?, private val sales: List<SaleDTO>) : BaseAdapter<SaleViewHolder>() {

    private var listner: ClickListner<SaleDTO>? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<SaleDTO>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleViewHolder {
        return SaleViewHolder(SalePaymentBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return sales.size
    }

    override fun onBindViewHolder(holder: SaleViewHolder, position: Int) {
        val saleDTO = sales[position]
        holder.setItemClickListner(listner)
        holder.bind(saleDTO)
    }
}