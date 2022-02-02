package mz.co.commandline.grocery.inventory.holder

import android.view.View
import mz.co.commandline.grocery.databinding.StockAnalysisItemBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.saleable.dto.StockDTO

class StockAnalysisViewHolder(private val binding: StockAnalysisItemBinding) : BaseViewHolder<StockDTO>(binding) {
    private lateinit var listner: ClickListner<StockDTO>

    private lateinit var stockDTO: StockDTO

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<StockDTO>
    }

    override fun onClick(view: View?) {
        listner.onClickListner(stockDTO);
    }

    override fun onLongClick(view: View?): Boolean {
        return false;
    }

    override fun bind(stockDTO: StockDTO) {
        this.stockDTO = stockDTO
        binding.stockAnalysisOrder.text = stockDTO.position;
        binding.stockAnalysisName.text = stockDTO.productDescriptionDTO.name
        binding.stockAnalysisDiscrepancy.text = stockDTO.discrepancy().toString()
    }
}