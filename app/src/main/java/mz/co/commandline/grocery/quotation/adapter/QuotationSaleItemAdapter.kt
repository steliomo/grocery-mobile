package mz.co.commandline.grocery.quotation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.QuotationSaleItemBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.quotation.dto.QuotationItemDTO
import mz.co.commandline.grocery.quotation.holder.QuotationItemViewHolder
import mz.co.commandline.grocery.quotation.holder.QuotationSaleItemViewHolder

class QuotationSaleItemAdapter(private val context: Context?, private val items: List<QuotationItemDTO>) : BaseAdapter<QuotationSaleItemViewHolder>() {

    override fun setItemClickListner(listner: ClickListner<*>?) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotationSaleItemViewHolder {
        return QuotationSaleItemViewHolder(QuotationSaleItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: QuotationSaleItemViewHolder, position: Int) {
        val quotationItemDTO = items[position]
        holder.bind(quotationItemDTO)
    }
}