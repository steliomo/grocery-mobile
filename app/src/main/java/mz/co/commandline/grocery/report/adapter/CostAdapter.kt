package mz.co.commandline.grocery.report.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.CostItemBinding
import mz.co.commandline.grocery.expense.dto.ExpenseDTO
import mz.co.commandline.grocery.expense.dto.ExpenseReport
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.report.holder.CostViewHolder

class CostAdapter(private val context: Context?, private val expensesDTO: List<ExpenseReport>) : BaseAdapter<CostViewHolder>() {

    override fun setItemClickListner(listner: ClickListner<*>?) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CostViewHolder {
        return CostViewHolder(CostItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return expensesDTO.size
    }

    override fun onBindViewHolder(holder: CostViewHolder, position: Int) {
        holder.bind(expensesDTO[position])
    }
}