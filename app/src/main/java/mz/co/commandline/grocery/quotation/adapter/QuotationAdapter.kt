package mz.co.commandline.grocery.quotation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.QuotationBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.quotation.dto.QuotationDTO
import mz.co.commandline.grocery.quotation.holder.QuotationViewHolder

class QuotationAdapter(private val context: Context?, private val quotations: List<QuotationDTO>) : BaseAdapter<QuotationViewHolder>() {

    private var listner: ClickListner<QuotationDTO>? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<QuotationDTO>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotationViewHolder {
        return QuotationViewHolder(QuotationBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return quotations.size
    }

    override fun onBindViewHolder(holder: QuotationViewHolder, position: Int) {
        val quotationDTO = quotations[position]
        holder.setItemClickListner(listner)
        holder.bind(quotationDTO)
    }
}