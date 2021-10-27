package mz.co.commandline.grocery.report.holder

import android.view.View
import mz.co.commandline.grocery.databinding.CostItemBinding
import mz.co.commandline.grocery.expense.dto.ExpenseReport
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.util.FormatterUtil

class CostViewHolder(private val binding: CostItemBinding) : BaseViewHolder<ExpenseReport>(binding) {

    override fun setItemClickListner(listner: ClickListner<*>?) {
    }

    override fun onClick(view: View?) {
    }

    override fun onLongClick(view: View?): Boolean {
        return false
    }

    override fun bind(expenseReport: ExpenseReport?) {
        binding.costName.text = expenseReport!!.name
        binding.costValue.text = FormatterUtil.mtFormat(expenseReport!!.expenseValue)
    }
}