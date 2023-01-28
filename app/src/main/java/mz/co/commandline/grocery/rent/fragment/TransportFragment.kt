package mz.co.commandline.grocery.rent.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentTransportBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.generics.listner.ClickListner
import mz.co.commandline.grocery.rent.adapter.TransportAdapter
import mz.co.commandline.grocery.rent.delegate.RentDelegate
import mz.co.commandline.grocery.rent.dto.RentItemDTO

class TransportFragment : BaseFragment(), ClickListner<RentItemDTO>, View.OnClickListener {

    private var _binding: FragmentTransportBinding? = null
    private val binding get() = _binding!!

    private var delegate: RentDelegate? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_transport
    }

    override fun onCreateView() {
        delegate = activity as RentDelegate

        val transportRecyclerView = binding.transportRecyclerView
        val adapter = TransportAdapter(activity, delegate!!.rent.rentItemsDTO)
        adapter?.setItemClickListner(this)

        transportRecyclerView.adapter = adapter
        transportRecyclerView.addItemDecoration(DividerItemDecoration(transportRecyclerView.context, DividerItemDecoration.VERTICAL))

        binding.processBtn.setOnClickListener(this)
    }

    override fun getTitle(): String {
        return getString(R.string.transport_guide)
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentTransportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        delegate = null
    }

    override fun onClickListner(rentItemDTO: RentItemDTO?) {
        delegate?.selectedRentItem(rentItemDTO)
    }

    override fun onClick(view: View?) {
        delegate?.issueTransportGuide()
    }
}