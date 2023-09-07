package mz.co.commandline.grocery.pos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.OrderBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.generics.listner.LongClickListner
import mz.co.commandline.grocery.pos.holder.PosSaleItemViewHolder
import mz.co.commandline.grocery.sale.dto.SaleItemDTO

class PosSaleItemAdapter(private val context: Context?, private val saleItems: List<SaleItemDTO>) : BaseAdapter<PosSaleItemViewHolder>() {

    private var listner: ClickListner<SaleItemDTO>? = null

    private var longClickListner: LongClickListner<SaleItemDTO>? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<SaleItemDTO>
    }

    override fun setItemLongClickListner(listner: LongClickListner<*>?) {
        longClickListner = listner as LongClickListner<SaleItemDTO>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosSaleItemViewHolder {
        return PosSaleItemViewHolder(OrderBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return saleItems.size
    }

    override fun onBindViewHolder(holder: PosSaleItemViewHolder, position: Int) {
        val saleItem = saleItems[position]
        holder.setItemClickListner(listner)
        holder.setItemLongClickListner(longClickListner)
        holder.bind(saleItem)
    }
}