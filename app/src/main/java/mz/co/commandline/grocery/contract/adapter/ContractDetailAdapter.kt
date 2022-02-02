package mz.co.commandline.grocery.contract.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.contract.dto.ContractDTO
import mz.co.commandline.grocery.contract.holder.ContractDetailHolder
import mz.co.commandline.grocery.databinding.ContractDetailBinding
import mz.co.commandline.grocery.generics.listner.ClickListner

class ContractDetailAdapter(private val context: Context?, private val contracts: List<ContractDTO>) : BaseAdapter<ContractDetailHolder>() {

    private lateinit var listener: ClickListner<ContractDTO>

    override fun setItemClickListner(clickListner: ClickListner<*>?) {
        this.listener = clickListner as ClickListner<ContractDTO>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContractDetailHolder {
        return ContractDetailHolder(ContractDetailBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return contracts.size
    }

    override fun onBindViewHolder(holder: ContractDetailHolder, position: Int) {
        val contractDTO = contracts[position]
        holder.setItemClickListner(listener)
        holder.bind(contractDTO)
    }
}