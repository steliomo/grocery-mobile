package mz.co.commandline.grocery.quotation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.QuotationItemBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.quotation.dto.QuotationItemDTO
import mz.co.commandline.grocery.quotation.holder.ItemViewHolder
import mz.co.commandline.grocery.quotation.holder.QuotationItemViewHolder

class ItemAdapter(private val context: Context?, private val quotationItemsDTO: List<QuotationItemDTO>) : BaseAdapter<ItemViewHolder>() {
    override fun setItemClickListner(listner: ClickListner<*>?) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(QuotationItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return quotationItemsDTO.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val quotationItemDTO = quotationItemsDTO[position]
        holder.bind(quotationItemDTO)
    }
}