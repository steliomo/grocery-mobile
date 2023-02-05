package mz.co.commandline.grocery.rent.holder

import android.view.View
import mz.co.commandline.grocery.databinding.GuideBinding
import mz.co.commandline.grocery.generics.holder.BaseViewHolder
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.dto.GuideDTO

class GuideViewHolder(private val binding: GuideBinding) : BaseViewHolder<GuideDTO>(binding) {

    private var listner: ClickListner<GuideDTO>? = null

    private var guideDTO: GuideDTO? = null

    override fun setItemClickListner(listner: ClickListner<*>?) {
        this.listner = listner as ClickListner<GuideDTO>
    }

    override fun onClick(view: View?) {
        listner?.onClickListner(guideDTO)
    }

    override fun onLongClick(p0: View?): Boolean {
        return false
    }

    override fun bind(guide: GuideDTO?) {
        guideDTO = guide
        binding.guideIssueDate.text = guide?.issueDate
        binding.guideCode.text = guide?.code
        binding.guideType.text = binding.guideType.context.getString(guide?.type?.value!!)
    }
}
