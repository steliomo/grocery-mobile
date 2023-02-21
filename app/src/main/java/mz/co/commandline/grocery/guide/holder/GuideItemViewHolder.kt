package mz.co.commandline.grocery.guide.holder

import android.view.View
import mz.co.commandline.grocery.databinding.GuideItemBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.dto.GuideItemDTO

class GuideItemViewHolder(private val binding: GuideItemBinding) : BaseViewHolder<GuideItemDTO>(binding) {

    private var guideItemDTO: GuideItemDTO? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
    }

    override fun onClick(view: View?) {
    }

    override fun onLongClick(p0: View?): Boolean {
        return false
    }

    override fun bind(guideItemDTO: GuideItemDTO?) {
        this.guideItemDTO = guideItemDTO
        binding.guideItemName.text = guideItemDTO?.name
        binding.guideItemQuantity.text = guideItemDTO?.quantity?.toString()
    }
}
