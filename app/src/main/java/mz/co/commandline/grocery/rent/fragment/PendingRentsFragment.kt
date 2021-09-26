package mz.co.commandline.grocery.rent.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentPendingRentsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.adapter.RentAdapter
import mz.co.commandline.grocery.rent.delegate.RentDelegate
import mz.co.commandline.grocery.rent.model.RentDTO

class PendingRentsFragment : BaseFragment(), ClickListner<RentDTO> {

    private var _binding: FragmentPendingRentsBinding? = null
    private val binding get() = _binding!!

    private var _delegate: RentDelegate? = null
    private val delegate get() = _delegate!!

    override fun getResourceId(): Int {
        return R.layout.fragment_pending_rents
    }

    override fun onCreateView() {
        _delegate = activity as RentDelegate
        val adapter = RentAdapter(activity, delegate.rentsDTO)
        adapter.setItemClickListner(this)

        val pendingRentsRecycleView = binding.pendingRentsRecycleView
        pendingRentsRecycleView.adapter = adapter
        pendingRentsRecycleView.addItemDecoration(DividerItemDecoration(pendingRentsRecycleView.context, DividerItemDecoration.VERTICAL))
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentPendingRentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.pending_rents)
    }

    override fun onClickListner(rentDTO: RentDTO) {
        delegate.selectedPendingRent(rentDTO)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}