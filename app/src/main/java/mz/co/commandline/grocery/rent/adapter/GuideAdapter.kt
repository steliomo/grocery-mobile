package mz.co.commandline.grocery.rent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.GuideBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.dto.GuideDTO
import mz.co.commandline.grocery.rent.holder.GuideViewHolder

class GuideAdapter(private val context: Context?, private val guidesDTO: List<GuideDTO>) : BaseAdapter<GuideViewHolder>() {

    private var listner: ClickListner<GuideDTO>? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<GuideDTO>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideViewHolder {
        return GuideViewHolder(GuideBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return guidesDTO.size
    }

    override fun onBindViewHolder(holder: GuideViewHolder, position: Int) {
        val guideDTO = guidesDTO[position]
        holder.setItemClickListner(listner)
        holder.bind(guideDTO)
    }

}
