package mz.co.commandline.grocery.inventory.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.StockAnalysisItemBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.inventory.holder.StockAnalysisViewHolder
import mz.co.commandline.grocery.saleable.dto.StockDTO

class StockAnalysisAdapter(private val context: Context?, private val stocksDTO: List<StockDTO>) : BaseAdapter<StockAnalysisViewHolder>() {

    private lateinit var listner: ClickListner<StockDTO>

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<StockDTO>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockAnalysisViewHolder {
        return StockAnalysisViewHolder(StockAnalysisItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return stocksDTO.size
    }

    override fun onBindViewHolder(holder: StockAnalysisViewHolder, position: Int) {
        val stockDTO = stocksDTO[position]
        holder.setItemClickListner(listner)
        stockDTO.setPosition(position)
        holder.bind(stockDTO)
    }
}