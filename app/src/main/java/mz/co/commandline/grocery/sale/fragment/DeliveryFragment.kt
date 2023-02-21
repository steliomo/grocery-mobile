package mz.co.commandline.grocery.sale.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.android.synthetic.main.sale.*
import mz.co.commandline.grocery.R
import mz.co.commandline.grocery.databinding.FragmentDeliveryBinding
import mz.co.commandline.grocery.generics.fragment.BaseFragment
import mz.co.commandline.grocery.sale.adapter.LoadAdapter
import mz.co.commandline.grocery.sale.delegate.SaleDelegate
import mz.co.commandline.grocery.sale.dto.SaleItemDTO

class DeliveryFragment : BaseFragment() {

    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!

    private var delegate: SaleDelegate? = null

    override fun getResourceId(): Int {
        return R.layout.fragment_delivery
    }

    override fun onCreateView() {
        delegate = activity as SaleDelegate

        var adapter = LoadAdapter(activity, delegate?.sale!!.items)
        val deliveryRecyclerView = binding.deliveryRecyclerView
        adapter.setItemClickListner { saleItemDTO -> delegate?.selectedSaleItemDTO(saleItemDTO as SaleItemDTO) }
        deliveryRecyclerView.adapter = adapter
        deliveryRecyclerView.addItemDecoration(DividerItemDecoration(deliveryRecyclerView.context, DividerItemDecoration.VERTICAL))

        binding.deliveryBtn.setOnClickListener { delegate?.issueDeliveryGuide() }
    }

    override fun getView(inflater: LayoutInflater, container: ViewGroup?): View {
        _binding = FragmentDeliveryBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun getTitle(): String {
        return getString(R.string.delivery_guide)
    }
}