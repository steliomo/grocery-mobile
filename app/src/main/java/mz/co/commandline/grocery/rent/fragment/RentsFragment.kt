package mz.co.commandline.grocery.rent.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentRentsBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.adapter.RentAdapter
import mz.co.commandline.grocery.rent.delegate.RentDelegate
import mz.co.commandline.grocery.rent.dto.RentDTO

class RentsFragment : BaseFragment(), ClickListner<RentDTO> {

    private var _binding: FragmentRentsBinding? = null
    private val binding get() = _binding!!

    private var _delegate: RentDelegate? = null
    private val delegate get() = _delegate!!

    override fun getResourceId(): Int {
        return R.layout.fragment_rents
    }

    override fun onCreateView() {
        _delegate = activity as RentDelegate
        val adapter = RentAdapter(activity, delegate.rentsDTO)
        adapter.setItemClickListner(this)

        val rentsRecycleView = binding.rentsRecycleView
        rentsRecycleView.adapter = adapter
        rentsRecycleView.addItemDecoration(DividerItemDecoration(rentsRecycleView.context, DividerItemDecoration.VERTICAL))
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentRentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String {
        return getString(R.string.rents)
    }

    override fun onClickListner(rentDTO: RentDTO) {
        delegate.selectedRent(rentDTO)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}