package mz.co.commandline.grocery.rent.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import mz.co.commandline.grocery.adapter.BaseAdapter
import mz.co.commandline.grocery.databinding.GuideItemBinding
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.dto.GuideItemDTO
import mz.co.commandline.grocery.guide.holder.GuideItemViewHolder

class GuideItemAdapter(private val context: Context?, private val guideItemsDTO: List<GuideItemDTO>) : BaseAdapter<GuideItemViewHolder>() {
    override fun setItemClickListner(listner: ClickListner<*>?) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideItemViewHolder {
        return GuideItemViewHolder(GuideItemBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return guideItemsDTO.size
    }

    override fun onBindViewHolder(holder: GuideItemViewHolder, position: Int) {
        val guideItemDTO = guideItemsDTO[position]
        holder.bind(guideItemDTO)
    }
}
