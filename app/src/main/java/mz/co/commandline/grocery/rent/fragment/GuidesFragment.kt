package mz.co.commandline.grocery.rent.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentGuidesBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.adapter.GuideAdapter
import mz.co.commandline.grocery.rent.delegate.RentDelegate
import mz.co.commandline.grocery.rent.dto.GuideDTO


class GuidesFragment : BaseFragment(), ClickListner<GuideDTO> {

    private var _binding: FragmentGuidesBinding? = null
    private val binding get() = _binding!!

    private var delegate: RentDelegate? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_guides
    }

    override fun onCreateView() {
        delegate = activity as RentDelegate
        var adapter = GuideAdapter(activity, delegate?.rent!!.guidesDTO)
        adapter.setItemClickListner(this)

        val guidesRecyclerView = binding.guidesRecyclerView
        guidesRecyclerView.adapter = adapter
        guidesRecyclerView.addItemDecoration(DividerItemDecoration(guidesRecyclerView.context, DividerItemDecoration.VERTICAL))
    }

    override fun getTitle(): String {
        return getString(R.string.guides)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentGuidesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onClickListner(guideDTO: GuideDTO?) {
        delegate?.selectedGuide(guideDTO);
    }
}
