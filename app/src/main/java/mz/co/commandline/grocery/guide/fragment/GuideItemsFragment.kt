package mz.co.commandline.grocery.guide.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentGuideItemsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.guide.delegate.GuideDelegate
import mz.co.commandline.grocery.rent.adapter.GuideItemAdapter
import mz.co.commandline.grocery.rent.delegate.RentDelegate


class GuideItemsFragment : BaseFragment(), View.OnClickListener {

    private var _binding: FragmentGuideItemsBinding? = null
    private val binding get() = _binding!!

    private var delegate: GuideDelegate? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_guide_items
    }

    override fun onCreateView() {
        delegate = activity as GuideDelegate
        val guideDTO = delegate?.guideDTO;

        binding.guideItemIssueDate.text = guideDTO?.issueDate
        binding.guideItemCode.text = guideDTO?.code
        binding.guideItemType.text = getString(guideDTO?.type!!.value)

        val guideItemRecyclerView = binding.guideItemRecyclerView
        guideItemRecyclerView.adapter = GuideItemAdapter(activity, guideDTO.guideItemsDTO)
        guideItemRecyclerView.addItemDecoration(DividerItemDecoration(guideItemRecyclerView.context, DividerItemDecoration.VERTICAL))

        binding.reIssueGuideBtn.setOnClickListener(this)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentGuideItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.guide_details)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClick(view: View?) {
        delegate?.reIssueGuide()
    }
}