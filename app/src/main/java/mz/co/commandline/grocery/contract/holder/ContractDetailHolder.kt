package mz.co.commandline.grocery.contract.holder

import android.view.View
import mz.co.commandline.grocery.contract.dto.ContractDTO
import mz.co.commandline.grocery.databinding.ContractDetailBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.util.FormatterUtil

class ContractDetailHolder(private val binding: ContractDetailBinding) : BaseViewHolder<ContractDTO>(binding) {

    private lateinit var listner: ClickListner<ContractDTO>

    private lateinit var contractDTO: ContractDTO

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<ContractDTO>
    }

    override fun onClick(view: View?) {
        listner.onClickListner(contractDTO)
    }

    override fun onLongClick(view: View?): Boolean {
        return false
    }

    override fun bind(contractDTO: ContractDTO) {
        this.contractDTO = contractDTO
        binding.contractType.text = contractDTO.contractType?.label
        binding.contractStartDate.text = contractDTO.startDate
        binding.contractEndDate.text = contractDTO.endDate
        binding.contractMonthlyPayment.text = FormatterUtil.mtFormat(contractDTO.monthlyPayment)
    }
}